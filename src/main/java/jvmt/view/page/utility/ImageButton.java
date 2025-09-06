package jvmt.view.page.utility;

import javax.swing.JButton;

import jvmt.utils.CommonUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

/**
 * Represents a {@link JButton} that contains an adaptable {@link Image}: the
 * image associated with this {@code ImageButton} is automatically resized to
 * have the same dimensions as this button.
 * 
 * @see JButton
 * @see Image
 * 
 * @author Emir Wanes Aouioua
 */
public class ImageButton extends JButton {

    private static final long serialVersionUID = 1L;

    private final transient Image image;

    /**
     * Creates a new {@code ImageButton}.
     * 
     * @param image the image that must adapt to this button.
     */
    public ImageButton(final Image image) {
        this.image = CommonUtils.makeImageCopyAsBufferedImage(image);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * It adjusts the size of the image associated with this button to match that of
     * the button itself and draws it on top of it.
     * </p>
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        /*
         * Enables antialiasing and high quality rendering.
         * This is done to limit the loss of image quality
         * if this label is resized.
         */
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);

        g2d.dispose();
    }
}
