package controller.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import controller.api.PageController;
import controller.api.SettingsController;
import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.endcondition.EndConditionFactory;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.roundeffect.gemmodifier.GemModifierFactory;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import model.game.api.GameSettings;
import model.game.impl.GameSettingsImpl;
import model.game.impl.InvalidGameSettingsException;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

/**
 * The implementation of the SettingController interface.
 * 
 * @author Andrea La Tosa
 */
public class SettingsControllerImpl extends PageController implements SettingsController {

    private static final EndConditionFactory FACTORY_END_COND = new EndConditionFactoryImpl();
    private static final GemModifierFactory FACTORY_GEM_MOD = new GemModifierFactoryImpl();

    /**
     * The list of possible end-of-round conditions.
     */
    public static final List<EndCondition> END_CONDITIONS = List.of(
        FACTORY_END_COND.standard(),
        FACTORY_END_COND.firstTrapEnds());

    /**
     * The list of possible gem modifiers.
     */
    public static final List<GemModifier> GEM_MODIFIERS = List.of(
        FACTORY_GEM_MOD.standard(),
        FACTORY_GEM_MOD.gemMultiplier(2),
        FACTORY_GEM_MOD.gemMultiplier(3),
        FACTORY_GEM_MOD.riskyReward(10),
        FACTORY_GEM_MOD.leftReward(3));

    private final Consumer<GameSettings> settingsSetter;
    private Optional<List<String>> errors = Optional.empty();

    /**
     * Creates a new instance of {@code SettingsControllerImpl}.
     * 
     * @param page the page that this controller handles.
     * @param nav the navigation controller to move between the various views
     * @param settingsSetter used to configure game settings
     */
    public SettingsControllerImpl(
            final Page page,
            final PageNavigator nav,
            final Consumer<GameSettings> settingsSetter) {
        super(page, nav);
        this.settingsSetter = settingsSetter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areGameSettingOK(
        final List<String> listPlayersName,
        final int numCpu,
        final Deck deck,
        final EndCondition endCond,
        final GemModifier gemMod,
        final CpuDifficulty cpuDiff,
        final int nRound) {
            final GameSettings gameSet;
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
            } catch (final InvalidGameSettingsException ex) {
                this.errors = Optional.of(ex.getErrors());
                return false;
            }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToGamePlayPage() {
        this.getPageNavigator().navigateTo(PageId.MENU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<String>> getErrors() {
        return this.errors;
    }
}
