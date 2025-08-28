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
    List<PlayerInRound> getPlayers();
}
