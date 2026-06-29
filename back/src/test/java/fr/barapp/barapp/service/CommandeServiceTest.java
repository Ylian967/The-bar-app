package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.CommandeDto;
import fr.barapp.barapp.dto.CommandeRequest;
import fr.barapp.barapp.dto.LigneCommandeRequest;
import fr.barapp.barapp.entity.*;
import fr.barapp.barapp.entity.enums.StatutCommande;
import fr.barapp.barapp.entity.enums.StatutPreparation;
import fr.barapp.barapp.entity.enums.Taille;
import fr.barapp.barapp.exception.RequeteInvalideException;
import fr.barapp.barapp.repository.CocktailRepository;
import fr.barapp.barapp.repository.CommandeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeServiceTest {

    @Mock
    private CommandeRepository commandeRepository;
    @Mock
    private CocktailRepository cocktailRepository;
    @InjectMocks
    private CommandeService service;

    private Cocktail mojito() {
        Categorie cat = new Categorie();
        cat.setId(1L);
        cat.setNom("Classiques");
        Cocktail c = new Cocktail();
        c.setId(1L);
        c.setNom("Mojito");
        c.setCategorie(cat);
        CocktailTaille m = new CocktailTaille();
        m.setCocktail(c);
        m.setTaille(Taille.M);
        m.setPrix(new BigDecimal("8.00"));
        c.getTailles().add(m);
        return c;
    }

    @Test
    void passer_calcule_le_prix_et_initialise_les_statuts() {
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(mojito()));
        when(commandeRepository.save(any(Commande.class))).thenAnswer(i -> i.getArgument(0));

        CommandeRequest req = new CommandeRequest("Ylian",
                List.of(new LigneCommandeRequest(1L, Taille.M, 2)));
        CommandeDto dto = service.passer(req);

        assertThat(dto.statut()).isEqualTo(StatutCommande.COMMANDEE);
        assertThat(dto.lignes()).hasSize(1);
        assertThat(dto.lignes().get(0).statutPreparation())
                .isEqualTo(StatutPreparation.PREPARATION_INGREDIENTS);
        assertThat(dto.total()).isEqualByComparingTo("16.00"); // 8.00 x 2
    }

    @Test
    void passer_refuse_une_taille_indisponible() {
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(mojito()));
        CommandeRequest req = new CommandeRequest("Ylian",
                List.of(new LigneCommandeRequest(1L, Taille.L, 1))); // pas de taille L

        assertThatThrownBy(() -> service.passer(req))
                .isInstanceOf(RequeteInvalideException.class);
    }

    @Test
    void avancer_le_dernier_cocktail_termine_la_commande() {
        Commande cmd = new Commande();
        cmd.setId(10L);
        cmd.setStatut(StatutCommande.EN_PREPARATION);
        LigneCommande ligne = new LigneCommande();
        ligne.setId(100L);
        ligne.setCommande(cmd);
        ligne.setCocktail(mojito());
        ligne.setTaille(Taille.M);
        ligne.setPrix(new BigDecimal("8.00"));
        ligne.setQuantite(1);
        ligne.setStatutPreparation(StatutPreparation.DRESSAGE);
        cmd.getLignes().add(ligne);

        when(commandeRepository.findById(10L)).thenReturn(Optional.of(cmd));
        when(commandeRepository.save(any(Commande.class))).thenAnswer(i -> i.getArgument(0));

        CommandeDto dto = service.avancerLigne(10L, 100L);

        assertThat(dto.lignes().get(0).statutPreparation()).isEqualTo(StatutPreparation.TERMINEE);
        assertThat(dto.statut()).isEqualTo(StatutCommande.TERMINEE);
    }
}
