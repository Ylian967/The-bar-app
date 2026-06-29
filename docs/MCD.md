# 🧩 MCD — Le Bar'app

Modèle Conceptuel de Données. Il décrit **les tables de la base** et **leurs liens**.

## Les tables (entités)

### 1. utilisateur
Représente un client OU un barmaker (le champ `role` les distingue).
| Champ | Type | Note |
|-------|------|------|
| id | bigint (PK) | identifiant unique |
| nom | varchar | |
| email | varchar (unique) | sert à se connecter |
| mot_de_passe | varchar | stocké haché (BCrypt) |
| role | varchar | `CLIENT` ou `BARMAKER` |

### 2. categorie
Une rubrique de la carte (ex : « Classiques », « Sans alcool »).
| Champ | Type | Note |
|-------|------|------|
| id | bigint (PK) | |
| nom | varchar | |

### 3. cocktail
Une boisson de la carte. Appartient à une catégorie.
| Champ | Type | Note |
|-------|------|------|
| id | bigint (PK) | |
| nom | varchar | |
| description | text | |
| image_url | varchar | pour l'ergonomie (image du cocktail) |
| categorie_id | bigint (FK → categorie) | |

### 4. ingredient
La liste des ingrédients possibles (ex : rhum, menthe, citron vert).
| Champ | Type |
|-------|------|
| id | bigint (PK) |
| nom | varchar |

### 5. cocktail_ingredient  (table de liaison N–N)
Quels ingrédients composent quel cocktail.
| Champ | Type |
|-------|------|
| cocktail_id | bigint (FK → cocktail) |
| ingredient_id | bigint (FK → ingredient) |

### 6. cocktail_taille
Le **prix par taille** d'un cocktail (un cocktail a plusieurs tailles).
| Champ | Type | Note |
|-------|------|------|
| id | bigint (PK) | |
| cocktail_id | bigint (FK → cocktail) | |
| taille | varchar | `S`, `M` ou `L` |
| prix | numeric(6,2) | prix pour cette taille |

### 7. commande
Une commande passée par un client.
| Champ | Type | Note |
|-------|------|------|
| id | bigint (PK) | |
| client_id | bigint (FK → utilisateur, **nullable**) | client connecté éventuel |
| client_nom | varchar | prénom / table (client non authentifié — cas par défaut) |
| statut | varchar | `COMMANDEE`, `EN_PREPARATION`, `TERMINEE` |
| date_creation | timestamp | |

### 8. ligne_commande
Un cocktail commandé dans une commande (avec sa taille, son prix figé, et son **avancement de préparation**).
| Champ | Type | Note |
|-------|------|------|
| id | bigint (PK) | |
| commande_id | bigint (FK → commande) | |
| cocktail_id | bigint (FK → cocktail) | |
| taille | varchar | S/M/L choisie |
| prix | numeric(6,2) | prix au moment de la commande |
| quantite | int | |
| statut_preparation | varchar | `PREPARATION_INGREDIENTS`, `ASSEMBLAGE`, `DRESSAGE`, `TERMINEE` |

## Les liens (cardinalités)
- un **utilisateur** (client) passe 0..N **commandes** ; une commande a 1 client.
- une **catégorie** contient 0..N **cocktails** ; un cocktail a 1 catégorie.
- un **cocktail** a 1..N **cocktail_taille** (S/M/L) ; chaque taille appartient à 1 cocktail.
- un **cocktail** a 0..N **ingrédients**, un **ingrédient** est dans 0..N cocktails (N–N via `cocktail_ingredient`).
- une **commande** a 1..N **ligne_commande** ; chaque ligne référence 1 cocktail.

## Schéma (vue d'ensemble)
```
utilisateur(role)──< commande ──< ligne_commande >── cocktail >── categorie
                                                        │
                                          cocktail_taille (prix par S/M/L)
                                                        │
                              cocktail_ingredient >── ingredient
```

## Règle métier clé
Quand **toutes** les `ligne_commande` d'une commande passent à `statut_preparation = TERMINEE`,
la `commande.statut` passe automatiquement à `TERMINEE`.
Dès qu'au moins une ligne avance, la commande passe de `COMMANDEE` à `EN_PREPARATION`.
