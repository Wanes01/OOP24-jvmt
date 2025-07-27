package api;

public abstract class CardWithGem extends Card{

    protected int gemValue;

    public CardWithGem(String name, TypeCard type, String imagePath) {
        super(name, type, imagePath);
    }

    public int getGemValue() {
        return this.gemValue;
    }

}
