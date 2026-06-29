package fr.barapp.barapp.security;

import fr.barapp.barapp.entity.Utilisateur;
import fr.barapp.barapp.repository.UtilisateurRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Charge un utilisateur depuis la base pour Spring Security. */
@Service
public class UtilisateurDetailsService implements UserDetailsService {

    private final UtilisateurRepository repository;

    public UtilisateurDetailsService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Utilisateur u = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + email));
        return User.withUsername(u.getEmail())
                .password(u.getMotDePasse())
                .authorities(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
                .build();
    }
}
