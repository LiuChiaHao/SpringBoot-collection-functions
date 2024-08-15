package com.myproject.Collection.configuration;


import com.myproject.Collection.service.RabbitMQReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //TopicExchange is route to decide messages to send.
    public static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    //storage a message until they are consumed by a message listener or receiver.
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    //return a TopicExchange bean name spring-boot-exchange
    //TopicExchange is routing messages to one or more queues when the message is sent.
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    //binds the specified queue to the TopicExchange
    //By using patterns with wildcards like # and *, you can control which messages are delivered to which queues
    //starts with "foo.bar." will be routed to the spring-boot queue
    //# (hash) is a wildcard character that matches zero or more words in the routing key.
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    //SimpleMessageListenerContainer which is responsible for listening to an incoming messages and processing them using a message listener
    @Bean
    SimpleMessageListenerContainer RabbitMQContainer(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    //listenerAdapter method creates a MessageListenerAdapter that connects Receiver class to the RabbitMQ message listener infrastructure.
    @Bean
    MessageListenerAdapter RabbitMQListenerAdapter(RabbitMQReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
