# 🎨 Guide d'adaptation des maquettes — Le Bar'app (texte exact à recopier)

Kit de base : **Drink ordering app** (café/vert). Client = téléphone · Barman = tablette.
Garder le **vert** et la **typo** du kit partout.
👉 Tout ce qui est entre « guillemets » ci-dessous est **le texte exact à taper** dans Figma.

---

## 🍸 Le catalogue complet (textes exacts à réutiliser partout)

### Catégories
« Carte du jour » · « Classiques » · « Signatures » · « Mocktails » · « Shots »

### Cocktails (nom · accroche courte · description longue · ingrédients · prix S/M/L)

**1. Mojito** — Classiques
- Accroche (pour les cartes) : « Frais & mentholé »
- Description : « Le grand classique cubain : rhum blanc, menthe fraîche et citron vert, allongé d'eau gazeuse. Frais et désaltérant, parfait pour démarrer la soirée. »
- Ingrédients : Rhum blanc · Menthe · Citron vert · Sucre de canne · Eau gazeuse
- Prix : S 7 € · M 8 € · L 10 €

**2. Margarita** — Classiques
- Accroche : « Acidulé & puissant »
- Description : « L'icône mexicaine : tequila, triple sec et jus de citron vert, servie avec un givrage de sel. Vive et rafraîchissante. »
- Ingrédients : Tequila · Triple sec · Citron vert · Sel
- Prix : S 8 € · M 9 € · L 11 €

**3. Piña Colada** — Classiques
- Accroche : « Doux & tropical »
- Description : « Évasion tropicale : rhum, lait de coco et jus d'ananas frais. Onctueux, crémeux et ensoleillé. »
- Ingrédients : Rhum · Lait de coco · Jus d'ananas
- Prix : S 8 € · M 9 € · L 11 €

**4. Cosmopolitan** — Signatures
- Accroche : « Chic & fruité »
- Description : « Élégant et acidulé : vodka, triple sec, jus de cranberry et un trait de citron vert. Le cocktail des grandes occasions. »
- Ingrédients : Vodka · Triple sec · Jus de cranberry · Citron vert
- Prix : S 9 € · M 10 € · L 12 €

**5. Espresso Martini** — Signatures
- Accroche : « Corsé & énergisant »
- Description : « Le cocktail qui réveille : vodka, café fraîchement extrait et liqueur de café. Onctueux, intense, légèrement sucré. »
- Ingrédients : Vodka · Café · Liqueur de café
- Prix : S 9 € · M 11 € · L 13 €

**6. Virgin Mojito** — Mocktails
- Accroche : « Sans alcool »
- Description : « Toute la fraîcheur du Mojito, sans alcool : menthe, citron vert, sucre de canne et eau gazeuse. Pour tout le monde. »
- Ingrédients : Menthe · Citron vert · Sucre de canne · Eau gazeuse
- Prix : S 5 € · M 6 € · L 7 €

### Compte barmaker de test (à afficher dans le README plus tard)
Email : « barmaker@barapp.fr » — Mot de passe : « Barmaker123 »

---

# 📱 CLIENT (téléphone)

## Écran 1 — La Carte (à partir de « Home »)
- [ ] Dupliquer le frame `Home` (Ctrl+D) → renommer **`01 - Carte`**.
- [ ] Titre `Home` → taper « La Carte ».
- [ ] (Optionnel) sous le titre, petit texte : « Le Bar'app · Cocktails du jour ».
- [ ] Barre de recherche : placeholder `Search` → « Rechercher un cocktail ».
- [ ] Onglets catégories, dans l'ordre : « Carte du jour » · « Classiques » · « Signatures » · « Mocktails » · « Shots ».
- [ ] Titre de la section des cartes horizontales `Recommend` → « Cocktails du jour ».
      Les 3 cartes : « Mojito » (8 €), « Margarita » (9 €), « Piña Colada » (9 €).
      (sous chaque nom, l'accroche : « Frais & mentholé », « Acidulé & puissant », « Doux & tropical »).
- [ ] Titre de la liste `Menu` → « Tous les cocktails ». Lignes (nom — accroche — prix « dès X € ») :
      - « Mojito » — « Frais & mentholé » — « dès 7 € »
      - « Margarita » — « Acidulé & puissant » — « dès 8 € »
      - « Cosmopolitan » — « Chic & fruité » — « dès 9 € »
      - « Espresso Martini » — « Corsé & énergisant » — « dès 9 € »
      - « Virgin Mojito » — « Sans alcool » — « dès 5 € »
- [ ] Remplacer tous les `$` par `€`.
- [ ] Barre du bas, 4 onglets → « Accueil » · « Panier » · « Mes commandes » · « Profil ».

## Écran 2 — Fiche cocktail (à partir de la fiche produit « Mocha »)
- [ ] Dupliquer le frame détail → renommer **`02 - Fiche cocktail`**.
- [ ] Nom `Mocha` → « Mojito ». Sous-titre `Coffee` → «      ».
- [ ] Image : garder le placeholder (remplacée à l'import).
- [ ] Bloc description : taper « Le grand classique cubain : rhum blanc, menthe fraîche et citron vert,
      allongé d'eau gazeuse. Frais et désaltérant, parfait pour démarrer la soirée. »
- [ ] **Ajouter une rubrique** « Ingrédients » (titre en gras), puis dessous, en pastilles ou en liste :
      « Rhum blanc » · « Menthe » · « Citron vert » · « Sucre de canne » · « Eau gazeuse ».
- [ ] **Taille** : titre « Taille ». Transformer les 2 boutons Hot/Iced en **3 boutons** :
      « S — 7 € » · « M — 8 € » · « L — 10 € » (duplique un bouton pour en avoir 3 ; M sélectionné par défaut).
- [ ] Garder le sélecteur de quantité (valeur par défaut « 1 »).
- [ ] Bouton `Add` → « Ajouter au panier ».
- [ ] Section produits liés `Recommend` → titre « Vous aimerez aussi » + « Margarita », « Cosmopolitan »
      (ou supprime cette section pour faire simple).

## Écran 3 — Panier / Commande (à partir de « Make Payment / Product list »)
- [ ] Dupliquer → renommer **`03 - Panier`**.
- [ ] Titre `Product list` → « Mon panier ».
- [ ] Lignes d'articles (exemple à mettre) :
      - « Mojito » · « Taille M » · quantité « 1 » · « 8 € »
      - « Margarita » · « Taille L » · quantité « 2 » · « 22 € »
- [ ] Bloc `Make Payment` → titre « Récapitulatif ».
      Lignes : « Sous-total » → « 30 € » · « Total » → « 30 € ».
- [ ] **Supprimer** le bloc « Payment channels / Cash ».
      (Option : à la place, un champ « Votre prénom » ou « Table n° ».)
- [ ] Bouton `Confirm` → « Commander ».

## Écran 4 — Mes commandes (à CONSTRUIRE avec les composants du kit)
- [ ] Nouveau frame téléphone → **`04 - Mes commandes`**.
- [ ] Titre « Mes commandes ». Deux onglets : « En cours » · « Historique » (style des onglets de l'écran 1).
- [ ] **Onglet « En cours »**, une carte commande :
      - Titre « Commande #1024 »
      - Date « 21 juin · 21h32 »
      - Contenu « Mojito (M) · Margarita (L) ×2 »
      - Total « 30 € »
      - Badge statut « En préparation » (badge orange/vert)
      - Suivi 3 étapes : « Commandée » — **« En préparation »** — « Terminée »
        (étape du milieu en vert plein, les autres en gris).
- [ ] **Onglet « Historique »**, une carte commande :
      - « Commande #1019 » · « 21 juin · 20h10 » · « Cosmopolitan (M) · Virgin Mojito (S) » · « 15 € »
      - Badge « Terminée » · les 3 étapes toutes en vert.

---

# 📊 BARMAN (tablette ~1280 × 800, mêmes composants en plus grand)

## Écran 5 — Login /admin
- [ ] Frame tablette → **`05 - Login admin`**.
- [ ] Carte centrée. Titre « Le Bar'app ». Sous-titre « Espace Barmaker ».
- [ ] Champ avec label « Email » (placeholder « barmaker@barapp.fr »).
- [ ] Champ avec label « Mot de passe » (placeholder « •••••••• »).
- [ ] Bouton vert « Se connecter ».
- [ ] (Optionnel) petit lien « Mot de passe oublié ? ».

## Écran 6 — Commandes à traiter
- [ ] Frame tablette → **`06 - Commandes a traiter`**.
- [ ] En-tête : titre « Commandes à traiter ». À droite « Barmaker · Ylian » + bouton « Déconnexion ».
- [ ] Filtres (onglets) : « À faire » · « En cours » · « Terminées ».
- [ ] Grille de cartes commande (exemple, 3 cartes) :
      - « Commande #1024 » · « 21h32 » · « 3 cocktails » · badge « En préparation » · bouton « Traiter »
      - « Commande #1025 » · « 21h40 » · « 1 cocktail »  · badge « Commandée »    · bouton « Traiter »
      - « Commande #1026 » · « 21h45 » · « 2 cocktails » · badge « Commandée »    · bouton « Traiter »

## Écran 7 — Détail commande + avancement
- [ ] Frame tablette → **`07 - Detail commande`**.
- [ ] En-tête : « ← Commande #1024 » · « 21h32 ».
- [ ] Liste des cocktails de la commande, une ligne chacun :
      - « Mojito » · « Taille M » · « ×1 » · étape actuelle « Assemblage » · bouton « Étape suivante »
      - « Margarita » · « Taille L » · « ×2 » · étape actuelle « Préparation des ingrédients » · bouton « Étape suivante »
- [ ] Sous chaque cocktail, le suivi 4 étapes :
      « Préparation des ingrédients » → « Assemblage » → « Dressage » → « Terminée »
      (étape en cours en vert plein).
- [ ] Bandeau en bas (état final, à montrer aussi) : « ✅ Commande terminée — prête à servir ! ».

## Écran 8 — Gestion de la carte
- [ ] Frame tablette → **`08 - Gestion carte`**.
- [ ] En-tête « Gestion de la carte ». Onglets : « Catégories » · « Cocktails » · « Ingrédients ».
- [ ] Onglet « Cocktails » : liste avec, par ligne, le nom + icône « ✏️ Modifier » + icône « 🗑️ Supprimer ».
      Bouton en haut à droite « + Ajouter un cocktail ».
      Exemples de lignes : « Mojito », « Margarita », « Cosmopolitan », « Espresso Martini ».
- [ ] Panneau « Ajouter / Modifier un cocktail » (formulaire) avec valeurs d'exemple :
      - Label « Nom » → « Mojito »
      - Label « Catégorie » (liste déroulante) → « Classiques »
      - Label « Description » → la description du Mojito
      - Label « Ingrédients » (cases à cocher) → cocher : Rhum blanc, Menthe, Citron vert, Sucre de canne, Eau gazeuse
      - Label « Tailles et prix » → « S : 7 € »  « M : 8 € »  « L : 10 € »
      - Label « Image » → bouton « Choisir une image »
      - Boutons « Enregistrer » / « Annuler ».

---

## ✅ Pour finir la phase maquettes
- [ ] Renommer tous les frames avec le préfixe numéroté (`01 -`, `02 -`…) pour un export ordonné.
- [ ] Supprimer les écrans inutiles du kit.
- [ ] Export : sélectionner les 8 frames → **Export to PDF** → ranger dans `maquettes/maquettes.pdf`.
