package controller.api;

import java.util.List;
import java.util.Set;

import controller.impl.GameplayControllerImpl;
import model.card.api.Card;
import model.player.impl.PlayerInRound;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import view.window.impl.SwingWindow;

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
     * Executes the turn's draw phase.
     */
    void drawPhase();

    /**
     * Executes the turn's choice phase.
     * 
     * @throws NullPointerException if {@link window} is null.
     * 
     * @param window the main application window.
     * 
     * @return the set of exiting players of the turn.
     */
    Set<PlayerInRound> choicePhase(SwingWindow window);

    /**
     * Method for controlling if the game is over.
     * 
     * @return true if the game is over or false if not.
     */
    boolean isGameOver();

    /**
     * Advances to the next turn.
     * 
     * @throws NullPointerException if {@link exitedPlayers} is null.
     * 
     * @param exitedPlayers the set of exiting players of the turn.
     */
    void advanceTurn(Set<PlayerInRound> exitedPlayers);

    /**
     * Method that redirects to the Leaderboard page.
     */
    void goToLeaderboardPage();
}
