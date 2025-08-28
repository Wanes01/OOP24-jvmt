package model.settings.api;

import java.util.List;

import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.player.impl.PlayerInRound;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.settings.impl.GameSettingsImpl;

/**
 * Represents the settings of the game.
 * <p>
 * This interface provides a method for checking that everything
 * is ready to start the game and creates all players.
 * </p>
 * 
 * @see GameSettingsImpl
 * 
 * @author Filippo Gaggi
 */
public interface GameSettings {

    /**
     * This method checks if there is an acceptable number of players,
     * if their name has an acceptable number of characters and if there's
     * an acceptable number of rounds.
     * 
     * @return true if the number of players is between its min and its max, if
     *         their names don't exceed the maximum amount of characters and if
     *         the number of rounds isn't exceeded by its max, returns false
     *         otherwise.
     */
    boolean areSettingsOk();

    /**
     * @return the total number of players.
     */
    int getNumberOfPlayers();

    /**
     * @return the number of CPU players.
     */
    int getNumberOfCpu();

    /**
     * @return the number of real players.
     */
    int getNumberOfRealPlayers();

    /**
     * @return the deck chosen.
     */
    Deck getDeck();

    /**
     * @return the game end condition chosen.
     */
    EndCondition getRoundEndCondition();

    /**
     * @return the gem modifier chosen.
     */
    GemModifier getRoundGemModifier();

    /**
     * @return the difficulty of the CPUs chosen.
     */
    CpuDifficulty getCpuDifficulty();

    /**
     * @return the number of rounds chosen.
     */
    int getNumberOfRounds();

    /**
     * Creates the list of all players (real players + CPU players).
     * 
     * @return the list of all players.
     */
    List<PlayerInRound> createPlayers();
}
