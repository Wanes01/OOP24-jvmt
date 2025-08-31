package model.game.api;

import java.util.Iterator;
import java.util.List;

import model.card.api.Deck;
import model.game.impl.GameImpl;
import model.leaderboard.api.Leaderboard;
import model.player.api.LogicCpu;
import model.player.impl.PlayerInRound;
import model.round.api.Round;
import model.round.api.roundeffect.RoundEffect;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;

/**
 * Iterator that manages the game's rounds
 * and has all of the game informations.
 * 
 * @see GameImpl
 * @see Iterator
 * 
 * @author Filippo Gaggi
 */
public interface Game extends Iterator<Round> {

    /**
     * @return the end game's leaderboard.
     */
    Leaderboard getLeaderboard();

    /**
     * @return the game's players.
     */
    List<PlayerInRound> getPlayers();

    /**
     * @return the game's deck.
     */
    Deck getDeck();

    /**
     * @return the current round number.
     */
    int getCurrentRoundNumber();

    /**
     * @return the game's end condition.
     */
    EndCondition getEndCondition();

    /**
     * @return the game's gem modifier.
     */
    GemModifier getGemModifier();

    /**
     * @return the game's gem modifier.
     */
    RoundEffect getRoundEffect();

    /**
     * @return the game's CPU logic.
     */
    LogicCpu getLogicCpu();
}
