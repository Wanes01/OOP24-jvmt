package round;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.api.others.Deck;
import model.api.others.PlayerInRound;
import model.api.round.Round;
import model.api.round.RoundPlayersManager;
import model.api.round.RoundState;
import model.api.round.roundeffect.RoundEffect;
import model.api.round.turn.Turn;
import model.impl.others.DeckImpl;
import model.impl.round.RoundImpl;
import model.impl.round.roundeffect.RoundEffectImpl;
import model.impl.round.roundeffect.endcondition.EndConditionFactoryImpl;
import model.impl.round.roundeffect.gemmodifier.GemModifierFactoryImpl;
import utils.CommonUtils;

/**
 * Tests a full game, i.e. a succession of {@link Round}s.
 * This class simulates a full game using a fixed number of players.
 * 
 * @author Emir Wanes Aouioua
 */
class FullGameTest {

    private static final int GAME_SIMULATIONS = 1000;
    private static final int EXIT_CHANCES = 10;
    private static final int NUMBER_OF_ROUND = 10;
    private static final int NUMBER_OF_PLAYERS = 8;
    private final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(NUMBER_OF_PLAYERS);
    private final RoundEffect effect = new RoundEffectImpl(
            new EndConditionFactoryImpl().standard(),
            new GemModifierFactoryImpl().standard());

    @Test
    void testGame() {
        for (int r = 0; r < NUMBER_OF_ROUND; r++) {
            final Deck deck = new DeckImpl();
            final Round round = new RoundImpl(this.players, deck, this.effect);
            final RoundState state = round.getState();
            final RoundPlayersManager pm = state.getRoundPlayersManager();
            for (final Turn turn : round) {
                turn.executeDrawPhase();
                final Set<PlayerInRound> exiting = this.makeRandomPlayersLeave(pm);
                turn.endTurn(exiting);
            }
            round.endRound();
        }

        for (final PlayerInRound player : this.players) {
            if (player.getSackGems() > 0) {
                assertFalse(player.hasLeft());
            }
        }
    }

    private Set<PlayerInRound> makeRandomPlayersLeave(final RoundPlayersManager pm) {
        final Set<PlayerInRound> leaving = new HashSet<>();
        final List<PlayerInRound> actives = pm.getActivePlayers();
        for (final PlayerInRound player : actives) {
            if (CommonUtils.chanceOneIn(EXIT_CHANCES)) {
                player.leave();
                leaving.add(player);
            }
        }
        return leaving;
    }

    @Test
    void simulateGames() {
        for (int g = 0; g < GAME_SIMULATIONS; g++) {
            this.testGame();
        }
    }
}
