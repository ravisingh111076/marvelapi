package com.ravi.marvel.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Setter;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import java.util.Map;

@Configuration
@ConfigurationProperties("redis")
public class CacheConfiguration extends CachingConfigurerSupport {
    @Setter
    private String host;
    @Setter
    private Integer port;
    @Setter
    private Long ttl;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        ObjectMapper mapper = redisCacheObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
        return template;
    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(host);
        redisConnectionFactory.setPort(port);
        redisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return redisConnectionFactory;
    }
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(2);
        return jedisPoolConfig;
    }

    @Bean
    @Qualifier("redisCacheObjectMapper")
    public ObjectMapper redisCacheObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(ttl);
        Map<String, Long> expiryMap = ImmutableMap.<String, Long>builder()
                .put("characterCache", ttl)
                .build();
        cacheManager.setExpires(expiryMap);
        return cacheManager;
    }

    //TODO not used wanted atm. Wanted To support multiple caching provider.
    @Bean
    @ConditionalOnProperty("cache.default")
    public CacheManager cacheManager() {
        CacheManager cacheManager = new ConcurrentMapCacheManager("characterCache");
        return cacheManager;
    }

    public CacheResolver cacheResolver() {
        return null;
    }
}
