package com.acorn.tutorial.itemsservice.web;

import com.acorn.tutorial.itemsservice.model.Item;
import com.acorn.tutorial.itemsservice.repository.ItemRepository;
import com.acorn.tutorial.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ItemsServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsServiceController.class);

    private ItemRepository itemRepository;

    private ServiceUtil serviceUtil;

    @Autowired
    public ItemsServiceController(ItemRepository itemRepository, ServiceUtil serviceUtil) {
        this.itemRepository = itemRepository;
        this.serviceUtil = serviceUtil;
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
        final ItemDto itemDto = ItemDto.of(item, serviceUtil.getServiceAddress());
        LOGGER.info("Returning {}", itemDto);
        return itemDto;
    }
}