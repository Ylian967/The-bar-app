package fr.barapp.barapp.dto;

import fr.barapp.barapp.entity.enums.StatutPreparation;
import fr.barapp.barapp.entity.enums.Taille;

import java.math.BigDecimal;

/** Une ligne de commande renvoyée par l'API. */
public record LigneCommandeDto(
        Long id,
        Long cocktailId,
        String cocktailNom,
        Taille taille,
        BigDecimal prix,
        Integer quantite,
        StatutPreparation statutPreparation) {
}
