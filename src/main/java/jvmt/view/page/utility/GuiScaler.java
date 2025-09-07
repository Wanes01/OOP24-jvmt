package jvmt.view.page.utility;

import java.awt.Dimension;
import java.util.Objects;

/**
 * Utility class for scaling UI elements proportionally based on the window's
 * dimensions.
 * This class receives the dimensions of the view.
 * It provides methods for calculating the dimensions of components as a
 * percentage
 * of the width and height of the input.
 * 
 * @author Andrea La Tosa
 */
public class GuiScaler {

    private final Dimension winDim;

    /**
     * Constructs a {@code GuiScaler} using the specified view dimensions.
     * 
     * @param winDim the width and height dimensions of the window
     * 
     * @throws NullPointerException if winDim is null
     */
    public GuiScaler(final Dimension winDim) {
        this.winDim = Objects.requireNonNull(winDim, "winDim cannot be null");
    }

    /**
     * Scales a given width ratio based on the window's width.
     * 
     * @param ratio a value between 0.0 and 1.0
     *              representing the percentage of the total width
     * 
     * @return the computed width in pixels
     */
    public int scaleWidth(final double ratio) {
        return (int) (this.winDim.getWidth() * ratio);
    }

    /**
     * Scales a given height ratio based on the window's height.
     * 
     * @param ratio a value between 0.0 and 1.0
     *              representing the percentage of the total height
     * 
     * @return the computed hight in pixels
     */
    public int scaleHeight(final double ratio) {
        return (int) (winDim.getHeight() * ratio);
    }

    /**
     * Scales both the width and height of the input based on the size of the
     * window.
     * 
     * @param ratioWidth  a value between 0.0 and 1.0
     *                    representing the percentage of the total width
     * @param ratioHeight a value between 0.0 and 1.0
     *                    representing the percentage of the total height
     * 
     * @return the height and width calculated in pixels
     */
    public Dimension scaleDim(final double ratioWidth, final double ratioHeight) {
        return new Dimension(scaleWidth(ratioWidth), scaleHeight(ratioHeight));
    }
}
