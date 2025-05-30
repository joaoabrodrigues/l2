package br.com.store.packing.controller;

import br.com.store.packing.controller.dto.BoxDTO;
import br.com.store.packing.service.BoxService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BoxControllerTest {

    @Mock
    private BoxService boxService;

    @InjectMocks
    private BoxController boxController;

    public BoxControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBox() {
        BoxDTO inputBox = new BoxDTO(1L, 30, 40, 80);
        BoxDTO savedBox = new BoxDTO(1L, 30, 40, 80);

        when(boxService.saveBox(any(BoxDTO.class))).thenReturn(savedBox);

        ResponseEntity<BoxDTO> response = boxController.createBox(inputBox);

        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedBox, response.getBody());
        assertEquals(savedBox.id(), response.getBody().id());
    }
}