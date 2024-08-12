package com.vck.microservices.app.productos.service;

import com.vck.microservices.app.productos.model.Producto;
import com.vck.microservices.app.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService{

	private ProductoRepository productoRepository;

	@Autowired
	public ProductoServiceImpl(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@Override
	//@Transactional(readOnly = true)
	public List<Producto> findAll() {		 
		return  productoRepository.findAll();
	}

	@Override
	public Producto findId(Long id) {
		return productoRepository.findById(id).orElse(null); //esta es otra forma de regresar un valor desde un optional
	}

}
