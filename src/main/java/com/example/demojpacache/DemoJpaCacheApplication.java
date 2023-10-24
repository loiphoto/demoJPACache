package com.example.demojpacache;

import com.example.demojpacache.reflection.BeanContainer;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@AllArgsConstructor
@EnableCaching
@EnableMethodSecurity
public class DemoJpaCacheApplication implements CommandLineRunner {

//    private RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoJpaCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BeanContainer beanContainer = new BeanContainer();
//        redisTemplate.opsForValue().set("olaaa","333");
//        System.out.println("Value of key loda: "+redisTemplate.opsForValue().get("olaaa"));
    }
}
