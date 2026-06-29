package fr.barapp.barapp.entity;

import fr.barapp.barapp.entity.enums.StatutPreparation;
import fr.barapp.barapp.entity.enums.Taille;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/** Un cocktail commandé dans une commande, avec son avancement de préparation. */
@Entity
@Table(name = "ligne_commande")
@Getter
@Setter
@NoArgsConstructor
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Taille taille;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal prix;

    @Column(nullable = false)
    private Integer quantite = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_preparation", nullable = false, length = 30)
    private StatutPreparation statutPreparation = StatutPreparation.PREPARATION_INGREDIENTS;
}
