package impl.round;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import api.others.PlayerInRound;
import api.round.RoundPlayersManager;

public class RoundPlayersManagerImpl implements RoundPlayersManager {

    private final List<PlayerInRound> players; // all players partecipating in the round
    private int current = 0;

    /**
     * 
     * @param players a list containing all the players that are going to play in
     *                the round
     * @throws IllegalStateException if the list of players contains a player that
     *                               is not active
     */
    public RoundPlayersManagerImpl(final List<PlayerInRound> players) throws IllegalStateException {
        this.players = new ArrayList<>(players);

        if (!this.getExitedPlayers().isEmpty()) {
            throw new IllegalStateException("All players must be in active state at the beginning of a round.");
        }
    }

    @Override
    public boolean hasNext() {
        return !this.getActivePlayers().isEmpty();
    }

    @Override
    public PlayerInRound next() throws NoSuchElementException {
        // tries to find the first active player from this.current
        int checked = 0;
        while (checked < players.size()) {
            PlayerInRound candidate = players.get(current);
            current = (current + 1) % players.size();
            checked++;

            if (!candidate.hasLeft()) {
                return candidate;
            }
        }

        throw new NoSuchElementException("No active players left.");
    }

    @Override
    public List<PlayerInRound> getActivePlayers() {
        return this.players.stream()
                .filter(p -> !p.hasLeft())
                .toList();
    }

    @Override
    public List<PlayerInRound> getExitedPlayers() {
        final List<PlayerInRound> exiting = new ArrayList<>(this.players);
        exiting.removeAll(this.getActivePlayers());
        return exiting;
    }

}
