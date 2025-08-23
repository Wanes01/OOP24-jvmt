package model.settings.impl;

import java.util.List;

import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.player.impl.PlayerCpu;
import model.player.impl.PlayerInRound;
import model.player.impl.RealPlayer;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.settings.api.GameSettings;

public class GameSettingsImpl implements GameSettings {

    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 8;
    public static final int MAX_PLAYERS_NAME_CHR = 12;
    public static final int MAX_ROUNDS = 16;
    private final List<PlayerInRound> listPlayers;
    private final Deck deck;
    private final EndCondition endCondition;
    private final GemModifier gemModifier;
    private final CpuDifficulty cpuDifficulty;
    private final int nRounds;

    public GameSettingsImpl(List<PlayerInRound> listPlayers, Deck deck, EndCondition endCondition, GemModifier gemModifier, CpuDifficulty cpuDifficulty, int nRound) {
        this.listPlayers = listPlayers;
        this.deck = deck;
        this.endCondition = endCondition;
        this.gemModifier = gemModifier;
        this.cpuDifficulty = cpuDifficulty;
        this.nRounds = nRound;
    }

    @Override
    public boolean areSettingsOk() {
        return getNumberOfPlayers() <= MAX_PLAYERS
            && getNumberOfPlayers() >= MIN_PLAYERS
            && getNumberOfRounds() <= MAX_ROUNDS;
    }

    @Override
    public int getNumberOfPlayers() {
        return getNumberOfCpu() + getNumberOfRealPlayers();
    }

    @Override
    public int getNumberOfCpu() {
        int i = 0;
        for (PlayerInRound player : this.listPlayers) {
            if (player instanceof PlayerCpu) {
                i++;
            }
        }
        return i;
    }

    @Override
    public int getNumberOfRealPlayers() {
        int i = 0;
        for (PlayerInRound player : this.listPlayers) {
            if (player instanceof RealPlayer) {
                i++;
            }
        }
        return i;
    }

    @Override
    public Deck getDeck() {
        return this.deck;
    }

    @Override
    public EndCondition getRoundEndCondition() {
        return this.endCondition;
    }

    @Override
    public GemModifier getRoundGemModifier() {
        return this.gemModifier;
    }

    @Override
    public CpuDifficulty getCpuDifficulty() {
        return this.cpuDifficulty;
    }

    @Override
    public int getNumberOfRounds() {
        return this.nRounds;
    }

    @Override
    public List<PlayerInRound> createPlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPlayers'");
    }
}
