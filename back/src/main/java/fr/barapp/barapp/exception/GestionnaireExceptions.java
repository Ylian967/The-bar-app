package fr.barapp.barapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** Transforme les exceptions en réponses HTTP propres et homogènes. */
@RestControllerAdvice
public class GestionnaireExceptions {

    /** Ressource inexistante -> 404. */
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ResponseEntity<Map<String, Object>> introuvable(RessourceIntrouvableException ex) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /** Identifiants invalides -> 401. */
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> authentification(
            org.springframework.security.core.AuthenticationException ex) {
        return reponse(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect");
    }

    /** Règle métier non respectée -> 400. */
    @ExceptionHandler(RequeteInvalideException.class)
    public ResponseEntity<Map<String, Object>> requeteInvalide(RequeteInvalideException ex) {
        return reponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /** Données d'entrée invalides -> 400 avec le détail par champ. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> invalide(MethodArgumentNotValidException ex) {
        Map<String, Object> corps = base(HttpStatus.BAD_REQUEST, "Données invalides");
        Map<String, String> champs = new HashMap<>();
        for (FieldError e : ex.getBindingResult().getFieldErrors()) {
            champs.put(e.getField(), e.getDefaultMessage());
        }
        corps.put("champs", champs);
        return ResponseEntity.badRequest().body(corps);
    }

    private ResponseEntity<Map<String, Object>> reponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(base(status, message));
    }

    private Map<String, Object> base(HttpStatus status, String message) {
        Map<String, Object> corps = new HashMap<>();
        corps.put("date", LocalDateTime.now());
        corps.put("status", status.value());
        corps.put("message", message);
        return corps;
    }
}
