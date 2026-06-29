-- ============================================================
-- Le Bar'app - script d'initialisation de la base PostgreSQL
-- Genere automatiquement (donnees + images TheCocktailDB).
-- ============================================================

DROP TABLE IF EXISTS ligne_commande, commande, cocktail_taille, cocktail_ingredient, cocktail, ingredient, categorie, utilisateur CASCADE;

CREATE TABLE utilisateur (
  id           BIGSERIAL PRIMARY KEY,
  nom          VARCHAR(100) NOT NULL,
  email        VARCHAR(150) UNIQUE NOT NULL,
  mot_de_passe VARCHAR(255) NOT NULL,
  role         VARCHAR(20)  NOT NULL CHECK (role IN ('CLIENT','BARMAKER'))
);

CREATE TABLE categorie (
  id  BIGSERIAL PRIMARY KEY,
  nom VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE ingredient (
  id         BIGSERIAL PRIMARY KEY,
  nom        VARCHAR(100) NOT NULL UNIQUE,
  image_url  VARCHAR(255),
  disponible BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE cocktail (
  id           BIGSERIAL PRIMARY KEY,
  nom          VARCHAR(120) NOT NULL,
  accroche     VARCHAR(160),
  description  TEXT,
  image_url    VARCHAR(255),
  du_jour      BOOLEAN NOT NULL DEFAULT true,
  favori       BOOLEAN NOT NULL DEFAULT false,
  categorie_id BIGINT NOT NULL REFERENCES categorie(id)
);

CREATE TABLE cocktail_ingredient (
  cocktail_id   BIGINT NOT NULL REFERENCES cocktail(id) ON DELETE CASCADE,
  ingredient_id BIGINT NOT NULL REFERENCES ingredient(id),
  PRIMARY KEY (cocktail_id, ingredient_id)
);

CREATE TABLE cocktail_taille (
  id          BIGSERIAL PRIMARY KEY,
  cocktail_id BIGINT NOT NULL REFERENCES cocktail(id) ON DELETE CASCADE,
  taille      VARCHAR(1) NOT NULL CHECK (taille IN ('S','M','L')),
  prix        NUMERIC(6,2) NOT NULL,
  UNIQUE (cocktail_id, taille)
);

CREATE TABLE commande (
  id            BIGSERIAL PRIMARY KEY,
  client_id     BIGINT REFERENCES utilisateur(id),
  client_nom    VARCHAR(100),
  statut        VARCHAR(20) NOT NULL DEFAULT 'COMMANDEE'
                CHECK (statut IN ('COMMANDEE','EN_PREPARATION','TERMINEE')),
  date_creation TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE ligne_commande (
  id                 BIGSERIAL PRIMARY KEY,
  commande_id        BIGINT NOT NULL REFERENCES commande(id) ON DELETE CASCADE,
  cocktail_id        BIGINT NOT NULL REFERENCES cocktail(id),
  taille             VARCHAR(1)   NOT NULL CHECK (taille IN ('S','M','L')),
  prix               NUMERIC(6,2) NOT NULL,
  quantite           INT NOT NULL DEFAULT 1 CHECK (quantite > 0),
  statut_preparation VARCHAR(30) NOT NULL DEFAULT 'PREPARATION_INGREDIENTS'
                     CHECK (statut_preparation IN ('PREPARATION_INGREDIENTS','ASSEMBLAGE','DRESSAGE','TERMINEE'))
);

-- ---------- Utilisateur barmaker (cree au demarrage par InitialisationDonnees.java) ----------
--   email : barmaker@barapp.fr   /   mot de passe : Barmaker123

-- ---------- Categories ----------
INSERT INTO categorie (nom) VALUES
  ('Classiques'),
  ('Signatures'),
  ('Tropicaux'),
  ('Shots'),
  ('Mocktails');

-- ---------- Ingredients (avec image) ----------
INSERT INTO ingredient (nom, image_url) VALUES
  ('Rhum blanc', 'https://www.thecocktaildb.com/images/ingredients/Light%20rum-Small.png'),
  ('Citron vert', 'https://www.thecocktaildb.com/images/ingredients/Lime-Small.png'),
  ('Sucre', 'https://www.thecocktaildb.com/images/ingredients/Sugar-Small.png'),
  ('Menthe', 'https://www.thecocktaildb.com/images/ingredients/Mint-Small.png'),
  ('Eau gazeuse', 'https://www.thecocktaildb.com/images/ingredients/Soda%20water-Small.png'),
  ('Tequila', 'https://www.thecocktaildb.com/images/ingredients/Tequila-Small.png'),
  ('Triple sec', 'https://www.thecocktaildb.com/images/ingredients/Triple%20sec-Small.png'),
  ('Jus de citron vert', 'https://www.thecocktaildb.com/images/ingredients/Lime%20juice-Small.png'),
  ('Sel', 'https://www.thecocktaildb.com/images/ingredients/Salt-Small.png'),
  ('Sucre glace', 'https://www.thecocktaildb.com/images/ingredients/Powdered%20sugar-Small.png'),
  ('Gin', 'https://www.thecocktaildb.com/images/ingredients/Gin-Small.png'),
  ('Campari', 'https://www.thecocktaildb.com/images/ingredients/Campari-Small.png'),
  ('Vermouth rouge', 'https://www.thecocktaildb.com/images/ingredients/Sweet%20Vermouth-Small.png'),
  ('Cachaca', 'https://www.thecocktaildb.com/images/ingredients/Cachaca-Small.png'),
  ('Vodka', 'https://www.thecocktaildb.com/images/ingredients/Vodka-Small.png'),
  ('Cointreau', 'https://www.thecocktaildb.com/images/ingredients/Cointreau-Small.png'),
  ('Jus de cranberry', 'https://www.thecocktaildb.com/images/ingredients/Cranberry%20juice-Small.png'),
  ('Kahlua', 'https://www.thecocktaildb.com/images/ingredients/Kahlua-Small.png'),
  ('Sirop de sucre', 'https://www.thecocktaildb.com/images/ingredients/Sugar%20syrup-Small.png'),
  ('Sirop d''orgeat', 'https://www.thecocktaildb.com/images/ingredients/Orgeat%20syrup-Small.png'),
  ('Sweet and sour', 'https://www.thecocktaildb.com/images/ingredients/Sweet%20and%20sour-Small.png'),
  ('Cerise', 'https://www.thecocktaildb.com/images/ingredients/Cherry-Small.png'),
  ('Curacao bleu', 'https://www.thecocktaildb.com/images/ingredients/Blue%20Curacao-Small.png'),
  ('Limonade', 'https://www.thecocktaildb.com/images/ingredients/Lemonade-Small.png'),
  ('Lait de coco', 'https://www.thecocktaildb.com/images/ingredients/Coconut%20milk-Small.png'),
  ('Ananas', 'https://www.thecocktaildb.com/images/ingredients/Pineapple-Small.png'),
  ('Schnaps peche', 'https://www.thecocktaildb.com/images/ingredients/Peach%20schnapps-Small.png'),
  ('Jus d''orange', 'https://www.thecocktaildb.com/images/ingredients/Orange%20juice-Small.png'),
  ('Grenadine', 'https://www.thecocktaildb.com/images/ingredients/Grenadine-Small.png'),
  ('Baileys', 'https://www.thecocktaildb.com/images/ingredients/Baileys%20irish%20cream-Small.png'),
  ('Grand Marnier', 'https://www.thecocktaildb.com/images/ingredients/Grand%20Marnier-Small.png'),
  ('Sucre de canne', 'https://www.thecocktaildb.com/images/ingredients/Sugar-Small.png'),
  ('Jus d''ananas', 'https://www.thecocktaildb.com/images/ingredients/Pineapple%20Juice-Small.png');

-- ---------- Cocktails + tailles (S, M=+1.5, L=+3) + ingredients ----------
INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Mojito', 'Frais & mentholé', 'Le grand classique cubain : rhum blanc, menthe fraiche et citron vert, allonge d''eau gazeuse. Frais et desalterant.', '/images/cocktails/mojito.jpg', (SELECT id FROM categorie WHERE nom='Classiques'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Mojito'), 'S', 7.00),
  ((SELECT id FROM cocktail WHERE nom='Mojito'), 'M', 8.50),
  ((SELECT id FROM cocktail WHERE nom='Mojito'), 'L', 10.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Mojito'), (SELECT id FROM ingredient WHERE nom='Rhum blanc')),
  ((SELECT id FROM cocktail WHERE nom='Mojito'), (SELECT id FROM ingredient WHERE nom='Citron vert')),
  ((SELECT id FROM cocktail WHERE nom='Mojito'), (SELECT id FROM ingredient WHERE nom='Sucre')),
  ((SELECT id FROM cocktail WHERE nom='Mojito'), (SELECT id FROM ingredient WHERE nom='Menthe')),
  ((SELECT id FROM cocktail WHERE nom='Mojito'), (SELECT id FROM ingredient WHERE nom='Eau gazeuse'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Margarita', 'Acidule & puissant', 'L''icone mexicaine : tequila, triple sec et citron vert, servie avec un givrage de sel.', '/images/cocktails/margarita.jpg', (SELECT id FROM categorie WHERE nom='Classiques'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Margarita'), 'S', 8.00),
  ((SELECT id FROM cocktail WHERE nom='Margarita'), 'M', 9.50),
  ((SELECT id FROM cocktail WHERE nom='Margarita'), 'L', 11.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Margarita'), (SELECT id FROM ingredient WHERE nom='Tequila')),
  ((SELECT id FROM cocktail WHERE nom='Margarita'), (SELECT id FROM ingredient WHERE nom='Triple sec')),
  ((SELECT id FROM cocktail WHERE nom='Margarita'), (SELECT id FROM ingredient WHERE nom='Jus de citron vert')),
  ((SELECT id FROM cocktail WHERE nom='Margarita'), (SELECT id FROM ingredient WHERE nom='Sel'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Daiquiri', 'Simple & equilibre', 'Rhum, citron vert et sucre : simple, frais et parfaitement equilibre.', '/images/cocktails/daiquiri.jpg', (SELECT id FROM categorie WHERE nom='Classiques'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Daiquiri'), 'S', 8.00),
  ((SELECT id FROM cocktail WHERE nom='Daiquiri'), 'M', 9.50),
  ((SELECT id FROM cocktail WHERE nom='Daiquiri'), 'L', 11.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Daiquiri'), (SELECT id FROM ingredient WHERE nom='Rhum blanc')),
  ((SELECT id FROM cocktail WHERE nom='Daiquiri'), (SELECT id FROM ingredient WHERE nom='Citron vert')),
  ((SELECT id FROM cocktail WHERE nom='Daiquiri'), (SELECT id FROM ingredient WHERE nom='Sucre glace'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Negroni', 'Amer & elegant', 'Amer et elegant : gin, Campari et vermouth rouge, sur glace.', '/images/cocktails/negroni.jpg', (SELECT id FROM categorie WHERE nom='Classiques'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Negroni'), 'S', 9.00),
  ((SELECT id FROM cocktail WHERE nom='Negroni'), 'M', 10.50),
  ((SELECT id FROM cocktail WHERE nom='Negroni'), 'L', 12.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Negroni'), (SELECT id FROM ingredient WHERE nom='Gin')),
  ((SELECT id FROM cocktail WHERE nom='Negroni'), (SELECT id FROM ingredient WHERE nom='Campari')),
  ((SELECT id FROM cocktail WHERE nom='Negroni'), (SELECT id FROM ingredient WHERE nom='Vermouth rouge'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Caipirinha', 'Petillant & bresilien', 'La star bresilienne : cachaca, citron vert et sucre de canne piles.', '/images/cocktails/caipirinha.jpg', (SELECT id FROM categorie WHERE nom='Classiques'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Caipirinha'), 'S', 8.00),
  ((SELECT id FROM cocktail WHERE nom='Caipirinha'), 'M', 9.50),
  ((SELECT id FROM cocktail WHERE nom='Caipirinha'), 'L', 11.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Caipirinha'), (SELECT id FROM ingredient WHERE nom='Sucre')),
  ((SELECT id FROM cocktail WHERE nom='Caipirinha'), (SELECT id FROM ingredient WHERE nom='Citron vert')),
  ((SELECT id FROM cocktail WHERE nom='Caipirinha'), (SELECT id FROM ingredient WHERE nom='Cachaca'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Cosmopolitan', 'Chic & fruite', 'Chic et acidule : vodka, triple sec, jus de cranberry et un trait de citron vert.', '/images/cocktails/cosmopolitan.jpg', (SELECT id FROM categorie WHERE nom='Signatures'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), 'S', 9.00),
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), 'M', 10.50),
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), 'L', 12.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), (SELECT id FROM ingredient WHERE nom='Vodka')),
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), (SELECT id FROM ingredient WHERE nom='Jus de citron vert')),
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), (SELECT id FROM ingredient WHERE nom='Cointreau')),
  ((SELECT id FROM cocktail WHERE nom='Cosmopolitan'), (SELECT id FROM ingredient WHERE nom='Jus de cranberry'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Espresso Martini', 'Corse & energisant', 'Vodka, cafe fraichement extrait et liqueur de cafe. Onctueux et corse.', '/images/cocktails/espresso_martini.jpg', (SELECT id FROM categorie WHERE nom='Signatures'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Espresso Martini'), 'S', 10.00),
  ((SELECT id FROM cocktail WHERE nom='Espresso Martini'), 'M', 11.50),
  ((SELECT id FROM cocktail WHERE nom='Espresso Martini'), 'L', 13.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Espresso Martini'), (SELECT id FROM ingredient WHERE nom='Vodka')),
  ((SELECT id FROM cocktail WHERE nom='Espresso Martini'), (SELECT id FROM ingredient WHERE nom='Kahlua')),
  ((SELECT id FROM cocktail WHERE nom='Espresso Martini'), (SELECT id FROM ingredient WHERE nom='Sirop de sucre'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Mai Tai', 'Tiki & exotique', 'Cocktail tiki culte : rhum, curacao, sirop d''orgeat et citron vert.', '/images/cocktails/mai_tai.jpg', (SELECT id FROM categorie WHERE nom='Signatures'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), 'S', 10.00),
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), 'M', 11.50),
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), 'L', 13.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), (SELECT id FROM ingredient WHERE nom='Rhum blanc')),
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), (SELECT id FROM ingredient WHERE nom='Sirop d''orgeat')),
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), (SELECT id FROM ingredient WHERE nom='Triple sec')),
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), (SELECT id FROM ingredient WHERE nom='Sweet and sour')),
  ((SELECT id FROM cocktail WHERE nom='Mai Tai'), (SELECT id FROM ingredient WHERE nom='Cerise'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Blue Lagoon', 'Bleu & rafraichissant', 'Bleu lagon : vodka, curacao bleu et limonade. Frais et spectaculaire.', '/images/cocktails/blue_lagoon.jpg', (SELECT id FROM categorie WHERE nom='Signatures'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), 'S', 9.00),
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), 'M', 10.50),
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), 'L', 12.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), (SELECT id FROM ingredient WHERE nom='Vodka')),
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), (SELECT id FROM ingredient WHERE nom='Curacao bleu')),
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), (SELECT id FROM ingredient WHERE nom='Limonade')),
  ((SELECT id FROM cocktail WHERE nom='Blue Lagoon'), (SELECT id FROM ingredient WHERE nom='Cerise'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Pina Colada', 'Doux & tropical', 'Evasion tropicale : rhum, lait de coco et jus d''ananas. Doux et cremeux.', '/images/cocktails/pina_colada.jpg', (SELECT id FROM categorie WHERE nom='Tropicaux'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Pina Colada'), 'S', 8.00),
  ((SELECT id FROM cocktail WHERE nom='Pina Colada'), 'M', 9.50),
  ((SELECT id FROM cocktail WHERE nom='Pina Colada'), 'L', 11.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Pina Colada'), (SELECT id FROM ingredient WHERE nom='Rhum blanc')),
  ((SELECT id FROM cocktail WHERE nom='Pina Colada'), (SELECT id FROM ingredient WHERE nom='Lait de coco')),
  ((SELECT id FROM cocktail WHERE nom='Pina Colada'), (SELECT id FROM ingredient WHERE nom='Ananas'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Sex on the Beach', 'Fruite & solaire', 'Fruite et solaire : vodka, schnaps peche, jus d''orange et cranberry.', '/images/cocktails/sex_on_the_beach.jpg', (SELECT id FROM categorie WHERE nom='Tropicaux'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), 'S', 9.00),
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), 'M', 10.50),
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), 'L', 12.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), (SELECT id FROM ingredient WHERE nom='Vodka')),
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), (SELECT id FROM ingredient WHERE nom='Schnaps peche')),
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), (SELECT id FROM ingredient WHERE nom='Jus de cranberry')),
  ((SELECT id FROM cocktail WHERE nom='Sex on the Beach'), (SELECT id FROM ingredient WHERE nom='Jus d''orange'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Tequila Sunrise', 'Soleil couchant', 'Tequila, jus d''orange et grenadine, en degrade soleil couchant.', '/images/cocktails/tequila_sunrise.jpg', (SELECT id FROM categorie WHERE nom='Tropicaux'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Tequila Sunrise'), 'S', 8.00),
  ((SELECT id FROM cocktail WHERE nom='Tequila Sunrise'), 'M', 9.50),
  ((SELECT id FROM cocktail WHERE nom='Tequila Sunrise'), 'L', 11.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Tequila Sunrise'), (SELECT id FROM ingredient WHERE nom='Tequila')),
  ((SELECT id FROM cocktail WHERE nom='Tequila Sunrise'), (SELECT id FROM ingredient WHERE nom='Jus d''orange')),
  ((SELECT id FROM cocktail WHERE nom='Tequila Sunrise'), (SELECT id FROM ingredient WHERE nom='Grenadine'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('B-52', 'Trois couches', 'Le shot en trois couches : Kahlua, Baileys et Grand Marnier.', '/images/cocktails/b_52.jpg', (SELECT id FROM categorie WHERE nom='Shots'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='B-52'), 'S', 5.00),
  ((SELECT id FROM cocktail WHERE nom='B-52'), 'M', 6.50),
  ((SELECT id FROM cocktail WHERE nom='B-52'), 'L', 8.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='B-52'), (SELECT id FROM ingredient WHERE nom='Baileys')),
  ((SELECT id FROM cocktail WHERE nom='B-52'), (SELECT id FROM ingredient WHERE nom='Grand Marnier')),
  ((SELECT id FROM cocktail WHERE nom='B-52'), (SELECT id FROM ingredient WHERE nom='Kahlua'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Kamikaze', 'Vif & acidule', 'Shot vif et acidule : vodka, triple sec et jus de citron vert.', '/images/cocktails/kamikaze.jpg', (SELECT id FROM categorie WHERE nom='Shots'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Kamikaze'), 'S', 5.00),
  ((SELECT id FROM cocktail WHERE nom='Kamikaze'), 'M', 6.50),
  ((SELECT id FROM cocktail WHERE nom='Kamikaze'), 'L', 8.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Kamikaze'), (SELECT id FROM ingredient WHERE nom='Vodka')),
  ((SELECT id FROM cocktail WHERE nom='Kamikaze'), (SELECT id FROM ingredient WHERE nom='Triple sec')),
  ((SELECT id FROM cocktail WHERE nom='Kamikaze'), (SELECT id FROM ingredient WHERE nom='Jus de citron vert'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Virgin Mojito', 'Sans alcool', 'Toute la fraicheur du Mojito, sans alcool : menthe, citron vert, sucre de canne et eau gazeuse.', '/images/cocktails/virgin_mojito.jpg', (SELECT id FROM categorie WHERE nom='Mocktails'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), 'S', 5.00),
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), 'M', 6.50),
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), 'L', 8.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), (SELECT id FROM ingredient WHERE nom='Menthe')),
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), (SELECT id FROM ingredient WHERE nom='Citron vert')),
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), (SELECT id FROM ingredient WHERE nom='Sucre de canne')),
  ((SELECT id FROM cocktail WHERE nom='Virgin Mojito'), (SELECT id FROM ingredient WHERE nom='Eau gazeuse'));

INSERT INTO cocktail (nom, accroche, description, image_url, categorie_id) VALUES ('Virgin Colada', 'Sans alcool & cremeux', 'Sans alcool, tout en douceur : lait de coco et jus d''ananas frappes.', '/images/cocktails/virgin_colada.jpg', (SELECT id FROM categorie WHERE nom='Mocktails'));
INSERT INTO cocktail_taille (cocktail_id, taille, prix) VALUES
  ((SELECT id FROM cocktail WHERE nom='Virgin Colada'), 'S', 5.00),
  ((SELECT id FROM cocktail WHERE nom='Virgin Colada'), 'M', 6.50),
  ((SELECT id FROM cocktail WHERE nom='Virgin Colada'), 'L', 8.00);
INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id) VALUES
  ((SELECT id FROM cocktail WHERE nom='Virgin Colada'), (SELECT id FROM ingredient WHERE nom='Lait de coco')),
  ((SELECT id FROM cocktail WHERE nom='Virgin Colada'), (SELECT id FROM ingredient WHERE nom='Jus d''ananas'));

-- ---------- Favoris (mis en avant cote client) ----------
UPDATE cocktail SET favori = true WHERE nom IN ('Mojito', 'Margarita', 'Cosmopolitan', 'Espresso Martini');