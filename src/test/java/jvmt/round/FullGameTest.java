package jvmt.round;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import jvmt.model.round.api.roundeffect.RoundEffect;
import jvmt.model.round.api.turn.Turn;
import jvmt.model.card.api.Deck;
import jvmt.model.card.impl.DeckFactoryImpl;
import jvmt.model.player.api.Player;
import jvmt.model.player.api.PlayerChoice;
import jvmt.model.round.api.Round;
import jvmt.model.round.api.RoundPlayersManager;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.impl.RoundImpl;
import jvmt.model.round.impl.roundeffect.RoundEffectImpl;
import jvmt.model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import jvmt.model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import jvmt.utils.CommonUtils;

/**
 * Tests a full game, i.e. a succession of {@link Round}s.
 * This class simulates a full game using a fixed number of players.
 * 
 * @author Emir Wanes Aouioua
 */
class FullGameTest {

    private static final int GAME_SIMULATIONS = 1000;
    private static final int EXIT_CHANCES = 10;
    private static final int NUMBER_OF_ROUNDS = 10;
    private static final int NUMBER_OF_PLAYERS = 8;
    private final List<Player> players = CommonUtils.generatePlayerList(NUMBER_OF_PLAYERS);
    private final RoundEffect effect = new RoundEffectImpl(
            new EndConditionFactoryImpl().standard(),
            new GemModifierFactoryImpl().standard());

    @Test
    void testGame() {
        for (int r = 0; r < NUMBER_OF_ROUNDS; r++) {
            final Deck deck = new DeckFactoryImpl().standardDeck();
            final Round round = new RoundImpl(this.players, deck, this.effect);
            final RoundState state = round.getState();
            final RoundPlayersManager pm = state.getRoundPlayersManager();
            while (round.hasNext()) {
                final Turn turn = round.next();
                turn.executeDrawPhase();
                final Set<Player> exiting = this.makeRandomPlayersLeave(pm);
                turn.endTurn(exiting);
            }
            round.endRound();
        }

        for (final Player player : this.players) {
            if (player.getSackGems() > 0) {
                assertEquals(PlayerChoice.STAY, player.getChoice());
            }
        }
    }

    private Set<Player> makeRandomPlayersLeave(final RoundPlayersManager pm) {
        final Set<Player> leaving = new HashSet<>();
        final List<Player> actives = pm.getActivePlayers();
        for (final Player player : actives) {
            if (CommonUtils.chanceOneIn(EXIT_CHANCES)) {
                player.exit();
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
