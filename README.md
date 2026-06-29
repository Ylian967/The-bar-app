# 🍹 Le Bar'app

Application web de bar à cocktails — examen fin 1ʳᵉ année ForEach (Ylian MERRIR).

Deux interfaces :
- **Client** : consulte la carte, remplit son panier, passe commande et suit la préparation.
- **Barmaker** (`/admin`, accès protégé) : gère la carte (catégories, cocktails, ingrédients,
  tailles & prix) et fait avancer les commandes étape par étape.

## 🛠️ Stack
- **Back** : Java + Spring Boot (API REST, JWT, tests JUnit)
- **Front** : VueJS 3 + TypeScript
- **Base de données** : PostgreSQL
- **Conteneurisation** : Docker / Docker Compose

## 🚀 Lancer l'application
> ⚠️ Section complétée en Phase 2/3. Objectif final : `docker compose up`.

```bash
# (à venir)
docker compose up --build
```

## 👤 Comptes de démonstration
> (à compléter — un compte client + un compte barmaker de test)

## 📁 Structure
```
bar-app/
├── back/        API Spring Boot
├── front/       App VueJS + TypeScript
├── db/          Script SQL d'initialisation
├── docs/        MCD et documentation
└── maquettes/   Maquettes Figma (PDF) + MCD
```
