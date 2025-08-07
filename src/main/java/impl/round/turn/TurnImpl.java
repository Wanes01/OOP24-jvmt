package impl.round.turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import api.others.Card;
import api.others.Deck;
import api.others.PlayerInRound;
import api.others.RelicCard;
import api.others.SpecialCard;
import api.others.TreasureCard;
import api.round.RoundPlayersManager;
import api.round.RoundState;
import api.round.roundeffect.RoundEffect;
import api.round.turn.Turn;

/**
 * Concrete implementation of a {@link Turn} in a round of the game.
 * <p>
 * This class handles the logic of a single player's turn, including drawing a
 * card from the deck and the distribution of gems among players.
 * </p>
 * <p>
 * <strong>Note:</strong>
 * this class uses {@link RoundEffect} to apply
 * modifiers to the number of gems added to player's sacks when they receive
 * gems during the turn.
 * </p>
 * <p>
 * This class has a strict usage that may lead to the following exceptions:
 * <ul>
 * <li>{@link IllegalStateException} if a player attempts to draw more than one
 * card in a turn or if {@link #endTurn(Set)} is called without drawing a card
 * first.</li>
 * <li>{@link IllegalArgumentException} if any players passed to
 * {@link #endTurn()} have not left the round.
 * </ul>
 * </p>
 */
public class TurnImpl implements Turn {

    private final PlayerInRound player;
    private final RoundState roundState;
    private final RoundEffect roundEffect;
    private Optional<Card> drawnCard = Optional.empty();

    /**
     * Constructs a new {@code TurnImpl}.
     * 
     * @param player
     * @param roundState
     * @param roundEffect
     */
    public TurnImpl(
            final PlayerInRound player,
            final RoundState roundState,
            final RoundEffect roundEffect) {
        this.player = player;
        this.roundState = roundState;
        this.roundEffect = roundEffect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInRound getCurrentPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Card> getDrawnCard() {
        return this.drawnCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeDrawPhase() {
        if (this.drawnCard.isPresent()) {
            throw new IllegalStateException("A card has already been drawn this turn.");
        }

        final Deck deck = this.roundState.getDeck();
        final Card card = deck.drawCard();

        this.roundState.addCardToPath(card);
        this.drawnCard = Optional.of(card);

        if (card instanceof SpecialCard) {
            // TODO: add behavior
        } else if (card instanceof TreasureCard) {
            final TreasureCard treasure = (TreasureCard) card;
            final RoundPlayersManager pm = this.roundState.getRoundPlayersManager();
            this.divideGemsAmongPlayers(treasure.getGems(), pm.getActivePlayers());
        }
    }

    /**
     * Divides the {@code gems} equally between {@code players}.
     * <p>
     * The {@link RoundEffect} associated with this turn is applyed to the gems that
     * are added to the player's sacks. The remainder of the division between the
     * gems and the number of players is added to the total gems in the path.
     * </p>
     * <p>
     * Note: the RoundEffect is applied only to gems that are added to the players'
     * sacks and not to gems added to the path.
     * </p>
     * 
     * @param gems    gems to be divided among the players, the remainder of which
     *                will be placed in the path
     * @param players the players to whom to divide the gems
     */
    private void divideGemsAmongPlayers(int gems, List<PlayerInRound> players) {
        final int reward = this.roundEffect.applyGemModifier(
                roundState, gems / players.size());
        final int pathGems = gems % players.size();

        players.forEach(a -> a.addSackGems(reward));
        this.roundState.setPathGems(this.roundState.getPathGems() + pathGems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn(Set<PlayerInRound> playersExitingThisTurn) {
        if (isAnyActive(playersExitingThisTurn)) {
            throw new IllegalArgumentException("Players passed to endTurn function must all have left the round.");
        } else if (this.drawnCard.isEmpty()) {
            throw new IllegalStateException("A card must be drawn before a turn can end.");
        }

        // Only one player exited. He is given all the available relics.
        if (playersExitingThisTurn.size() == 1) {
            final PlayerInRound exiting = playersExitingThisTurn.stream()
                    .findFirst()
                    .get();
            this.giveAvailableRelicsToPlayer(exiting);
        }

        final int pathGems = this.roundState.getPathGems();
        // Resets the gems in the path so that they can be distributed.
        this.roundState.setPathGems(0);
        this.divideGemsAmongPlayers(pathGems, new ArrayList<>(playersExitingThisTurn));
    }

    /**
     * Assigns the total value of relics in gems not yet collected during the game
     * to the specified player.
     * 
     * @param player the player whom will receives the relics.
     */
    private void giveAvailableRelicsToPlayer(final PlayerInRound player) {
        final List<RelicCard> relics = this.roundState.getDrawnRelics();
        relics.stream()
                .filter(r -> !r.isAlreadyTaken())
                .forEach(r -> {
                    player.addSackGems(r.getGems());
                    r.setAsTaken();
                });
    }

    /**
     * Checks if any of the specified players is still active in the round.
     * 
     * @param players the players whose status will be checked.
     * @return true if any player is still active in the round, false otherwise.
     */
    private boolean isAnyActive(Set<PlayerInRound> players) {
        for (final PlayerInRound player : players) {
            if (!player.hasLeft()) {
                return true;
            }
        }
        return false;
    }

}
