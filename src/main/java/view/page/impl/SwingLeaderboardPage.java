package view.page.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import controller.api.LeaderboardController;
import controller.impl.LeaderboardControllerImpl;
import view.page.api.SwingPage;
import view.page.utility.Pair;

/**
 * Represents the leaderboard that appears at the end of the game.
 * The user interaction is handled using a {@link LeaderboardController} that
 * specifies an action for every possible user interaction with this page.
 * 
 * @see SwingPage
 * @see LeaderboardController
 * 
 * @author Filippo Gaggi
 */
public class SwingLeaderboardPage extends SwingPage {
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE_WINNER = 30;
    private static final int FONT_SIZE_HOME_BUTTON = 30;
    private static final int CELL_HEIGHT_MARGIN = 3;
    private static final Dimension SCROLLABLE_DIM = new Dimension(400, 200);
    private static final Border BOX_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
    private final JLabel lblWinner;
    private final JButton btnHome;
    private DefaultTableModel leaderboardInfo;

    /**
     * Main panel of the leaderboard page.
     * It has a scrollable leaderboard and a button that redirects to the home page.
     */
    public SwingLeaderboardPage() {
        final JPanel leaderboardUi = new JPanel();
        final Font fontWinner = new Font("Arial", Font.PLAIN, FONT_SIZE_WINNER);
        final Font fontHomeButton = new Font("Arial", Font.PLAIN, FONT_SIZE_HOME_BUTTON);
        leaderboardUi.setLayout(new BoxLayout(leaderboardUi, BoxLayout.Y_AXIS));

        //Winner label.
        this.lblWinner = new JLabel("");
        this.lblWinner.setAlignmentX(CENTER_ALIGNMENT);
        this.lblWinner.setFont(fontWinner);
        this.lblWinner.setBorder(BOX_BORDER);
        leaderboardUi.add(this.lblWinner);

        final JLabel lblleaderboard = new JLabel("Leaderboard");
        leaderboardUi.add(lblleaderboard);

        //Leaderboard.
        leaderboardUi.add(playersList());
        lblleaderboard.setAlignmentX(CENTER_ALIGNMENT);

        //Go to home page button.
        this.btnHome = new JButton("Go back to Home page");
        this.btnHome.setFont(fontHomeButton);
        this.btnHome.setAlignmentX(CENTER_ALIGNMENT);
        leaderboardUi.add(this.btnHome);

        super.add(leaderboardUi);
    }

    /**
     * Panel which contains the leaderboard itself.
     * 
     * @return  the panel itself.
     */
    private JPanel playersList() {
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.X_AXIS));

        //Making the cells in the table uneditable.
        this.leaderboardInfo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        this.leaderboardInfo.setColumnIdentifiers(new Object[] { "Name", "Score" });
        final JTable table = new JTable(leaderboardInfo);
        final JScrollPane scrollableBoard = new JScrollPane(table);

        //Adapting the cells' height to the text inside.
        final Font font = table.getFont();
        final FontMetrics fm = table.getFontMetrics(font);
        final int rowHeight = fm.getHeight();
        table.setRowHeight(rowHeight + CELL_HEIGHT_MARGIN);

        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.setPreferredSize(SCROLLABLE_DIM);
        playersList.add(scrollableBoard);

        return playersList;
    }

    /**
     * Method that fills the leaderboard.
     * 
     * @throws NullPointerException if {@link scores} is null.
     * 
     * @param scores    the list of pair of players and their scores.
     */
    private void fillLeaderboard(final List<Pair<String, Integer>> scores) {
        Objects.requireNonNull(scores);
        //Cleaning the leaderboard in case there was another already done.
        this.leaderboardInfo.setRowCount(0);
        for (final var score : scores) {
            this.leaderboardInfo.addRow(new Object[] { score.first(), score.second() });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {
        final LeaderboardController ctrl = this.getController(LeaderboardControllerImpl.class);

        //Setting the winner player's name in the winner lable.
        this.lblWinner.setText("WINNER: " + ctrl.getWinner());

        //Filling the leaderboard with the results.
        this.fillLeaderboard(ctrl.getSortedPlayerScores());

        this.btnHome.addActionListener(e -> ctrl.goToHomePage());
    }
}
