package com.microservices.app.items.service;

import com.microservices.app.items.clients.ProductoClienteRest;
import com.microservices.app.items.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ItemServiceFeingImpl implements ItemService{


    private final ProductoClienteRest productoClienteRest;

    @Autowired
    public ItemServiceFeingImpl(ProductoClienteRest productoClienteRest) {
        this.productoClienteRest = productoClienteRest;
    }

    @Override
    public List<Item> findAll() {
        return productoClienteRest.listar().stream().map(producto -> new Item(producto,10)).toList();
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(productoClienteRest.detalle(id),10);
    }
}
