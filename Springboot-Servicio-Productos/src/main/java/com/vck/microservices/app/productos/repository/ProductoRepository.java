package com.vck.microservices.app.productos.repository;

import com.vck.microservices.app.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Long> {

}