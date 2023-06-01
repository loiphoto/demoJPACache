//package com.example.demojpacache.service.impl;
//
//import com.example.demojpacache.Entity.Student;
//import com.example.demojpacache.Entity.User;
//import com.example.demojpacache.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//
//@Service
//public class LockService {
//
//    @Autowired
//    private RedisLockService redisLockService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public <S extends Student> S createOrUpdate(S entity) {
//        String key = redisLockService.generateCacheLockKey("abc", String.valueOf(entity.getCode()));
//        try {
//            if (!redisLockService.tryLock(key, 10, Integer.MAX_VALUE, TimeUnit.MILLISECONDS)) {
//                System.out.println("ko lay dc lock");
//            }
//            if (entity.getId() == null) {
//                userRepository.findFirstByCode(entity.getCode())
//                        .ifPresent(existed -> entity.setId(existed.getId()));
//            }
//            userRepository.saveAndFlush(entity);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            redisLockService.unlock(key);
//        }
//        return entity;
//    }
//}
