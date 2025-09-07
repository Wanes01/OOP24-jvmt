package jvmt.model.player.api;

import jvmt.model.player.impl.LogicCpuImpl;

/**
 * Record containing the CPU's difficulty
 * variables used for calculating the score.
 * 
 * @see LogicCpuImpl
 * 
 * @param weightGems    weighted value for the gems on path.
 * @param weightTraps   weighted value for the trap cards.
 * @param weightCards   weighted value for the cards drawn.
 * @param weightRelics  weighted value for the relic cards.
 * @param weightPlayers weighted value for the players.
 * @param minBl         minimum borderline value.
 * @param maxBl         maximum borderline value.
 * 
 * @author Filippo Gaggi
 */
public record CpuDifficultyVariables(
        double weightGems,
        double weightTraps,
        double weightCards,
        double weightRelics,
        double weightPlayers,
        double minBl,
        double maxBl) {
}
