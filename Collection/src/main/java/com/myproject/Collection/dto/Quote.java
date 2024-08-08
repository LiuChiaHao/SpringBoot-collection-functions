package com.myproject.Collection.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// if the class is not bound with String content, String author the class will ignore
@JsonIgnoreProperties(ignoreUnknown = true)
public record Quote(String content, String author) {

}
