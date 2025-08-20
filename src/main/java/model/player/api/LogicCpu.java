package model.player.api;

import model.round.api.RoundState;

/**
 * Represents the logic behind a CPU player's
 * end turn choice.
 * <p>
 * This interface gives a method that represents the CPU player's
 * choice they take at the end of the turn.
 * </p>
 * 
 * @see LogicCpuImpl
 * 
 * @author Filippo Gaggi
 */
public interface LogicCpu {

    /**
     * This method returns the CPU's choice at the end of the turn.
     * It'll return a PlayerChoice.EXIT if the CPU exits or a
     * PlayerChoice.STAY if the CPU stays.
     * Said choice is obtained through a comparison between a score
     * and a borderline.
     * 
     * @param state the round state.
     * 
     * @return a PlayerChoice that represents the CPU choice.
     */
    PlayerChoice cpuChoice(RoundState state);
}
