package fr.barapp.barapp.entity;

import fr.barapp.barapp.entity.enums.StatutCommande;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Une commande passée par un client. */
@Entity
@Table(name = "commande")
@Getter
@Setter
@NoArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Client connecté éventuel (peut être null : commande au comptoir). */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Utilisateur client;

    /** Prénom ou n° de table saisi par un client non authentifié. */
    @Column(name = "client_nom", length = 100)
    private String clientNom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutCommande statut = StatutCommande.COMMANDEE;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignes = new ArrayList<>();
}
