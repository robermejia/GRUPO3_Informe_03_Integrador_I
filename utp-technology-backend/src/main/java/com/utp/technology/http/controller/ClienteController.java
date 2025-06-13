package com.utp.technology.http.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utp.technology.http.dto.cliente.ListClienteDto;
import com.utp.technology.http.response.ApiPageResponse;
import com.utp.technology.services.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clientes")
public class ClienteController {

  private final ClienteService clienteService;

  @GetMapping
  public ResponseEntity<ApiPageResponse<ListClienteDto>> index(
      @RequestParam(required = false) String busqueda,
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

    return ResponseEntity.ok()
        .body(ApiPageResponse.success(this.clienteService.listCliente(busqueda, pageReq)));
  }

}
