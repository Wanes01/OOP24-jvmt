package controller.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import controller.api.GameAwarePageController;
import controller.api.GameplayController;
import model.card.api.Card;
import model.game.api.Game;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
import model.player.impl.RealPlayer;
import model.round.api.Round;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.turn.Turn;
import model.round.impl.RoundImpl;
import view.modal.api.Modal;
import view.modal.impl.SwingPlayerChoiceModal;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;
import view.window.impl.SwingWindow;

/**
 * The implementation of the {@link GameplayController} interface.
 * 
 * @see GameplayController
 * 
 * @author Filippo Gaggi
 */
public class GameplayControllerImpl extends GameAwarePageController implements GameplayController {

    private final Round round;
    private final Iterator<Turn> turns;
    private final Game game;
    private Turn currentTurn;

    /**
     * Constructor of the class.
     * 
     * @throws NullPointerException if {@link page} is null.
     * @throws NullPointerException if {@link navigator} is null.
     * @throws NullPointerException if {@link game} is null.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     * @param game      the round iterator of the game.
     */
    public GameplayControllerImpl(final Page page,
        final PageNavigator navigator,
        final Game game) {

        super(Objects.requireNonNull(page),
            Objects.requireNonNull(navigator),
            Objects.requireNonNull(game));

        this.round = new RoundImpl(Objects.requireNonNull(game).getPlayers(),
            Objects.requireNonNull(game).getDeck(),
            Objects.requireNonNull(game).getRoundEffect());
        this.turns = this.round.iterator();
        this.game = Objects.requireNonNull(game);

        if (this.turns.hasNext()) {
            this.currentTurn = this.turns.next();
        }
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
    public EndCondition getGameEndCondition() {
        return this.game.getEndCondition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier getGemModifier() {
        return this.game.getGemModifier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPathGems() {
        return this.round.getState().getPathGems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPathRelics() {
        return this.round.getState().getDrawnRelics().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDrawnCardsNumber() {
        return this.round.getState().getDrawCards().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card getDrawnCard() {
        return this.currentTurn.getDrawnCard().get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getActivePlayersList() {
        return round.getState().getRoundPlayersManager().getActivePlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getExitedPlayersList() {
        return round.getState().getRoundPlayersManager().getExitedPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawPhase() {
        this.currentTurn.executeDrawPhase();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PlayerInRound> choicePhase(final SwingWindow window) {
        final Set<PlayerInRound> exitingPlayers = new HashSet<>();
        PlayerChoice choice;
        for (final PlayerInRound activePlayer : this.round.getState().getRoundPlayersManager().getActivePlayers()) {
            if (Objects.requireNonNull(activePlayer) instanceof RealPlayer) {
                choice = obtainPlayerChoice(Objects.requireNonNull(window), Objects.requireNonNull(activePlayer));
            } else {
                choice = this.game.getLogicCpu().cpuChoice(this.round.getState());
                Objects.requireNonNull(activePlayer).choose(choice);
            }
            if (choice == PlayerChoice.EXIT) {
                exitingPlayers.add(activePlayer);
            }
        }
        return exitingPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return !this.game.hasNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceTurn(final Set<PlayerInRound> exitedPlayers) {
        endTurn(Objects.requireNonNull(exitedPlayers));
        if (isRoundOver()) {
            this.round.endRound();
            this.game.next();
        } else {
            this.turns.next();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToLeaderboardPage() {
        this.getPageNavigator().navigateTo(PageId.LEADERBOARD);
    }

    /**
     * Opens a modal for making the player take the choice.
     * 
     * @throws NullPointerException if {@link window} is null.
     * @throws NullPointerException if {@link player} is null.
     * 
     * @param window the main application window.
     * @param player the player who does the choice.
     * 
     * @return the choice of the player.
     */
    private PlayerChoice obtainPlayerChoice(final SwingWindow window, final PlayerInRound player) {
        final Modal<PlayerChoice> modal = new SwingPlayerChoiceModal(Objects.requireNonNull(window),
            Objects.requireNonNull(player).getName());
        modal.waitUserInput();

        final PlayerChoice choice = modal.getUserInput();

        Objects.requireNonNull(player).choose(choice);
        return choice;
    }

    /**
     * Executes the end turn.
     * 
     * @throws NullPointerException if {@link exitedPlayers} is null.
     * 
     * @param exitedPlayers the main application window.
     */
    private void endTurn(final Set<PlayerInRound> exitedPlayers) {
        this.currentTurn.endTurn(Objects.requireNonNull(exitedPlayers));
    }

    /**
     * Method for controlling if the round is over.
     * 
     * @return true if the round is over or false if not.
     */
    private boolean isRoundOver() {
        return !this.turns.hasNext();
    }
}
