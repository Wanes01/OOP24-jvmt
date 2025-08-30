package model.game.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import model.game.api.Game;
import model.game.api.GameSettings;
import model.leaderboard.api.Leaderboard;
import model.leaderboard.impl.LeaderboardImpl;
import model.player.impl.PlayerInRound;
import model.round.api.Round;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.impl.RoundImpl;
import model.round.impl.roundeffect.RoundEffectImpl;

/**
 * Implementation of the {@link Game} interface.
 * 
 * @see Game
 * @see Iterator
 * 
 * @author Filippo Gaggi
 */
public class GameImpl implements Game {

    private final GameSettings settings;
    private int currentRound;

    /**
     * Constructor of the method.
     * 
     * @throws NullPointerException if {@link settings} is null.
     * 
     * @param settings the game's settings.
     */
    public GameImpl(final GameSettings settings) {
        Objects.requireNonNull(settings);
        this.settings = settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return this.currentRound < settings.getNumberOfRounds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Round next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more rounds");
        }
        this.currentRound++;
        return new RoundImpl(
                settings.getPlayers(),
                settings.getDeck(),
                new RoundEffectImpl(
                        settings.getRoundEndCondition(),
                        settings.getRoundGemModifier()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Leaderboard getLeaderboard() {
        if (this.hasNext()) {
            throw new IllegalStateException("There are still rounds to do");
        }
        return new LeaderboardImpl(this.getPlayers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getPlayers() {
        return new ArrayList<>(this.settings.getPlayers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentRoundNumber() {
        return this.currentRound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndCondition getEndCondition() {
        return this.settings.getRoundEndCondition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier getGemModifier() {
        return this.settings.getRoundGemModifier();
    }
}
