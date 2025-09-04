package round;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.round.api.roundeffect.RoundEffect;
import model.round.api.turn.Turn;
import model.card.api.Deck;
import model.card.impl.DeckFactoryImpl;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
import model.round.api.Round;
import model.round.api.RoundPlayersManager;
import model.round.api.RoundState;
import model.round.impl.RoundImpl;
import model.round.impl.roundeffect.RoundEffectImpl;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
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
    private static final int NUMBER_OF_ROUNDS = 10;
    private static final int NUMBER_OF_PLAYERS = 8;
    private final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(NUMBER_OF_PLAYERS);
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
                final Set<PlayerInRound> exiting = this.makeRandomPlayersLeave(pm);
                turn.endTurn(exiting);
            }
            round.endRound();
        }

        for (final PlayerInRound player : this.players) {
            if (player.getSackGems() > 0) {
                assertEquals(PlayerChoice.STAY, player.getChoice());
            }
        }
    }

    private Set<PlayerInRound> makeRandomPlayersLeave(final RoundPlayersManager pm) {
        final Set<PlayerInRound> leaving = new HashSet<>();
        final List<PlayerInRound> actives = pm.getActivePlayers();
        for (final PlayerInRound player : actives) {
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
