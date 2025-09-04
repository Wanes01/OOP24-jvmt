package player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.card.api.Deck;
import model.card.impl.DeckFactoryImpl;
import model.game.api.GameSettings;
import model.game.impl.GameSettingsImpl;
import model.player.api.CpuDifficulty;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerCpu;
import model.player.impl.PlayerInRound;
import model.round.api.RoundState;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.impl.RoundStateImpl;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import utils.CommonUtils;


/**
 * CPU player test class.
 * 
 * @author Filippo Gaggi
 */
class PlayerCpuTest {

    private static final String PLAYER_NAME = "TestCpu";
    private static final String PLAYER_DIFF_NAME = "TestDiffCpu";
    private static final int NUMBER_OF_PLAYERS = 5;
    private static final int LOW_RAND_SEED = 1_234_537; // nextDouble() = 0.222229786389059
    private static final int N_CPU = 3;
    private static final int N_ROUND = 5;
    private Deck deck;
    private RoundState roundState;
    private final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(NUMBER_OF_PLAYERS);
    private final List<String> playerNames = new ArrayList<>();
    private PlayerCpu test;
    private EndCondition endCondition;
    private GemModifier gemModifier;
    private GameSettings settings;

    @BeforeEach
    void setUp() {
        this.deck = new DeckFactoryImpl().standardDeck();
        this.roundState = new RoundStateImpl(this.players, this.deck);
        this.endCondition = new EndConditionFactoryImpl().standard();
        this.gemModifier = new  GemModifierFactoryImpl().standard();
        this.settings = new GameSettingsImpl(this.playerNames,
            N_CPU,
            this.deck,
            this.endCondition,
            this.gemModifier,
            CpuDifficulty.EASY,
            N_ROUND);
        this.test = new PlayerCpu(PLAYER_NAME, this.settings, LOW_RAND_SEED);
    }

    // -- Testing the initial values --
    @Test
    void testInitialValues() {
        assertEquals(PLAYER_NAME, test.getName());
        assertEquals(0, test.getChestGems());
        assertEquals(0, test.getSackGems());
        assertEquals(PlayerChoice.STAY, test.getChoice());
    }

    // -- Testing methods for modifying the sack's and chest's gems --
    @Test
    void addSackGemsPositiveTest() {
        final int gemsToAdd = 5;
        test.addSackGems(gemsToAdd);
        assertEquals(gemsToAdd, test.getSackGems());
    }

    @Test
    void addSackGemsNegativeTest() {
        final int gemsToAdd = -5;
        assertThrows(IllegalArgumentException.class, () -> test.addSackGems(gemsToAdd));
    }

    @Test
    void subSackGemsPositiveTest() {
        final int initialGems = 10;
        final int gemsToSubtract = 5;
        test.addSackGems(initialGems);
        test.subSackGems(gemsToSubtract);
        assertEquals(initialGems - gemsToSubtract, test.getSackGems());
    }

    @Test
    void subSackGemsExceedingTest() {
        final int initialGems = 5;
        final int gemsToSubtract = 10;
        test.addSackGems(initialGems);
        test.subSackGems(gemsToSubtract);
        assertEquals(0, test.getSackGems());
    }

    @Test
    void subSackGemsNegativeTest() {
        final int initialGems = 10;
        final int gemsToSubtract = -5;
        test.addSackGems(initialGems);
        assertThrows(IllegalArgumentException.class, () -> test.subSackGems(gemsToSubtract));
    }

    @Test
    void addSackToChestTest() {
        final int gemsInSack = 5;
        test.addSackGems(gemsInSack);
        test.addSackToChest();
        assertEquals(gemsInSack, test.getChestGems());
        assertEquals(0, test.getSackGems());
    }

    @Test
    void subChestGemsPositiveTest() {
        final int initialGems = 10;
        final int gemsToSubtract = 5;
        test.addSackGems(initialGems);
        test.addSackToChest();
        test.subChestGems(gemsToSubtract);
        assertEquals(initialGems - gemsToSubtract, test.getChestGems());
    }

    @Test
    void subChestGemsExceedingTest() {
        final int initialGems = 5;
        final int gemsToSubtract = 10;
        test.addSackGems(initialGems);
        test.addSackToChest();
        test.subChestGems(gemsToSubtract);
        assertEquals(0, test.getChestGems());
    }

    @Test
    void subChestGemsNegativeTest() {
        final int initialGems = 10;
        final int gemsToSubtract = -5;
        test.addSackGems(initialGems);
        test.addSackToChest();
        assertThrows(IllegalArgumentException.class, () -> test.subChestGems(gemsToSubtract));
    }

    // -- Testing methods for managing the player choice --
    @Test
    void chooseTest() {
        test.choose(PlayerChoice.EXIT);
        assertEquals(PlayerChoice.EXIT, test.getChoice());
    }
    @Test
    void exitTest() {
        test.exit();
        assertEquals(PlayerChoice.EXIT, test.getChoice());
    }

    @Test
    void impossibleExitTest() {
        test.choose(PlayerChoice.EXIT);
        assertThrows(IllegalStateException.class, test::exit);
    }

    // -- Testing methods for reset --
    @Test
    void resetSackTest() {
        final int gemsToAdd = 5;
        test.addSackGems(gemsToAdd);
        test.resetSack();
        assertEquals(0, test.getSackGems());
    }

    @Test
    void resetChoiceTest() {
        test.choose(PlayerChoice.EXIT);
        test.resetChoice();
        assertEquals(PlayerChoice.STAY, test.getChoice());
    }

    @Test
    void resetRoundPlayerTest() {
        final int gemsToAdd = 5;
        test.addSackGems(gemsToAdd);
        test.choose(PlayerChoice.EXIT);
        test.resetRoundPlayer();
        assertEquals(0, test.getSackGems());
        assertEquals(PlayerChoice.STAY, test.getChoice());
    }

    // -- Testing the CPU choice --
    @Test
    void testCpuChoose() {
        test.choose(PlayerChoice.STAY);
        final PlayerChoice choice = test.getChoice();
        test.choose(this.roundState);
        final PlayerChoice choiceCpu = test.getChoice();
        assertEquals(choice, choiceCpu);
    }

    // -- Testing for hashCode() and equals() methods --
    @Test
    void hashCodeTest() {
        final PlayerCpu other = new PlayerCpu(PLAYER_NAME, this.settings);
        assertEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void hashCodeDiffNameTest() {
        final PlayerCpu other = new PlayerCpu(PLAYER_DIFF_NAME, this.settings);
        assertNotEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void equalsTest() {
        final PlayerCpu other = new PlayerCpu(PLAYER_NAME, this.settings);
        assertEquals(test, other);
    }

    @Test
    void equalsDiffNameTest() {
        final PlayerCpu other = new PlayerCpu(PLAYER_DIFF_NAME, this.settings);
        assertNotEquals(test, other);
    }

    @Test
    void equalsNullTest() {
        assertNotNull(test);
    }

    @Test
    void equalsDiffClassTest() {
        final Object other = new Object();
        assertNotEquals(test, other);
    }
}
