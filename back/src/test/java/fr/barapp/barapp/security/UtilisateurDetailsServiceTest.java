package fr.barapp.barapp.security;

import fr.barapp.barapp.entity.Utilisateur;
import fr.barapp.barapp.entity.enums.Role;
import fr.barapp.barapp.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilisateurDetailsServiceTest {

    @Mock
    private UtilisateurRepository repository;
    @InjectMocks
    private UtilisateurDetailsService service;

    @Test
    void charge_l_utilisateur_avec_son_role() {
        Utilisateur u = new Utilisateur();
        u.setEmail("barmaker@barapp.fr");
        u.setMotDePasse("hash");
        u.setRole(Role.BARMAKER);
        when(repository.findByEmail("barmaker@barapp.fr")).thenReturn(Optional.of(u));

        UserDetails details = service.loadUserByUsername("barmaker@barapp.fr");

        assertThat(details.getUsername()).isEqualTo("barmaker@barapp.fr");
        assertThat(details.getAuthorities()).extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_BARMAKER");
    }

    @Test
    void utilisateur_inconnu_leve_une_erreur() {
        when(repository.findByEmail("x@x.fr")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.loadUserByUsername("x@x.fr"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
