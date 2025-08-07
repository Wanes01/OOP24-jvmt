package api.others;

public interface Deck {
    Card drawCard();

    void shuffle();

    int numberOfRemainingCards();

    Card peekCard(); // restituisce la carta senza estrarla

    boolean hasNext();

    int totRelicCardsInDeck();

    int totTreasureCardsInDeck();

    int totTrapCardsInDeck();
}
