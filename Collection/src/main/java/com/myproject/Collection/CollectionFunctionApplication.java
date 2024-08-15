package com.myproject.Collection;

import com.myproject.Collection.configuration.StorageProperties;
import com.myproject.Collection.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class CollectionFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectionFunctionApplication.class, args);
	}


	//initial the storage service delete all file in the storage and make a user directory
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
