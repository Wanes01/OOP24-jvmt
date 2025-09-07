package jvmt.view.page.utility;

import java.util.Objects;

/**
 * Contains useful methods for representing text in graphic elements.
 * 
 * @author Andrea La Tosa
 */
public final class HtmlUtils {

    private HtmlUtils() {
    }

    /**
     * Is intended to wrap the string to start a new line after maxLineLength
     * characters without cutting the word.
     * If a word longer than maxLineLength is passed,
     * it will be wrapped and exceed the maximum length allowed.
     * 
     * @param str           the string to be wrapped
     * @param maxLineLength the maximum line length before a line break
     * 
     * @return the wrapped string
     * 
     * @throws NullPointerException     if str is null
     * @throws IllegalArgumentException if maxLineLength is less or equal to zero
     */
    public static String wrapTextHTML(final String str, final int maxLineLength) {
        Objects.requireNonNull(str);

        if (maxLineLength <= 0) {
            throw new IllegalArgumentException("maxLineLength must be greater than zero.");
        }

        int lineLength = 0;
        final StringBuilder sb = new StringBuilder("<html>");

        for (final String word : str.split(" ")) {
            if (lineLength + word.length() > maxLineLength) {
                sb.append("<br>");
                lineLength = 0;
            } else {
                sb.append(' ');
                lineLength++;
            }
            sb.append(word);
            lineLength += word.length();
        }
        sb.append("</html>");

        return sb.toString();
    }
}
