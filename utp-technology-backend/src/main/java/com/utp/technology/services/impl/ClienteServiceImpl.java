package com.utp.technology.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.utp.technology.http.dto.cliente.ListClienteDto;
import com.utp.technology.repository.ClienteRepository;
import com.utp.technology.services.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;

  public Page<ListClienteDto> listCliente(String busqueda, Pageable page) {
    return this.clienteRepository.listCliente(busqueda, page);
  }

}
