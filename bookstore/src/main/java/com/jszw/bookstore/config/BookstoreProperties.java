package com.jszw.bookstore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bookstore")
public class BookstoreProperties {

    private String name;
    private String version;
    private String supportEmail;
    private int paginationDefaultPageSize;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public int getPaginationDefaultPageSize() {
        return paginationDefaultPageSize;
    }

    public void setPaginationDefaultPageSize(int paginationDefaultPageSize) {
        this.paginationDefaultPageSize = paginationDefaultPageSize;
    }
}
