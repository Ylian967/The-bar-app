package fr.barapp.barapp.repository;

import fr.barapp.barapp.entity.Commande;
import fr.barapp.barapp.entity.enums.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByStatutNot(StatutCommande statut);
}
