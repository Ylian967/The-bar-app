package fr.barapp.barapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

/** Données reçues pour créer/modifier un cocktail. */
public record CocktailRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 120, message = "Le nom ne doit pas dépasser 120 caractères")
        String nom,

        String description,

        @Size(max = 255, message = "L'URL d'image est trop longue")
        String imageUrl,

        @NotNull(message = "La catégorie est obligatoire")
        Long categorieId,

        @NotEmpty(message = "Au moins une taille est requise")
        List<@Valid TailleRequest> tailles,

        Set<Long> ingredientIds) {
}
