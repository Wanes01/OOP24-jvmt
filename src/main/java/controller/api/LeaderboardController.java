package controller.api;

import java.util.List;

import model.player.impl.PlayerInRound;

/**
 * Represents the controller of the leaderboard page.
 * 
 * @see LeaderboardControllerImpl
 * 
 * @author Filippo Gaggi
 */
public interface LeaderboardController {

    /**
     * @return the list of the players sorted by their final score.
     */
    List<PlayerInRound> getPlayerList();

    /**
     * @return the winner of the game.
     */
    PlayerInRound getWinner();

    /**
     * Method that redirects to the Home page.
     */
    void goToHomePage();
}
