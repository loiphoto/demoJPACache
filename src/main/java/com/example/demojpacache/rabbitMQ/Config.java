package com.example.demojpacache.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Value("${application.rabbitmq.queue1.name}")
    private String queue1;

    @Value("${application.rabbitmq.queue2.name}")
    private String queue2;

    @Value("${rabbitmq.exchangeName}")
    private String exchangeName;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    Queue queueName1() {
        return new Queue(queue1, false);
    }

    @Bean
    Queue queueName2() {
        return new Queue(queue2, false);
    }

    @Bean
    Queue queueResponse() {
        return new Queue("queueResponse", false);
    }

    @Bean
    DirectExchange exchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    //deadLetter
//    @Bean
//    DirectExchange deadLetterExchange() {
//        return new DirectExchange("deadLetterExchange");
//    }
//
//    @Bean
//    Queue dlq() {
//        return new Queue("queueResponse", false);
//    }
//
//    @Bean
//    Binding DLQbinding() {
//        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
//    }

    //
    @Bean
    Binding bindingQueue1(Queue queueName1, DirectExchange exchange) {
        return BindingBuilder.bind(queueName1).to(exchange).with(routingKey);
    }

    @Bean
    Binding bindingQueue2(Queue queueName2, DirectExchange exchange) {
        return BindingBuilder.bind(queueName2).to(exchange).with(routingKey);
    }


//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }
//
//    @Bean
//    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setReplyAddress(queueResponse().getName());
//        rabbitTemplate.setReplyTimeout(1000 * 60);
//        rabbitTemplate.setUseDirectReplyToContainer(false);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
//        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(jsonMessageConverter());
//        factory.setConcurrentConsumers(1);
//        factory.setMaxConcurrentConsumers(1);
//        factory.setErrorHandler(errorHandler());
//        return factory;
//    }

//    @Bean
//    public ErrorHandler errorHandler() {
//        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
//    }
//
//    public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
//        @Override
//        public boolean isFatal(Throwable t) {
//            if (t instanceof ListenerExecutionFailedException) {
//                ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
//                logger.error("Failed to process inbound message from queue "
//                        + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
//                        + "; failed message: " + lefe.getFailedMessage(), t);
//            }
//            return super.isFatal(t);
//        }
//    }
}
