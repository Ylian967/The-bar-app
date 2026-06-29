package fr.barapp.barapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Un ingrédient pouvant composer des cocktails. */
@Entity
@Table(name = "ingredient")
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    /** En stock : si false, les cocktails qui l'utilisent ne sont plus réalisables. */
    @Column(nullable = false)
    private boolean disponible = true;
}
