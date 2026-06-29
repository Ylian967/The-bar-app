package fr.barapp.barapp.controller;

import fr.barapp.barapp.dto.LoginRequest;
import fr.barapp.barapp.dto.LoginResponse;
import fr.barapp.barapp.entity.Utilisateur;
import fr.barapp.barapp.repository.UtilisateurRepository;
import fr.barapp.barapp.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Authentification : connexion et délivrance du jeton JWT. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UtilisateurRepository utilisateurRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.motDePasse()));
        Utilisateur u = utilisateurRepository.findByEmail(req.email()).orElseThrow();
        String token = jwtService.genererToken(u.getEmail(), u.getRole().name());
        return new LoginResponse(token, u.getEmail(), u.getRole().name(), u.getNom());
    }
}
