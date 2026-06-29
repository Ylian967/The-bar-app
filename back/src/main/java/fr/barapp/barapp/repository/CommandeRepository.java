package fr.barapp.barapp.repository;

import fr.barapp.barapp.entity.Commande;
import fr.barapp.barapp.entity.enums.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    /** Commandes à traiter par le barman (tout sauf les terminées), plus anciennes d'abord. */
    List<Commande> findByStatutNotOrderByDateCreationAsc(StatutCommande statut);

    /** Commandes d'un client (par prénom / table), plus récentes d'abord. */
    List<Commande> findByClientNomIgnoreCaseOrderByDateCreationDesc(String clientNom);

    /** Commandes ayant un statut donné (ex : terminées), plus récentes d'abord. */
    List<Commande> findByStatutOrderByDateCreationDesc(StatutCommande statut);
}
