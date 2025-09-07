package jvmt.model.player.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import jvmt.model.game.api.GameSettings;
import jvmt.model.player.api.CpuDifficulty;
import jvmt.model.player.api.CpuDifficultyVariables;
import jvmt.model.player.api.LogicCpu;
import jvmt.model.player.api.PlayerChoice;
import jvmt.model.round.api.RoundState;

/**
 * The implementation of the {@link LogicCpu} interface.
 * This class includes a method for calculating a score with a
 * weighted average that changes at the variation of certain round
 * informations and various methods for calculating the normalized
 * values needed for it.
 * Each normalized value calculated with private methods goes from
 * 0.0 to 1.0 (except for normRelics that reaches up to 2.0).
 * The sum of all weights in all difficulties is equal to 1.0.
 * In the end said score is going to be confronted with a calculated
 * borderline value.
 * 
 * @see LogicCpu
 * @see CpuDifficulty
 * @see CpuDifficultyVariables
 * 
 * @author Filippo Gaggi
 */
public class LogicCpuImpl implements LogicCpu {

        /**
         * Map containing the various weights of the variables used for
         * making the CPU take a choice at the end of the turn based off
         * the round informations.
         */
        private static final Map<CpuDifficulty, CpuDifficultyVariables> DIFFICULTY_VARIABLES = Map.of(
                        CpuDifficulty.EASY, new CpuDifficultyVariables(0.60, 0.05, 0.15, 0, 0.20, 0.4, 0.7),
                        CpuDifficulty.NORMAL, new CpuDifficultyVariables(0.25, 0.20, 0.15, 0.20, 0.20, 0.5, 0.7),
                        CpuDifficulty.HARD, new CpuDifficultyVariables(0.05, 0.50, 0.15, 0.30, 0, 0.5, 0.7));
        private final GameSettings settings;
        private final CpuDifficulty difficulty;
        private final CpuDifficultyVariables config;
        private final Random rand;

        /**
         * Initializes the CPU's logic.
         * 
         * @throws NullPointerException if {@link settings} is null.
         * 
         * @param settings the game settings.
         */
        public LogicCpuImpl(final GameSettings settings) {
                Objects.requireNonNull(settings);
                this.settings = settings;
                this.difficulty = settings.getCpuDifficulty();
                this.config = DIFFICULTY_VARIABLES.get(this.difficulty);
                this.rand = new Random();
        }

        /**
         * Initializes the CPU's logic, second constructor that gives in input
         * the seed of the Random object on top of other fields in order to
         * facilitate testing.
         * 
         * @throws NullPointerException if {@link settings} is null.
         * 
         * @param settings the game settings.
         * @param seed     seed for the Random object.
         */
        public LogicCpuImpl(final GameSettings settings, final int seed) {
                Objects.requireNonNull(settings);
                this.settings = settings;
                this.difficulty = settings.getCpuDifficulty();
                this.config = DIFFICULTY_VARIABLES.get(this.difficulty);
                this.rand = new Random(seed);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public PlayerChoice cpuChoice(final RoundState state) {
                final double score = calculateScore(Objects.requireNonNull(state));
                final double borderline = calculateBorderline();
                return (score >= borderline) ? PlayerChoice.EXIT : PlayerChoice.STAY;
        }

        /**
         * This method normalizes the variable representing the gems.
         * It divides the gems on the path by the amount of active players multiplied by
         * 2.0.
         * This way the variable can reach its highest value (1.0) when the gems per
         * player
         * are 2 or more.
         * 
         * @throws NullPointerException if {@link state} is null.
         * 
         * @param state the round state.
         * 
         * @return the normalized gems value.
         */
        private double calculateNormGems(final RoundState state) {
                Objects.requireNonNull(state);
                return state.getPathGems() / (state.getRoundPlayersManager().getActivePlayers().size() * 2.0);
        }

        /**
         * This method normalizes the variable representing the traps.
         * It divides the amount of drawn traps by the amount of trap types in the deck.
         * This way the variable can reach its highest value (1.0) when the amount of
         * drawn traps are equal to the number of trap types in the deck.
         * 
         * @throws NullPointerException if {@link state} is null.
         * 
         * @param state the round state.
         * 
         * @return the normalized traps value.
         */
        private double calculateNormTraps(final RoundState state) {
                Objects.requireNonNull(state);
                return state.getDrawnTraps().size() / (double) this.settings.getDeck().totTrapCardTypesInDeck();
        }

        /**
         * This method normalizes the variable representing the drawn cards.
         * It substracts from 1.0 the amount of cards not yet drawn divided by the
         * total amount of cards in the deck.
         * This way the less cards remain in the deck, the higher the variable is.
         * 
         * @throws NullPointerException if {@link state} is null.
         * 
         * @param state the round state.
         * 
         * @return the normalized cards value.
         */
        private double calculateNormCards(final RoundState state) {
                Objects.requireNonNull(state);
                final int remainingCards = this.settings.getDeck().deckSize() - state.getDrawCards().size();
                return 1.0 - (remainingCards / (double) this.settings.getDeck().deckSize());
        }

        /**
         * This method normalizes the variable representing the relic cards.
         * It divides the amount of drawn relics by the total amount of relics in the
         * deck
         * and multiplies it by 2.0.
         * It's the only variable that can reach 2.0 as its maximum.
         * 
         * @throws NullPointerException if {@link state} is null.
         * 
         * @param state the round state.
         * 
         * @return the normalized relics value.
         */
        private double calculateNormRelics(final RoundState state) {
                Objects.requireNonNull(state);
                return state.getReedamableRelics().size() * 2.0 / this.settings.getDeck().totRelicCardsInDeck();
        }

        /**
         * This method normalizes the variable representing the players.
         * It substracts from 1.0 the amount of exited players divided by the total
         * amount of
         * players in the game.
         * This way the less players exit, the higher the variable is.
         * 
         * @throws NullPointerException if {@link state} is null.
         * 
         * @param state the round state.
         * 
         * @return the normalized players value.
         */
        private double calculateNormPlayers(final RoundState state) {
                Objects.requireNonNull(state);
                return 1.0 - (state.getRoundPlayersManager().getExitedPlayers().size()
                                / (double) (state.getRoundPlayersManager().getExitedPlayers().size()
                                                + state.getRoundPlayersManager().getActivePlayers().size()));
        }

        /**
         * Returns the score calculated by the sum of the products
         * of the normalized round informations for their weights.
         * 
         * @throws NullPointerException     if {@link state} is null.
         * @throws IllegalArgumentException if there aren't active players.
         * 
         * @param state the round state.
         * 
         * @return the score calculated.
         */
        private double calculateScore(final RoundState state) {
                Objects.requireNonNull(state);
                if (state.getRoundPlayersManager().getActivePlayers().isEmpty()) {
                        throw new IllegalArgumentException("There must be at least one active player.");
                }
                final double normPlayers = calculateNormPlayers(state);
                final double normCards = calculateNormCards(state);
                final double normGems = calculateNormGems(state);
                final double normTraps = calculateNormTraps(state);
                final double normRelics = calculateNormRelics(state);
                return (config.weightGems() * normGems)
                                + (config.weightTraps() * normTraps)
                                + (config.weightCards() * normCards)
                                + (config.weightRelics() * normRelics)
                                + (config.weightPlayers() * normPlayers);
        }

        /**
         * Calculates the borderline value using the configured minimum borderline
         * and maximum borderline that depend on the chosen difficulty.
         * The borderline is calculated by summing the minimum borderline to the
         * difference
         * between the maximum and minimum borderlines multiplied by a random real
         * number
         * between 0.0 and 1.0.
         * 
         * @return the borderline value of the difficulty.
         */
        private double calculateBorderline() {
                return config.minBl() + this.rand.nextDouble() * (config.maxBl() - config.minBl());
        }
}
