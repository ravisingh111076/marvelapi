package com.ravi.marvel.integration.loader;

import com.ravi.marvel.integration.MarvelFeignClient;
import com.ravi.marvel.domain.GetCharacterResponse;
import com.ravi.marvel.security.MarvelKeyProvider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Setter;

@Component
@ConfigurationProperties("marvel")
public class CharacterIdLoader {

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final MarvelFeignClient marvelFeignClient;
    private final MarvelKeyProvider marvelKeyProvider;

    @Setter
    private Integer total;
    @Setter
    private Integer limit;

    public CharacterIdLoader(MarvelFeignClient marvelFeignClient,
                             MarvelKeyProvider marvelKeyProvider) {
        this.marvelFeignClient = marvelFeignClient;
        this.marvelKeyProvider = marvelKeyProvider;
    }

    public List<Long> loadCharacterIds() {

        //no of request calls
        int requestCount = total/limit + 1;
        if((total%limit) > 0) {
            requestCount++;
        }
        List<Supplier<GetCharacterResponse>> tasks = buildAllTasks(limit, requestCount);
        CompletableFuture<GetCharacterResponse>[] futures = tasks.stream()
                .map(task -> CompletableFuture.supplyAsync(task, executorService))
                .toArray(CompletableFuture[]::new);
        CompletableFuture
                .allOf(futures)
                .join();
        executorService.shutdown();
        return Arrays.stream(futures).flatMap(future -> getIds(future).stream()).collect(Collectors.toList());
    }

    private List<Supplier<GetCharacterResponse>> buildAllTasks(Integer limit, Integer requestCount){
        List<Supplier<GetCharacterResponse>> callables = new ArrayList<>();
        IntStream.range(1, requestCount)
                .forEach(value -> callables.add(() ->
                        {
                                Long timeStamp = System.currentTimeMillis();
                            try {
                                return  marvelFeignClient.getMarvelCharacters(
                                        marvelKeyProvider.getPublic_key(),
                                        marvelKeyProvider.getKey(timeStamp),
                                        timeStamp, limit, (value - 1) * limit);
                            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                                noSuchAlgorithmException.printStackTrace();
                                throw new SecurityException("Inappropriate security keys");
                            }
                        }
                ));
        return callables;
    }

    private List<Long> getIds(CompletableFuture<GetCharacterResponse> future) {
        try {
            return future.get().getMarvelResponse().getCharacterList().stream()
                    .map(GetCharacterResponse.Character::getId).collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
