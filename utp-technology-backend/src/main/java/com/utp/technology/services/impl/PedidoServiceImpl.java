package com.utp.technology.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utp.technology.http.dto.pedido.ListDetallePedidoDto;
import com.utp.technology.http.dto.pedido.ListPedidoAdmin;
import com.utp.technology.http.dto.pedido.ListPedidoCliente;
import com.utp.technology.http.dto.pedido.StorePedidoDto;
import com.utp.technology.model.Cliente;
import com.utp.technology.model.DetallePedido;
import com.utp.technology.model.Pedido;
import com.utp.technology.model.Producto;
import com.utp.technology.model.Usuario;
import com.utp.technology.repository.ClienteRepository;
import com.utp.technology.repository.PedidoDetalleRepository;
import com.utp.technology.repository.PedidoRepository;
import com.utp.technology.security.JwtUsuario;
import com.utp.technology.services.PedidoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

  private final ClienteRepository clienteRepository;

  private final PedidoRepository pedidoRepository;

  private final PedidoDetalleRepository pedidoDetalleRepository;

  @Override
  public Optional<Pedido> findById(Integer id) {
    return this.pedidoRepository.findById(id);
  }

  @Override
  public Page<ListPedidoAdmin> listProductoAdmin(PageRequest pageReq) {
    return this.pedidoRepository.listProductoAdmin(pageReq);
  }

  @Override
  public Page<ListPedidoCliente> listProductoCustomer(Integer usuarioId, PageRequest pageReq) {
    return this.pedidoRepository.listProductoCustomer(usuarioId, pageReq);
  }

  @Override
  @Transactional
  public void guardarPedido(StorePedidoDto pedidoData, JwtUsuario jwtUsuario) {
    Usuario usuario = new Usuario();
    usuario.setId(jwtUsuario.getId());
    Optional<Cliente> cliente = this.clienteRepository.findByUsuario(usuario);

    if (cliente.isEmpty()) {
      throw new IllegalArgumentException("");
    }
    Pedido pedido = new Pedido();
    pedido.setCliente(cliente.get());
    pedido.setUsuario(usuario);
    pedido.setFecha(new Date());
    pedido.setEstado("Pendiente");

    this.pedidoRepository.save(pedido);

    List<DetallePedido> detalles = pedidoData.getDetalles().stream().map(item -> {
      DetallePedido detalle = new DetallePedido();
      Producto producto = new Producto();
      detalle.setPedido(pedido);
      producto.setId(item.getIdProducto());
      detalle.setProducto(producto);
      detalle.setCantidad(item.getCantidad());
      // Se va a actualizar mas adelante
      detalle.setPrecioUnitario(0D);
      return detalle;
    }).toList();

    this.pedidoDetalleRepository.saveAll(detalles);
    this.pedidoRepository.actualizarPrecioUnitarioDetalles(pedido.getId());
  }

  @Override
  public List<ListDetallePedidoDto> listPedidoDetalles(Integer pedidoId) {
    return this.pedidoDetalleRepository.listPedidoDetalles(pedidoId);
  }

  @Override
  public void actualizar(Pedido pedido) {
    this.pedidoRepository.save(pedido);
  }

}
