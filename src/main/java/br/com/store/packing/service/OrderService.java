package br.com.store.packing.service;

import br.com.store.packing.controller.dto.OrderDTO;
import br.com.store.packing.controller.dto.OrderResponseDTO;
import br.com.store.packing.domain.repository.BoxRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OrderService {

    @Autowired
    private BoxRepository repository;

    public OrderResponseDTO packOrder(OrderDTO orderDTO) {
        val boxes = repository.findAll();

        val pedidos = orderDTO.getPedidos().stream().map(order ->  {
            val orderVolume = order.getProducts().stream().map(product -> product.getDimensions().calculateVolume()).mapToInt(Integer::intValue).sum();

            val boxesForOrder = boxes.stream().filter(box -> box.calculateVolume() >= orderVolume).toList();

            if (boxesForOrder.isEmpty()) {
                return OrderResponseDTO.Pedido.builder()
                        .pedido_id(order.getOrderId())
                        .caixas(Collections.singletonList(
                                OrderResponseDTO.Pedido.Caixa
                                        .builder()
                                        .caixa_id(null)
                                        .produtos(order.getProducts().stream().map(OrderDTO.Order.Product::getProductId).toList())
                                        .observacao("Produto não cabe em nenhuma caixa disponível.")
                                        .build()
                        ))
                        .build();
            }

            val list = boxesForOrder.stream().map(box -> OrderResponseDTO.Pedido.Caixa
                    .builder()
                    .caixa_id(box.getId())
                    .produtos(order.getProducts().stream().map(OrderDTO.Order.Product::getProductId).toList())
                    .build()).toList().getFirst();

            return OrderResponseDTO.Pedido.builder()
                    .pedido_id(order.getOrderId())
                    .caixas(Collections.singletonList(list))
                    .build();
        }).toList();

        return OrderResponseDTO.builder().pedidos(pedidos).build();
    }
}
