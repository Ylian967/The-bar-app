# CLAUDE.md — Le Bar'app (mémoire de projet)

> Ce fichier est lu automatiquement à chaque session. Il dit **où on en est** et **comment travailler avec Ylian**.
> ⚠️ À chaque fin d'étape : mettre à jour ce fichier ET `JOURNAL.md`.

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
- ⏳ **Phase 1 — EN COURS : maquettes Figma** (guider Ylian écran par écran ; il les fait à la main).
- ⬜ Phase 1 — script SQL d'init (`db/`).
- ⬜ Phase 2 — back Spring Boot (entités, API CRUD, validation, JWT, tests >85%, Dockerfile, script BDD).
- ⬜ Phase 3 — front Vue + TS (vues client + barman /admin, responsive, tests, Dockerfile).
- ⬜ Phase 4 — PowerPoint + répétition démo 15 min.

## 🗂️ Fichiers de référence
- `JOURNAL.md` — carnet de bord détaillé jour par jour.
- `docs/MCD.md` — modèle de données complet.
- `README.md` — instructions de lancement (à compléter en Phase 2/3).

## 🔜 Prochaine action concrète
Guider Ylian pour faire les **maquettes Figma** : écran par écran (carte/cocktails, fiche cocktail,
panier, commande, suivi de préparation côté client ; login /admin, gestion carte, liste des
commandes, écran d'avancement côté barman). Puis exporter en PDF dans `maquettes/`.
