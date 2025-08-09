package impl.card;

import java.util.Map;

import api.card.CardWithGem;
import api.card.TypeCard;

/**
 * Represents a treasure card.
 * 
 * @see TypeCard
 * 
 * @author Andrea La Tosa
 */
public final class TreasureCard extends CardWithGem {

    // represents the folder containing images of the various treasures
    private static final String TREASURE_PATH_IMAGE = "treasure/";
    // map that associates each type of treasures with the corresponding image
    private static final Map<Integer, String> PATH_IMAGE = Map.ofEntries(
        Map.entry(1, TREASURE_PATH_IMAGE + "1_Gem.png"),
        Map.entry(2, TREASURE_PATH_IMAGE + "2_Gem.png"),
        Map.entry(3, TREASURE_PATH_IMAGE + "3_Gem.png"),
        Map.entry(4, TREASURE_PATH_IMAGE + "4_Gem.png"),
        Map.entry(5, TREASURE_PATH_IMAGE + "5_Gem.png"),
        Map.entry(7, TREASURE_PATH_IMAGE + "7_Gem.png"),
        Map.entry(9, TREASURE_PATH_IMAGE + "9_Gem.png"),
        Map.entry(11, TREASURE_PATH_IMAGE + "11_Gem.png"),
        Map.entry(13, TREASURE_PATH_IMAGE + "13_Gem.png"),
        Map.entry(14, TREASURE_PATH_IMAGE + "14_Gem.png"),
        Map.entry(15, TREASURE_PATH_IMAGE + "15_Gem.png"),
        Map.entry(17, TREASURE_PATH_IMAGE + "17_Gem.png")
    );

    /*
    public TreasureCard(final String name, final TypeCard type, final String imagePath, final int gemValue) {
        super(name, type, imagePath);
        setGemValue(gemValue);
    }*/

    /**
     * Creates a new Treasure card.
     * The image path is automatically derived from the number of gems.
     * 
     * @param name the name of the card
     * @param gemValue the gem value of the card
     */
    public TreasureCard(final String name, final int gemValue) {
        super(name, TypeCard.TREASURE, PATH_IMAGE.get(gemValue));
        setGemValue(gemValue);
    }

    /**
     * @return Returns a string representation of the Treasure including:
     * the name, type, gem value of the card and the path to the card image.
     */
    @Override
    public String toString() {
        return "Treasure " + super.toString();
    }
}
