package com.example.demojpacache;

import com.example.demojpacache.reflection.BeanContainer;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@AllArgsConstructor
public class DemoJpaCacheApplication implements CommandLineRunner {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DemoJpaCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BeanContainer beanContainer = new BeanContainer();
    }
}
