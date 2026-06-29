package fr.barapp.barapp.service;

import java.util.Map;
import java.util.Set;

/** Traduit en français les noms d'ingrédients renvoyés (en anglais) par TheCocktailDB. */
final class TraductionIngredients {

    private TraductionIngredients() {
    }

    private static final Set<String> IGNORES = Set.of("ice", "water", "crushed ice", "ice cubes");

    private static final Map<String, String> FR = Map.ofEntries(
            Map.entry("light rum", "Rhum blanc"), Map.entry("white rum", "Rhum blanc"),
            Map.entry("rum", "Rhum"), Map.entry("dark rum", "Rhum ambré"), Map.entry("gold rum", "Rhum ambré"),
            Map.entry("tequila", "Tequila"), Map.entry("vodka", "Vodka"), Map.entry("gin", "Gin"),
            Map.entry("cachaca", "Cachaça"), Map.entry("lime", "Citron vert"),
            Map.entry("lime juice", "Jus de citron vert"), Map.entry("lemon", "Citron"),
            Map.entry("lemon juice", "Jus de citron"), Map.entry("mint", "Menthe"),
            Map.entry("sugar", "Sucre"), Map.entry("sugar syrup", "Sirop de sucre"),
            Map.entry("powdered sugar", "Sucre glace"), Map.entry("soda water", "Eau gazeuse"),
            Map.entry("carbonated water", "Eau gazeuse"), Map.entry("club soda", "Eau gazeuse"),
            Map.entry("triple sec", "Triple sec"), Map.entry("cointreau", "Cointreau"),
            Map.entry("blue curacao", "Curaçao bleu"), Map.entry("orange curacao", "Curaçao orange"),
            Map.entry("curacao", "Curaçao"), Map.entry("campari", "Campari"),
            Map.entry("sweet vermouth", "Vermouth rouge"), Map.entry("vermouth", "Vermouth"),
            Map.entry("cranberry juice", "Jus de cranberry"), Map.entry("orange juice", "Jus d'orange"),
            Map.entry("pineapple juice", "Jus d'ananas"), Map.entry("pineapple", "Ananas"),
            Map.entry("coconut milk", "Lait de coco"), Map.entry("coconut cream", "Crème de coco"),
            Map.entry("coffee", "Café"), Map.entry("espresso", "Espresso"),
            Map.entry("coffee liqueur", "Liqueur de café"), Map.entry("kahlua", "Kahlúa"),
            Map.entry("baileys irish cream", "Baileys"), Map.entry("grand marnier", "Grand Marnier"),
            Map.entry("grenadine", "Grenadine"), Map.entry("peach schnapps", "Schnaps pêche"),
            Map.entry("orgeat syrup", "Sirop d'orgeat"), Map.entry("lemonade", "Limonade"),
            Map.entry("salt", "Sel"), Map.entry("cherry", "Cerise"), Map.entry("orange", "Orange"),
            Map.entry("amaretto", "Amaretto"), Map.entry("angostura bitters", "Angostura"));

    static String traduire(String nomAnglais) {
        String cle = nomAnglais.toLowerCase();
        if (IGNORES.contains(cle)) {
            return null;
        }
        String fr = FR.get(cle);
        if (fr != null) {
            return fr;
        }
        return nomAnglais.substring(0, 1).toUpperCase() + nomAnglais.substring(1);
    }
}
