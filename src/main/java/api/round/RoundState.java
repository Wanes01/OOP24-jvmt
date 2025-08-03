package api.round;

import java.util.List;
import api.others.Card;
import api.others.Deck;
import api.others.PlayerInRound;
import api.others.RelicCard;
import api.others.TrapCard;

/**
 * Rapresents the current state of a round in the game.
 * <p>
 * This interface provides access to various data related to the round,
 * including the deck used, the cards drawn so far, the remaining gems in the
 * path and the status of the players involved. It is used by the round logic,
 * the individual turns and the graphical user interface to retrieve
 * information about the current progression of the round.
 * </p>
 * 
 * @see Round
 * @see RoundPlayersManager
 * @see Card
 * 
 * @author Emir Wanes Aouioua
 */
public interface RoundState {

    /**
     * 
     * @return all cards drawn from the deck used during this round, in their order
     *         of appearance.
     */
    List<Card> getDrawCards();

    /**
     * 
     * @return a list of all relic cards drawn from the deck used during this round,
     *         in their order of appearance.
     */
    List<RelicCard> getDrawnRelics();

    /**
     * 
     * @return a list of all trap cards drawn from the deck used during this round,
     *         in their order of appearance.
     */
    List<TrapCard> getDrawnTraps();

    /**
     * 
     * @return a list of players who are still active in the current round (players
     *         who, at the time this method is called, have always chosen to
     *         continue exploring during the various turns of the round) sorted by
     *         turn sequence.
     */
    List<PlayerInRound> getActivePlayers();

    /**
     * 
     * @return a list containing the players who, at the time this method was
     *         called, decided to abandon exploration, sorted by turn sequence.
     */
    List<PlayerInRound> getExitedPlayers();

    /**
     * 
     * @return the total number of gems left on the path to be divided among the
     *         players who decide to leave during a turn.
     */
    int getPathGems();

    /**
     * 
     * @return the deck from which the cards are drawn during this round.
     */
    Deck getDeck();

    /**
     * 
     * @return the player manager used during this round to determine the next
     *         player.
     */
    RoundPlayersManager getRoundPlayersManager();

    /**
     * Adds a card to the path of cards drawn during the exploration of this round.
     * 
     * @param card the card to add to the exploration path.
     */
    void addCardToPath(Card card);

    /**
     * Sets the total number of gems left on the exploration path of the round.
     * 
     * @param gems the total number of gems to set.
     */
    void setPathGems(int gems);

    // RoundEndCondition getRoundEndCondition();

    // RoundGemModifier getRoundGemModifier();
}
