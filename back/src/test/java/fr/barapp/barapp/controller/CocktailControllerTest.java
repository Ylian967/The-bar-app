package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.CocktailRequest;
import fr.barapp.barapp.service.CocktailService;
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
class CocktailControllerTest {

    @Mock
    private CocktailService service;
    @InjectMocks
    private CocktailController controller;

    private CocktailDto dto() {
        return new CocktailDto(1L, "Mojito", "Frais", "/images/cocktails/mojito.jpg",
                1L, "Classiques", List.of(), List.of());
    }

    @Test
    void lister() {
        when(service.lister(null)).thenReturn(List.of(dto()));
        assertThat(controller.lister(null)).hasSize(1);
    }

    @Test
    void trouver() {
        when(service.trouver(1L)).thenReturn(dto());
        assertThat(controller.trouver(1L).nom()).isEqualTo("Mojito");
    }

    @Test
    void creer_renvoie_201() {
        CocktailRequest req = new CocktailRequest("Mojito", null, null, 1L, List.of(), null);
        when(service.creer(req)).thenReturn(dto());
        assertThat(controller.creer(req).getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void modifier() {
        CocktailRequest req = new CocktailRequest("Mojito", null, null, 1L, List.of(), null);
        when(service.modifier(1L, req)).thenReturn(dto());
        assertThat(controller.modifier(1L, req).nom()).isEqualTo("Mojito");
    }

    @Test
    void supprimer_renvoie_204() {
        assertThat(controller.supprimer(1L).getStatusCode().value()).isEqualTo(204);
        verify(service).supprimer(1L);
    }
}
