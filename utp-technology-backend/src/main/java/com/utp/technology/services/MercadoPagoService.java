package com.utp.technology.services;

public interface MercadoPagoService {

  public String generarLinkPagoTarjetaCredito(Integer idPedido, Double total, String email);

}
