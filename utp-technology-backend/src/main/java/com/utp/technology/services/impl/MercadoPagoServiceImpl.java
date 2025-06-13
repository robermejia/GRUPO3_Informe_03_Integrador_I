package com.utp.technology.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.OrderClient;
import com.mercadopago.client.order.OrderCreateRequest;
import com.mercadopago.client.order.OrderPayerRequest;
import com.mercadopago.client.order.OrderPaymentMethodRequest;
import com.mercadopago.client.order.OrderPaymentRequest;
import com.mercadopago.client.order.OrderTransactionRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;
import com.utp.technology.services.MercadoPagoService;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

  private final OrderClient client;

  public MercadoPagoServiceImpl() {
    MercadoPagoConfig.setAccessToken("{{ACCESS_TOKEN}}");
    this.client = new OrderClient();
  }

  public String generarLinkPagoTarjetaCredito(Integer idPedido, Double total, String email) {
    OrderPaymentRequest payment = OrderPaymentRequest.builder()
        .amount(Double.toString(total))
        .paymentMethod(OrderPaymentMethodRequest.builder()
            .id("master")
            .type("credit_card")
            .token("{{CARD_TOKEN}}")
            .installments(1)
            .build())
        .build();

    List<OrderPaymentRequest> payments = new ArrayList<>();
    payments.add(payment);

    OrderCreateRequest request = OrderCreateRequest.builder()
        .type("online")
        .totalAmount(Double.toString(total))
        .externalReference(idPedido.toString())
        .payer(OrderPayerRequest.builder().email(email).build())
        .transactions(OrderTransactionRequest.builder()
            .payments(payments)
            .build())
        .build();

    Map<String, String> headers = new HashMap<>();
    headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

    MPRequestOptions requestOptions = MPRequestOptions.builder()
        .customHeaders(headers)
        .build();

    try {
      Order order = client.create(request, requestOptions);
      System.out.println("Order created: " + order.getId());
      return order.getId().toString();
    } catch (MPApiException | MPException e) {
      System.out.println("Error creating order: " + e.getMessage());
      return null;
    }
  }
}
