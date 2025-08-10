package round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
import model.round.api.RoundPlayersManager;
import model.round.impl.RoundPlayersManagerImpl;
import utils.CommonUtils;

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
        final int numberOfPlayers = 6;
        this.players = CommonUtils.generatePlayerInRoundList(numberOfPlayers);

        manager = new RoundPlayersManagerImpl(players);
    }

    @Test
    void testInitializedWithExitedPlayer() {
        this.players.getFirst().exit();
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
                .filter(p -> p.getChoice() == PlayerChoice.STAY)
                .toList();

        assertEquals(oddPlayers, manager.getActivePlayers());
    }

    /**
     * makes all even indexed players leave.
     */
    private void makeEvenPlayersLeave() {
        for (int p = 0; p < this.players.size(); p += 2) {
            this.players.get(p).exit();
        }
    }

    @Test
    void testGetExitedPlayers() {
        makeEvenPlayersLeave();

        final List<PlayerInRound> evenPlayers = this.players.stream()
                .filter(p -> p.getChoice() == PlayerChoice.EXIT)
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
            player.exit();
        }

        assertFalse(manager.hasNext());
        assertThrows(NoSuchElementException.class, manager::next);
    }
}
