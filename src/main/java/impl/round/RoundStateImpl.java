package impl.round;

import java.util.ArrayList;
import java.util.List;

import api.others.Card;
import api.others.Deck;
import api.others.PlayerInRound;
import api.others.RelicCard;
import api.others.TrapCard;
import api.round.RoundPlayersManager;
import api.round.RoundState;

/**
 * Concrete implementation of {@link RoundState}, responsible
 * for managing the dynamic state of a round during the game.
 * 
 * <p>
 * This class keeps track of the players currently involved in the round
 * via {@link RoundPlayersManager}, the cards drawn during the round,
 * the deck of card used for this round and the total gems on the path.
 * </p>
 * 
 * @see RoundPlayersManager
 * @see Round
 * @see Deck
 * 
 * @author Emir Wanes Aouioua
 */
public class RoundStateImpl implements RoundState {

    private final RoundPlayersManager playersManager;
    private final List<Card> drawnCards;
    private final Deck deck;
    private int pathGems;

    /**
     * Initializes the round's state with the given list of players and deck to be
     * used in the round.
     * 
     * @param players the players partecipating in the round.
     *                <p>
     *                Note:
     *                {@link RoundPlayersManagerImpl} throws an
     *                {@link IllegalArgumentException} if a player in this list is
     *                not active.
     *                </p>
     * 
     * @param deck    the deck that will be used in this round.
     */
    public RoundStateImpl(final List<PlayerInRound> players, final Deck deck) {
        this.playersManager = new RoundPlayersManagerImpl(players);
        this.deck = deck;
        this.drawnCards = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Card> getDrawCards() {
        return new ArrayList<>(this.drawnCards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RelicCard> getDrawnRelics() {
        return this.drawnCards.stream()
                .filter(c -> c instanceof RelicCard)
                .map(c -> (RelicCard) c)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TrapCard> getDrawnTraps() {
        return this.drawnCards.stream()
                .filter(c -> c instanceof TrapCard)
                .map(c -> (TrapCard) c)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPathGems() {
        return this.pathGems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck getDeck() {
        return this.deck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoundPlayersManager getRoundPlayersManager() {
        return this.playersManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCardToPath(final Card card) {
        this.drawnCards.add(card);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException if a negative amount of gems is specified.
     */
    @Override
    public void setPathGems(final int gems) {
        if (gems < 0) {
            throw new IllegalArgumentException("Path's gem amount can't be negative.");
        }
        this.pathGems = gems;
    }
}
