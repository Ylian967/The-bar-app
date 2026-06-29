package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.CategorieDto;
import fr.barapp.barapp.dto.CategorieRequest;
import fr.barapp.barapp.entity.Categorie;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.repository.CategorieRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategorieServiceTest {

    @Mock
    private CategorieRepository repository;
    @InjectMocks
    private CategorieService service;

    private Categorie categorie(Long id, String nom) {
        Categorie c = new Categorie();
        c.setId(id);
        c.setNom(nom);
        return c;
    }

    @Test
    void lister_renvoie_toutes_les_categories() {
        when(repository.findAll()).thenReturn(List.of(categorie(1L, "Classiques"), categorie(2L, "Signatures")));
        List<CategorieDto> result = service.lister();
        assertThat(result).extracting(CategorieDto::nom).containsExactly("Classiques", "Signatures");
    }

    @Test
    void trouver_renvoie_la_categorie() {
        when(repository.findById(1L)).thenReturn(Optional.of(categorie(1L, "Classiques")));
        assertThat(service.trouver(1L).nom()).isEqualTo("Classiques");
    }

    @Test
    void trouver_inexistante_leve_une_erreur() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.trouver(99L))
                .isInstanceOf(RessourceIntrouvableException.class);
    }

    @Test
    void creer_enregistre_et_renvoie_le_dto() {
        when(repository.save(any(Categorie.class))).thenAnswer(i -> i.getArgument(0));
        CategorieDto dto = service.creer(new CategorieRequest("Mocktails"));
        assertThat(dto.nom()).isEqualTo("Mocktails");
        verify(repository).save(any(Categorie.class));
    }

    @Test
    void modifier_met_a_jour_le_nom() {
        when(repository.findById(1L)).thenReturn(Optional.of(categorie(1L, "Ancien")));
        when(repository.save(any(Categorie.class))).thenAnswer(i -> i.getArgument(0));
        assertThat(service.modifier(1L, new CategorieRequest("Nouveau")).nom()).isEqualTo("Nouveau");
    }

    @Test
    void supprimer_appelle_le_repository() {
        Categorie c = categorie(1L, "Classiques");
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        service.supprimer(1L);
        verify(repository).delete(c);
    }
}
