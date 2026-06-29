package fr.barapp.barapp.entity.enums;

/** Étape de préparation d'un cocktail commandé. */
public enum StatutPreparation {
    PREPARATION_INGREDIENTS,
    ASSEMBLAGE,
    DRESSAGE,
    TERMINEE;

    /** Étape suivante (ou la même si déjà terminée). */
    public StatutPreparation suivant() {
        return this == TERMINEE ? this : values()[ordinal() + 1];
    }
}
