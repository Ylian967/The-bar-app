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

## 📐 Conventions de code (EXIGÉ par Ylian — à respecter tout du long)
- **Fichiers COURTS** (~150 lignes max), une classe/un composant = **une responsabilité**, méthodes courtes.
- **Clean code + patterns** (noté à l'exam). Pas de logique métier dans les controllers.
- **Back** : couches `controller`/`service`/`repository`/`entity` + **DTO** (ne jamais exposer les entités).
  Validation des entrées (`@Valid`), gestion d'erreurs centralisée (`@ControllerAdvice`), tests par service.
- **Front** : petits composants Vue typés (Composition API + TS), appels API dans des **services dédiés**,
  état dans **Pinia** (pas d'appels API dispersés dans les composants).
- **Partout** : nommage explicite, DRY (pas de duplication), commits réguliers.
- Quand un fichier devient long → le découper. Préférer plusieurs petits fichiers clairs.

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
- ✅ Phase 1 — données importées : `db/init.sql` (schéma 8 tables + 5 catégories, 16 cocktails,
  34 ingrédients, tailles S/M/L) + 16 images dans `db/images/` (TheCocktailDB). Schéma = référence
  pour les entités JPA en Phase 2. Note : `commande.client_nom` ajouté (client non authentifié) ;
  user barmaker seedé avec mot de passe à encoder en BCrypt en Phase 2 ('Barmaker123').
- ⏳ Phase 2 — back Spring Boot EN COURS. Fait : projet Maven (`back/pom.xml`, Spring Boot 3.3.5,
  Java 21, Lombok, JaCoCo), `BarappApplication`, `application.properties`, **7 entités + 4 enums**
  (package `fr.barapp.barapp.entity`), mappées sur `db/init.sql`. `mvn compile` = OK.
  Fait aussi : 7 repositories, gestion d'erreurs (`RessourceIntrouvableException` + `GestionnaireExceptions`),
  **API Catégories + Cocktails** (DTO records, mapper, service, controller, validation `@Valid`). mvn compile OK.
  Packages : dto/ mapper/ service/ controller/ exception/ repository/. Reste : Ingrédients + Commandes
  (cœur métier) → JWT → tests >85% → Dockerfile/compose.
  ✅ FAIT depuis : API **Ingrédients** (CRUD) + API **Commandes** (passer commande avec calcul prix,
  `aTraiter`, `parClient`, `avancerLigne` avec la RÈGLE D'OR de recalcul du statut). Exception métier
  `RequeteInvalideException` (400). 1er test : `CommandeServiceTest` (3 tests verts via Mockito+H2 non requis).
  Lancer les tests : `cd back && . ~/tools/env.sh && mvn test`.
  ✅ FAIT : **Sécurité JWT** (Spring Security + jjwt 0.12.6). `POST /api/auth/login` {email,motDePasse}
  -> {token,...}. Barmaker créé au démarrage par `InitialisationDonnees` (barmaker@barapp.fr / Barmaker123).
  Routes publiques : GET catalogue, POST /api/commandes, GET /api/commandes (suivi). Routes BARMAKER :
  écritures cocktails/categories/ingredients, GET /commandes/a-traiter, PATCH avancer. CORS configuré
  (app.cors.origins). `db/init.sql` ne seede plus l'utilisateur (créé par le runner).
  ✅ FAIT : **Docker** (`back/Dockerfile` multi-stage, `docker-compose.yml` postgres16+back).
  Lancer : `cd <projet> && rtk proxy docker compose up -d --build` (CLI docker via `rtk proxy ...`
  car le hook intercepte `docker`). Images cocktails copiées dans `back/.../static/images/cocktails/`,
  servies à `/images/cocktails/x.jpg`. Fix appliqué : enlevé `length=1` sur les colonnes `taille`
  (Hibernate validate exigeait char(1) vs varchar). **API testée OK end-to-end** (carte, login JWT,
  403 sans token, passer commande total auto, avancer prépa → règle d'or). Back = 8080, db = 5432.
  ✅ **PHASE 2 COMPLÈTE** : 47 tests JUnit (Mockito), couverture **92% lignes / 90% instructions** (JaCoCo,
  excludes : entity/dto/config/JwtAuthFilter/main). Rapport : `back/target/site/jacoco/index.html`.
  ➡️ PROCHAINE GROSSE ÉTAPE = **Phase 3 : front Vue 3 + TypeScript** (vues client mobile + barman /admin,
  appels API via services, store Pinia, responsive ; consommer http://localhost:8080 ; Dockerfile front).
  S'appuyer sur les maquettes `maquettes/barapp-maquettes.html` (style + structure déjà faits).
  ⏳ Phase 3 EN COURS. Projet `front/` = Vue 3 + TS (Vite) + vue-router + pinia + axios.
  Fondation FAITE : `types/models.ts`, `services/{http,catalogue,commandes,auth}.ts`, `stores/{auth,panier}.ts`,
  `router/index.ts` (routes client + /admin barman avec garde), `assets/theme.css` (design system vibrant),
  `components/BottomNav.vue`. **Page Carte client FAITE et vérifiée** (vraies données/photos via API).
  ✅ **Parcours CLIENT complet** : CartePage, FicheCocktail (tailles/ingrédients/ajout panier),
  PanierPage (récap + commander → POST API), MesCommandes (onglets en cours/historique + suivi).
  Vérifié visuellement (responsive, max-width 480). Stubs restants : LoginAdmin, CommandesATraiter,
  DetailCommande, GestionCarte (= partie BARMAN, à faire).
  Build front : `cd front && npm run build` (OK). Dev : `npm run dev` (5173). Le back doit tourner (docker compose).
  CORS back autorise 5173/4173.
  ✅ **PARCOURS BARMAN COMPLET** : LoginAdmin (auth JWT), CommandesATraiter (liste + filtres + refresh
  auto 5s), DetailCommande (steppers 4 étapes + avancer), GestionCarte (onglets via sous-composants
  OngletCategories/Ingredients/Cocktails = CRUD complet). Sidebar `components/barman/SidebarBarman.vue`.
  Service catalogue étendu (create/update/delete categories/cocktails/ingredients). Login vérifié visuellement.
  ✅ Champ **accroche** ajouté end-to-end (entity/dto/mapper/request/service + init.sql re-seedé + front).
  ⚠️ Détail : données seed SANS accents (accroches/desc en ASCII) — à corriger au "grand tour" final.
  ⚠️ DetailCommande reconstruit l'URL image depuis le nom (slug) car LigneCommandeDto n'a pas imageUrl.
  ✅ **Import catalogue externe** : back `CatalogueExterneService` + `CatalogueExterneController`
  (`GET /api/catalogue-externe?q=`, `POST /api/catalogue-externe/import`, réservé BARMAKER) appelle
  TheCocktailDB côté serveur, traduit les ingrédients (`TraductionIngredients`), crée les manquants.
  Front : gestion cocktails refondue → `OngletCocktails` (liste+choix) + `FormulaireCocktail`
  (création/édition avec **sélecteur d'ingrédients propre** : recherche + puces + créer à la volée)
  + `ImportCocktail` (recherche → choix → catégorie+prix → import). Testé via API (recherche+import OK).
  ⚠️ Images des cocktails IMPORTÉS = URL externe TheCocktailDB (pas téléchargées en local) — à
  améliorer au grand tour si on veut du 100% local (nécessite dir images en volume, pas dans le jar).
  ✅ **Tests front** (Vitest) : 9 tests (stores panier/auth + composant BottomNav). `cd front && npm test`.
  ✅ **Dockerfile front** (multi-stage node build + nginx) + nginx.conf (sert SPA + proxy /api et /images
  vers back → même origine, pas de CORS). Front build avec VITE_API_URL="" (URLs relatives).
  ✅ **`docker compose up --build` lance TOUT** (db+back+front) ; front=5173, back=8080. Vérifié OK.
  ✅ **README** complet (lancement, compte démo, tests, structure).
  ➡️ APP COMPLÈTE & DÉPLOYABLE. RESTE : le "grand tour" de polish (accents data, images importées en
  local, ajustements maquette), puis **Phase 4** (PowerPoint + répétition démo 15 min, soutenance 03/07).
  Dev front local (HMR) : `cd front && npm run dev` (port 5173) — mais en Docker le front est déjà servi sur 5173.
- 🔧 TOOLCHAIN (Java/Maven pas en système) : installés en user-space dans `~/tools`. Avant tout
  `mvn` : `. "$HOME/tools/env.sh"` (définit JAVA_HOME=jdk21 + PATH maven). Build : `cd back && mvn ...`.
  Pas de Postgres local → `ddl-auto=validate` ne se teste qu'en Docker ; tests via H2.
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
