package model.game.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import model.game.api.Game;
import model.game.api.GameSettings;
import model.leaderboard.api.Leaderboard;
import model.leaderboard.impl.LeaderboardImpl;
import model.round.api.Round;
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
     * @param settings  the game's settings.
     * 
     * @throws NullPointerException if @param settings is null.
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
     * 
     * @throws NullPointerException if there are no more rounds.
     */
    @Override
    public Round next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more rounds");
        }
        this.currentRound++;
        return new RoundImpl(
                this.settings.getPlayers(),
                this.settings.getDeck(),
                new RoundEffectImpl(
                        this.settings.getRoundEndCondition(),
                        this.settings.getRoundGemModifier()));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if there are still rounds to do.
     */
    @Override
    public Leaderboard getLeaderboard() {
        if (this.hasNext()) {
            throw new IllegalStateException("There are still rounds to do");
        }
        return new LeaderboardImpl(this.settings.getPlayers());
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
    public GameSettings getSettings() {
        return this.settings;
    }
}
