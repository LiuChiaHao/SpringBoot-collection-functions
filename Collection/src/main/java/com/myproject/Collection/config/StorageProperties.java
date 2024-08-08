package com.myproject.Collection.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

//this class
//??
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\Users\\david\\Downloads\\tesst";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
