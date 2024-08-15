package com.myproject.Collection.configuration;

import com.myproject.Collection.service.RedisReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig{


    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    //This method sets up a listener for Redis messages on the "chat" channel.
    //RedisConnectionFactory use create connections to the Redis server
    //MessageListenerAdapter wraps your message-handling logic converts into MessageListener that Redis can work with
    @Bean
    RedisMessageListenerContainer RedisContainer(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        //RedisMessageListenerContainer is a core component managing subscriptions and receiving messages from Redis channels.
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //set listenerAdapter as a listener for messages published
        //PatternTopic allows a pattern for subscribing to multiple channels
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    //MessageListenerAdapter use to adapt an object to use as a Redis message listener
    @Bean
    MessageListenerAdapter RedisListenerAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "receiveMessage");
    }

    @Bean
    RedisReceiver RedisReceiver() {
        return new RedisReceiver();
    }

    //RedisConnectionFactory use create connections to the Redis server
    @Bean
    StringRedisTemplate RedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
