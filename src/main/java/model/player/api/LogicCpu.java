package model.player.api;

import model.player.impl.LogicCpuImpl;
import model.round.api.RoundState;

/**
 * Represents the logic behind a CPU player's end turn choice.
 * This interface gives a method that represents the CPU player's
 * choice taken at the end of the turn.
 * 
 * @see LogicCpuImpl
 * 
 * @author Filippo Gaggi
 */
public interface LogicCpu {

    /**
     * This method returns the CPU's choice at the end of the turn.
     * It'll return a PlayerChoice.EXIT if the score of the CPU is bigger
     * than the borderline and PlayerChoice.STAY if not.
     * 
     * @throws NullPointerException if {@link state} is null.
     * 
     * @param state the round state.
     * 
     * @return  a PlayerChoice that represents the CPU choice.
     */
    PlayerChoice cpuChoice(RoundState state);
}
