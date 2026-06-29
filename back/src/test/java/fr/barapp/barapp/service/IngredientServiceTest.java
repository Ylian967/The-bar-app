package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.IngredientRequest;
import fr.barapp.barapp.entity.Ingredient;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository repository;
    @InjectMocks
    private IngredientService service;

    private Ingredient ingredient(Long id, String nom) {
        Ingredient i = new Ingredient();
        i.setId(id);
        i.setNom(nom);
        return i;
    }

    @Test
    void lister_renvoie_les_ingredients() {
        when(repository.findAll()).thenReturn(List.of(ingredient(1L, "Menthe"), ingredient(2L, "Rhum blanc")));
        assertThat(service.lister()).hasSize(2);
    }

    @Test
    void creer_enregistre_l_ingredient() {
        when(repository.save(any(Ingredient.class))).thenAnswer(i -> i.getArgument(0));
        assertThat(service.creer(new IngredientRequest("Citron vert", null)).nom()).isEqualTo("Citron vert");
    }

    @Test
    void modifier_inexistant_leve_une_erreur() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.modifier(99L, new IngredientRequest("X", null)))
                .isInstanceOf(RessourceIntrouvableException.class);
    }

    @Test
    void supprimer_appelle_le_repository() {
        Ingredient i = ingredient(1L, "Menthe");
        when(repository.findById(1L)).thenReturn(Optional.of(i));
        service.supprimer(1L);
        verify(repository).delete(i);
    }
}
