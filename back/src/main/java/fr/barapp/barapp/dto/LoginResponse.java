package fr.barapp.barapp.dto;

/** Réponse renvoyée après une connexion réussie. */
public record LoginResponse(String token, String email, String role, String nom) {
}
