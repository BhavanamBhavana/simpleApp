package com.dockerpractice.simpleApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Controller
public class AppController {
    JedisPoolConfig poolConfig;
    JedisPool jedisPool;
    Jedis jedis;

    @PostConstruct
    public void a() {
        try {
            this.poolConfig = buildPoolConfig();
            this.jedisPool = new JedisPool(poolConfig, "rserver", 6379);
            this.jedis = jedisPool.getResource();
            this.jedis.set("counter", "0");
        } catch (Exception e) {
            System.out.println(" Error msg" + e.getMessage());
            System.out.println(" Error LocalizedMessage" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping("/counter")
    @ResponseBody
    public String helloWorld() {
        int a = Integer.parseInt(jedis.get("counter"));
        a++;
        jedis.set("counter", String.valueOf(a));
        return "hello there !!! ".concat(String.valueOf(a));
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
