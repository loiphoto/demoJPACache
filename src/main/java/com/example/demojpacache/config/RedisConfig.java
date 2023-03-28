//package com.example.demojpacache.config;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
//@AutoConfiguration
//public class RedisConfig {
//    @Value("${redis.host}")
//    private String redisHost;
//
//    @Value("${redis.port}")
//    private int redisPort;
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        // Tạo Standalone Connection tới Redis
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
//    }
//
//    @Bean
//    @Primary
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//    @Bean
//    @Primary
//    public RedisCacheConfiguration defaultCacheConfig(ObjectMapper objectMapper) {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
//    }
//}
