package settings;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.card.api.Deck;
import model.card.impl.DeckFactoryImpl;
import model.player.api.CpuDifficulty;
import model.player.impl.PlayerInRound;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import model.settings.impl.GameSettingsImpl;
import model.settings.impl.InvalidGameSettingsException;


/**
 * Game settings test class.
 * 
 * @author Filippo Gaggi
 */
class GameSettingsTest {

    private Deck deck;
    private EndCondition endCondition;
    private GemModifier gemModifier;
    private CpuDifficulty cpuDifficulty;

    @BeforeEach
    void setUp() {
        deck =  new DeckFactoryImpl().standardDeck();
        endCondition = new EndConditionFactoryImpl().standard();
        gemModifier = new  GemModifierFactoryImpl().standard();
        cpuDifficulty = CpuDifficulty.EASY;
    }

    // -- Testing players creation with valid settings --

    @Test
    void areSettingsOkTestAllRight() {
        final List<String> playerNames = new ArrayList<>();
        playerNames.add("Player-1-ok");
        playerNames.add("Player-2-ok");
        final int numberOfCpu = 1;
        final int numberOfRounds = GameSettingsImpl.MAX_ROUNDS;
        final GameSettingsImpl settings = new GameSettingsImpl(playerNames,
            numberOfCpu,
            deck,
            endCondition,
            gemModifier,
            cpuDifficulty,
            numberOfRounds);
        final List<PlayerInRound> allPlayers = settings.getPlayers();

        assertEquals(3, allPlayers.size());
    }

    // -- Testing exceptions with invalid settings --

    @Test
    void areSettingsOkTestNameTooLong() {
        final List<String> playerNames = new ArrayList<>();
        playerNames.add("Player-1-ok");
        playerNames.add("Player-2-too-long");
        final int numberOfCpu = 1;
        final int numberOfRounds = GameSettingsImpl.MAX_ROUNDS;
        final InvalidGameSettingsException exception = assertThrows(InvalidGameSettingsException.class, () -> {
            new GameSettingsImpl(playerNames, numberOfCpu, deck,
            endCondition, gemModifier, cpuDifficulty, numberOfRounds);
        });

        assertEquals("One or more players' names exceed the maximum of 12 characters.",
            exception.getErrors().get(0));
    }

    @Test
    void areSettingsOkTestFewPlayers() {
        final List<String> playerNames = new ArrayList<>();
        playerNames.add("Player-1");
        final int numberOfCpu = 1;
        final int numberOfRounds = GameSettingsImpl.MAX_ROUNDS;
        final InvalidGameSettingsException exception = assertThrows(InvalidGameSettingsException.class, () -> {
            new GameSettingsImpl(playerNames, numberOfCpu, deck,
            endCondition, gemModifier, cpuDifficulty, numberOfRounds);
        });

        assertEquals("The number of players is inferior to the minimum of "
            + GameSettingsImpl.MIN_PLAYERS
            + " players.", exception.getErrors().get(0));
    }

    @Test
    void areSettingsOkTestTooManyPlayers() {
        final List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < GameSettingsImpl.MAX_PLAYERS; i++) {
            playerNames.add("Player-" + i);
        }
        final int numberOfCpu = 1;
        final int numberOfRounds = GameSettingsImpl.MAX_ROUNDS;
        final InvalidGameSettingsException exception = assertThrows(InvalidGameSettingsException.class, () -> {
            new GameSettingsImpl(playerNames, numberOfCpu, deck,
            endCondition, gemModifier, cpuDifficulty, numberOfRounds);
        });

        assertEquals("The number of players exceeds the maximum of "
            + GameSettingsImpl.MAX_PLAYERS
            + " players.", exception.getErrors().get(0));
    }

    @Test
    void areSettingsOkTestTooManyRounds() {
        final List<String> playerNames = new ArrayList<>();
        playerNames.add("Player-1");
        playerNames.add("Player-2");
        final int numberOfCpu = 1;
        final int numberOfRounds = GameSettingsImpl.MAX_ROUNDS + 1;
        final InvalidGameSettingsException exception = assertThrows(InvalidGameSettingsException.class, () -> {
            new GameSettingsImpl(playerNames, numberOfCpu, deck,
            endCondition, gemModifier, cpuDifficulty, numberOfRounds);
        });

        assertEquals("The number of rounds exceeds the maximum of "
            + GameSettingsImpl.MAX_ROUNDS
            + " rounds.", exception.getErrors().get(0));
    }
}
