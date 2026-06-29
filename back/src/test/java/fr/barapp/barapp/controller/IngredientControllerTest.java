package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.IngredientDto;
import fr.barapp.barapp.dto.IngredientRequest;
import fr.barapp.barapp.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService service;
    @InjectMocks
    private IngredientController controller;

    @Test
    void lister() {
        when(service.lister()).thenReturn(List.of(new IngredientDto(1L, "Menthe")));
        assertThat(controller.lister()).hasSize(1);
    }

    @Test
    void creer_renvoie_201() {
        IngredientRequest req = new IngredientRequest("Menthe");
        when(service.creer(req)).thenReturn(new IngredientDto(1L, "Menthe"));
        assertThat(controller.creer(req).getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void modifier() {
        IngredientRequest req = new IngredientRequest("Citron");
        when(service.modifier(1L, req)).thenReturn(new IngredientDto(1L, "Citron"));
        assertThat(controller.modifier(1L, req).nom()).isEqualTo("Citron");
    }

    @Test
    void supprimer_renvoie_204() {
        assertThat(controller.supprimer(1L).getStatusCode().value()).isEqualTo(204);
        verify(service).supprimer(1L);
    }
}
