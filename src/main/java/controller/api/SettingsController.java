package controller.api;

import java.util.List;
import java.util.Optional;

import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;

public interface SettingsController {

    boolean setGameSetting(
        final List<String> listPlayersName,
        final int numCpu,
        final Deck deck,
        final EndCondition endCondition,
        final GemModifier gemModifier,
        final CpuDifficulty cpuDifficulty,
        final int nRound);

    Optional<List<String>> getErrors();
    
    void goToGamePlayPage();
    
}
