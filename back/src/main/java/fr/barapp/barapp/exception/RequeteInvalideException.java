package fr.barapp.barapp.exception;

/** Levée quand une requête est métier-invalide (-> HTTP 400). */
public class RequeteInvalideException extends RuntimeException {

    public RequeteInvalideException(String message) {
        super(message);
    }
}
