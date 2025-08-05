package impl.round;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import api.others.PlayerInRound;
import api.round.RoundPlayersManager;

/**
 * Concrete implementation of {@link RoundPlayersManager}
 * <p>
 * Keeps track of the players that are still exploring the path.
 * This implementation treats the list of players as a circular list in which
 * those found to be exited are skipped.
 * </p>
 * 
 * @see PlayerInRound
 * @author Emir Wanes Aouioua
 */
public final class RoundPlayersManagerImpl implements RoundPlayersManager {

    private final List<PlayerInRound> players; // all players partecipating in the round
    private int current;

    /**
     * Constuct a RoundPlayersManagerImpl object based of a list of active players.
     * 
     * @param players a list containing all the players that are going to play in
     *                the round.
     * @throws IllegalArgumentException if the list of players contains a player
     *                                  that is not active.
     */
    public RoundPlayersManagerImpl(final List<PlayerInRound> players) {
        this.players = new ArrayList<>(players);

        if (!this.getExitedPlayers().isEmpty()) {
            throw new IllegalArgumentException("All players must be in active state at the beginning of a round.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return !this.getActivePlayers().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInRound next() {
        // tries to find the first active player from this.current
        int checked = 0;
        while (checked < players.size()) {
            final PlayerInRound candidate = players.get(this.current);
            this.current = (this.current + 1) % players.size();
            checked++;

            if (!candidate.hasLeft()) {
                return candidate;
            }
        }

        throw new NoSuchElementException("No active players left.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getActivePlayers() {
        return this.players.stream()
                .filter(p -> !p.hasLeft())
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getExitedPlayers() {
        final List<PlayerInRound> exiting = new ArrayList<>(this.players);
        exiting.removeAll(this.getActivePlayers());
        return exiting;
    }

}
