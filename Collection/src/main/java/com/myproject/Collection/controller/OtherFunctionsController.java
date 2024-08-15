package com.myproject.Collection.controller;

import com.myproject.Collection.dto.Quote;
import com.myproject.Collection.service.RabbitMQReceiver;
import com.myproject.Collection.service.RedisReceiver;
import com.myproject.Collection.configuration.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/otherFunctionsController")
//@ResponseBody
public class OtherFunctionsController {

    private static final Logger log = LoggerFactory.getLogger(OtherFunctionsController.class);

    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;


    //RestTemplateBuilder is a class us to instances a build RestTemplate
    //Synchronous client to perform HTTP requests
    /*@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/

    @Autowired
    public OtherFunctionsController(RestTemplateBuilder builder, RabbitTemplate rabbitTemplate, RabbitMQReceiver rabbitMQReceiver) {
        this.restTemplate = builder.build();
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQReceiver = rabbitMQReceiver;
    }



    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private RedisReceiver redisReceiver;

    private final RabbitMQReceiver rabbitMQReceiver;


    //RestTemplate sends the request, and then returns control to the calling method
    //generally the bean will wait until to use but CommandLineRunner will let the bean run when the code generate
    /*@Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {}*/
    @GetMapping("/resolveAPIWithQuote")
    public String resolveAPIWithQuote(Model theModel) {
        Quote quote = restTemplate.getForObject(
                "https://api.quotable.io/random", Quote.class);
        theModel.addAttribute("QuoteAPI", quote);
        log.info("log " + quote.toString());
        return "other-functions-page";
    }

    @GetMapping("/storeToRedis")
    @ResponseBody
    public String sendMessage(@RequestParam String message) {
        template.convertAndSend("chat", message);
        return "Message sent: " + message;
    }

    @GetMapping("/getFromRedis")
    @ResponseBody
    public String getMessageCount() {
        return "Messages received: " + redisReceiver.getMessages();
    }


    @GetMapping("/rabbitMQSend")
    @ResponseBody
    public String sendMessage() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        try {
            rabbitMQReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Failed to send message: " + e.getMessage();
        }
        return "Message sent!";
    }

}
