package fr.barapp.barapp.entity;

import fr.barapp.barapp.entity.enums.Taille;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/** Le prix d'un cocktail pour une taille donnée (S, M ou L). */
@Entity
@Table(name = "cocktail_taille")
@Getter
@Setter
@NoArgsConstructor
public class CocktailTaille {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Taille taille;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal prix;
}
