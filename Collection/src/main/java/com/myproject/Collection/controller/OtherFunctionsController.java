package com.myproject.Collection.controller;

import com.myproject.Collection.dto.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
@Controller
@RequestMapping("/otherFunctionsController")
public class OtherFunctionsController {

    private static final Logger log = LoggerFactory.getLogger(OtherFunctionsController.class);

    private final RestTemplate restTemplate;
    //RestTemplateBuilder is a class us to instances a build RestTemplate
    //Synchronous client to perform HTTP requests
    /*@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/
    @Autowired
    public OtherFunctionsController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    //RestTemplate sends the request, and then returns control to the calling method
    //generally the bean will wait until to use but CommandLineRunner will let the bean run when the code generate
    /*@Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {*/
    @GetMapping("/resolveAPIWithQuote")
    public String resolveAPIWithQuote(Model theModel) {
        Quote quote = restTemplate.getForObject(
                "https://api.quotable.io/random", Quote.class);
        theModel.addAttribute("QuoteAPI", quote);
        log.info("log " + quote.toString());
        return "other-functions-page";
    }
    //    };
}
