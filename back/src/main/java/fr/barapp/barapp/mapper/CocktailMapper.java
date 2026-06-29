package fr.barapp.barapp.mapper;

import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.TailleDto;
import fr.barapp.barapp.entity.Cocktail;
import fr.barapp.barapp.entity.Ingredient;

import java.util.List;

/** Conversion Cocktail (entité) -> CocktailDto (réponse API). */
public final class CocktailMapper {

    private CocktailMapper() {
    }

    public static CocktailDto versDto(Cocktail c) {
        List<TailleDto> tailles = c.getTailles().stream()
                .map(t -> new TailleDto(t.getTaille(), t.getPrix()))
                .toList();
        List<String> ingredients = c.getIngredients().stream()
                .map(Ingredient::getNom)
                .toList();
        return new CocktailDto(
                c.getId(),
                c.getNom(),
                c.getAccroche(),
                c.getDescription(),
                c.getImageUrl(),
                c.getCategorie().getId(),
                c.getCategorie().getNom(),
                tailles,
                ingredients);
    }
}
