package br.com.store.packing.controller;

import br.com.store.packing.controller.dto.OrderDTO;
import br.com.store.packing.controller.dto.OrderResponseDTO;
import br.com.store.packing.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        // Arrange: Define the input order object and the expected response
        OrderDTO inputOrder = new OrderDTO();
        inputOrder.setPedidos(Collections.emptyList()); // Add whatever data relevant to your DTO

        OrderResponseDTO expectedResponse = new OrderResponseDTO();
        expectedResponse.setPedidos(Collections.emptyList());

        when(orderService.packOrder(any(OrderDTO.class))).thenReturn(expectedResponse);

        // Act: Call the createOrder method
        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(inputOrder);

        // Assert: Verify the output
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
    }
}