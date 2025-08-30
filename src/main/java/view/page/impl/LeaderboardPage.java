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

import controller.impl.LeaderboardControllerImpl;
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
    private static final int FONT_SIZE_WINNER = 30;
    private static final int FONT_SIZE_HOME_BUTTON = 30;
    private static final int COL_GAP = 10;
    private static final Dimension SCROLLABLE_DIM = new Dimension(400, 200);
    private static final Border BOX_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
    private final JLabel lblWinner;
    private final JButton btnHome;
    private DefaultTableModel leaderboardInfo;

    /**
     * Main panel of the leaderboard page.
     * It has a scrollable leaderboard and a button that redirects to the home page.
     */
    public LeaderboardPage(Dimension winDim) {
        super(winDim);
        final JPanel leaderboardUi = new JPanel();
        final Font fontWinner = new Font("Arial", Font.PLAIN, FONT_SIZE_WINNER);
        final Font fontHomeButton = new Font("Arial", Font.PLAIN, FONT_SIZE_HOME_BUTTON);
        leaderboardUi.setLayout(new BoxLayout(leaderboardUi, BoxLayout.Y_AXIS));

        this.lblWinner = new JLabel("");
        this.lblWinner.setAlignmentX(CENTER_ALIGNMENT);
        this.lblWinner.setFont(fontWinner);
        this.lblWinner.setBorder(BOX_BORDER);
        leaderboardUi.add(this.lblWinner);

        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));

        final JLabel lblleaderboard = new JLabel("Leaderboard");
        leaderboardUi.add(lblleaderboard);

        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));

        leaderboardUi.add(playersList());
        lblleaderboard.setAlignmentX(CENTER_ALIGNMENT);

        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));

        this.btnHome = new JButton("Go back to Home page");
        this.btnHome.setFont(fontHomeButton);
        this.btnHome.setAlignmentX(CENTER_ALIGNMENT);
        leaderboardUi.add(this.btnHome);

        super.add(leaderboardUi);
    }

    /**
     * Panel which contains the leaderboard itself.
     * 
     * @return the panel itself.
     */
    private JPanel playersList() {
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.X_AXIS));

        this.leaderboardInfo = new DefaultTableModel();
        this.leaderboardInfo.setColumnIdentifiers(new Object[] { "Name", "Score" });
        final JTable table = new JTable(leaderboardInfo);
        final JScrollPane scrollableBoard = new JScrollPane(table);
        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.setPreferredSize(SCROLLABLE_DIM);
        playersList.add(scrollableBoard);

        return playersList;
    }

    /**
     * Method that fills the leaderboard.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players the list of players that are to appear in the leaderboard.
     */
    private void fillLeaderboard(final List<PlayerInRound> players) {
        for (final PlayerInRound player : Objects.requireNonNull(players)) {
            this.leaderboardInfo.addRow(new Object[] { player.getName(), player.getChestGems() });
        }
    }

    /**
     * Method that updates the winner label.
     * 
     * @throws NullPointerException if {@link leaderboardCtrl} is null.
     * 
     * @param leaderboardCtrl the leaderboard controller.
     */
    private void updateWinnerLabel(final LeaderboardControllerImpl leaderboardCtrl) {
        this.lblWinner.setText(Objects.requireNonNull(leaderboardCtrl).getWinner().getName()
                + " won with "
                + Objects.requireNonNull(leaderboardCtrl).getWinner().getChestGems()
                + "gems!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {
        final LeaderboardControllerImpl leaderboardCtrl = this.getController(LeaderboardControllerImpl.class);
        fillLeaderboard(leaderboardCtrl.getPlayerList());
        updateWinnerLabel(leaderboardCtrl);
        this.btnHome.addActionListener(e -> {
            leaderboardCtrl.goToHomePage();
        });
    }
}
