package model.round.api.roundeffect.endcondition;

import java.util.function.Predicate;

import model.round.api.roundeffect.RoundEffect;
import model.common.api.Describable;
import model.round.api.RoundState;

/**
 * Represents the end condition of a round used by {@link RoundEffect}. The
 * condition for which a round ends is modeled via a {@link Predicate} that
 * decides whether the round has ended via the current state in
 * {@link RoundState}.
 * 
 * @author Emir Wanes Aouioua.
 */
public interface EndCondition extends Describable {
    /**
     * Returns the predicate that determines whether the round should end
     * based on the current {@link RoundState}.
     * 
     * @return a predicate that evaluates to {@code true} when the condition for
     *         ending the round is satisfied.
     */
    Predicate<RoundState> getEndCondition();
}
