package fr.barapp.barapp.service;

import fr.barapp.barapp.dto.CategorieDto;
import fr.barapp.barapp.dto.CategorieRequest;
import fr.barapp.barapp.entity.Categorie;
import fr.barapp.barapp.exception.RessourceIntrouvableException;
import fr.barapp.barapp.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Logique métier des catégories. */
@Service
@Transactional
public class CategorieService {

    private final CategorieRepository repository;

    public CategorieService(CategorieRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategorieDto> lister() {
        return repository.findAll().stream().map(this::versDto).toList();
    }

    @Transactional(readOnly = true)
    public CategorieDto trouver(Long id) {
        return versDto(getOuErreur(id));
    }

    public CategorieDto creer(CategorieRequest req) {
        Categorie c = new Categorie();
        c.setNom(req.nom());
        return versDto(repository.save(c));
    }

    public CategorieDto modifier(Long id, CategorieRequest req) {
        Categorie c = getOuErreur(id);
        c.setNom(req.nom());
        return versDto(repository.save(c));
    }

    public void supprimer(Long id) {
        repository.delete(getOuErreur(id));
    }

    private Categorie getOuErreur(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> RessourceIntrouvableException.of("Catégorie", id));
    }

    private CategorieDto versDto(Categorie c) {
        return new CategorieDto(c.getId(), c.getNom());
    }
}
