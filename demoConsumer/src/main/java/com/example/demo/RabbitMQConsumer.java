package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQConsumer {


    //    @RabbitListener(queues = {"${application.rabbitmq.queue1.name}"})
    @RabbitListener(queues = {"queueDirect"})
    public void consume1(String message) throws InterruptedException {
        log.info("consumer 1");
        log.info("----------Sleeep -------------");
        Thread.sleep(1000 * 15);
        log.info(String.format("Queue 1 Received message -> %s", message));
    }

    //    @RabbitListener(queues = {"${application.rabbitmq.queue2.name}"})
    public void consume2(String message) {
        log.info("consumer 2");
        log.info(String.format("Queue 2 Received message -> %s", message));
//        Khi throw exception thì message chưa được xử lý
        throw new NullPointerException("Exception?");

    }


    @RabbitListener(queues = {"fanout1"})
    public void consumeFanout1(String message) throws InterruptedException {
        log.info("consumer fanout 1");
        log.info("----------Sleeep -------------");
        Thread.sleep(1000 * 15);
        log.info(String.format("Queue fanout 1 Received message -> %s", message));
    }

    @RabbitListener(queues = {"fanout2"})
    public void consumeFanout2(String message) throws InterruptedException {
        log.info("consumer fanout 2");
        log.info("----------Sleeep -------------");
        Thread.sleep(1000 * 15);
        log.info(String.format("Queue fanout 2 Received message -> %s", message));
    }

}