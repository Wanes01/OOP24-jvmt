package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.others.api.PlayerInRound;
import model.others.impl.PlayerInRoundImpl;

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
     * This class cannot be instantiated because it is a utility class.
     */
    private CommonUtils() {
    }

    /**
     * Generates a random integer between the specified {@code min} and {@code max}
     * values (inclusive).
     * 
     * @param min the lower bound (inclusive).
     * @param max the upper bound (inclusive).
     * @return a random integer between {@code min} and {@code max}.
     * 
     * @throws IllegalArgumentException if {@code min} is greater than {@code max}.
     */
    @SuppressFBWarnings(value = "DMI_RANDOM_USED_ONLY_ONCE", justification = "static method")
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
     * @param min the lower bound (inclusive).
     * @param max the upper bound (exclusive).
     * @return a random double between {@code min} (inclusive) and {@code max}.
     *         (exclusive)
     * @throws IllegalArgumentException if {@code min} is greater than {@code max}.
     */
    @SuppressFBWarnings(value = "DMI_RANDOM_USED_ONLY_ONCE", justification = "static method")
    public static double randomDoubleBetweenValues(final double min, final double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less or equal to max.");
        }
        final Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    /**
     * Generates a list containing {@code count} {@link PlayerInRound}s.
     * 
     * @param count the number of players to be generated.
     * @return a list of {@code count} {@code PlayerInRound}s.
     */
    public static List<PlayerInRound> generatePlayerInRoundList(final int count) {
        final List<PlayerInRound> players = new ArrayList<>();
        for (int p = 0; players.size() < count; p++) {
            players.add(new PlayerInRoundImpl("P-" + p));
        }
        return players;
    }

    /**
     * Returns {@code true} with a probability of 1 out of {@code chances}.
     * 
     * @param chances the denominator to compute the probability for this function
     *                to return true ({@code 1/chances}}).
     * @return true with probability {@code 1/chances}, false otherwise.
     * @throws IllegalArgumentException if {@code chances} is less or equal to 0.
     */
    public static boolean chanceOneIn(final int chances) {
        final Random r = new Random();
        if (chances <= 0) {
            throw new IllegalArgumentException("The changes must be > 0");
        }
        return r.nextInt(chances) == 0;
    }
}
