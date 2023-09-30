package fun.crimiwar.intellstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Component
public class RedisUtil {

    @Autowired
    JedisPool jedisPool;

    @Bean
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

}
