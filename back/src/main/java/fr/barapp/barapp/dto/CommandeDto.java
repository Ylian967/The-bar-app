package fr.barapp.barapp.dto;

import fr.barapp.barapp.entity.enums.StatutCommande;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Une commande renvoyée par l'API. */
public record CommandeDto(
        Long id,
        String clientNom,
        StatutCommande statut,
        LocalDateTime dateCreation,
        List<LigneCommandeDto> lignes,
        BigDecimal total) {
}
