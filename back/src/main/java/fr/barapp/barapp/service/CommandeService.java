package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.CommandeDto;
import fr.barapp.barapp.dto.CommandeRequest;
import fr.barapp.barapp.dto.LigneCommandeRequest;
import fr.barapp.barapp.entity.Cocktail;
import fr.barapp.barapp.entity.Commande;
import fr.barapp.barapp.entity.CocktailTaille;
import fr.barapp.barapp.entity.LigneCommande;
import fr.barapp.barapp.entity.enums.StatutCommande;
import fr.barapp.barapp.entity.enums.StatutPreparation;
import fr.barapp.barapp.entity.enums.Taille;
import fr.barapp.barapp.exception.RequeteInvalideException;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.mapper.CommandeMapper;
import fr.barapp.barapp.repository.CocktailRepository;
import fr.barapp.barapp.repository.CommandeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/** Logique métier des commandes (passage + avancement de préparation). */
@Service
@Transactional
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final CocktailRepository cocktailRepository;

    public CommandeService(CommandeRepository commandeRepository, CocktailRepository cocktailRepository) {
        this.commandeRepository = commandeRepository;
        this.cocktailRepository = cocktailRepository;
    }

    /** Un client passe une commande. */
    public CommandeDto passer(CommandeRequest req) {
        Commande commande = new Commande();
        commande.setClientNom(req.clientNom());
        for (LigneCommandeRequest l : req.lignes()) {
            commande.getLignes().add(construireLigne(commande, l));
        }
        return CommandeMapper.versDto(commandeRepository.save(commande));
    }

    @Transactional(readOnly = true)
    public CommandeDto trouver(Long id) {
        return CommandeMapper.versDto(getOuErreur(id));
    }

    /** Commandes à traiter par le barman (tout sauf terminées). */
    @Transactional(readOnly = true)
    public List<CommandeDto> aTraiter() {
        return commandeRepository.findByStatutNotOrderByDateCreationAsc(StatutCommande.TERMINEE)
                .stream().map(CommandeMapper::versDto).toList();
    }

    /** Commandes terminées (historique du barman). */
    @Transactional(readOnly = true)
    public List<CommandeDto> terminees() {
        return commandeRepository.findByStatutOrderByDateCreationDesc(StatutCommande.TERMINEE)
                .stream().map(CommandeMapper::versDto).toList();
    }

    /** Commandes d'un client (« Mes commandes »). */
    @Transactional(readOnly = true)
    public List<CommandeDto> parClient(String clientNom) {
        return commandeRepository.findByClientNomIgnoreCaseOrderByDateCreationDesc(clientNom)
                .stream().map(CommandeMapper::versDto).toList();
    }

    /** Le barman fait avancer un cocktail d'une étape ; met à jour le statut global. */
    public CommandeDto avancerLigne(Long commandeId, Long ligneId) {
        Commande commande = getOuErreur(commandeId);
        LigneCommande ligne = commande.getLignes().stream()
                .filter(l -> l.getId().equals(ligneId))
                .findFirst()
                .orElseThrow(() -> RessourceIntrouvableException.of("Ligne de commande", ligneId));

        if (ligne.getStatutPreparation() == StatutPreparation.TERMINEE) {
            throw new RequeteInvalideException("Ce cocktail est déjà terminé");
        }
        ligne.setStatutPreparation(ligne.getStatutPreparation().suivant());
        majStatutCommande(commande);
        return CommandeMapper.versDto(commandeRepository.save(commande));
    }

    /** Règle d'or : recalcule le statut de la commande selon ses lignes. */
    private void majStatutCommande(Commande commande) {
        boolean toutesTerminees = commande.getLignes().stream()
                .allMatch(l -> l.getStatutPreparation() == StatutPreparation.TERMINEE);
        boolean demarree = commande.getLignes().stream()
                .anyMatch(l -> l.getStatutPreparation() != StatutPreparation.PREPARATION_INGREDIENTS);

        if (toutesTerminees) {
            commande.setStatut(StatutCommande.TERMINEE);
        } else if (demarree) {
            commande.setStatut(StatutCommande.EN_PREPARATION);
        } else {
            commande.setStatut(StatutCommande.COMMANDEE);
        }
    }

    private LigneCommande construireLigne(Commande commande, LigneCommandeRequest req) {
        Cocktail cocktail = cocktailRepository.findById(req.cocktailId())
                .orElseThrow(() -> RessourceIntrouvableException.of("Cocktail", req.cocktailId()));
        LigneCommande ligne = new LigneCommande();
        ligne.setCommande(commande);
        ligne.setCocktail(cocktail);
        ligne.setTaille(req.taille());
        ligne.setQuantite(req.quantite());
        ligne.setPrix(prixPourTaille(cocktail, req.taille()));
        return ligne;
    }

    private BigDecimal prixPourTaille(Cocktail cocktail, Taille taille) {
        return cocktail.getTailles().stream()
                .filter(t -> t.getTaille() == taille)
                .map(CocktailTaille::getPrix)
                .findFirst()
                .orElseThrow(() -> new RequeteInvalideException(
                        "Le cocktail « " + cocktail.getNom() + " » n'est pas disponible en taille " + taille));
    }

    private Commande getOuErreur(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> RessourceIntrouvableException.of("Commande", id));
    }
}
