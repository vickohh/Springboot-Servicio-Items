package com.vck.microservices.app.productos.service;

import com.vck.microservices.app.productos.model.Producto;

import java.util.List;

public interface IProductoService {
	public List<Producto> findAll();
	public Producto findId(Long id);

}
