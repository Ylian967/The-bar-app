package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CategorieDto;
import fr.barapp.barapp.dto.CategorieRequest;
import fr.barapp.barapp.service.CategorieService;
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
class CategorieControllerTest {

    @Mock
    private CategorieService service;
    @InjectMocks
    private CategorieController controller;

    @Test
    void lister() {
        when(service.lister()).thenReturn(List.of(new CategorieDto(1L, "Classiques")));
        assertThat(controller.lister()).hasSize(1);
    }

    @Test
    void trouver() {
        when(service.trouver(1L)).thenReturn(new CategorieDto(1L, "Classiques"));
        assertThat(controller.trouver(1L).nom()).isEqualTo("Classiques");
    }

    @Test
    void creer_renvoie_201() {
        CategorieRequest req = new CategorieRequest("Mocktails");
        when(service.creer(req)).thenReturn(new CategorieDto(1L, "Mocktails"));
        assertThat(controller.creer(req).getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void modifier() {
        CategorieRequest req = new CategorieRequest("Nouveau");
        when(service.modifier(1L, req)).thenReturn(new CategorieDto(1L, "Nouveau"));
        assertThat(controller.modifier(1L, req).nom()).isEqualTo("Nouveau");
    }

    @Test
    void supprimer_renvoie_204() {
        assertThat(controller.supprimer(1L).getStatusCode().value()).isEqualTo(204);
        verify(service).supprimer(1L);
    }
}
