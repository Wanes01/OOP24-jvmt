package model.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.card.api.Deck;
import model.game.api.GameSettings;
import model.player.api.CpuDifficulty;
import model.player.impl.PlayerCpu;
import model.player.impl.PlayerInRound;
import model.player.impl.RealPlayer;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;

/**
 * The implementation of the {@link GameSettings} interface.
 * 
 * @see GameSettings
 * 
 * @author Filippo Gaggi
 */
public class GameSettingsImpl implements GameSettings {

    /**
     * Constant that represents the minimum number of players that can be added
     * in the game.
     */
    public static final int MIN_PLAYERS = 3;

    /**
     * Constant that represents the maximum number of players that can be added
     * in the game.
     */
    public static final int MAX_PLAYERS = 8;

    /**
     * Constant that represents the minimum number of real players that can be added
     * in the game.
     */
    public static final int MIN_REAL_PLAYERS = 0;

    /**
     * Constant that represents the maximum number of real players that can be added
     * in the game.
     */
    public static final int MAX_REAL_PLAYERS = 8;

    /**
     * Constant that represents the minimum number of CPU players that can be added
     * in the game.
     */
    public static final int MIN_CPU_PLAYERS = 0;

    /**
     * Constant that represents the maximum number of CPU players that can be added
     * in the game.
     */
    public static final int MAX_CPU_PLAYERS = 8;

    /**
     * Constant that represents the minimum rounds the game can have.
     */
    public static final int MIN_ROUNDS = 3;

    /**
     * Constant that represents the maximum rounds the game can have.
     */
    public static final int MAX_ROUNDS = 16;

    /**
     * Constant that represents the maximum characters a player's name
     * can have.
     */
    private static final int MAX_PLAYERS_NAME_CHR = 12;

    private final List<String> listNamePlayers;
    private final int numberOfCpu;
    private final int numberRealPlayers;
    private final int totalNumberOfPlayers;
    private final Deck deck;
    private final EndCondition endCondition;
    private final GemModifier gemModifier;
    private final CpuDifficulty cpuDifficulty;
    private final int nRounds;

    private final List<PlayerInRound> players;

    /**
     * Constructor of the method, creates the list of players of the game after
     * making sure the game settings are acceptable.
     * 
     * @throws NullPointerException if {@link listNamePlayers} is null.
     * @throws NullPointerException if {@link deck} is null.
     * @throws NullPointerException if {@link endCondition} is null.
     * @throws NullPointerException if {@link gemModifier} is null.
     * @throws NullPointerException if {@link cpuDifficulty} is null.
     * @throws InvalidGameSettingsException if {@link error} isn't empty
     *         (the settings aren't acceptable).
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
        this.listNamePlayers = new ArrayList<>(Objects.requireNonNull(listNamePlayers));
        this.numberOfCpu = numberOfCpu;
        this.numberRealPlayers = Objects.requireNonNull(listNamePlayers).size();
        this.totalNumberOfPlayers = this.numberRealPlayers + this.numberOfCpu;
        this.deck = Objects.requireNonNull(deck);
        this.endCondition = Objects.requireNonNull(endCondition);
        this.gemModifier = Objects.requireNonNull(gemModifier);
        this.cpuDifficulty = Objects.requireNonNull(cpuDifficulty);
        this.nRounds = nRound;

        final List<String> errors = this.getSettingsErrors();

        if (!errors.isEmpty()) {
            throw new InvalidGameSettingsException(errors);
        }

        this.players = this.createPlayers();
    }

    /**
     * This method checks if the game settings are ok and adds error messages if
     * they aren't.
     * 
     * @return the list of error messages.
     */
    private List<String> getSettingsErrors() {
        final List<String> errorMessagesList = new ArrayList<>();
        if (!namePlayersLengthOk()) {
            errorMessagesList.add("One or more players' names exceed the maximum of "
                    + MAX_PLAYERS_NAME_CHR
                    + " characters.");
        }
        if (!minNumberPlayersOk()) {
            errorMessagesList.add("The number of players is inferior to the minimum of "
                    + MIN_PLAYERS
                    + " players.");
        }
        if (!maxNumberPlayersOk()) {
            errorMessagesList.add("The number of players exceeds the maximum of "
                    + MAX_PLAYERS
                    + " players.");
        }
        if (!numberOfRoundsOk()) {
            errorMessagesList.add("The number of rounds exceeds the maximum of "
                    + MAX_ROUNDS
                    + " rounds.");
        }
        return errorMessagesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfPlayers() {
        return this.totalNumberOfPlayers;
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
        return this.numberRealPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck getDeck() {
        return this.deck.getShuffledCopy();
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
        return this.totalNumberOfPlayers <= MAX_PLAYERS;
    }

    /**
     * @return true if the number of players isn't inferior to MIN_PLAYERS,
     *         false if not.
     */
    private boolean minNumberPlayersOk() {
        return this.totalNumberOfPlayers >= MIN_PLAYERS;
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
        for (int i = 0; i < this.numberOfCpu; i++) {
            final PlayerCpu playerCpu = new PlayerCpu("CPU-" + i);
            listCpuPlayers.add(playerCpu);
        }
        return listCpuPlayers;
    }

    /**
     * Creates the list of all players (real players + CPU players)
     * and shuffles it.
     * 
     * @return the shuffled list of all players.
     */
    private List<PlayerInRound> createPlayers() {
        final List<PlayerInRound> listAllPlayers = new ArrayList<>();
        listAllPlayers.addAll(createRealPlayers());
        listAllPlayers.addAll(createCpuPlayers());
        Collections.shuffle(listAllPlayers);
        return listAllPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getPlayers() {
        return new ArrayList<>(this.players);
    }
}
