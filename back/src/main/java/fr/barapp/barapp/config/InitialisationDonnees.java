package fr.barapp.barapp.config;

import fr.barapp.barapp.entity.Utilisateur;
import fr.barapp.barapp.entity.enums.Role;
import fr.barapp.barapp.repository.UtilisateurRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Crée le compte barmaker de démonstration au démarrage (s'il n'existe pas). */
@Component
public class InitialisationDonnees implements ApplicationRunner {

    private static final String EMAIL_BARMAKER = "barmaker@barapp.fr";

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;

    public InitialisationDonnees(UtilisateurRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (repository.findByEmail(EMAIL_BARMAKER).isPresent()) {
            return;
        }
        Utilisateur barmaker = new Utilisateur();
        barmaker.setNom("Ylian");
        barmaker.setEmail(EMAIL_BARMAKER);
        barmaker.setMotDePasse(passwordEncoder.encode("Barmaker123"));
        barmaker.setRole(Role.BARMAKER);
        repository.save(barmaker);
    }
}
