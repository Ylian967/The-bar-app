package fr.barapp.barapp.repository;

import fr.barapp.barapp.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByNomIgnoreCase(String nom);

    Optional<Ingredient> findByNomIgnoreCase(String nom);
}
