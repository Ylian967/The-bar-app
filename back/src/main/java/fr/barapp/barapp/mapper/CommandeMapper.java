package fr.barapp.barapp.mapper;

import fr.barapp.barapp.dto.CommandeDto;
import fr.barapp.barapp.dto.LigneCommandeDto;
import fr.barapp.barapp.entity.Commande;
import fr.barapp.barapp.entity.LigneCommande;

import java.math.BigDecimal;
import java.util.List;

/** Conversion Commande (entité) -> CommandeDto (réponse API). */
public final class CommandeMapper {

    private CommandeMapper() {
    }

    public static CommandeDto versDto(Commande c) {
        List<LigneCommandeDto> lignes = c.getLignes().stream()
                .map(CommandeMapper::versLigneDto)
                .toList();
        BigDecimal total = c.getLignes().stream()
                .map(l -> l.getPrix().multiply(BigDecimal.valueOf(l.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CommandeDto(c.getId(), c.getClientNom(), c.getStatut(),
                c.getDateCreation(), lignes, total);
    }

    private static LigneCommandeDto versLigneDto(LigneCommande l) {
        return new LigneCommandeDto(
                l.getId(),
                l.getCocktail().getId(),
                l.getCocktail().getNom(),
                l.getTaille(),
                l.getPrix(),
                l.getQuantite(),
                l.getStatutPreparation());
    }
}
