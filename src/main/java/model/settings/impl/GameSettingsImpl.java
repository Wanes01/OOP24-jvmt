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

/**
 * The implementation of the {@link GameSettings} interface.
 * 
 * @see GameSettings
 * 
 * @author Filippo Gaggi
 */
public class GameSettingsImpl implements GameSettings {

    /**
     * Constant that represents the minimum players that can be added
     * in the game.
     */
    public static final int MIN_PLAYERS = 3;
    /**
     * Constant that represents the maximum players that can be added
     * in the game.
     */
    public static final int MAX_PLAYERS = 8;
    /**
     * Constant that represents the maximum characters a player's name
     * can have.
     */
    public static final int MAX_PLAYERS_NAME_CHR = 12;
    /**
     * Constant that represents the maximum rounds the game can have.
     */
    public static final int MAX_ROUNDS = 16;
    private final List<PlayerInRound> listPlayers;
    private final Deck deck;
    private final EndCondition endCondition;
    private final GemModifier gemModifier;
    private final CpuDifficulty cpuDifficulty;
    private final int nRounds;

    /**
     * Constructor of the method.
     * 
     * @param listPlayers the list of the players.
     * @param deck the deck chosen.
     * @param endCondition the game end condition chosen.
     * @param gemModifier the gem modifier chosen.
     * @param cpuDifficulty the difficulty of the CPUs chosen.
     * @param nRound the number of rounds chosen.
     */
    public GameSettingsImpl(final List<PlayerInRound> listPlayers,
        final Deck deck,
        final EndCondition endCondition,
        final GemModifier gemModifier,
        final CpuDifficulty cpuDifficulty,
        final int nRound) {
        this.listPlayers = listPlayers;
        this.deck = deck;
        this.endCondition = endCondition;
        this.gemModifier = gemModifier;
        this.cpuDifficulty = cpuDifficulty;
        this.nRounds = nRound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areSettingsOk() {
        return getNumberOfPlayers() <= MAX_PLAYERS
            && getNumberOfPlayers() >= MIN_PLAYERS
            && getNumberOfRounds() <= MAX_ROUNDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfPlayers() {
        return getNumberOfCpu() + getNumberOfRealPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfCpu() {
        int i = 0;
        for (final PlayerInRound player : this.listPlayers) {
            if (player instanceof PlayerCpu) {
                i++;
            }
        }
        return i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRealPlayers() {
        int i = 0;
        for (final PlayerInRound player : this.listPlayers) {
            if (player instanceof RealPlayer) {
                i++;
            }
        }
        return i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck getDeck() {
        return this.deck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndCondition getRoundEndCondition() {
        return this.endCondition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier getRoundGemModifier() {
        return this.gemModifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CpuDifficulty getCpuDifficulty() {
        return this.cpuDifficulty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRounds() {
        return this.nRounds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> createPlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPlayers'");
    }
}
