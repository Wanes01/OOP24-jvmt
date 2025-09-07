package jvmt.model.game.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import jvmt.model.game.api.Game;
import jvmt.model.game.api.GameSettings;
import jvmt.model.leaderboard.api.Leaderboard;
import jvmt.model.leaderboard.impl.LeaderboardImpl;
import jvmt.model.player.api.LogicCpu;
import jvmt.model.player.impl.LogicCpuImpl;
import jvmt.model.round.api.Round;
import jvmt.model.round.impl.RoundImpl;
import jvmt.model.round.impl.roundeffect.RoundEffectImpl;

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
    private final LogicCpu logicCpu;
    private int currentRound;

    /**
     * Constructor of the method.
     * 
     * @throws NullPointerException if {@link settings} is null.
     * 
     * @param settings the game's settings.
     */
    public GameImpl(final GameSettings settings) {
        this.settings = Objects.requireNonNull(settings);
        this.logicCpu = new LogicCpuImpl(Objects.requireNonNull(settings));
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
                this.settings.getPlayers(),
                this.settings.getDeck(),
                new RoundEffectImpl(
                        this.settings.getRoundEndCondition(),
                        this.settings.getRoundGemModifier()));
    }

    /**
     * {@inheritDoc}
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
    public LogicCpu getLogicCpu() {
        return this.logicCpu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSettings getSettings() {
        return this.settings;
    }
}
