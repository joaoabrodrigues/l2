package br.com.store.packing.service;

import br.com.store.packing.controller.dto.OrderDTO;
import br.com.store.packing.controller.dto.OrderResponseDTO;
import br.com.store.packing.domain.entity.Box;
import br.com.store.packing.domain.repository.BoxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPackOrder() {
        Box box = Box.builder().id(1L).height(10).width(10).length(10).build();
        when(boxRepository.findAll()).thenReturn(Collections.singletonList(box));

        OrderDTO.Order.Product product = new OrderDTO.Order.Product("prod-1", new OrderDTO.Order.Product.Dimensions(5, 5, 5));
        OrderDTO.Order order = new OrderDTO.Order(1, Collections.singletonList(product));
        OrderDTO inputOrder = new OrderDTO(Collections.singletonList(order));

        OrderResponseDTO.Pedido.Caixa caixa = OrderResponseDTO.Pedido.Caixa.builder()
                .caixa_id(1L)
                .produtos(Collections.singletonList("prod-1"))
                .build();
        OrderResponseDTO.Pedido expectedPedido = OrderResponseDTO.Pedido.builder()
                .pedido_id(1)
                .caixas(Collections.singletonList(caixa))
                .build();
        List<OrderResponseDTO.Pedido> expectedPedidos = Collections.singletonList(expectedPedido);

        OrderResponseDTO result = orderService.packOrder(inputOrder);

        assertEquals(expectedPedidos, result.getPedidos());
    }

    @Test
    void testPackOrderWithNoBoxAvailable() {
        when(boxRepository.findAll()).thenReturn(Collections.emptyList());

        OrderDTO.Order.Product product = new OrderDTO.Order.Product("prod-1", new OrderDTO.Order.Product.Dimensions(5, 5, 5));
        OrderDTO.Order order = new OrderDTO.Order(1, Collections.singletonList(product));
        OrderDTO inputOrder = new OrderDTO(Collections.singletonList(order));

        OrderResponseDTO.Pedido.Caixa caixa = OrderResponseDTO.Pedido.Caixa.builder()
                .caixa_id(null)
                .produtos(Collections.singletonList("prod-1"))
                .observacao("Produto não cabe em nenhuma caixa disponível.")
                .build();
        OrderResponseDTO.Pedido expectedPedido = OrderResponseDTO.Pedido.builder()
                .pedido_id(1)
                .caixas(Collections.singletonList(caixa))
                .build();
        List<OrderResponseDTO.Pedido> expectedPedidos = Collections.singletonList(expectedPedido);

        OrderResponseDTO result = orderService.packOrder(inputOrder);

        assertEquals(expectedPedidos, result.getPedidos());
    }
}