package fr.barapp.barapp.exception;

/** Levée quand une ressource demandée n'existe pas (-> HTTP 404). */
public class RessourceIntrouvableException extends RuntimeException {

    public RessourceIntrouvableException(String message) {
        super(message);
    }

    public static RessourceIntrouvableException of(String type, Long id) {
        return new RessourceIntrouvableException(type + " introuvable (id=" + id + ")");
    }
}
