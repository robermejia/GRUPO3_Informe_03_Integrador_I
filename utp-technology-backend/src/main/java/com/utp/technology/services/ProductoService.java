package com.utp.technology.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.utp.technology.http.dto.product.ListProductoDto;
import com.utp.technology.http.dto.product.StoreProductDTO;
import com.utp.technology.model.Producto;

public interface ProductoService {

  public Page<ListProductoDto> listAll(String nombre, PageRequest pageReq);

  public Optional<Producto> findById(Integer id);

  public Producto guardar(StoreProductDTO productoData);

  public Producto editar(Producto producto);

  public void eliminar(Producto producto);

}
