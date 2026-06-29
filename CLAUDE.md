# CLAUDE.md — Le Bar'app (mémoire de projet)

> Ce fichier est lu automatiquement à chaque session. Il dit **où on en est** et **comment travailler avec Ylian**.
> ⚠️ À chaque fin d'étape : mettre à jour ce fichier ET `JOURNAL.md`.

## 🔝 À FAIRE EN DÉBUT DE CHAQUE SESSION (obligatoire)
1. **Lire le sujet de l'examen** : `docs/sujet-exam.pdf` (la source de vérité des consignes).
2. Lire `JOURNAL.md` (où on en est en détail).
3. Reprendre à « Prochaine action concrète » (en bas de ce fichier).

## 🎯 Le projet
Examen de fin de 1ʳᵉ année ForEach — **Ylian MERRIR**.
Application web **« Le Bar'app »** : bar à cocktails. Deux rôles **client** et **barmaker**.
- **Rendu** : mail à `laury.bossaert@foreach-academy.fr` avant le **02/07/2026 17h**.
  Livrables = maquettes (PDF) + code source (zip) + lien GitHub + README.
- **Soutenance** : 03/07/2026 à 11h00 — démo live scénarisée 15 min + 5 min d'échange + PowerPoint.

## 🛠️ Stack imposée
Java **Spring Boot** (API REST) · **VueJS 3 + TypeScript** · **PostgreSQL** · **Docker**.
Exigences notées : tests JUnit **> 85 %** de couverture, validation des entrées, front testé,
responsive, clean code + design patterns, Dockerfile back + front + script init BDD.

## 🏗️ Décisions d'architecture (déjà tranchées avec Ylian)
- **2 interfaces** : app client (publique) + interface barman cachée sur **`/admin`**.
- **Auth** : login **email + mot de passe**, rôle `BARMAKER`, sécurisé par **JWT** (PAS de Google Auth).
- Un **compte barmaker de test** sera fourni à l'évaluateur (identifiants dans le README).
- Environnement lancé via **Docker Compose**.
- **Formats d'écran (pas de responsive imposé)** : interface CLIENT = **téléphone (mobile)**
  uniquement ; interface BARMAN = **tablette** uniquement. Maquettes faites à ces formats.
- **Données** : import UNIQUE depuis TheCocktailDB → générer le script SQL d'init (`db/`) avec
  cocktails + ingrédients + images **téléchargées en local**. PAS d'appel API au runtime
  (l'app doit être autonome pour la démo). Tailles S/M/L + prix générés par nous.
- MCD = 8 tables : utilisateur, categorie, cocktail, ingredient, cocktail_ingredient,
  cocktail_taille (prix par taille S/M/L), commande, ligne_commande (avec statut_preparation).
  Détail complet dans `docs/MCD.md`.

### Règle métier clé
Statuts commande : `COMMANDEE` → `EN_PREPARATION` → `TERMINEE`.
Statuts d'une ligne (par cocktail) : `PREPARATION_INGREDIENTS` → `ASSEMBLAGE` → `DRESSAGE` → `TERMINEE`.
Quand **toutes** les lignes sont `TERMINEE`, la commande passe `TERMINEE`.

## 🤝 Comment travailler avec Ylian (IMPORTANT)
- Écrire **en français**, ton bienveillant.
- **Ylian débute** : expliquer chaque étape lentement, clic par clic, sans jargon non expliqué.
- **Une étape à la fois** : on découpe, on ne balance pas tout d'un coup. Attendre son « suivant ».
- **Donner une estimation de temps** avant chaque tâche.
- **Tenir le `JOURNAL.md` à jour** (c'est un livrable de la démo).
- Projet sur le bureau Windows : `C:\Users\meryl\Desktop\bar-app` (= `/mnt/c/Users/meryl/Desktop/bar-app` en WSL).
- Ne pas toucher à une BDD sans prévenir.

## 📊 Où on en est (mettre à jour à chaque session)
- ✅ Phase 0 : structure projet + journal + .gitignore + README + Git/GitHub poussé.
- ✅ Phase 1 — MCD : `docs/MCD.md` + `maquettes/MCD.pdf`.
- ✅ **Phase 1 — Maquettes VALIDÉES** (8 écrans, `maquettes/barapp-maquettes.html`, vraies photos + SVG). Méthode : Ylian a DUPLIQUÉ 1 SEUL kit communautaire
  (CLIENT = **"Drink ordering app" (kit café/vert), file 1305198455555652896**, déjà dans son Figma,
  fichier renommé `Bar'app`). Je le guide écran par écran (limite Figma MCP = 6/mois → PAS de use_figma).
  Le kit contient : Home, Fiche produit, Panier/Make Payment, planche styles+composants.
  Parcours CLIENT = 4 écrans : (1) La Carte [= Home adapté], (2) Détail cocktail [= Fiche produit,
  ajouter tailles S/M/L + ingrédients], (3) Panier/commande [= Make Payment adapté],
  (4) **Mes commandes** [historique + en cours / suivi] → À CONSTRUIRE avec les composants du kit.
  ⚠️ BARMAN : même style, format TABLETTE, construit avec les composants du kit (pas un autre kit).
  Barman = 4 écrans : login /admin, commandes à traiter, détail commande + avancement, gestion carte.
  Export PDF final dans maquettes/.
  → ⚠️ BLOCAGE Figma MCP : le compte connecté (ylianmerrir7@gmail.com, siège **View / Starter**)
    n'a PAS le droit d'écrire dans les fichiers Figma (erreur "don't have edit access" même après
    partage en édition). Donc impossible de générer via use_figma. NE PAS retenter sans changement de forfait.
  → SOLUTION RETENUE : maquettes construites en **HTML/CSS** par Claude, style "Vibrant & Fun",
    fichier `maquettes/barapp-maquettes.html` (8 écrans). À importer dans Figma via le plugin
    **html.to.design**, et/ou exporter en PDF (Ctrl+P). Réutilisable comme base du front Vue (Phase 3).
  → Aperçu rendu : `maquettes/preview.png`. Ylian avait déjà fait 3 écrans client à la main dans Figma.
- ⬜ Phase 1 — script SQL d'init (`db/`).
- ⬜ Phase 2 — back Spring Boot (entités, API CRUD, validation, JWT, tests >85%, Dockerfile, script BDD).
- ⬜ Phase 3 — front Vue + TS (vues client + barman /admin, responsive, tests, Dockerfile).
- ⬜ Phase 4 — PowerPoint + répétition démo 15 min.

## 🗂️ Fichiers de référence
- `JOURNAL.md` — carnet de bord détaillé jour par jour.
- `docs/MCD.md` — modèle de données complet.
- `README.md` — instructions de lancement (à compléter en Phase 2/3).

## 🔜 Prochaine action concrète
Maquettes validées. Prochaine étape = **Phase 2 (back Spring Boot)** OU d'abord l'**import des données**
(script qui interroge TheCocktailDB une fois → génère `db/init.sql` avec catégories/cocktails/ingrédients/
tailles+prix + télécharge les images). Recommander de commencer par initialiser le projet Spring Boot
(entités du MCD) puis brancher le script d'init. Penser : Docker Compose (postgres+back+front), tests JUnit >85%.
