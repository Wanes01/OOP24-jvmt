package controller.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import controller.api.PageController;
import controller.api.SettingsController;
import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.settings.api.GameSettings;
import model.settings.impl.GameSettingsImpl;
import model.settings.impl.InvalidGameSettingsException;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

public class SettingsControllerImpl extends PageController implements SettingsController {

    private final Consumer<GameSettings> settingsSetter;
    private Optional<List<String>> errors = Optional.empty();

    public SettingsControllerImpl(
        final Page page,
        final PageNavigator nav,
        Consumer<GameSettings> settingsSetter) {
            super(page,nav);
            this.settingsSetter = settingsSetter;
    } 

    public boolean setGameSetting(
        final List<String> listPlayersName,
        final int numCpu,
        final Deck deck,
        final EndCondition endCond,
        final GemModifier gemMod,
        final CpuDifficulty cpuDiff,
        final int nRound) {
            GameSettings gameSet; 
            try {
                gameSet = new GameSettingsImpl(
                    listPlayersName,
                    numCpu,
                    deck,
                    endCond,
                    gemMod,
                    cpuDiff,
                    nRound);
                this.settingsSetter.accept(gameSet);
                return true;
            } catch (InvalidGameSettingsException ex) {
                this.errors = Optional.of(ex.getErrors());
                return false;
            }
    }

    @Override
    public void goToGamePlayPage() {
        this.getPageNavigator().navigateTo(PageId.MENU);
    }

    @Override
    public Optional<List<String>> getErrors() {
        return this.errors;
    }
}
