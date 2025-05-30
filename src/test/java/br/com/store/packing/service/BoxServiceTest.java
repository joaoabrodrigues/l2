package br.com.store.packing.service;

import br.com.store.packing.domain.entity.Box;
import br.com.store.packing.domain.repository.BoxRepository;
import br.com.store.packing.controller.dto.BoxDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class BoxServiceTest {

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private BoxService boxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBox() {
        BoxDTO boxDTO = new BoxDTO(null, 10, 20, 30);

        Box savedBox = Box.builder()
                .id(1L)
                .height(10)
                .width(20)
                .length(30)
                .build();

        when(boxRepository.save(any(Box.class))).thenReturn(savedBox);

        BoxDTO result = boxService.saveBox(boxDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(10, result.height());
        assertEquals(20, result.width());
        assertEquals(30, result.length());
    }

    @Test
    void testGetBoxFound() {
        Box box = Box.builder()
                .id(1L)
                .height(10)
                .width(20)
                .length(30)
                .build();

        when(boxRepository.findById(eq(1L))).thenReturn(Optional.of(box));

        Box result = boxService.getBox(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10, result.getHeight());
        assertEquals(20, result.getWidth());
        assertEquals(30, result.getLength());
    }

    @Test
    void testGetBoxNotFound() {
        when(boxRepository.findById(eq(1L))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> boxService.getBox(1L));
        assertEquals("Box not found", exception.getMessage());
    }
}