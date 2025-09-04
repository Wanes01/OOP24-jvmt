package view.page.utility;

import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Image;

public class ImageLabel extends JButton {

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
