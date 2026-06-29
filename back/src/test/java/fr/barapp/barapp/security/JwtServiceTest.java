package fr.barapp.barapp.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private final JwtService jwtService =
            new JwtService("cle-secrete-de-test-suffisamment-longue-2026", 86400000L);

    @Test
    void genere_un_token_valide_et_relit_l_email() {
        String token = jwtService.genererToken("barmaker@barapp.fr", "BARMAKER");
        assertThat(jwtService.estValide(token)).isTrue();
        assertThat(jwtService.extraireEmail(token)).isEqualTo("barmaker@barapp.fr");
    }

    @Test
    void un_token_invalide_est_rejete() {
        assertThat(jwtService.estValide("ceci.nest.pas.un.token")).isFalse();
    }
}
