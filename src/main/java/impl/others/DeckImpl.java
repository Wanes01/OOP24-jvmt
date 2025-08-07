package impl.others;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import api.others.Card;
import api.others.Deck;
import api.others.TrapCard;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class DeckImpl implements Deck {

    private static final int RELICS = 5;
    private static final int TRAPS = 15;
    private static final int TREASURE = 15;
    private static final Set<String> TRAP_NAMES = Set.of("T1", "T2", "T3", "T4", "T5");

    private final List<Card> cards = new ArrayList<>();

    @SuppressFBWarnings(value = "LV_MC", justification = "Questo chiamata è sicura in questo contesto specifico.")
    public DeckImpl() {
        for (int r = 0; r < RELICS; r++) {
            this.cards.add(new RelicCardImpl());
        }
        for (int t = 0; t < TREASURE; t++) {
            this.cards.add(new TreasureCardImpl());
        }
        TRAP_NAMES.stream().flatMap(tn -> {
            final int rep = TRAPS / TRAP_NAMES.size();
            final List<TrapCard> traps = new ArrayList<>();
            for (int r = 0; r < rep; r++) {
                traps.add(new TrapCardImpl(tn));
            }
            return traps.stream();
        }).forEach(cards::add);

        this.shuffle();
    }

    @Override
    public Card drawCard() {
        return this.cards.removeLast();
    }

    @Override
    public final void shuffle() {
        Collections.shuffle(this.cards);
    }

    @Override
    public int numberOfRemainingCards() {
        return this.cards.size();
    }

    @Override
    public Card peekCard() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return this.numberOfRemainingCards() > 0;
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }

    @Override
    public int totRelicCardsInDeck() {
        return RELICS;
    }

    @Override
    public int totTreasureCardsInDeck() {
        return TREASURE;
    }

    @Override
    public int totTrapCardsInDeck() {
        return TRAPS;
    }
}
