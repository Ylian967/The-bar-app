package fr.barapp.barapp.dto;

/** Ingrédient renvoyé par l'API. */
public record IngredientDto(Long id, String nom, String imageUrl, boolean disponible) {
}
