package jvmt.view.page.utility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import jvmt.utils.CommonUtils;

/**
 * Represents a {@link JLabel} that contains an adaptable {@link Image}: the
 * image associated with this {@code ImageLabel} is automatically resized to
 * have the same dimensions as this label.
 * 
 * @see JLabel
 * @see Image
 * 
 * @author Emir Wanes Aouioua
 */
public class ImageLabel extends JLabel {

    private static final long serialVersionUID = 1L;

    private final transient Image image;

    /**
     * Creates a new {@code ImageLabel}.
     * 
     * @param image the image that must adapt to this label.
     */
    public ImageLabel(final Image image) {
        this.image = CommonUtils.makeImageCopyAsBufferedImage(image);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * It adjusts the size of the image associated with this label to match that of
     * the label itself and draws it on top of it.
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
