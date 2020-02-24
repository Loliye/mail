package com.mikufans.mail.common.redis;

import io.netty.handler.codec.redis.RedisMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class RedisListener
{
    private static final Logger logger = LoggerFactory.getLogger(RedisListener.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter)
    {
        logger.info("启动监听");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("mail"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver)
    {
        return new MessageListenerAdapter(receiver, "receiverMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch)
    {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch()
    {
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory)
    {
        return new StringRedisTemplate(connectionFactory);
    }

}
