package controller.api;

import java.util.List;

import controller.impl.GameplayControllerImpl;
import model.card.api.Card;
import model.player.impl.PlayerInRound;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;

/**
 * Represents the controller of the gameplay page.
 * 
 * @see GameplayControllerImpl
 * 
 * @author Filippo Gaggi
 */
public interface GameplayController {

    /**
     * @return the current player's name.
     */
    String getCurrentPlayerName();

    /**
     * @return the current player's chest gems.
     */
    int getCurrentPlayerChestGems();

    /**
     * @return the current player's sack gems.
     */
    int getCurrentPlayerSackGems();

    /**
     * @return the game's end condition.
     */
    EndCondition getGameEndCondition();

    /**
     * @return the game's gem modifier.
     */
    GemModifier getGemModifier();

    /**
     * @return the current path gems.
     */
    int getPathGems();

    /**
     * @return the current path relics.
     */
    int getPathRelics();

    /**
     * @return the number of drawn cards.
     */
    int getDrawnCardsNumber();

    /**
     * @return the last drawn card.
     */
    Card getDrawnCard();

    /**
     * @return the list of the current active players.
     */
    List<PlayerInRound> getActivePlayersList();

    /**
     * @return the list of the current exited players.
     */
    List<PlayerInRound> getExitedPlayersList();

    /**
     * Executes the turn's round phase.
     */
    void drawPhase();

    /**
     * Method that redirects to the Leaderboard page.
     */
    void goToLeaderbooardPage();
}
