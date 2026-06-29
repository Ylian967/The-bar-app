package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.CocktailDto;
import fr.barapp.barapp.dto.CocktailRequest;
import fr.barapp.barapp.dto.TailleRequest;
import fr.barapp.barapp.entity.Cocktail;
import fr.barapp.barapp.entity.CocktailTaille;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.mapper.CocktailMapper;
import fr.barapp.barapp.repository.CategorieRepository;
import fr.barapp.barapp.repository.CocktailRepository;
import fr.barapp.barapp.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

/** Logique métier des cocktails (carte + gestion barman). */
@Service
@Transactional
public class CocktailService {

    private final CocktailRepository cocktailRepository;
    private final CategorieRepository categorieRepository;
    private final IngredientRepository ingredientRepository;

    public CocktailService(CocktailRepository cocktailRepository,
                           CategorieRepository categorieRepository,
                           IngredientRepository ingredientRepository) {
        this.cocktailRepository = cocktailRepository;
        this.categorieRepository = categorieRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional(readOnly = true)
    public List<CocktailDto> lister(Long categorieId) {
        List<Cocktail> cocktails = (categorieId == null)
                ? cocktailRepository.findAll()
                : cocktailRepository.findByCategorieId(categorieId);
        return cocktails.stream().map(CocktailMapper::versDto).toList();
    }

    @Transactional(readOnly = true)
    public CocktailDto trouver(Long id) {
        return CocktailMapper.versDto(getOuErreur(id));
    }

    public CocktailDto creer(CocktailRequest req) {
        return CocktailMapper.versDto(cocktailRepository.save(appliquer(new Cocktail(), req)));
    }

    public CocktailDto modifier(Long id, CocktailRequest req) {
        return CocktailMapper.versDto(cocktailRepository.save(appliquer(getOuErreur(id), req)));
    }

    public void supprimer(Long id) {
        cocktailRepository.delete(getOuErreur(id));
    }

    private Cocktail getOuErreur(Long id) {
        return cocktailRepository.findById(id)
                .orElseThrow(() -> RessourceIntrouvableException.of("Cocktail", id));
    }

    /** Reporte les données du DTO sur l'entité (création comme modification). */
    private Cocktail appliquer(Cocktail c, CocktailRequest req) {
        c.setNom(req.nom());
        c.setAccroche(req.accroche());
        c.setDescription(req.description());
        c.setImageUrl(req.imageUrl());
        c.setCategorie(categorieRepository.findById(req.categorieId())
                .orElseThrow(() -> RessourceIntrouvableException.of("Catégorie", req.categorieId())));

        if (req.ingredientIds() != null) {
            c.setIngredients(new LinkedHashSet<>(ingredientRepository.findAllById(req.ingredientIds())));
        }

        c.getTailles().clear();
        for (TailleRequest tr : req.tailles()) {
            CocktailTaille t = new CocktailTaille();
            t.setCocktail(c);
            t.setTaille(tr.taille());
            t.setPrix(tr.prix());
            c.getTailles().add(t);
        }
        return c;
    }
}
