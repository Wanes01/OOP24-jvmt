package impl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import api.CardWithGem;
import api.TypeCard;

public class RelicCard extends CardWithGem{

    private final List<Integer> POSSIBLE_RELIC_GEM = Arrays.asList(5, 7, 8, 10, 12);
    private List<Integer> actualRelicGem;

    public RelicCard(String name, TypeCard type, String imagePath) {
        super(name, type, imagePath);
        resetArtefactPlayedInRound();
        this.gemValue = generateGemValue();
        removeGeneratedGemValue();
    }

    private int generateGemValue() {
        Random rnd = new Random();
        int nextEl = rnd.nextInt(this.actualRelicGem.size());
        return this.actualRelicGem.get(nextEl);
    }

    private void removeGeneratedGemValue() {
        this.actualRelicGem.remove(getGemValue());
    }

    public void resetArtefactPlayedInRound() {
        this.actualRelicGem = List.copyOf(this.POSSIBLE_RELIC_GEM);
    }

}
