package com.acorn.tutorial.itemsservice.web;

import com.acorn.tutorial.itemsservice.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private String serviceAddress;

    public static ItemDto of(Item item, String serviceAddress) {
        return new ItemDto(item.getId(), item.getName(), serviceAddress);
    }
}