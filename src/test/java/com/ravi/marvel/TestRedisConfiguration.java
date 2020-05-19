package com.ravi.marvel;
import com.ravi.marvel.cache.CacheConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import redis.embedded.RedisServerBuilder;
import java.io.IOException;

@TestConfiguration
@ContextConfiguration(classes = CacheConfiguration.class)
public class TestRedisConfiguration {
    private static redis.embedded.RedisServer redisServer;
    @BeforeClass
    public static void startRedisServer() throws IOException {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 128M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() throws IOException {
        redisServer.stop();
    }
}
