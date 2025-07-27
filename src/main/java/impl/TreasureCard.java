package impl;

import java.util.Random;

import api.CardWithGem;
import api.TypeCard;

public class TreasureCard extends CardWithGem{

    public TreasureCard(String name, TypeCard type, String imagePath, int gemValue) {
        super(name, type, imagePath);
        this.gemValue = gemValue;
    }

}
