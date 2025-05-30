package br.com.store.packing.controller;

import br.com.store.packing.controller.dto.OrderDTO;
import br.com.store.packing.controller.dto.OrderResponseDTO;
import br.com.store.packing.service.OrderService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO order) {
        val response = service.packOrder(order);
        return ResponseEntity.ok(response);
    }
}
