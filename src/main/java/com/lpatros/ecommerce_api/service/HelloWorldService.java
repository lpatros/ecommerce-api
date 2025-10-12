package com.lpatros.ecommerce_api.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String helloWorld(String name) {
        return "Hello World " + name;
    }

    public String helloWorldPost(String id, String name, String filter) {
        return "Hello World! Id: " + id + ", Name: " + name + ", Filter: " + filter;
    }
}
