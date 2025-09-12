package jvmt.model.player.api;

/**
 * Enum containing the CPU's difficulties.
 * 
 * @see LogicCpu
 * 
 * @author Filippo Gaggi
 */
public enum CpuDifficulty {
    /**
     * Represents the easy difficulty.
     * In this difficulty gems on the path weight the most,
     * traps and relics weight the less.
     */
    EASY,
    /**
     * Represents the normal difficulty.
     * In this difficulty all the weights are balanced 
     */
    NORMAL,
    /**
     * Represents the hard difficulty.
     * In this difficulty traps and relics weight the most,
     * gems on the path and active players weight the less.
     */
    HARD
}
