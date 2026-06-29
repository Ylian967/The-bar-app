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

## 📊 Avancement

| Phase | Tâche | Statut |
|-------|-------|--------|
| 0 | Setup projet + Git + GitHub | ⏳ En cours |
| 1 | MCD | ✅ Fait (docs/MCD.md + maquettes/MCD.pdf) |
| 1 | Script SQL d'init | ⬜ À faire |
| 1 | Maquettes Figma | ⬜ À faire |
| 2 | Entités + API CRUD Spring Boot | ⬜ À faire |
| 2 | Validation des entrées | ⬜ À faire |
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
