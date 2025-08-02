package impl.round;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import api.others.PlayerInRound;
import api.round.RoundPlayersManager;

public class RoundPlayersManagerImpl implements RoundPlayersManager {

    private final List<PlayerInRound> players; // all players partecipating in the round
    private int current = 0;

    public RoundPlayersManagerImpl(final List<PlayerInRound> players) {
        this.players = new ArrayList<>(players);
    }

    @Override
    public boolean hasNext() {
        return !this.getActivePlayers().isEmpty();
    }

    @Override
    public PlayerInRound next() throws NoSuchElementException {
        // tries to find a player that has not exited yet
        for (int i = 0; i < players.size(); i++) {
            this.current = (this.current + 1) % players.size();
            final PlayerInRound candidate = players.get(this.current);
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
