package model.settings.api;

import java.util.List;

import model.card.api.Deck;
import model.player.api.CpuDifficulty;
import model.player.impl.PlayerInRound;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;

public interface GameSettings {
    boolean areSettingsOk();
    
    int getNumberOfPlayers();

    int getNumberOfCpu();

    int getNumberOfRealPlayers();

    Deck getDeck();

    EndCondition getRoundEndCondition();

    GemModifier getRoundGemModifier();

    CpuDifficulty getCpuDifficulty();

    int getNumberOfRounds();

    List<PlayerInRound> createPlayers();
}
