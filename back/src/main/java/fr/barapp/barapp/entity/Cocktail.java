package fr.barapp.barapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Un cocktail de la carte. */
@Entity
@Table(name = "cocktail")
@Getter
@Setter
@NoArgsConstructor
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nom;

    /** Courte accroche affichée sous le nom (ex : « Frais & mentholé »). */
    @Column(length = 160)
    private String accroche;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    /** Servi : sélectionné par le barmaker, visible des clients. */
    @Column(name = "du_jour", nullable = false)
    private boolean duJour = true;

    /** Mis en avant dans la section « Cocktails du jour » côté client. */
    @Column(nullable = false)
    private boolean favori = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailTaille> tailles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "cocktail_ingredient",
            joinColumns = @JoinColumn(name = "cocktail_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new LinkedHashSet<>();
}
