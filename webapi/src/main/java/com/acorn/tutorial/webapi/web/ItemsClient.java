package com.acorn.tutorial.webapi.web;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "items-service")
@RibbonClient(name = "items-service")
public interface ItemsClient {

    @GetMapping("/items")
    List<Item> getItems();

    @GetMapping("/items/{id}")
    Item getItem(@PathVariable Long id);
}