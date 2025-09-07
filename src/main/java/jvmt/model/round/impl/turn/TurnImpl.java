package jvmt.model.round.impl.turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jvmt.model.round.api.roundeffect.RoundEffect;
import jvmt.model.round.api.turn.Turn;
import jvmt.utils.CommonUtils;
import jvmt.model.round.api.Round;
import jvmt.model.round.api.RoundPlayersManager;
import jvmt.model.round.api.RoundState;
import jvmt.model.card.api.Card;
import jvmt.model.card.api.Deck;
import jvmt.model.card.impl.RelicCard;
import jvmt.model.card.impl.TreasureCard;
import jvmt.model.player.api.PlayerChoice;
import jvmt.model.player.api.Player;

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
 * This class has a strict usage that may lead to the following exceptions:
 * <ul>
 * <li>{@link IllegalStateException} if a player attempts to draw more than one
 * card in a turn or if {@link #endTurn(Set)} is called without drawing a card
 * first.</li>
 * <li>{@link IllegalArgumentException} if any players passed to
 * {@link #endTurn(Set)} have not left the round.</li>
 * </ul>
 * 
 * @see Turn
 * @see RoundState
 * @see RoundEffect
 * @see Player
 * @see Round
 * 
 * @author Emir Wanes Aouioua
 */

@SuppressFBWarnings(value = { "EI_EXPOSE_REP",
        "EI_EXPOSE_REP2" }, justification = "Internal mutable objects are part of the game logic and shared by design")
public class TurnImpl implements Turn {

    private final Player player;
    private final RoundState roundState;
    private final RoundEffect roundEffect;
    private Optional<Card> drawnCard = Optional.empty();

    /**
     * Constructs a new {@code TurnImpl}.
     * 
     * @param player      the player that will play this turn.
     * @param roundState  the state of the round.
     * @param roundEffect the effect that has to be applied on the round.
     * 
     * @throws NullPointerException if {@code player}, {@code roundState} or
     *                              {@code roundEffect} is null.
     */
    public TurnImpl(
            final Player player,
            final RoundState roundState,
            final RoundEffect roundEffect) {
        CommonUtils.requireNonNulls(player, roundState, roundEffect);
        this.player = player;
        this.roundState = roundState;
        this.roundEffect = roundEffect;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if a card has already been drawn during this
     *                               turn.
     */
    @Override
    public void executeDrawPhase() {
        if (this.drawnCard.isPresent()) {
            throw new IllegalStateException("A card has already been drawn this turn.");
        }

        final Deck deck = this.roundState.getDeck();
        final Card card = deck.next();

        this.roundState.addCardToPath(card);
        this.drawnCard = Optional.of(card);

        /*
         * Note: future usage of special cards must be put here
         */

        final RoundPlayersManager pm = this.roundState.getRoundPlayersManager();
        final List<Player> actives = pm.getActivePlayers();
        if (card instanceof TreasureCard && !actives.isEmpty()) {
            final TreasureCard treasure = (TreasureCard) card;
            this.divideGemsAmongPlayers(treasure.getGemValue(), actives);
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
    private void divideGemsAmongPlayers(final int gems, final List<Player> players) {
        final int reward = this.roundEffect.applyGemModifier(
                roundState, gems / players.size());
        final int pathGems = gems % players.size();

        players.forEach(a -> a.addSackGems(reward));
        this.roundState.setPathGems(this.roundState.getPathGems() + pathGems);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException    if the {@link #executeDrawPhase()}
     *                                  has not yet been executed.
     * @throws IllegalArgumentException if at least one player in
     *                                  {@code playersExitingThisTurn} is still
     *                                  active in the round.
     * @throws NullPointerException     if {@code playersExitingThisTurn} is null.
     */
    @Override
    public void endTurn(final Set<Player> playersExitingThisTurn) {
        Objects.requireNonNull(playersExitingThisTurn);

        if (isAnyActive(playersExitingThisTurn)) {
            throw new IllegalArgumentException("Players passed to endTurn function must all have left the round.");
        } else if (this.drawnCard.isEmpty()) {
            throw new IllegalStateException("A card must be drawn before a turn can end.");
        }

        if (playersExitingThisTurn.isEmpty()) {
            return;
        }

        // Only one player exited. He is given all the available relics.
        if (playersExitingThisTurn.size() == 1) {
            final Player exiting = playersExitingThisTurn.stream()
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
     * Assigns the total value of relics in gems not yet redeemed during the game
     * to the specified player.
     * 
     * @param player the player whom will receives the relics.
     */
    private void giveAvailableRelicsToPlayer(final Player player) {
        final List<RelicCard> relics = this.roundState.getRedeemableRelics();
        relics.stream()
                .forEach(r -> {
                    player.addSackGems(r.getGemValue());
                    r.redeemCard();
                });
    }

    /**
     * Checks if any of the specified players is still active in the round.
     * 
     * @param players the players whose status will be checked.
     * @return true if any player is still active in the round, false otherwise.
     */
    private boolean isAnyActive(final Set<Player> players) {
        for (final Player player : players) {
            if (player.getChoice() == PlayerChoice.STAY) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Card> getDrawnCard() {
        return this.drawnCard;
    }

}
