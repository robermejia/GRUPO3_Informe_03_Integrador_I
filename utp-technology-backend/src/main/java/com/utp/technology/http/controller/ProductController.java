package com.utp.technology.http.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utp.technology.http.dto.product.ListProductoDto;
import com.utp.technology.http.dto.product.StoreProductDTO;
import com.utp.technology.http.response.ApiPageResponse;
import com.utp.technology.http.response.ApiResponse;
import com.utp.technology.model.Producto;
import com.utp.technology.services.ProductoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/producto")
public class ProductController {

  private final ProductoService productoService;

  @GetMapping
  @SecurityRequirements
  public ResponseEntity<ApiPageResponse<ListProductoDto>> index(
      @RequestParam(required = false) String nombre,
      @RequestParam(name = "page_number", defaultValue = "0") Integer pageNumber,
      @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
      @RequestParam(name = "sort_by", defaultValue = "id") String sortBy,
      @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {

    Sort sort;
    if (sortOrder.equalsIgnoreCase("ASC")) {
      sort = Sort.by(Direction.ASC, sortBy);
    } else {
      sort = Sort.by(Direction.DESC, sortBy);
    }
    PageRequest pageReq = PageRequest.of(pageNumber, pageSize, sort);

    Page<ListProductoDto> pageProducto = this.productoService.listAll(nombre, pageReq);
    ApiPageResponse<ListProductoDto> apiPage = ApiPageResponse.success(pageProducto);
    return ResponseEntity.ok().body(apiPage);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Producto>> store(@Valid @RequestBody StoreProductDTO request,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errors.put(error.getField(), error.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.badRequest(null,
          errors.toString()));
    }

    Producto producto = this.productoService.guardar(request);

    return ResponseEntity.ok().body(ApiResponse.success(producto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
    Optional<Producto> productoOpt = this.productoService.findById(id);

    if (productoOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound());
    }

    this.productoService.eliminar(productoOpt.get());
    return ResponseEntity.ok().body(ApiResponse.success(null));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Producto>> update(@PathVariable Integer id,
      @Valid @RequestBody StoreProductDTO request, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errors.put(error.getField(), error.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.badRequest(null,
          errors.toString()));
    }

    Optional<Producto> productoOpt = this.productoService.findById(id);

    if (productoOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound());
    }

    Producto producto = productoOpt.get();
    producto.setNombre(request.getNombre());
    producto.setImagen(request.getImagen());
    producto.setPrecio(request.getPrecio());
    producto.setStock(request.getStock());

    producto = this.productoService.editar(producto);

    return ResponseEntity.ok().body(ApiResponse.success(producto));
  }

}
