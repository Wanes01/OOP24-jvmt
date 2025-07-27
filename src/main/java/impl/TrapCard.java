package impl;

import api.Card;
import api.TypeCard;

public class TrapCard extends Card{

    private TrapCard typeTrap;

    public TrapCard(String name, TypeCard type, String imagePath, TrapCard typeTrap) {
        super(name, type, imagePath);
        this.typeTrap = typeTrap;
    }

    public TrapCard getTypeTrap() {
        return typeTrap;
    }

}
