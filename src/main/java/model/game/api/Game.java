package model.game.api;

import java.util.Iterator;
import java.util.List;

import model.leaderboard.api.Leaderboard;
import model.player.impl.PlayerInRound;
import model.round.api.Round;

public interface Game extends Iterator<Round> {
    Leaderboard getLeaderboard();

    List<PlayerInRound> getPlayers();

    int getCurrentRoundNumber();
}