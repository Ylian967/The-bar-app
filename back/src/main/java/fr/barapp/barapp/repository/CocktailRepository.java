package fr.barapp.barapp.repository;

import fr.barapp.barapp.entity.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    List<Cocktail> findByCategorieId(Long categorieId);

    List<Cocktail> findByDuJourTrue();

    List<Cocktail> findByCategorieIdAndDuJourTrue(Long categorieId);
}
