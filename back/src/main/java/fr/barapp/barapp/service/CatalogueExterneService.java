package fr.barapp.barapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.CocktailExterneDto;
import fr.barapp.barapp.dto.ImportCocktailRequest;
import fr.barapp.barapp.dto.TailleRequest;
import fr.barapp.barapp.entity.Categorie;
import fr.barapp.barapp.entity.Cocktail;
import fr.barapp.barapp.entity.CocktailTaille;
import fr.barapp.barapp.entity.Ingredient;
import fr.barapp.barapp.exception.RequeteInvalideException;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.mapper.CocktailMapper;
import fr.barapp.barapp.repository.CategorieRepository;
import fr.barapp.barapp.repository.CocktailRepository;
import fr.barapp.barapp.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Recherche et import de cocktails depuis le catalogue externe TheCocktailDB. */
@Service
public class CatalogueExterneService {

    private static final String BASE = "https://www.thecocktaildb.com/api/json/v1/1/";

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final CocktailRepository cocktailRepository;
    private final CategorieRepository categorieRepository;
    private final IngredientRepository ingredientRepository;

    public CatalogueExterneService(CocktailRepository cocktailRepository,
                                   CategorieRepository categorieRepository,
                                   IngredientRepository ingredientRepository) {
        this.cocktailRepository = cocktailRepository;
        this.categorieRepository = categorieRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /** Liste de cocktails à parcourir par défaut (sans recherche). */
    public List<CocktailExterneDto> suggestions() {
        JsonNode drinks = appel("filter.php?c=Cocktail").path("drinks");
        List<CocktailExterneDto> resultats = new ArrayList<>();
        if (drinks.isArray()) {
            for (JsonNode d : drinks) {
                resultats.add(new CocktailExterneDto(
                        texte(d, "idDrink"), texte(d, "strDrink"),
                        texte(d, "strDrinkThumb"), null, List.of()));
                if (resultats.size() >= 30) {
                    break;
                }
            }
        }
        return resultats;
    }

    public List<CocktailExterneDto> rechercher(String requete) {
        JsonNode drinks = appel("search.php?s=" + encode(requete)).path("drinks");
        List<CocktailExterneDto> resultats = new ArrayList<>();
        if (drinks.isArray()) {
            for (JsonNode d : drinks) {
                resultats.add(new CocktailExterneDto(
                        texte(d, "idDrink"), texte(d, "strDrink"),
                        texte(d, "strDrinkThumb"), description(d), ingredients(d)));
            }
        }
        return resultats;
    }

    @Transactional
    public CocktailDto importer(ImportCocktailRequest req) {
        JsonNode drinks = appel("lookup.php?i=" + encode(req.idExterne())).path("drinks");
        if (!drinks.isArray() || drinks.isEmpty()) {
            throw new RequeteInvalideException("Cocktail externe introuvable");
        }
        JsonNode d = drinks.get(0);
        Categorie categorie = categorieRepository.findById(req.categorieId())
                .orElseThrow(() -> RessourceIntrouvableException.of("Catégorie", req.categorieId()));

        Cocktail c = new Cocktail();
        c.setNom(texte(d, "strDrink"));
        c.setDescription(description(d));
        c.setImageUrl(texte(d, "strDrinkThumb"));
        c.setCategorie(categorie);
        for (int i = 1; i <= 15; i++) {
            String v = texte(d, "strIngredient" + i);
            if (v == null || v.isBlank()) {
                continue;
            }
            String anglais = v.trim();
            String fr = TraductionIngredients.traduire(anglais);
            if (fr != null) {
                c.getIngredients().add(getOuCreerIngredient(fr, anglais));
            }
        }
        for (TailleRequest t : req.tailles()) {
            CocktailTaille ct = new CocktailTaille();
            ct.setCocktail(c);
            ct.setTaille(t.taille());
            ct.setPrix(t.prix());
            c.getTailles().add(ct);
        }
        return CocktailMapper.versDto(cocktailRepository.save(c));
    }

    private Ingredient getOuCreerIngredient(String fr, String anglais) {
        return ingredientRepository.findByNomIgnoreCase(fr).orElseGet(() -> {
            Ingredient i = new Ingredient();
            i.setNom(fr);
            i.setImageUrl("https://www.thecocktaildb.com/images/ingredients/"
                    + encode(anglais).replace("+", "%20") + "-Small.png");
            return ingredientRepository.save(i);
        });
    }

    private String description(JsonNode d) {
        String fr = texte(d, "strInstructionsFR");
        return (fr != null && !fr.isBlank()) ? fr : texte(d, "strInstructions");
    }

    private List<String> ingredients(JsonNode d) {
        List<String> liste = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            String v = texte(d, "strIngredient" + i);
            if (v != null && !v.isBlank()) {
                String t = TraductionIngredients.traduire(v.trim());
                if (t != null && !liste.contains(t)) {
                    liste.add(t);
                }
            }
        }
        return liste;
    }

    private JsonNode appel(String chemin) {
        try {
            HttpRequest requete = HttpRequest.newBuilder(URI.create(BASE + chemin)).GET().build();
            HttpResponse<String> reponse = http.send(requete, HttpResponse.BodyHandlers.ofString());
            return mapper.readTree(reponse.body());
        } catch (Exception e) {
            throw new RequeteInvalideException("Catalogue externe momentanément indisponible");
        }
    }

    private String texte(JsonNode d, String champ) {
        JsonNode n = d.get(champ);
        return (n == null || n.isNull()) ? null : n.asText();
    }

    private String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
