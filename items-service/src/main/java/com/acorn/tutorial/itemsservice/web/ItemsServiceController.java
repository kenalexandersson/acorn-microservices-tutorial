package com.acorn.tutorial.itemsservice.web;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.acorn.tutorial.itemsservice.model.Item;
import com.acorn.tutorial.itemsservice.repository.ItemRepository;

@RestController
public class ItemsServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsServiceController.class);

    private ItemRepository itemRepository;

    private Environment environment;

    @Autowired
    public ItemsServiceController(ItemRepository itemRepository, Environment environment) {
        this.itemRepository = itemRepository;
        this.environment = environment;
    }

    @GetMapping(path = "/items", produces = "application/json")
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/items/{id}", produces = "application/json")
    public ItemDto getItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return toItemDto(item);
    }

    private ItemDto toItemDto(Item item) {
        int port = Integer.parseInt(environment.getProperty("local.server.port", "0"));
        final ItemDto itemDto = ItemDto.of(item, port);
        LOGGER.info(String.format("Returning %s", itemDto));
        return itemDto;
    }
}