package controller.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import controller.api.GameAwarePageController;
import controller.api.GameplayController;
import model.card.api.Card;
import model.game.api.Game;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
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
public class GameplayControllerImpl extends GameAwarePageController {

    /* private final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(3);
    private final Deck deck = new DeckFactoryImpl().standardDeck();
    private final RoundEffect effect = new RoundEffectImpl(
            new EndConditionFactoryImpl().standard(),
            new GemModifierFactoryImpl().standard()); */

    /* private final List<PlayerInRound> players;
    private final Deck deck;
    private final RoundEffect effect; */
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
    public String getCurrentPlayerName() {
        return this.currentTurn.getCurrentPlayer().getName();
    }

    /**
     * {@inheritDoc}
     */
    public int getCurrentPlayerChestGems() {
        return this.currentTurn.getCurrentPlayer().getChestGems();
    }

    /**
     * {@inheritDoc}
     */
    public int getCurrentPlayerSackGems() {
        return this.currentTurn.getCurrentPlayer().getSackGems();
    }

    /**
     * {@inheritDoc}
     */
    public EndCondition getGameEndCondition() {
        return this.game.getEndCondition();
    }

    /**
     * {@inheritDoc}
     */
    public GemModifier getGemModifier() {
        return this.game.getGemModifier();
    }

    /**
     * {@inheritDoc}
     */
    public int getPathGems() {
        return this.round.getState().getPathGems();
    }

    /**
     * {@inheritDoc}
     */
    public int getPathRelics() {
        return this.round.getState().getDrawnRelics().size();
    }

    /**
     * {@inheritDoc}
     */
    public int getDrawnCardsNumber() {
        return this.round.getState().getDrawCards().size();
    }

    /**
     * {@inheritDoc}
     */
    public Card getDrawnCard() {
        return round.getState().getDrawCards().getLast();
    }

    /**
     * {@inheritDoc}
     */
    public List<PlayerInRound> getActivePlayersList() {
        return round.getState().getRoundPlayersManager().getActivePlayers();
    }

    /**
     * {@inheritDoc}
     */
    public List<PlayerInRound> getExitedPlayersList() {
        return round.getState().getRoundPlayersManager().getExitedPlayers();
    }

    /**
     * {@inheritDoc}
     */
    public void drawPhase() {
        this.currentTurn.executeDrawPhase();
    }

    /** Apri il modale e ritorna la scelta dell'utente */
    public void openModalAndGetResult(final SwingWindow window, final String playerName) {
        final Modal<PlayerChoice> modal = new SwingPlayerChoiceModal(window, playerName);
        modal.waitUserInput(); // blocca finché l'utente non chiude

        final PlayerChoice choice = modal.getUserInput();

        // Salva la scelta sul giocatore corrente
        this.currentTurn.getCurrentPlayer().choose(choice);
    }

    /** Controlla se ci sono ancora turni da giocare */
    public boolean canRoundContinue() {
        return this.currentTurn != null;
    }

    /** Avanza al turno successivo */
    public void advanceTurn() {
        if (this.turns.hasNext()) {
            this.currentTurn = this.turns.next();
        } else {
            // Round finito
            this.round.endRound();
            this.currentTurn = null; // segnala che il round è terminato
        }
    }

    /**
     * {@inheritDoc}
     */
    public void goToLeaderbooardPage() {
        this.getPageNavigator().navigateTo(PageId.LEADERBOARD);
    }
}
