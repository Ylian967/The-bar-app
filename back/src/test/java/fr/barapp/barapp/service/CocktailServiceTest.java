package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.CocktailRequest;
import fr.barapp.barapp.dto.TailleRequest;
import fr.barapp.barapp.entity.Categorie;
import fr.barapp.barapp.entity.Cocktail;
import fr.barapp.barapp.entity.Ingredient;
import fr.barapp.barapp.entity.enums.Taille;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.repository.CategorieRepository;
import fr.barapp.barapp.repository.CocktailRepository;
import fr.barapp.barapp.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {

    @Mock
    private CocktailRepository cocktailRepository;
    @Mock
    private CategorieRepository categorieRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private CocktailService service;

    private Categorie classiques() {
        Categorie c = new Categorie();
        c.setId(1L);
        c.setNom("Classiques");
        return c;
    }

    private Cocktail mojito() {
        Cocktail c = new Cocktail();
        c.setId(1L);
        c.setNom("Mojito");
        c.setCategorie(classiques());
        return c;
    }

    private CocktailRequest requete() {
        return new CocktailRequest("Mojito", "Frais & mentholé", "Frais", "/images/cocktails/mojito.jpg", 1L,
                List.of(new TailleRequest(Taille.S, new BigDecimal("7.00"))), Set.of(1L));
    }

    @Test
    void lister_sans_filtre_renvoie_tout() {
        when(cocktailRepository.findAll()).thenReturn(List.of(mojito()));
        assertThat(service.lister(null, false)).hasSize(1);
    }

    @Test
    void lister_par_categorie() {
        when(cocktailRepository.findByCategorieId(1L)).thenReturn(List.of(mojito()));
        assertThat(service.lister(1L, false)).hasSize(1);
    }

    @Test
    void trouver_inexistant_leve_une_erreur() {
        when(cocktailRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.trouver(99L)).isInstanceOf(RessourceIntrouvableException.class);
    }

    @Test
    void creer_construit_le_cocktail_complet() {
        Ingredient menthe = new Ingredient();
        menthe.setId(1L);
        menthe.setNom("Menthe");
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(classiques()));
        when(ingredientRepository.findAllById(any())).thenReturn(List.of(menthe));
        when(cocktailRepository.save(any(Cocktail.class))).thenAnswer(i -> i.getArgument(0));

        CocktailDto dto = service.creer(requete());

        assertThat(dto.nom()).isEqualTo("Mojito");
        assertThat(dto.categorie()).isEqualTo("Classiques");
        assertThat(dto.tailles()).hasSize(1);
        assertThat(dto.tailles().get(0).prix()).isEqualByComparingTo("7.00");
        assertThat(dto.ingredients()).containsExactly("Menthe");
    }

    @Test
    void creer_avec_categorie_inexistante_leve_une_erreur() {
        when(categorieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.creer(requete()))
                .isInstanceOf(RessourceIntrouvableException.class);
    }

    @Test
    void modifier_met_a_jour_le_cocktail() {
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(mojito()));
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(classiques()));
        when(ingredientRepository.findAllById(any())).thenReturn(List.of());
        when(cocktailRepository.save(any(Cocktail.class))).thenAnswer(i -> i.getArgument(0));

        CocktailDto dto = service.modifier(1L, requete());

        assertThat(dto.nom()).isEqualTo("Mojito");
        assertThat(dto.tailles()).hasSize(1);
    }

    @Test
    void supprimer_appelle_le_repository() {
        Cocktail c = mojito();
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(c));
        service.supprimer(1L);
        verify(cocktailRepository).delete(c);
    }
}
