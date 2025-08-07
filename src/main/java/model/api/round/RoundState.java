package model.api.round;

import java.util.List;

import model.api.others.Card;
import model.api.others.Deck;
import model.api.others.RelicCard;
import model.api.others.TrapCard;
import model.api.round.roundeffect.RoundEffect;

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
 * @see RoundEffect
 * @see Card
 * 
 * @author Emir Wanes Aouioua
 */
public interface RoundState {

    /**
     * Returns all cards drawn from the deck used during this round, in their order
     * of appearance.
     * 
     * @return the list of drawn cards in order of appearance.
     */
    List<Card> getDrawCards();

    /**
     * Returns the list of all relic cards drawn from the deck used during this
     * round, in their order of appearance.
     * 
     * @return the list of drawn relics in order of appearance.
     */
    List<RelicCard> getDrawnRelics();

    /**
     * Returns the list of all trap cards drawn from the deck used during this
     * round, in their order of appearance.
     * 
     * @return the list of drawn traps in order of appearance.
     */
    List<TrapCard> getDrawnTraps();

    /**
     * Returns the total number of gems left on the path to be divided among the
     * players who decide to leave during a turn.
     * 
     * @return the total number of gems left on the path.
     */
    int getPathGems();

    /**
     * Returns the deck from which the cards are drawn during this round.
     * 
     * @return the deck used in this round.
     */
    Deck getDeck();

    /**
     * Returns the player manager used during this round to determine the next
     * player.
     * 
     * @return the player manager used to determine the next player.
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
}
