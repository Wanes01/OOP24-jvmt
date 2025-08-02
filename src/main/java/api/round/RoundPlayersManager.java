package api.round;

import java.util.Iterator;
import java.util.List;

import api.others.PlayerInRound;

public interface RoundPlayersManager extends Iterator<PlayerInRound> {

    /**
     * 
     * @return a list of players who are still active in the current round (players
     *         who, at the time this method is called, have always chosen to
     *         continue exploring during the various turns of the round) sorted by
     *         turn sequence
     */
    List<PlayerInRound> getActivePlayers();

    /**
     * 
     * @return a list containing the players who, at the time this method was
     *         called, decided to abandon exploration, sorted by turn sequence
     */
    List<PlayerInRound> getExitedPlayers();
}
