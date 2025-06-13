package com.utp.technology.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.utp.technology.http.dto.product.ListProductoDto;
import com.utp.technology.http.dto.product.StoreProductDTO;
import com.utp.technology.model.Producto;
import com.utp.technology.repository.ProductoRepository;
import com.utp.technology.services.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

  private final ProductoRepository productoRepository;

  @Override
  public Page<ListProductoDto> listAll(String nombre, PageRequest pageReq) {
    return this.productoRepository.listProducto(nombre, pageReq);
  }

  @Override
  public Optional<Producto> findById(Integer id) {
    return this.productoRepository.findById(id);
  }

  @Override
  public Producto guardar(StoreProductDTO productoData) {

    Producto producto = new Producto();
    producto.setNombre(productoData.getNombre());
    producto.setImagen(productoData.getImagen());
    producto.setPrecio(productoData.getPrecio());
    producto.setStock(productoData.getStock());

    return this.productoRepository.save(producto);
  }

  @Override
  public Producto editar(Producto producto) {
    return this.productoRepository.save(producto);
  }

  @Override
  public void eliminar(Producto producto) {
    this.productoRepository.delete(producto);
  }

}
