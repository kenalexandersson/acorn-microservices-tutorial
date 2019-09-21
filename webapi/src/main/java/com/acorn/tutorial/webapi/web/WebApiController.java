package com.acorn.tutorial.webapi.web;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApiController.class);

    @GetMapping(path = "/webapi/items")
    public List<ItemInfoDto > getItems() {
        return Collections.singletonList(ItemInfoDto.of(null, null));
    }

    @GetMapping(path = "/webapi/items/{id}")
    public ItemInfoDto getItem(@PathVariable Long id) {

        return ItemInfoDto.of(null, null);
    }
}
