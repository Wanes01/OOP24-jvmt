package jvmt.controller.api;

import java.util.List;
import java.util.Optional;

import jvmt.model.card.api.Deck;
import jvmt.model.player.api.CpuDifficulty;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;

/**
 * Controller for the view related to game settings.
 * 
 * @author Andrea La Tosa
 */
public interface SettingsController {

    /**
     * If the settings are valid returns {@code true},
     * otherwise, it logs errors in the settings and returns {@code false}.
     * 
     * @param listPlayersName the list composed of the players' names
     * @param numCpu the number of CPUs to use in the game
     * @param deck the type of deck to use during the game
     * @param endCondition the end condition of the rounds
     * @param gemModifier the gem modifier applied to the game
     * @param cpuDifficulty the difficulty level of all CPUs in the game
     * @param nRound the number of rounds in the game
     * 
     * @return the result of the settings check
     */
    boolean areGameSettingOK(
        List<String> listPlayersName,
        int numCpu,
        Deck deck,
        EndCondition endCondition,
        GemModifier gemModifier,
        CpuDifficulty cpuDifficulty,
        int nRound);

    /**
     * If errors are encountered while entering settings to configure the game,
     * this method can be used to obtain a list of those errors.
     * 
     * @return the list of errors in the settings, if any
     */
    Optional<List<String>> getErrors();

    /**
     * Navigates to the gameplay view.
     */
    void goToGamePlayPage();

}
