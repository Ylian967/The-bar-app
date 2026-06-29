package fr.barapp.barapp.dto;

import java.util.List;

/** Un cocktail proposé par le catalogue externe (TheCocktailDB), avant import. */
public record CocktailExterneDto(
        String idExterne,
        String nom,
        String imageUrl,
        String description,
        List<String> ingredients) {
}
