package model.settings.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private static final int MIN_PLAYERS = 3;
    /**
     * Constant that represents the maximum players that can be added
     * in the game.
     */
    private static final int MAX_PLAYERS = 8;
    /**
     * Constant that represents the maximum characters a player's name
     * can have.
     */
    private static final int MAX_PLAYERS_NAME_CHR = 12;
    /**
     * Constant that represents the maximum rounds the game can have.
     */
    private static final int MAX_ROUNDS = 16;
    private final List<String> listNamePlayers;
    private final int numberOfCpu;
    private final Deck deck;
    private final EndCondition endCondition;
    private final GemModifier gemModifier;
    private final CpuDifficulty cpuDifficulty;
    private final int nRounds;
    private final List<String> errorMessagesList;

    /**
     * Constructor of the method.
     * 
     * @param listNamePlayers the list of the name of the players.
     * @param numberOfCpu the number of CPU players.
     * @param deck the deck chosen.
     * @param endCondition the game end condition chosen.
     * @param gemModifier the gem modifier chosen.
     * @param cpuDifficulty the difficulty of the CPUs chosen.
     * @param nRound the number of rounds chosen.
     */
    public GameSettingsImpl(final List<String> listNamePlayers,
        final int numberOfCpu,
        final Deck deck,
        final EndCondition endCondition,
        final GemModifier gemModifier,
        final CpuDifficulty cpuDifficulty,
        final int nRound) {
        this.listNamePlayers = new ArrayList<>(listNamePlayers);
        this.numberOfCpu = Objects.requireNonNull(numberOfCpu);
        this.deck = Objects.requireNonNull(deck);
        this.endCondition = Objects.requireNonNull(endCondition);
        this.gemModifier = Objects.requireNonNull(gemModifier);
        this.cpuDifficulty = Objects.requireNonNull(cpuDifficulty);
        this.nRounds =  Objects.requireNonNull(nRound);
        this.errorMessagesList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areSettingsOk() {
        this.errorMessagesList.clear();
        final boolean result = namePlayersLengthOk()
            && minNumberPlayersOk()
            && maxNumberPlayersOk()
            && numberOfRoundsOk();
        if (!namePlayersLengthOk()) {
            this.errorMessagesList.add("One or more players' names exceed the maximum of "
                + MAX_PLAYERS_NAME_CHR
                + " characters.");
        }
        if (!minNumberPlayersOk()) {
            this.errorMessagesList.add("The number of players is inferior to the minimum of "
                + MIN_PLAYERS
                + " players.");
        }
        if (!maxNumberPlayersOk()) {
            this.errorMessagesList.add("The number of players exceeds the maximum of "
                + MAX_PLAYERS
                + " players.");
        }
        if (!numberOfRoundsOk()) {
            this.errorMessagesList.add("The number of rounds exceeds the maximum of "
                + MAX_ROUNDS
                + " rounds.");
        }
        return result;
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
        return this.numberOfCpu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRealPlayers() {
        return this.listNamePlayers.size();
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
     * @return true if the longest player name doesn't exceed MAX_PLAYERS_NAME_CHR,
     *         false if not.
     */
    private boolean namePlayersLengthOk() {
        final int maxLength = this.listNamePlayers.stream()
            .mapToInt(String::length)
            .max()
            .orElse(0);
        return maxLength <= MAX_PLAYERS_NAME_CHR;
    }

    /**
     * @return true if the number of rounds doesn't exceed MAX_ROUNDS,
     *         false if not.
     */
    private boolean numberOfRoundsOk() {
        return this.nRounds <= MAX_ROUNDS;
    }

    /**
     * @return true if the number of players doesn't exceed MAX_PLAYERS,
     *         false if not.
     */
    private boolean maxNumberPlayersOk() {
        return getNumberOfPlayers() <= MAX_PLAYERS;
    }

    /**
     * @return true if the number of players isn't inferior to MIN_PLAYERS,
     *         false if not.
     */
    private boolean minNumberPlayersOk() {
        return getNumberOfPlayers() >= MIN_PLAYERS;
    }

    /**
     * Creates the list of real players.
     * 
     * @return the list of real players.
     */
    private List<PlayerInRound> createRealPlayers() {
        final List<PlayerInRound> listRealPlayers = new ArrayList<>();
        for (final String name : this.listNamePlayers) {
            final RealPlayer realPlayer = new RealPlayer(name);
            listRealPlayers.add(realPlayer);
        }
        return listRealPlayers;
    }

    /**
     * Creates the list of CPU players.
     * 
     * @return the list of CPU players.
     */
    private List<PlayerInRound> createCpuPlayers() {
        final List<PlayerInRound> listCpuPlayers = new ArrayList<>();
        for (int i = 0; i < this.getNumberOfCpu(); i++) {
            final PlayerCpu playerCpu = new PlayerCpu("CPU-" + i);
            listCpuPlayers.add(playerCpu);
        }
        return listCpuPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> createPlayers() {
        final List<PlayerInRound> listAllPlayers = new ArrayList<>();
        listAllPlayers.addAll(createRealPlayers());
        listAllPlayers.addAll(createCpuPlayers());
        return listAllPlayers;
    }
}
