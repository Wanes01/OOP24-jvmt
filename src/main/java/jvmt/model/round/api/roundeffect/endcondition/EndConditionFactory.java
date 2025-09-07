package jvmt.model.round.api.roundeffect.endcondition;

import jvmt.model.round.api.Round;
import jvmt.model.round.api.roundeffect.RoundEffect;

/**
 * A factory interface for creating {@link EndCondition} instances used during a
 * round.
 * 
 * @see Round
 * @see RoundEffect
 * 
 * @author Emir Wanes Aouioua
 */
public interface EndConditionFactory {

     /**
      * The standard end condition for a round:
      * the round ends when the deck is over, if all players leave, or if two
      * identical trap cards are drawn.
      * 
      * @return a {@link EndCondition} following the standard round end condition.
      */
     EndCondition standard();

     /**
      * Returns an end condition that ends the round if the deck
      * is over, if all players leave, or if a trap card is drawn.
      * 
      * @return a {@link EndCondition} where the first drawn traps ends the round.
      */
     EndCondition firstTrapEnds();

     /**
      * Returns an end condition that ends the round if the deck is over, if
      * all players leave, or if three relics gets drawn.
      * 
      * @return a {@link EndCondition} where drawing three relics makes the round
      *         end.
      */
     EndCondition threeRelicsDrawn();
}
