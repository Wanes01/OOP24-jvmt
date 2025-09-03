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
     * @return the list of the players sorted by their final score.
     */
    List<Pair<String, Integer>> getSortedPlayerScores();

    /**
     * @return the winner of the game.
     */
    String getWinner();

    /**
     * Method that redirects to the Home page.
     */
    void goToHomePage();
}
