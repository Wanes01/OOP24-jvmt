package view.page.impl;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.player.impl.PlayerInRound;
import view.page.api.SwingPage;

public class LeaderboardView extends SwingPage{
    private static final long serialVersionUID = 1L;
    final static int FONT_SIZE_VICTOR = 30;
    final static int COL_GAP = 10;
    final Border boxBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    public LeaderboardView(List<PlayerInRound> players) {
        final JPanel leaderboardUi = new JPanel();
        final Font fontVictor = new Font("Arial", Font.PLAIN, FONT_SIZE_VICTOR);
        leaderboardUi.setLayout(new BoxLayout(leaderboardUi, BoxLayout.Y_AXIS));

        final JLabel lblVictor = new JLabel(players.get(0).getName()
            + " ha vinto con "
            + players.get(0).getChestGems()
            + " punti!");
        final JLabel lblleaderboard = new JLabel("Classifica");
        lblleaderboard.setAlignmentX(CENTER_ALIGNMENT);
        lblVictor.setAlignmentX(CENTER_ALIGNMENT);
        lblVictor.setFont(fontVictor);
        lblVictor.setBorder(boxBorder);
        leaderboardUi.add(lblVictor);
        leaderboardUi.add(Box.createVerticalStrut(COL_GAP));
        
        leaderboardUi.add(lblleaderboard);
        leaderboardUi.add(playersList(players, boxBorder));

        this.add(leaderboardUi);
    }

    private JPanel playersList(List<PlayerInRound> players, Border boxBorder) {
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.X_AXIS));
        playersList.setBorder(boxBorder);


        final DefaultListModel<String> gamePlayers = new DefaultListModel<>();
        players.stream()
            .map(PlayerInRound::getName)
            .forEach(gamePlayers::addElement);
        final JList<String> activePlayerNamesList = new JList<>(gamePlayers);
        playersList.add(activePlayerNamesList);

        final DefaultListModel<Integer> scorePlayers = new DefaultListModel<>();
        players.stream()
            .map(PlayerInRound::getChestGems)
            .forEach(scorePlayers::addElement);
        final JList<Integer> scorePlayersList = new JList<>(scorePlayers);
        playersList.add(scorePlayersList);

        return playersList;
    }

    @Override
    protected void setHandlers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHandlers'");
    }
    
}
