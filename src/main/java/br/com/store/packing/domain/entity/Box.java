package br.com.store.packing.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Data
@Table(name = "boxes")
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int height;

    private int width;

    private int length;

    public int calculateVolume() {
        return height * width * length;
    }
}