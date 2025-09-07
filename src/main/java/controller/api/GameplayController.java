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
     * Getter for the current player's name.
     * 
     * @return  the current player's name.
     */
    String getCurrentPlayerName();

    /**
     * Getter for the current player's chest gems.
     * 
     * @return  the current player's chest gems.
     */
    int getCurrentPlayerChestGems();

    /**
     * Getter for the current player's sack gems.
     * 
     * @return  the current player's sack gems.
     */
    int getCurrentPlayerSackGems();

    /**
     * Getter for the current turn number.
     * 
     * @return  the current turn number.
     */
    int getCurrentTurnNumber();

    /**
     * Getter for the current round number.
     * 
     * @return  the current round number.
     */
    int getCurrentRoundNumber();

    /**
     * Getter for the current redeemable relics.
     * 
     * @return  the number of the current redeemable relics.
     */
    int getRedeemableRelicsNumber();

    /**
     * Getter for the current path gems.
     * 
     * @return  the current path gems.
     */
    int getPathGems();

    /**
     * Getter for the game's gem modifier description.
     * 
     * @return  the game's gem modifier description.
     */
    String getGemModifierDescrition();

    /**
     * Getter for the game's end condition description.
     * 
     * @return  the game's end condition description.
     */
    String getEndConditionDescription();

    /**
     * Getter for the list of the names of the current active players.
     * 
     * @return  the list of the names of the current active players.
     */
    List<String> getActivePlayersNames();

    /**
     * Getter for the list of the names of the current exited players.
     * 
     * @return  the list of the names of the current exited players.
     */
    List<String> getExitedPlayersNames();

    /**
     * Getter for the number of drawn cards.
     * 
     * @return  the number of drawn cards.
     */
    int getDrawnCardsNumber();

    /**
     * Getter for the image of the drawn cards.
     * 
     * @return  the image of the drawn card.
     */
    Optional<Image> getDrawnCardImage();

    /**
     * Executes the turn's draw phase.
     */
    void executeDrawPhase();

    /**
     * Checks if the current player is a CPU.
     * 
     * @return  true if the current player is a CPU and false if not.
     */
    boolean isCurrentPlayerACpu();

    /**
     * Executes the turn's decision phase.
     * 
     * @throws NullPointerException if {@link window} is null.
     * @throws NullPointerException if an active player is null.
     * 
     * @param toBlockWindow the main application window.
     */
    void executeDecisionPhase(Window toBlockWindow);

    /**
     * Method for checking if the game can continue.
     * It checks if another turn or another round exists.
     * 
     * @return  true if the game can continue or false if not.
     */
    boolean canGameContinue();

    /**
     * Method for checking if the round can continue.
     * It checks if another turn exists.
     * 
     * @return  true if the round can continue or false if not.
     */
    boolean canRoundContinue();

    /**
     * Method for advancing to the next turn or round.
     */
    void advance();

    /**
     * Method that redirects to the Leaderboard page.
     */
    void goToLeaderboard();
}
