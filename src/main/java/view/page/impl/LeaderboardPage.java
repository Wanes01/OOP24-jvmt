package view.page.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import model.player.impl.PlayerInRound;
import view.page.api.SwingPage;

/**
 * Represents the leaderboard that appears at the end of the game.
 * <p>
 * The user interaction is handled using a {@link LeaderboardController} that
 * specifies an action for every possible user interaction with this page.
 * </p>
 * 
 * @see SwingPage
 * @see LeaderboardController
 * 
 * @author Filippo Gaggi
 */
public class LeaderboardPage extends SwingPage {
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE_VICTOR = 30;
    private static final int FONT_SIZE_HOME_BUTTON = 30;
    private static final int COL_GAP = 10;
    private static final Dimension SCROLLABLE_DIM = new Dimension(400, 200);
    private static final Border BOX_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    /**
     * Main panel of the leaderboard view.
     * It has a scrollable leaderboard and a button that redirects to the home page.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players the list of players that are to appear in the leaderboard.
     */
    public LeaderboardPage(final List<PlayerInRound> players) {
        final JPanel leaderboardUi = new JPanel();
        final Font fontVictor = new Font("Arial", Font.PLAIN, FONT_SIZE_VICTOR);
        final Font fontHomeButton = new Font("Arial", Font.PLAIN, FONT_SIZE_HOME_BUTTON);
        leaderboardUi.setLayout(new BoxLayout(leaderboardUi, BoxLayout.Y_AXIS));

        final JLabel lblVictor = new JLabel(Objects.requireNonNull(players).get(0).getName()
                + " won with a score of "
                + Objects.requireNonNull(players).get(0).getChestGems()
                + " gems!");
        lblVictor.setAlignmentX(CENTER_ALIGNMENT);
        lblVictor.setFont(fontVictor);
        lblVictor.setBorder(BOX_BORDER);
        leaderboardUi.add(lblVictor);

        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));

        final JLabel lblleaderboard = new JLabel("Leaderboard");
        leaderboardUi.add(lblleaderboard);

        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));

        leaderboardUi.add(playersList(Objects.requireNonNull(players)));
        lblleaderboard.setAlignmentX(CENTER_ALIGNMENT);

        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));

        final JButton btnHome = new JButton("Go back to Home page");
        btnHome.setFont(fontHomeButton);
        btnHome.setAlignmentX(CENTER_ALIGNMENT);
        leaderboardUi.add(btnHome);

        super.add(leaderboardUi);
    }

    /**
     * Panel which contains the leaderboard itself.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players the list of players that are to appear in the leaderboard.
     * 
     * @return the panel itself.
     */
    private JPanel playersList(final List<PlayerInRound> players) {
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.X_AXIS));

        final DefaultTableModel leaderboardInfo = new DefaultTableModel();
        leaderboardInfo.setColumnIdentifiers(new Object[] { "Name", "Score" });

        for (final PlayerInRound player : Objects.requireNonNull(players)) {
            leaderboardInfo.addRow(new Object[] { player.getName(), player.getChestGems() });
        }

        final JTable table = new JTable(leaderboardInfo);
        final JScrollPane scrollableBoard = new JScrollPane(table);
        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.setPreferredSize(SCROLLABLE_DIM);
        playersList.add(scrollableBoard);

        return playersList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHandlers'");
    }
}
