package com.ravi.marvel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class, properties = {
        "redis.enabled=true","cache.default=false", "marvel.url=http://gateway.marvel.com"
        ,"translator.url=http://translator"
})
public class CacheIntegrationTest {

    @Test
    public void testRedisConfiguration() {
        UUID id = UUID.randomUUID();
    }
}
