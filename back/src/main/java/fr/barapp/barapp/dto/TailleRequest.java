package fr.barapp.barapp.dto;

import fr.barapp.barapp.entity.enums.Taille;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/** Une taille et son prix, reçus lors de la création/modification d'un cocktail. */
public record TailleRequest(
        @NotNull(message = "La taille est obligatoire") Taille taille,
        @NotNull(message = "Le prix est obligatoire")
        @Positive(message = "Le prix doit être positif") BigDecimal prix) {
}
