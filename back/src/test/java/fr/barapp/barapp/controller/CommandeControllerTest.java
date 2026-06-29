package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.CommandeDto;
import fr.barapp.barapp.dto.CommandeRequest;
import fr.barapp.barapp.dto.LigneCommandeRequest;
import fr.barapp.barapp.entity.enums.StatutCommande;
import fr.barapp.barapp.entity.enums.Taille;
import fr.barapp.barapp.service.CommandeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeControllerTest {

    @Mock
    private CommandeService service;
    @InjectMocks
    private CommandeController controller;

    private CommandeDto dto() {
        return new CommandeDto(1L, "Ylian", StatutCommande.COMMANDEE,
                LocalDateTime.now(), List.of(), new BigDecimal("16.00"));
    }

    @Test
    void passer_renvoie_201() {
        CommandeRequest req = new CommandeRequest("Ylian",
                List.of(new LigneCommandeRequest(1L, Taille.M, 1)));
        when(service.passer(req)).thenReturn(dto());
        assertThat(controller.passer(req).getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void trouver() {
        when(service.trouver(1L)).thenReturn(dto());
        assertThat(controller.trouver(1L).clientNom()).isEqualTo("Ylian");
    }

    @Test
    void aTraiter() {
        when(service.aTraiter()).thenReturn(List.of(dto()));
        assertThat(controller.aTraiter()).hasSize(1);
    }

    @Test
    void parClient() {
        when(service.parClient("Ylian")).thenReturn(List.of(dto()));
        assertThat(controller.parClient("Ylian")).hasSize(1);
    }

    @Test
    void avancer() {
        when(service.avancerLigne(1L, 2L)).thenReturn(dto());
        assertThat(controller.avancer(1L, 2L).id()).isEqualTo(1L);
    }
}
