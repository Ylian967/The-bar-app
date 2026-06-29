# 📓 Journal de bord — Le Bar'app

> Examen fin 1ʳᵉ année ForEach — Ylian MERRIR
> **Rendu :** mail à laury.bossaert@foreach-academy.fr avant le **02/07/2026 17h**
> **Soutenance :** 03/07/2026 à 11h00 (démo 15 min + 5 min échange)
> **Stack imposée :** Java Spring Boot · VueJS + TypeScript · PostgreSQL · Docker

---

## 🎯 Le concept en une phrase
Application web de bar à cocktails : les **clients** commandent depuis une carte et suivent
la préparation ; les **barmakers** créent la carte et font avancer les commandes étape par étape.

## 👥 Les 2 rôles
- **Client** : consulte la carte → remplit son panier → passe commande → suit l'avancement
  (Commandée → En préparation → Terminée).
- **Barmaker** : crée catégories + cocktails (ingrédients, tailles S/M/L, prix par taille) →
  voit les commandes à traiter → fait avancer chaque cocktail
  (Préparation des ingrédients → Assemblage → Dressage → Terminée).
  Quand tous les cocktails sont « Terminée », la commande globale passe « Terminée ».

---

## 📋 Spécifications (tout ce qu'on a décidé)

### 🎨 Maquettes & écrans
- **Outil** : Figma. **Kit de base** : *Drink ordering app* (kit café/vert, file 1305198455555652896),
  déjà dupliqué dans le Figma d'Ylian (fichier `Bar'app`). Contient : Home, Fiche produit,
  Panier/Make Payment, planche styles+composants. L'écran « Mes commandes » sera construit à la main
  avec ses composants.
- **Formats** (pas de responsive imposé) : CLIENT = **téléphone (mobile)** · BARMAN = **tablette**.
- **Style** : un seul style cohérent partout (couleurs/typo/composants du même kit). Vert OK.
- Le BARMAN n'utilise PAS un autre kit : ses écrans sont construits avec les composants du kit client.

#### Écrans CLIENT (téléphone) — 4
1. **La Carte** — catégories + cocktails (image, nom, prix), recherche/filtres, barre de nav en bas.
2. **Fiche cocktail** — image, description, **ingrédients**, choix de la **taille S/M/L (prix par taille)**,
   quantité, bouton « Ajouter au panier ».
3. **Panier / Commande** — cocktails choisis (taille, quantité, prix), total, bouton « Commander ».
4. **Mes commandes** — **historique** des commandes passées **+ commandes en cours** avec suivi du
   statut (Commandée → En préparation → Terminée).

#### Écrans BARMAN (tablette) — 4
5. **Login `/admin`** — email + mot de passe (accès caché, rôle BARMAKER).
6. **Commandes à traiter** — liste des commandes en attente / en cours.
7. **Détail d'une commande** — liste des cocktails ; faire avancer chaque cocktail étape par étape :
   Préparation des ingrédients → Assemblage → Dressage → Terminée.
8. **Gestion de la carte** — créer/modifier catégories, cocktails (ingrédients, tailles S/M/L, prix),
   ingrédients ; gestion des « cocktails du jour ».

### 🔐 Authentification
- Login **email + mot de passe**, rôle `BARMAKER`, sécurisé par **JWT** (pas de Google Auth).
- Un **compte barmaker de test** sera fourni à l'évaluateur (identifiants dans le README).

### 🗄️ Données
- **Import unique** depuis TheCocktailDB → génère le script SQL d'init (`db/`).
- **Images téléchargées en local** (aucun appel API au runtime → app autonome pour la démo).
- Tailles **S/M/L** et **prix** générés par nous (TheCocktailDB n'en a pas).
- Le CRUD du barman reste complet : l'import est juste une carte de départ réaliste.

### ⚙️ Technique
- **Back** : Java **Spring Boot**, API REST CRUD sur toutes les entités, **validation** des entrées,
  **JWT**, tests **JUnit > 85 %**, **Dockerfile**, script d'init BDD.
- **Front** : **VueJS 3 + TypeScript**, vues client + barman, **tests**, **Dockerfile**.
- **BDD** : **PostgreSQL** (8 tables du MCD).
- **Orchestration** : **Docker Compose** (db + back + front).
- Clean code + design patterns appréciés (notés).

### 📦 Livrables (rappel)
Maquettes (PDF) · code source (zip) · lien **GitHub** · **README** de lancement · compte barmaker de test.
Mail à `laury.bossaert@foreach-academy.fr` avant **02/07/2026 17h**. Soutenance **03/07 11h00**.

---

## 📊 Avancement

| Phase | Tâche | Statut |
|-------|-------|--------|
| 0 | Setup projet + Git + GitHub | ⏳ En cours |
| 1 | MCD | ✅ Fait (docs/MCD.md + maquettes/MCD.pdf) |
| 1 | Script SQL d'init + données | ✅ Fait (db/init.sql, 16 cocktails, 16 images) |
| 1 | Maquettes (8 écrans) | ✅ Fait (maquettes/barapp-maquettes.html + preview.png) |
| 2 | Projet Spring Boot + entités JPA | ✅ Fait (7 entités + 4 enums, mvn compile OK) |
| 2 | Repositories (7) | ✅ Fait |
| 2 | API Catégories + Cocktails (CRUD) | ✅ Fait (DTO/service/controller + gestion erreurs) |
| 2 | API Ingrédients (CRUD) | ✅ Fait |
| 2 | API Commandes (passer + avancer prépa, règle d'or) | ✅ Fait + 3 tests OK |
| 2 | Validation des entrées | ✅ Fait (@Valid sur tous les endpoints) |
| 2 | Sécurité JWT (login barmaker) | ✅ Fait |
| 2 | Tests JUnit > 85 % | ⬜ À faire |
| 2 | Dockerfile back + script BDD | ⬜ À faire |
| 3 | Front Vue + TypeScript (vues client) | ⬜ À faire |
| 3 | Front (vues barmaker) | ⬜ À faire |
| 3 | Responsive + tests front | ⬜ À faire |
| 3 | Dockerfile front | ⬜ À faire |
| 4 | PowerPoint de présentation | ⬜ À faire |
| 4 | Répétition démo 15 min | ⬜ À faire |

Légende : ⬜ à faire · ⏳ en cours · ✅ fait

---

## 🗒️ Carnet de bord (on note ici ce qu'on fait, jour par jour)

### 29/06/2026
- Lecture du sujet, choix de l'organisation.
- Création de la structure du projet sur le bureau : `bar-app/` (back, front, db, docs, maquettes).
- Création de ce journal.
- Décision : 2 interfaces (client + barman sur `/admin`), auth email/mdp + JWT.
- MCD conçu (8 tables) → `docs/MCD.md`, exporté en PDF → `maquettes/MCD.pdf`.
- Ajout `.gitignore` + `README.md`.
- Maquettes : 1 seul kit (Drink ordering) dupliqué côté client ; barman construit dans le même
  style. Formats fixés : client = téléphone, barman = tablette (pas de responsive imposé).
- Sujet de l'exam copié dans `docs/sujet-exam.pdf` (à relire chaque début de session).
- Guide détaillé des maquettes rédigé (`docs/maquettes-guide.md`, textes exacts).
- Figma MCP impossible en écriture (forfait gratuit/siège View) → maquettes construites en
  **HTML/CSS** style « Vibrant & Fun » : `maquettes/barapp-maquettes.html` (8 écrans) + `preview.png`.
  À importer dans Figma via plugin html.to.design et/ou exporter en PDF. Servira de base au front Vue.
- Maquettes enrichies : vraies photos de cocktails (TheCocktailDB, en local dans `maquettes/img/`)
  + icônes SVG propres. **Étape maquettes VALIDÉE** ✅.
- Nettoyage final (principe « rien d'inutile dans la spec ») : menu barman = 2 sections
  (Commandes à traiter + Gestion de la carte) ; barre client = 3 onglets (Accueil/Panier/Commandes,
  « Profil » retiré car compte client non demandé). Polices = Poppins (titres) + Inter (texte).

---

## 🧩 Décisions techniques (on remplit au fur et à mesure)
- Environnement : **Docker** (PostgreSQL + back + front).
- Maquettes : **Figma** (faites à la main, guidées).
- **Architecture : 2 interfaces** — appli client (carte, panier, commande, suivi) + interface
  barman cachée sur **`/admin`** (gestion carte/catégories/cocktails/ingrédients + avancement
  des commandes, « cocktails du jour »).
- **Authentification barman : login email + mot de passe**, rôle `BARMAKER`, sécurisé par
  **JWT** côté Spring Boot. (Google Auth écarté : trop complexe + souci d'accès pour l'évaluateur.)
- Un **compte barmaker de test** sera fourni à l'évaluateur (identifiants dans le README).
- *(à compléter : noms des ports, version Java/Node, etc.)*

## ❓ Points à ne pas oublier
- Tests JUnit **> 85 % de couverture** (critère noté).
- App **responsive** (testée sur 2 écrans : un client, un barmaker).
- README clair pour lancer l'app.
- Repo **GitHub** public ou accessible.
- Mail de rendu avec : maquettes + zip du code + lien GitHub.
