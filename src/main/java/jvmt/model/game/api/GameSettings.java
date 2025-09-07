package jvmt.model.game.api;

import java.util.List;

import jvmt.model.card.api.Deck;
import jvmt.model.game.impl.GameSettingsImpl;
import jvmt.model.player.api.CpuDifficulty;
import jvmt.model.player.impl.PlayerInRound;
import jvmt.model.round.api.roundeffect.RoundEffect;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;

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
     * @return the round effect.
     */
    RoundEffect getRoundEffect();

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
