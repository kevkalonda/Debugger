package dbg.core;

/**
 * Gestion error
 */
public class Error {
    String message;

    /**
     * Getter
     *
     * @return {@link String}
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter
     *
     * @param message {@link String}
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Reset erreur 
     */
    public void reset() {
        message = "";
    }
}
