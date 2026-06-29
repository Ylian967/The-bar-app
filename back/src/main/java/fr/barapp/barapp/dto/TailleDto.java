package fr.barapp.barapp.dto;

import fr.barapp.barapp.entity.enums.Taille;

import java.math.BigDecimal;

/** Une taille et son prix, renvoyée par l'API. */
public record TailleDto(Taille taille, BigDecimal prix) {
}
