package fr.barapp.barapp.repository;

import fr.barapp.barapp.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByNomIgnoreCase(String nom);
}
