package fr.barapp.barapp.repository;

import fr.barapp.barapp.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByNomIgnoreCase(String nom);
}
