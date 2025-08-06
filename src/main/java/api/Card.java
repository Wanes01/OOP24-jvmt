package api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Card {

    private final String name;
    private final TypeCard type;
    private final String imagePath;
    private final BufferedImage imageCard;

    public Card(final String name, final TypeCard type, final String imagePath) {
        this.name = name;
        this.type = type;
        this.imagePath = imagePath;

        try {
            this.imageCard = ImageIO.read(new File(this.imagePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Image not available at path: " + this.imagePath);
        }
    }

    public String getName() {
        return this.name;
    }

    public TypeCard getType() {
        return this.type;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public BufferedImage getImageCard() {
        return this.imageCard;
    }

    @Override
    public String toString() {
        return "Card [getName()=" + getName() + ", getType()=" + getType() + ", getImagePath()=" + getImagePath() + "]";
    }
}
