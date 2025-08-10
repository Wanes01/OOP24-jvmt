package model.others.api;

public interface Card {

    public enum CardType {
        TREASURE, TRAP, RELIC
    };

    CardType getType();

    String getName();

    String getImage();
}
