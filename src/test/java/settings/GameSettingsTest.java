package settings;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import model.player.impl.PlayerInRound;
import model.settings.impl.GameSettingsImpl;
import utils.CommonUtils;

/**
 * GameSettings test class.
 * 
 * @author Filippo Gaggi
 */
class GameSettingsTest {

    private static final int PLAYERS_MIN_TEST = 2;
    private static final int PLAYERS_MAX_TEST = 9;
    private static final int PLAYERS_RIGHT_TEST = 4;
    private static final int RIGHT_ROUNDS_TEST = 10;
    private static final int MAX_ROUNDS_TEST = 17;

    // -- Testing the method for making sure the game settings are ok --
    @Test
    void testAreSettingsOkMinPlayers() {
        final List<PlayerInRound> playersTooFew = new ArrayList<>(CommonUtils.generatePlayerInRoundList(PLAYERS_MIN_TEST));
        final GameSettingsImpl gameSettings = new GameSettingsImpl(playersTooFew, null, null, null, null, RIGHT_ROUNDS_TEST);
        assertFalse(gameSettings.areSettingsOk());
    }

    @Test
    void testAreSettingsOkMaxPlayers() {
        final List<PlayerInRound> playersTooMany = new ArrayList<>(CommonUtils.generatePlayerInRoundList(PLAYERS_MAX_TEST));
        final GameSettingsImpl gameSettings = new GameSettingsImpl(playersTooMany, null, null, null, null, RIGHT_ROUNDS_TEST);
        assertFalse(gameSettings.areSettingsOk());
    }

    @Test
    void testAreSettingsOkRightPlayers() {
        final List<PlayerInRound> playersRight = new ArrayList<>(CommonUtils.generatePlayerInRoundList(PLAYERS_RIGHT_TEST));
        final GameSettingsImpl gameSettings = new GameSettingsImpl(playersRight, null, null, null, null, RIGHT_ROUNDS_TEST);
        assertTrue(gameSettings.areSettingsOk());
    }

    @Test
    void testAreSettingsOkMaxRounds() {
        final List<PlayerInRound> playersRight = new ArrayList<>(CommonUtils.generatePlayerInRoundList(PLAYERS_RIGHT_TEST));
        final GameSettingsImpl gameSettings = new GameSettingsImpl(playersRight, null, null, null, null, MAX_ROUNDS_TEST);
        assertFalse(gameSettings.areSettingsOk());
    }
}
