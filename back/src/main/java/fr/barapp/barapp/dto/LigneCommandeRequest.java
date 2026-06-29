package fr.barapp.barapp.dto;

import fr.barapp.barapp.entity.enums.Taille;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Un cocktail demandé dans une commande. */
public record LigneCommandeRequest(
        @NotNull(message = "Le cocktail est obligatoire") Long cocktailId,
        @NotNull(message = "La taille est obligatoire") Taille taille,
        @NotNull(message = "La quantité est obligatoire")
        @Positive(message = "La quantité doit être positive") Integer quantite) {
}
