package br.com.store.packing.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private List<Order> pedidos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        @JsonProperty("pedido_id")
        private int orderId;
        @JsonProperty("produtos")
        private List<Product> products;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Product {
            @JsonProperty("produto_id")
            private String productId;
            @JsonProperty("dimensoes")
            private Dimensions dimensions;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Dimensions {
                @JsonProperty("altura")
                private int height;
                @JsonProperty("largura")
                private int width;
                @JsonProperty("comprimento")
                private int length;

                public int calculateVolume() {
                    return height * width * length;
                }
            }
        }
    }
}
