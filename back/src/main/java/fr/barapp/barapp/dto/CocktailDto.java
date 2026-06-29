package fr.barapp.barapp.dto;

import java.util.List;

/** Cocktail renvoyé par l'API (carte côté client). */
public record CocktailDto(
        Long id,
        String nom,
        String description,
        String imageUrl,
        Long categorieId,
        String categorie,
        List<TailleDto> tailles,
        List<String> ingredients) {
}
