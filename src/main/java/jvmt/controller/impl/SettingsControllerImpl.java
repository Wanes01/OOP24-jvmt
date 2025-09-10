package jvmt.controller.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import jvmt.controller.api.PageController;
import jvmt.controller.api.SettingsController;
import jvmt.model.card.api.Deck;
import jvmt.model.player.api.CpuDifficulty;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.endcondition.EndConditionFactory;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifierFactory;
import jvmt.model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import jvmt.model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import jvmt.model.game.api.GameSettings;
import jvmt.model.game.impl.GameSettingsImpl;
import jvmt.model.game.impl.InvalidGameSettingsException;
import jvmt.controller.navigator.api.PageId;
import jvmt.controller.navigator.api.PageNavigator;
import jvmt.view.page.api.ControllerAwarePage;

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
            FACTORY_END_COND.firstTrapEnds(),
            FACTORY_END_COND.threeRelicsDrawn());

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
     * @param page           the page that this controller handles.
     * @param nav            the navigation controller to move between the various
     *                       views
     * @param settingsSetter used to configure game settings
     */
    public SettingsControllerImpl(
            final ControllerAwarePage page,
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
        this.getPageNavigator().navigateTo(PageId.GAMEPLAY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<String>> getErrors() {
        return this.errors;
    }
}
