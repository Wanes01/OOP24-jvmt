package controller.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import controller.api.GameAwarePageController;
import model.card.api.Card;
import model.card.api.Deck;
import model.card.impl.DeckFactoryImpl;
import model.game.api.Game;
import model.game.api.GameSettings;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
import model.round.api.Round;
import model.round.api.roundeffect.RoundEffect;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.turn.Turn;
import model.round.impl.RoundImpl;
import model.round.impl.roundeffect.RoundEffectImpl;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import utils.CommonUtils;
import view.modal.api.Modal;
import view.modal.impl.SwingPlayerChoiceModal;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;
import view.window.impl.SwingWindow;

public class GameplayControllerImpl extends GameAwarePageController {
    // QUESTI PASSATI NEL COSTRUTTORE PER COSTRUIRE IL ROUND
    private final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(3);
    private final Deck deck = new DeckFactoryImpl().standardDeck();
    private final RoundEffect effect = new RoundEffectImpl(
            new EndConditionFactoryImpl().standard(),
            new GemModifierFactoryImpl().standard());
    // ----------------------------------------------------------

    private final Round round;
    private final Iterator<Turn> turns;
    private final GameSettings settings;
    private Turn currentTurn;

    public GameplayControllerImpl(Page page, PageNavigator navigator, Game game) {
        super(
                Objects.requireNonNull(page),
                Objects.requireNonNull(navigator),
                Objects.requireNonNull(game));
        this.round = new RoundImpl(Objects.requireNonNull(players), Objects.requireNonNull(deck),
                Objects.requireNonNull(effect));
        this.turns = this.round.iterator();
        this.settings = null; // Objects.requireNonNull(settings);

        if (this.turns.hasNext()) {
            this.currentTurn = this.turns.next();
        }
    }

    public String getCurrentPlayerName() {
        return this.currentTurn.getCurrentPlayer().getName();
    }

    public int getCurrentPlayerChestGems() {
        return this.currentTurn.getCurrentPlayer().getChestGems();
    }

    public int getCurrentPlayerSackGems() {
        return this.currentTurn.getCurrentPlayer().getSackGems();
    }

    public EndCondition getGameEndCondition() {
        return this.settings.getRoundEndCondition();
    }

    public GemModifier getGemModifier() {
        return this.settings.getRoundGemModifier();
    }

    public int getPathGems() {
        return this.round.getState().getPathGems();
    }

    public int getPathRelics() {
        return this.round.getState().getDrawnRelics().size();
    }

    public int getDrawnCardsNumber() {
        return this.round.getState().getDrawCards().size();
    }

    public Card getDrawnCard() {
        return round.getState().getDrawCards().getLast();
    }

    public List<PlayerInRound> getActivePlayersList() {
        return round.getState().getRoundPlayersManager().getActivePlayers();
    }

    public List<PlayerInRound> getExitedPlayersList() {
        return round.getState().getRoundPlayersManager().getExitedPlayers();
    }

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

    public void goToLeaderbooardPage() {
        this.getPageNavigator().navigateTo(PageId.LEADERBOARD);
    }
}
