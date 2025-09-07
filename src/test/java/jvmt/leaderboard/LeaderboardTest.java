package jvmt.leaderboard;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import jvmt.model.leaderboard.api.Leaderboard;
import jvmt.model.leaderboard.impl.LeaderboardImpl;
import jvmt.model.player.api.Player;
import jvmt.utils.CommonUtils;

/**
 * Leaderboard test class.
 * 
 * @author Filippo Gaggi
 */
class LeaderboardTest {

    private static final int NUMBER_OF_PLAYERS = 5;
    private static final int P0_GEMS = 6;
    private static final int P1_GEMS = 9;
    private static final int P2_GEMS = 3;
    private static final int P3_GEMS = 5;
    private static final int P4_GEMS = 7;
    private final List<Player> unsortedList = new ArrayList<>(
            CommonUtils.generatePlayerInRoundList(NUMBER_OF_PLAYERS));

    // -- Testing method for sorting the players list --
    @Test
    void testGetPlayersSortedByScore() {
        unsortedList.get(0).addSackGems(P0_GEMS);
        unsortedList.get(0).addSackToChest();
        unsortedList.get(1).addSackGems(P1_GEMS);
        unsortedList.get(1).addSackToChest();
        unsortedList.get(2).addSackGems(P2_GEMS);
        unsortedList.get(2).addSackToChest();
        unsortedList.get(3).addSackGems(P3_GEMS);
        unsortedList.get(3).addSackToChest();
        unsortedList.get(4).addSackGems(P4_GEMS);
        unsortedList.get(4).addSackToChest();
        final Leaderboard leaderboard = new LeaderboardImpl(this.unsortedList);
        final List<Player> sortedList = new ArrayList<>(leaderboard.getPlayersSortedByScore());
        assertEquals(P1_GEMS, sortedList.get(0).getChestGems());
        assertEquals(P4_GEMS, sortedList.get(1).getChestGems());
        assertEquals(P0_GEMS, sortedList.get(2).getChestGems());
    }
}
