package model.game.api;

import java.util.List;

import model.card.api.Deck;
import model.game.impl.GameSettingsImpl;
import model.player.api.CpuDifficulty;
import model.player.impl.PlayerInRound;
import model.round.api.roundeffect.RoundEffect;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;

/**
 * Represents the settings of the game.
 * This interface provides a method for checking that everything
 * is ready to start the game and creates all players.
 * 
 * @see GameSettingsImpl
 * 
 * @author Filippo Gaggi
 */
public interface GameSettings {

    /**
     * Getter for the total number of players.
     * 
     * @return  the total number of players.
     */
    int getNumberOfPlayers();

    /**
     * Getter for the number of CPU players.
     * 
     * @return  the number of CPU players.
     */
    int getNumberOfCpu();

    /**
     * Getter for the number of real players.
     * 
     * @return  the number of real players.
     */
    int getNumberOfRealPlayers();

    /**
     * Getter for the chosen deck.
     * 
     * @return  the shuffled chosen deck.
     */
    Deck getDeck();

    /**
     * Getter for the chosen end condition.
     * 
     * @return  the chosen end condition.
     */
    EndCondition getRoundEndCondition();

    /**
     * Getter for the chosen gem modifier.
     * 
     * @return  the chosen gem modifier.
     */
    GemModifier getRoundGemModifier();

    /**
     * Getter for the chosen round effect.
     * 
     * @return  the chosen round effect.
     */
    RoundEffect getRoundEffect();

    /**
     * Getter for the chosen difficulty of the CPUs.
     * 
     * @return  the chosen difficulty of the CPUs.
     */
    CpuDifficulty getCpuDifficulty();

    /**
     * Getter for the chosen number of rounds.
     * 
     * @return  the chosen number of rounds.
     */
    int getNumberOfRounds();

    /**
     * Creates the list of all players (real players + CPU players).
     * 
     * @return  the list of all players.
     */
    List<PlayerInRound> getPlayers();
}
