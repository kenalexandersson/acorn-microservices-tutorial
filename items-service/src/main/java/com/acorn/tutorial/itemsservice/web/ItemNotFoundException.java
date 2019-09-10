package com.acorn.tutorial.itemsservice.web;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super(String.format("Failed to find item with id: %d", id));
    }
}