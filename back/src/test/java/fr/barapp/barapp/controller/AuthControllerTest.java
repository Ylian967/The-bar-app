package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.LoginRequest;
import fr.barapp.barapp.dto.LoginResponse;
import fr.barapp.barapp.entity.Utilisateur;
import fr.barapp.barapp.entity.enums.Role;
import fr.barapp.barapp.repository.UtilisateurRepository;
import fr.barapp.barapp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private UtilisateurRepository utilisateurRepository;
    @InjectMocks
    private AuthController controller;

    @Test
    void login_renvoie_un_token_et_le_role() {
        Utilisateur u = new Utilisateur();
        u.setNom("Ylian");
        u.setEmail("barmaker@barapp.fr");
        u.setRole(Role.BARMAKER);
        when(utilisateurRepository.findByEmail("barmaker@barapp.fr")).thenReturn(Optional.of(u));
        when(jwtService.genererToken("barmaker@barapp.fr", "BARMAKER")).thenReturn("le-token");

        LoginResponse resp = controller.login(new LoginRequest("barmaker@barapp.fr", "Barmaker123"));

        assertThat(resp.token()).isEqualTo("le-token");
        assertThat(resp.role()).isEqualTo("BARMAKER");
        assertThat(resp.nom()).isEqualTo("Ylian");
    }
}
