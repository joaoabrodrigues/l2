package br.com.store.packing.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private List<Pedido> pedidos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Pedido {
        private int pedido_id;
        private List<Caixa> caixas;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Caixa {
            private Long caixa_id;
            private List<String> produtos;
            private String observacao; // Optional field for edge cases (e.g., no box available)
        }
    }
}
