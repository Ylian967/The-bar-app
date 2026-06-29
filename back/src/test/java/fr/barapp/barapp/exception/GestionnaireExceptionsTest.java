package fr.barapp.barapp.exception;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;

class GestionnaireExceptionsTest {

    private final GestionnaireExceptions gestionnaire = new GestionnaireExceptions();

    @Test
    void ressource_introuvable_renvoie_404() {
        var reponse = gestionnaire.introuvable(new RessourceIntrouvableException("Cocktail introuvable"));
        assertThat(reponse.getStatusCode().value()).isEqualTo(404);
        assertThat(reponse.getBody()).containsEntry("status", 404);
    }

    @Test
    void requete_invalide_renvoie_400() {
        var reponse = gestionnaire.requeteInvalide(new RequeteInvalideException("Mauvaise requête"));
        assertThat(reponse.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void authentification_echouee_renvoie_401() {
        var reponse = gestionnaire.authentification(new BadCredentialsException("ko"));
        assertThat(reponse.getStatusCode().value()).isEqualTo(401);
    }
}
