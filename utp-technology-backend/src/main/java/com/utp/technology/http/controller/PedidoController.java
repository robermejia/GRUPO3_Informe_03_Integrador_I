package com.utp.technology.http.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.utp.technology.http.dto.pedido.ListDetallePedidoDto;
import com.utp.technology.http.dto.pedido.ListPedidoAdmin;
import com.utp.technology.http.dto.pedido.ListPedidoCliente;
import com.utp.technology.http.dto.pedido.StorePedidoDto;
import com.utp.technology.http.response.ApiPageResponse;
import com.utp.technology.http.response.ApiResponse;
import com.utp.technology.model.Pedido;
import com.utp.technology.security.AuthService;
import com.utp.technology.security.JwtUsuario;
import com.utp.technology.services.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedido")
public class PedidoController {

  private final PedidoService pedidoService;

  private final AuthService authService;

  @GetMapping
  public ResponseEntity<ApiPageResponse<ListPedidoCliente>> index(
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

    Integer idUsuario = this.authService.getCurrentUser().getId();

    return ResponseEntity.ok()
        .body(ApiPageResponse.success(this.pedidoService.listProductoCustomer(idUsuario, pageReq)));
  }

  @GetMapping("/admin")
  public ResponseEntity<ApiPageResponse<ListPedidoAdmin>> indexAdmin(
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

    Integer idRol = this.authService.getCurrentUser().getRolId();

    if (!(idRol.equals(1) || idRol.equals(2))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiPageResponse.badRequest("Error de permisos"));
    }

    return ResponseEntity.ok()
        .body(ApiPageResponse.success(this.pedidoService.listProductoAdmin(pageReq)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<?>> store(@Valid @RequestBody StorePedidoDto pedidoData,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errors.put(error.getField(), error.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.badRequest(null,
          errors.toString()));
    }

    JwtUsuario jwtUsuario = this.authService.getCurrentUser();

    try {
      this.pedidoService.guardarPedido(pedidoData, jwtUsuario);
      return ResponseEntity.ok().body(ApiResponse.success(null,
          "Pedido registrado exitosamente"));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.badRequest(null,
          "Ocurro un error interno"));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<List<ListDetallePedidoDto>>> show(@PathVariable Integer id) {
    return ResponseEntity.ok().body(ApiResponse.success(this.pedidoService.listPedidoDetalles(id)));
  }

  @PutMapping("/{id}/pagar")
  public ResponseEntity<ApiResponse<?>> pagar(@PathVariable Integer id) {
    Optional<Pedido> pedidoOpt = this.pedidoService.findById(id);
    if (pedidoOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound());
    }

    pedidoOpt.get().setEstado("En proceso");

    this.pedidoService.actualizar(pedidoOpt.get());

    return ResponseEntity.ok().body(ApiResponse.success(null, "Pedido pagado"));
  }

  @PutMapping("/{id}/completar")
  public ResponseEntity<ApiResponse<?>> completar(@PathVariable Integer id) {
    Optional<Pedido> pedidoOpt = this.pedidoService.findById(id);
    if (pedidoOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound());
    }

    pedidoOpt.get().setEstado("Completado");

    this.pedidoService.actualizar(pedidoOpt.get());

    return ResponseEntity.ok().body(ApiResponse.success(null, "Pedido pagado"));
  }

  @DeleteMapping("/{id}/anular")
  public ResponseEntity<ApiResponse<?>> anular(@PathVariable Integer id) {
    Optional<Pedido> pedidoOpt = this.pedidoService.findById(id);
    if (pedidoOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound());
    }

    pedidoOpt.get().setEstado("Cancelado");

    this.pedidoService.actualizar(pedidoOpt.get());

    return ResponseEntity.ok().body(ApiResponse.success(null, "Pedido anulado"));
  }

}
