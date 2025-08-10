package round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.others.api.PlayerInRound;
import model.others.impl.PlayerInRoundImpl;
import model.round.api.RoundPlayersManager;
import model.round.impl.RoundPlayersManagerImpl;

/**
 * Tests for {@link RoundPlayersManagerImpl} ({@link RoundPlayersManager}
 * implementation).
 * 
 * @author Emir Wanes Aouioua
 */
class RoundPlayersManagerImplTest {

    private RoundPlayersManager manager;
    private List<PlayerInRound> players;

    @BeforeEach
    void setUp() {
        this.players = new ArrayList<>();
        List.of("Emir", "Andrea", "Filippo", "Rebecca", "Luca", "Flavio")
                .forEach(p -> players.add(new PlayerInRoundImpl(p)));

        manager = new RoundPlayersManagerImpl(players);
    }

    @Test
    void testInitializedWithExitedPlayer() {
        this.players.getFirst().leave();
        assertThrows(IllegalArgumentException.class, () -> new RoundPlayersManagerImpl(players));
    }

    @Test
    void testPlayersRotation() {
        // no player is exiting
        for (final PlayerInRound expected : this.players) {
            final PlayerInRound actual = manager.next();
            assertEquals(expected, actual);
        }
        // no player has exited so it restarts from the first one
        assertTrue(manager.hasNext());
        assertEquals(players.getFirst(), manager.next());
    }

    @Test
    void testGetActivePlayers() {
        makeEvenPlayersLeave();

        final List<PlayerInRound> oddPlayers = this.players.stream()
                .filter(p -> !p.hasLeft())
                .toList();

        assertEquals(oddPlayers, manager.getActivePlayers());
    }

    /**
     * makes all even indexed players leave.
     */
    private void makeEvenPlayersLeave() {
        for (int p = 0; p < this.players.size(); p += 2) {
            this.players.get(p).leave();
        }
    }

    @Test
    void testGetExitedPlayers() {
        makeEvenPlayersLeave();

        final List<PlayerInRound> evenPlayers = this.players.stream()
                .filter(PlayerInRound::hasLeft)
                .toList();

        assertEquals(evenPlayers, manager.getExitedPlayers());
    }

    @Test
    void testExitedPlayersAreSkipped() {
        makeEvenPlayersLeave();

        for (int p = 1; p < this.players.size(); p += 2) {
            final PlayerInRound nextActive = manager.next();
            assertEquals(this.players.get(p), nextActive);
        }
        assertTrue(manager.hasNext());
        assertEquals(players.get(1), manager.next());
    }

    @Test
    void testNoMorePlayersException() {
        while (manager.hasNext()) {
            final PlayerInRound player = manager.next();
            player.leave();
        }

        assertFalse(manager.hasNext());
        assertThrows(NoSuchElementException.class, manager::next);
    }
}
