package jvmt.model.game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Extension of RunTimeException for the game settings error messages.
 * 
 * @see GameSettingsImpl
 * 
 * @author Filippo Gaggi
 */
public class InvalidGameSettingsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * List of strings that contain the various errors.
     */
    private final List<String> errors;

    /**
     * Constructor of the method.
     * 
     * @throws NullPointerException if {@link errors} is null.
     * 
     * @param errors the list of error messages.
     */
    public InvalidGameSettingsException(final List<String> errors) {
        super(String.join("; ", Objects.requireNonNull(errors)));
        this.errors = new ArrayList<>(Objects.requireNonNull(errors));
    }

    /**
     * Getter for the list of error messages.
     * 
     * @return the list of error messages.
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
}
