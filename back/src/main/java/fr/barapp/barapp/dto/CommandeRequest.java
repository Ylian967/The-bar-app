package fr.barapp.barapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/** Données reçues quand un client passe une commande. */
public record CommandeRequest(
        @NotBlank(message = "Indiquez un prénom ou un n° de table")
        @Size(max = 100) String clientNom,

        @NotEmpty(message = "La commande doit contenir au moins un cocktail")
        List<@Valid LigneCommandeRequest> lignes) {
}
