package api.round;

import java.util.Iterator;
import java.util.List;

import api.others.PlayerInRound;

// imports for javadoc
import java.util.NoSuchElementException;

/**
 * Manages the players partecipating in a round, keeping track of their state
 * (active/left). Iterates over the active players in a round, skipping the
 * players that have left.
 * 
 * This interface extends {@link Iterator}, providing the ability to iterate
 * over the {@link PlayerInRound} instances that are still active during
 * the current round. The {@code next()} method returns the next player who
 * has not exited the round yet.
 * 
 * When no active players remain, {@code next()} throws a
 * {@link NoSuchElementException}
 * 
 * @author Emir Wanes Aouioua
 */
public interface RoundPlayersManager extends Iterator<PlayerInRound> {

    /**
     * 
     * @return a list of players who are still active in the current round (players
     *         who have not chosen to exit). The list respects the original turn
     *         order.
     */
    List<PlayerInRound> getActivePlayers();

    /**
     * 
     * @return a list of players who have exited the current round (players
     *         who chose to stop exploring). The list respects the original turn
     *         order.
     */
    List<PlayerInRound> getExitedPlayers();

    /**
     * {@inheritDoc}
     * 
     * Returns true if there is at least one active player remaining in the current
     * round.
     */
    @Override
    boolean hasNext();

    /**
     * {@inheritDoc}
     * 
     * Returns the next active player in the round, skipping all the exited players.
     * If no active players remain, throws {@link NoSuchElementException}
     * 
     * @return the next active player
     * @throws NoSuchElementException if there are no active players left
     */
    @Override
    PlayerInRound next();
}
