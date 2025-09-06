package jvmt.view.page.utility;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

public class ImageLabel extends JLabel {

    private final Image image;

    public ImageLabel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
