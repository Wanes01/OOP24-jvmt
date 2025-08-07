package utils;

import java.util.Random;

/**
 * Utility class containing common helper methods for
 * general-purpose operations.
 * 
 * @author Emir Wanes Aouioua
 * @author Andrea La Tosa
 * @author Filippo Gaggi
 */
public final class CommonUtils {

    /**
     * Generates a random integer between the specified {@code min} and {@code max}
     * values (inclusive)
     * 
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     * @return a random integer between {@code min} and {@code max}
     * 
     * @throws IllegalArgumentException if {@code min} is greater than {@code max}
     */
    public static int randomIntBetweenValues(final int min, final int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less or equal to max.");
        }
        final Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random double between the specified {@code min} (inclusive) and
     * {@code max} (exclusive).
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random double between {@code min} (inclusive) and {@code max}
     *         (exclusive)
     * @throws IllegalArgumentException if {@code min} is greater than {@code max}
     */
    public static double randomDoubleBetweenValues(final double min, final double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less or equal to max.");
        }
        final Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
}
