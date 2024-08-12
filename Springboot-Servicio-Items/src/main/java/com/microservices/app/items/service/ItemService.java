package com.microservices.app.items.service;

import com.microservices.app.items.model.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer cantidad);
}
