package com.vck.microservices.app.productos.controllers;

import com.vck.microservices.app.productos.model.Producto;
import com.vck.microservices.app.productos.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FeignClient(name = "servicio-productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Value("${server.port}")
    private Integer env;

    @Autowired
    private IProductoService productoService;

    @GetMapping(value = "/listar")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(producto -> {
            producto.setPort(env);
            return producto;
        }).toList();
    }

    @GetMapping(value = "/ver/{id}")
    public Producto detalle(@PathVariable Long id) {
        Producto producto = productoService.findId(id);
        producto.setPort(env);

        return productoService.findId(id);
    }

}
