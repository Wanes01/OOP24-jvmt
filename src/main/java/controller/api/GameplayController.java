package controller.api;

import java.awt.Image;
import java.util.List;
import java.util.Optional;

import controller.impl.GameplayControllerImpl;
import view.window.api.Window;

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
     * @return the current turn number.
     */
    int getCurrentTurnNumber();

    /**
     * @return the current round number.
     */
    int getCurrentRoundNumber();

    /**
     * @return the number of current redeemable relics.
     */
    int getRedeemableRelicsNumber();

    /**
     * @return the current path gems.
     */
    int getPathGems();

    /**
     * @return the game's gem modifier description.
     */
    String getGemModifierDescrition();

    /**
     * @return the game's end condition description.
     */
    String getEndConditionDescription();

    /**
     * @return the list of names of the current active players.
     */
    List<String> getActivePlayersNames();

    /**
     * @return the list of names of the current exited players.
     */
    List<String> getExitedPlayersNames();

    /**
     * @return the number of drawn cards.
     */
    int getDrawnCardsNumber();

    /**
     * @return the image of the drawn card.
     */
    Optional<Image> getDrawnCardImage();

    /**
     * Executes the turn's draw phase.
     */
    void executeDrawPhase();

    /**
     * @return true if the current player is a CPU and false if not.
     */
    boolean isCurrentPlayerACpu();

    /**
     * Executes the turn's decision phase.
     * 
     * @throws NullPointerException if {@link window} is null.
     * 
     * @param toBlockWindow the main application window.
     */
    void executeDecisionPhase(Window toBlockWindow);

    /**
     * Method for checking if the game can continue.
     * It checks if another turn or another round exist.
     * 
     * @return true if the game can continue or false if not.
     */
    boolean canGameContinue();

    /**
     * Method for checking if the rounds can continue.
     * 
     * @return true if the rounds can continue or false if not.
     */
    boolean canRoundContinue();

    /**
     * Method for advancing to the next turn or round.
     */
    void advance();

    /**
     * Method that redirects to the Leaderboard page.
     * Executes the turn's decision phase.
     * 
     * @throws NullPointerException if {@link reset} is null.
     * 
     */
    void goToLeaderboard();
}
