package com.myproject.Collection.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;


//provides a way to externalize configuration properties related to file storage
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\Users\\david\\Downloads";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
