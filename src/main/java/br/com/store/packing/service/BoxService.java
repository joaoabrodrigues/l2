package br.com.store.packing.service;


import br.com.store.packing.controller.dto.BoxDTO;
import br.com.store.packing.domain.entity.Box;
import br.com.store.packing.domain.repository.BoxRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    public BoxDTO saveBox(BoxDTO boxDTO) {
        val box = Box.builder()
                .height(boxDTO.height())
                .width(boxDTO.width())
                .length(boxDTO.length())
                .build();

        val savedBox = boxRepository.save(box);
        return new BoxDTO(savedBox.getId(), savedBox.getHeight(), savedBox.getWidth(), savedBox.getLength());
    }

    public Box getBox(Long id) {
        return boxRepository.findById(id).orElseThrow(() -> new RuntimeException("Box not found"));
    }

}
