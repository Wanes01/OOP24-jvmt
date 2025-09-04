package controller.impl;

import java.awt.Image;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import controller.api.GameAwarePageController;
import controller.api.GameplayController;
import model.card.api.Card;
import model.game.api.Game;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerCpu;
import model.player.impl.PlayerInRound;
import model.player.impl.RealPlayer;
import model.round.api.Round;
import model.round.api.RoundPlayersManager;
import model.round.api.RoundState;
import model.round.api.turn.Turn;
import view.modal.api.Modal;
import view.modal.impl.SwingPlayerChoiceModal;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;
import view.window.api.Window;
import view.window.impl.SwingWindow;

/**
 * The implementation of the {@link GameplayController} interface.
 * 
 * @see GameplayController
 * 
 * @author Filippo Gaggi
 */
public class GameplayControllerImpl extends GameAwarePageController implements GameplayController {

    private final Runnable leaderboardSetter;
    private Turn currentTurn;
    private Round currentRound;

    /**
     * Constructor of the class.
     * 
     * @throws NullPointerException if {@link page} is null.
     * @throws NullPointerException if {@link navigator} is null.
     * @throws NullPointerException if {@link game} is null.
     * @throws NullPointerException if {@link leaderboardSetter} is null.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     * @param game      the round iterator of the game.
     * @param leaderboardSetter      the runnable for starting the leaderboard when the game ends.
     */
    public GameplayControllerImpl(final Page page,
        final PageNavigator navigator,
        final Game game,
        final Runnable leaderboardSetter) {

        super(
            Objects.requireNonNull(page),
            Objects.requireNonNull(navigator),
            Objects.requireNonNull(game));

        this.leaderboardSetter = Objects.requireNonNull(leaderboardSetter);

        if (!game.hasNext()) {
            throw new IllegalStateException("You can't start the game with 0 rounds!");
        }

        this.currentRound = game.next();

        if (!this.currentRound.hasNext()) {
            throw new IllegalStateException("You can't play the game with 0 turns!");
        }

        this.currentTurn = this.currentRound.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentPlayerName() {
        return this.currentTurn.getCurrentPlayer().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentPlayerChestGems() {
        return this.currentTurn.getCurrentPlayer().getChestGems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentPlayerSackGems() {
        return this.currentTurn.getCurrentPlayer().getSackGems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentTurnNumber() {
        return this.currentRound.getTurnNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentRoundNumber() {
        return this.getGame().getCurrentRoundNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRedeemableRelicsNumber() {
        return this.currentRound.getState().getReedamableRelics().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPathGems() {
        return this.currentRound.getState().getPathGems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGemModifierDescrition() {
        return this.getGame()
                .getSettings()
                .getRoundGemModifier()
                .getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEndConditionDescription() {
        return this.getGame()
                .getSettings()
                .getRoundEndCondition()
                .getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getActivePlayersNames() {
        return this.currentRound.getState()
                .getRoundPlayersManager()
                .getActivePlayers()
                .stream()
                .map(p -> p.getName())
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getExitedPlayersNames() {
        return this.currentRound.getState()
                .getRoundPlayersManager()
                .getExitedPlayers()
                .stream()
                .map(p -> p.getName())
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDrawnCardsNumber() {
        return this.currentRound.getState().getDrawCards().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Image> getDrawnCardImage() {
        final Card card = this.currentTurn.getDrawnCard().get();
        Optional<Image> img;
        try {
            img = Optional.of(ImageIO.read(card.getImagePath()));
        } catch (final IOException e) {
            img = Optional.empty();
        }
        return img;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeDrawPhase() {
        this.currentTurn.executeDrawPhase();
        this.getPage().refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentPlayerACpu() {
        return this.currentTurn.getCurrentPlayer() instanceof PlayerCpu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeDecisionPhase(final Window toBlockWindow) {
        if (!this.canRoundContinue()) {
            return;
        }
        final RoundState state = this.currentRound.getState();
        final RoundPlayersManager pManager = state.getRoundPlayersManager();
        final List<PlayerInRound> activePlayers = pManager.getActivePlayers();
        final Set<PlayerInRound> exitingThisTurn = new HashSet<>();
        for (final PlayerInRound player : activePlayers) {
            if (player instanceof RealPlayer) {
                final Modal<PlayerChoice> choiceModal = new SwingPlayerChoiceModal(
                        (SwingWindow) Objects.requireNonNull(toBlockWindow),
                        player.getName());
                choiceModal.waitUserInput();
                player.choose(choiceModal.getUserInput());
            } else {
                ((PlayerCpu) player).choose(state);
            }
            if (player.getChoice() == PlayerChoice.EXIT) {
                exitingThisTurn.add(player);
            }
        }
        this.currentTurn.endTurn(exitingThisTurn);
        this.getPage().refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canGameContinue() {
        return this.getGame().hasNext() || this.currentRound.hasNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRoundContinue() {
        return this.currentRound.hasNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advance() {
        if (!this.currentRound.hasNext() && this.getGame().hasNext()) {
            this.currentRound.endRound();
            this.currentRound = this.getGame().next();
        }
        if (this.currentRound.hasNext()) {
            this.currentTurn = this.currentRound.next();
        }
        this.getPage().refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToLeaderboard(final Runnable reset) {
        Objects.requireNonNull(reset).run();
        this.leaderboardSetter.run();
        this.getPageNavigator().navigateTo(PageId.LEADERBOARD);
    }
}
