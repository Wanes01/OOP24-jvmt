package model.player.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.player.api.CpuDifficultyVariables;
import model.player.api.LogicCpu;
import model.player.api.PlayerChoice;
import model.round.api.RoundState;

/**
 * The implementation of the {@link LogicCpu} interface.
 * <p>
 * This class includes a method for calculating a score
 * that changes at the variation of certain round informations and
 * various methods for calculating the normalized values needed for it.
 * In the end said score is going to be confronted with a calculated
 * borderline value in another method.
 * </p>
 * 
 * @see LogicCpu
 * @see CpuDifficulty
 * @see CpuDifficultyVariables
 * 
 * @author Filippo Gaggi
 */
public class LogicCpuImpl implements LogicCpu {
    /**
     * Map containing the various weights of the round informations
     * needed for the CPU to take the end turn choice.
     */
    private static final Map<CpuDifficulty, CpuDifficultyVariables> DIFFICULTY_VARIABLES = Map.of(
        CpuDifficulty.EASY, new CpuDifficultyVariables(0.35, 0.20, 0.30, 0.0, 0.15, 0.4, 0.7),
        CpuDifficulty.NORMAL, new CpuDifficultyVariables(0.30, 0.30, 0.20, 0.15, 0.05, 0.6, 0.8),
        CpuDifficulty.HARD, new CpuDifficultyVariables(0.45, 0.25, 0.05, 0.20, 0.05, 0.7, 0.9)
    );
    private final Deck deck;
    private final CpuDifficulty difficulty;
    private final CpuDifficultyVariables config;
    private final Random rand;

    /**
     * Initializes the CPU's logic.
     * 
     * @throws NullPointerException if {@link deck} is null.
     * @throws NullPointerException if {@link difficulty} is null.
     * @throws NullPointerException if {@link rand} is null.
     * 
     * @param deck the round cards deck.
     * @param difficulty the CPU's expected difficulty.
     * @param rand random number generator for giving the CPU some unpredictability.
     */
    public LogicCpuImpl(final Deck deck, final CpuDifficulty difficulty, final Random rand) {
        this.deck = Objects.requireNonNull(deck);
        this.difficulty = Objects.requireNonNull(difficulty);
        this.config = DIFFICULTY_VARIABLES.get(this.difficulty);
        this.rand = Objects.requireNonNull(rand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerChoice cpuChoice(final RoundState state) {
        final double score = calculateScore(Objects.requireNonNull(state));
        final double borderline = config.minBl() + this.rand.nextDouble() * (config.maxBl() - config.minBl());
        return (score >= borderline) ? PlayerChoice.EXIT : PlayerChoice.STAY;
    }

    /**
     * @throws NullPointerException if {@link state} is null.
     * 
     * @param state the round state.
     * @param remainingCards the remaining cards in the deck.
     * @param activePlayers the number of active players.
     * 
     * @return the normalized gems value.
     */
    private double calculateNormGems(final RoundState state, final int remainingCards, final int activePlayers) {
        final double averageGemsPerCard = Objects.requireNonNull(state).getPathGems()
            / (double) (this.deck.deckSize() - remainingCards);
        final double expectedAverage = (activePlayers - 1) / 2.0;
        return averageGemsPerCard / expectedAverage;
    }

    /**
     * @param differentTraps the number of different traps drawn until this turn.
     * 
     * @return the normalized traps value.
     */
    private double calculateNormTraps(final int differentTraps) {
        final double probTrapNextCard = this.deck.totTrapCardsInDeck()
            / (double) this.deck.deckSize();
        return (this.deck.totTrapCardTypesInDeck() - differentTraps)
            / (double) this.deck.totTrapCardTypesInDeck() * probTrapNextCard;
    }

    /**
     * @param remainingCards the remaining cards in the deck.
     * 
     * @return the normalized cards value.
     */
    private double calculateNormCards(final int remainingCards) {
        return 1.0 - (remainingCards / (double) this.deck.deckSize());
    }

    /**
     * @param drawnRelicCards the number of relics drawn until this turn.
     * 
     * @return the normalized relics value.
     */
    private double calculateNormRelics(final int drawnRelicCards) {
        return drawnRelicCards / (double) this.deck.totRelicCardsInDeck();
    }

    /**
     * @param activePlayers the number of active players.
     * @param exitedPlayers the number of players that left.
     * 
     * @return the normalized players value.
     */
    private double calculateNormPlayers(final int activePlayers, final int exitedPlayers) {
        return activePlayers > 0 ? (exitedPlayers / (double) activePlayers) : 0;
    }

    /**
     * Returns the score calculated by the sum of the products
     * of the normalized round informations for their weights.
     * 
     * @throws NullPointerException if {@link state} is null.
     * 
     * @param state the round state.
     * 
     * @return the score calculated.
     */
    private double calculateScore(final RoundState state) {
        final int activePlayers = Objects.requireNonNull(state).getRoundPlayersManager().getActivePlayers().size();
        if (activePlayers == 0) {
            throw new IllegalArgumentException("There must be at least one active player.");
        }
        final int exitedPlayers = Objects.requireNonNull(state).getRoundPlayersManager().getExitedPlayers().size();
        final double normPlayers = calculateNormPlayers(activePlayers, exitedPlayers);
        final int remainingCards = this.deck.deckSize() - Objects.requireNonNull(state).getDrawCards().size();
        final double normCards = calculateNormCards(remainingCards);
        final double normGems = calculateNormGems(Objects.requireNonNull(state), remainingCards, activePlayers);
        final int differentTraps = Objects.requireNonNull(state).getDrawnTraps().size();
        final double normTraps = calculateNormTraps(differentTraps);
        final int drawnRelicCards = Objects.requireNonNull(state).getDrawnRelics().size();
        final double normRelics = calculateNormRelics(drawnRelicCards);
        return (config.weightGems() * normGems)
            + (config.weightTraps() * normTraps)
            + (config.weightCards() * normCards)
            + (config.weightRelics() * normRelics)
            + (config.weightPlayers() * normPlayers);
    }
}
