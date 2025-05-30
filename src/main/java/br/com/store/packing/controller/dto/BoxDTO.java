package br.com.store.packing.controller.dto;

public record BoxDTO(
        Long id,
        Integer height,
        Integer width,
        Integer length
){}