# 🍹 Le Bar'app

Application web de commande de cocktails — examen fin 1ʳᵉ année ForEach (Ylian MERRIR).

Deux interfaces :
- **Client** (mobile) : consulte la carte, remplit son panier, passe commande et suit la préparation.
- **Barmaker** (`/admin`, accès protégé) : gère la carte (catégories, cocktails, ingrédients, tailles &
  prix, import depuis TheCocktailDB) et fait avancer les commandes étape par étape.

## 🛠️ Stack
- **Back** : Java 21 + Spring Boot 3.3 (API REST, JWT, tests JUnit ~92 % de couverture)
- **Front** : VueJS 3 + TypeScript + Vite (Pinia, Vue Router, tests Vitest)
- **Base de données** : PostgreSQL 16
- **Conteneurisation** : Docker / Docker Compose (db + back + front)

## 🚀 Lancer l'application (une seule commande)
Prérequis : **Docker Desktop** lancé.

```bash
docker compose up --build
```

Puis ouvrir :
- 🍸 **Client** : http://localhost:5173
- 🧑‍🍳 **Barmaker** : http://localhost:5173/admin
- 🔌 API (back) : http://localhost:8080

La base est créée et remplie automatiquement au démarrage (script `db/init.sql` : 16 cocktails,
catégories, ingrédients, images). Pour repartir d'une base vierge : `docker compose down -v`.

## 👤 Compte de démonstration (barmaker)
- **Email** : `barmaker@barapp.fr`
- **Mot de passe** : `Barmaker123`

Le client n'a pas besoin de compte (il saisit juste un prénom / n° de table).

## 🧪 Tests
```bash
# Back (JUnit + couverture JaCoCo -> back/target/site/jacoco/index.html)
cd back && mvn test

# Front (Vitest)
cd front && npm install && npm test
```

## 📁 Structure
```
bar-app/
├── back/         API Spring Boot (controller / service / repository / entity / dto / mapper / security)
├── front/        App VueJS + TypeScript (views / components / services / stores / router)
├── db/           Script SQL d'initialisation + images des cocktails
├── docs/         MCD, sujet, guides
├── maquettes/    Maquettes (HTML + PDF) + MCD
└── docker-compose.yml
```
