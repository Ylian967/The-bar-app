package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.IngredientDto;
import fr.barapp.barapp.dto.IngredientRequest;
import fr.barapp.barapp.entity.Ingredient;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Logique métier des ingrédients. */
@Service
@Transactional
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<IngredientDto> lister() {
        return repository.findAll().stream().map(this::versDto).toList();
    }

    public IngredientDto creer(IngredientRequest req) {
        Ingredient i = new Ingredient();
        i.setNom(req.nom());
        return versDto(repository.save(i));
    }

    public IngredientDto modifier(Long id, IngredientRequest req) {
        Ingredient i = getOuErreur(id);
        i.setNom(req.nom());
        return versDto(repository.save(i));
    }

    public void supprimer(Long id) {
        repository.delete(getOuErreur(id));
    }

    private Ingredient getOuErreur(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> RessourceIntrouvableException.of("Ingrédient", id));
    }

    private IngredientDto versDto(Ingredient i) {
        return new IngredientDto(i.getId(), i.getNom());
    }
}
