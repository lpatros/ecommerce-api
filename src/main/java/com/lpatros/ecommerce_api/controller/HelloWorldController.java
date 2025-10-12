package com.lpatros.ecommerce_api.controller;

import com.lpatros.ecommerce_api.entity.User;
import com.lpatros.ecommerce_api.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping
    public String helloWorld() {
        return  helloWorldService.helloWorld("Teste");
    }

    @PostMapping("/{id}")
    public String helloWorldPost(@PathVariable("id") String id, @RequestParam(value = "filter", defaultValue = "null") String filter, @RequestBody User body) {
        return helloWorldService.helloWorldPost(id, body.getName(), filter);
    }
}
