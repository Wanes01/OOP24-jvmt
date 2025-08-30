package model.game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import model.game.api.Game;
import model.game.api.GameSettings;
import model.leaderboard.api.Leaderboard;
import model.leaderboard.impl.LeaderboardImpl;
import model.player.impl.PlayerInRound;
import model.round.api.Round;
import model.round.impl.RoundImpl;
import model.round.impl.roundeffect.RoundEffectImpl;

public class GameImpl implements Game {

    private final GameSettings settings;
    private int currentRound;

    public GameImpl(GameSettings settings) {
        Objects.requireNonNull(settings);
        this.settings = settings;
    }

    @Override
    public boolean hasNext() {
        return this.currentRound < settings.getNumberOfRounds();
    }

    @Override
    public Round next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("Round finiti");
        }
        this.currentRound++;
        return new RoundImpl(
                settings.getPlayers(),
                settings.getDeck(),
                new RoundEffectImpl(
                        settings.getRoundEndCondition(),
                        settings.getRoundGemModifier()));
    }

    @Override
    public Leaderboard getLeaderboard() {
        if (this.hasNext()) {
            throw new IllegalStateException("Ci sono ancora dei round da fare");
        }
        return new LeaderboardImpl(this.getPlayers());
    }

    @Override
    public List<PlayerInRound> getPlayers() {
        return new ArrayList<>(this.settings.getPlayers());
    }

    @Override
    public int getCurrentRoundNumber() {
        return this.currentRound;
    }

}