package controller.api;

import java.util.List;

import controller.impl.LeaderboardControllerImpl;
import view.page.utility.Pair;

/**
 * Represents the controller of the leaderboard page.
 * 
 * @see LeaderboardControllerImpl
 * 
 * @author Filippo Gaggi
 */
public interface LeaderboardController {

    /**
     * Getter for the list of the pair of the players and their score,
     * sorted by their final score.
     * 
     * @return  the list of the pair of the players and their score,
     *          sorted by their final score.
     */
    List<Pair<String, Integer>> getSortedPlayerScores();

    /**
     * Getter for the name of the winner of the game.
     * 
     * @return  the name of the winner of the game.
     */
    String getWinner();

    /**
     * Method that redirects to the Home page.
     */
    void goToHomePage();
}
