package jvmt.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import jvmt.model.player.api.Player;
import jvmt.model.player.impl.PlayerInRound;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
            players.add(new PlayerInRound("P-" + p));
        }
        return players;
    }

    /**
     * Generates a list containing {@code count} {@link Player}s.
     * 
     * @param count the number of players to be generated.
     * @return a list of {@code count} {@code Player}s.
     */
    public static List<Player> generatePlayerList(final int count) {
        return CommonUtils.generatePlayerInRoundList(count)
                .stream()
                .map(p -> (Player) p)
                .toList();
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

    /**
     * Checks that all the given objects are non-null.
     * 
     * @param objects the objects to check to not be null.
     */
    public static void requireNonNulls(final Object... objects) {
        for (final Object obj : objects) {
            Objects.requireNonNull(obj);
        }
    }

    /**
     * Copies an {@link Image} into a {@link BufferedImage}.
     * 
     * @param img the {@code Image} to copy.
     * @return a new {@code BufferedImage} resembling {@code img}.
     * 
     * @throws NullPointerException if {@code img} is null.
     */
    public static BufferedImage makeImageCopyAsBufferedImage(final Image img) {
        Objects.requireNonNull(img);
        final int width = img.getWidth(null);
        final int height = img.getHeight(null);
        final int type = (img instanceof BufferedImage)
                ? ((BufferedImage) img).getType()
                : BufferedImage.TYPE_INT_ARGB;

        final BufferedImage copy = new BufferedImage(width, height, type);
        final Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return copy;
    }

}
