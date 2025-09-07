package jvmt.player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.card.api.Card;
import jvmt.model.card.api.Deck;
import jvmt.model.card.api.TypeTrapCard;
import jvmt.model.card.impl.DeckFactoryImpl;
import jvmt.model.card.impl.RelicCard;
import jvmt.model.card.impl.TrapCard;
import jvmt.model.card.impl.TreasureCard;
import jvmt.model.game.api.GameSettings;
import jvmt.model.game.impl.GameSettingsImpl;
import jvmt.model.player.api.CpuDifficulty;
import jvmt.model.player.api.LogicCpu;
import jvmt.model.player.api.PlayerChoice;
import jvmt.model.player.impl.LogicCpuImpl;
import jvmt.model.player.api.Player;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;
import jvmt.model.round.impl.RoundStateImpl;
import jvmt.model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import jvmt.model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import jvmt.utils.CommonUtils;

/**
 * CPU logic test class.
 * 
 * @author Filippo Gaggi
 */
class LogicCpuTest {

    private static final int HIGH_RAND_SEED = 2345; // nextDouble() = 0.9336903394987087
    private static final int LOW_RAND_SEED = 1_234_537; // nextDouble() = 0.222229786389059
    private static final int NUMBER_OF_PLAYERS = 5;
    private static final int PATH_GEMS_1 = 9;
    private static final int PATH_GEMS_2 = 8;
    private static final int PATH_GEMS_3 = 3;
    private static final int TREASURE_VALUE_1 = 9;
    private static final int TREASURE_VALUE_2 = 7;
    private static final int TREASURE_VALUE_3 = 5;
    private static final int TREASURE_VALUE_4 = 4;
    private static final int TREASURE_VALUE_5 = 3;
    private static final int N_CPU = 3;
    private static final int N_ROUND = 5;
    private static final String TREASURE_NAME = "Treasure card";
    private static final String TRAP_NAME = "Trap card";
    private static final String RELIC_NAME = "Relic card";
    private final List<Player> players = CommonUtils.generatePlayerList(NUMBER_OF_PLAYERS);
    private final List<String> playerNames = new ArrayList<>();
    private EndCondition endCondition;
    private GemModifier gemModifier;
    private LogicCpu logicCpu;
    private Deck deck;
    private RoundState roundState;

    @BeforeEach
    void setUp() {
        this.deck = new DeckFactoryImpl().standardDeck();
        this.endCondition = new EndConditionFactoryImpl().standard();
        this.gemModifier = new GemModifierFactoryImpl().standard();
        this.roundState = new RoundStateImpl(this.players, this.deck);
    }

    /*
     * -- Testing CPU choice in EASY difficulty --
     * When the CPU has EASY difficulty most weight is put in the gems.
     * The CPU tends to EXIT whenever the amount of gems per player is high,
     * without considering many risks.
     */

    /*
     * When the CPU has EASY difficulty, gems count the most.
     * In this test the CPU chooses to EXIT because the amount of gems per player
     * in the path is high.
     */
    @Test
    void cpuChoiceEasyGems() {
        final int seed = HIGH_RAND_SEED; // to make the borderline very high.
        this.roundState.setPathGems(PATH_GEMS_2);
        final Card treasure1 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_1);
        final Card treasure2 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        final Card treasure3 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        this.roundState.addCardToPath(treasure1);
        this.roundState.addCardToPath(treasure2);
        this.roundState.addCardToPath(treasure3);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.EASY,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.EXIT, choice);
    }

    /*
     * When the CPU has EASY difficulty, relics don't count at all.
     * In this test the CPU chooses to STAY despite having all relics in the deck
     * drawn.
     */
    @Test
    void cpuChoiceEasyRelics() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(0);
        final Card relic = new RelicCard(RELIC_NAME);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.EASY,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * When the CPU has EASY difficulty, traps don't count much.
     * In this test the CPU chooses to STAY despite having 4 types of traps drawn.
     */
    @Test
    void cpuChoiceEasyTraps() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(0);
        final Card trap1 = new TrapCard(TRAP_NAME, TypeTrapCard.SPIDER);
        final Card trap2 = new TrapCard(TRAP_NAME, TypeTrapCard.SNAKE);
        final Card trap3 = new TrapCard(TRAP_NAME, TypeTrapCard.LAVA);
        final Card trap4 = new TrapCard(TRAP_NAME, TypeTrapCard.BATTERING_RAM);
        this.roundState.addCardToPath(trap1);
        this.roundState.addCardToPath(trap2);
        this.roundState.addCardToPath(trap3);
        this.roundState.addCardToPath(trap4);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.EASY,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * When the CPU has EASY difficulty, players count a bit.
     * In this test the CPU chooses to STAY despite having a decent amount of gems
     * per player but also having 2 players choose to EXIT.
     */
    @Test
    void cpuChoiceEasyPlayers() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(PATH_GEMS_3);
        this.players.get(0).choose(PlayerChoice.EXIT);
        this.players.get(1).choose(PlayerChoice.EXIT);
        final Card treasure = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_1);
        this.roundState.addCardToPath(treasure);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.EASY,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * -- Testing CPU choice in NORMAL difficulty --
     * When the CPU has NORMAL difficulty all weights are balanced, so each variable
     * counts equally.
     */

    /*
     * In this test the CPU chooses to STAY despite the amount of gems in the path
     * being high.
     */
    @Test
    void cpuChoiceNormalGems() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(PATH_GEMS_2);
        final Card treasure1 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_1);
        final Card treasure2 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        final Card treasure3 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        this.roundState.addCardToPath(treasure1);
        this.roundState.addCardToPath(treasure2);
        this.roundState.addCardToPath(treasure3);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.NORMAL,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * In this test the CPU chooses to STAY despite having 4 relics in the deck
     * drawn.
     */
    @Test
    void cpuChoiceNormalRelics() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(0);
        final Card relic = new RelicCard(RELIC_NAME);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.NORMAL,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * In this test the CPU chooses to STAY despite having 4 types of traps drawn.
     */
    @Test
    void cpuChoiceNormalTraps() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(0);
        final Card trap1 = new TrapCard(TRAP_NAME, TypeTrapCard.SPIDER);
        final Card trap2 = new TrapCard(TRAP_NAME, TypeTrapCard.SNAKE);
        final Card trap3 = new TrapCard(TRAP_NAME, TypeTrapCard.LAVA);
        final Card trap4 = new TrapCard(TRAP_NAME, TypeTrapCard.BATTERING_RAM);
        this.roundState.addCardToPath(trap1);
        this.roundState.addCardToPath(trap2);
        this.roundState.addCardToPath(trap3);
        this.roundState.addCardToPath(trap4);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.NORMAL,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * In this test the CPU chooses to STAY despite having a decent amount of gems
     * per player but also having 2 players choose to EXIT.
     */
    @Test
    void cpuChoiceNormalPlayers() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(PATH_GEMS_3);
        this.players.get(0).choose(PlayerChoice.EXIT);
        this.players.get(1).choose(PlayerChoice.EXIT);
        final Card treasure1 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_4);
        final Card treasure2 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_3);
        this.roundState.addCardToPath(treasure1);
        this.roundState.addCardToPath(treasure2);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.NORMAL,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * In this test the CPU chooses to EXIT because each variable counts equally.
     */
    @Test
    void cpuChoiceNormalGeneral() {
        final int seed = HIGH_RAND_SEED; // to make the borderline very high.
        this.roundState.setPathGems(PATH_GEMS_1);
        this.players.get(0).choose(PlayerChoice.EXIT);
        final Card treasure1 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        final Card treasure2 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        final Card treasure3 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_5);
        final Card trap1 = new TrapCard(TRAP_NAME, TypeTrapCard.SPIDER);
        final Card trap2 = new TrapCard(TRAP_NAME, TypeTrapCard.SNAKE);
        final Card relic = new RelicCard(RELIC_NAME);
        this.roundState.addCardToPath(treasure1);
        this.roundState.addCardToPath(treasure2);
        this.roundState.addCardToPath(treasure3);
        this.roundState.addCardToPath(trap1);
        this.roundState.addCardToPath(trap2);
        this.roundState.addCardToPath(relic);
        this.roundState.addCardToPath(relic);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.NORMAL,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.EXIT, choice);
    }

    /*
     * -- Testing CPU choice in HARD difficulty --
     * When the CPU has HARD difficulty most weight is put in relics and traps.
     * The CPU tends to EXIT whenever the amount of relics and traps is high and
     * doesn't care too much about the amount of gems per player.
     */

    /*
     * When the CPU has HARD difficulty, players and gems almost don't count.
     * In this test the CPU chooses to STAY despite all players choose to STAY
     * and despite the high amount of gems per player.
     */
    @Test
    void cpuChoiceHardPlayers() {
        final int seed = LOW_RAND_SEED; // to make the borderline very low.
        this.roundState.setPathGems(PATH_GEMS_1);
        final Card treasure1 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        final Card treasure2 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_2);
        final Card treasure3 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_1);
        final Card treasure4 = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_5);
        this.roundState.addCardToPath(treasure1);
        this.roundState.addCardToPath(treasure2);
        this.roundState.addCardToPath(treasure3);
        this.roundState.addCardToPath(treasure4);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.HARD,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.STAY, choice);
    }

    /*
     * When the CPU has HARD difficulty, traps and relics count the most.
     * In this test the CPU chooses to EXIT because 3 traps were drawn and there are
     * 3
     * relics in the path, despite zero gems in the path.
     */
    @Test
    void cpuChoiceHardTraps() {
        final int seed = HIGH_RAND_SEED; // to make the borderline very high.
        this.roundState.setPathGems(0);
        final Card treasure = new TreasureCard(TREASURE_NAME, TREASURE_VALUE_1);
        final Card trap1 = new TrapCard(TRAP_NAME, TypeTrapCard.SPIDER);
        final Card trap2 = new TrapCard(TRAP_NAME, TypeTrapCard.SNAKE);
        final Card trap3 = new TrapCard(TRAP_NAME, TypeTrapCard.LAVA);
        final Card relic1 = new RelicCard(RELIC_NAME);
        final Card relic2 = new RelicCard(RELIC_NAME);
        this.roundState.addCardToPath(treasure);
        this.roundState.addCardToPath(trap1);
        this.roundState.addCardToPath(trap2);
        this.roundState.addCardToPath(trap3);
        this.roundState.addCardToPath(relic1);
        this.roundState.addCardToPath(relic1);
        this.roundState.addCardToPath(relic2);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.HARD,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings, seed);
        final PlayerChoice choice = logicCpu.cpuChoice(this.roundState);
        assertEquals(PlayerChoice.EXIT, choice);
    }

    /*
     * -- Testing for active players = 0 --
     * LogicCpuImpl should throw an IllegalArgumentException if the amount of active
     * players is zero.
     */

    @Test
    void activePlayersZero() {
        this.players.get(0).choose(PlayerChoice.EXIT);
        this.players.get(1).choose(PlayerChoice.EXIT);
        this.players.get(2).choose(PlayerChoice.EXIT);
        this.players.get(3).choose(PlayerChoice.EXIT);
        this.players.get(4).choose(PlayerChoice.EXIT);
        for (final Player player : this.players) {
            this.playerNames.add(player.getName());
        }
        final GameSettings settings = new GameSettingsImpl(this.playerNames,
                N_CPU,
                this.deck,
                this.endCondition,
                this.gemModifier,
                CpuDifficulty.HARD,
                N_ROUND);
        this.logicCpu = new LogicCpuImpl(settings);
        assertThrows(IllegalArgumentException.class, () -> logicCpu.cpuChoice(this.roundState));
    }
}
