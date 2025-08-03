package api.common;

/**
 * Represents an element that can provide a human readable description of
 * itself.
 * <p>
 * This interface is typically used to display information about objects
 * in a user interface.
 * It exists to separate user-facing descriptions from
 * {@link Object#toString()},
 * which is typically reserved for debugging.
 * </p>
 * 
 * @author Emir Wanes Aouioua
 */
public interface Describable {

    /**
     * Returns a human readable description of this object.
     * This is intended for display in GUI or other means aimed at the user.
     * 
     * @return a non-null string representing the object
     */
    String getDescription();
}
