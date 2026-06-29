package fr.barapp.barapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Données reçues pour créer/modifier une catégorie. */
public record CategorieRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 80, message = "Le nom ne doit pas dépasser 80 caractères")
        String nom) {
}
