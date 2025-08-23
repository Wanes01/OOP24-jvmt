package view.page.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
import utils.CommonUtils;
import view.page.api.SwingPage;

/**
 * Represents the gameplay view of the application.
 * <p>
 * The user interaction is handled using a {@link GameplayController} that
 * specifies an action for every possible user interaction with this page.
 * </p>
 * 
 * @see SwingPage
 * @see GameplayController
 * 
 * @author Filippo Gaggi
 */
public class GameplayView extends SwingPage {
    private static final long serialVersionUID = 1L;
    private static final int COL_GAP = 50;
    private static final int ROW_GAP = 20;
    private static final int CARDS_GAP = 5;
    private static final int CARDS_DIM = 150;
    private static final int CARDS_PER_ROW = 5;
    private static final int SCROLLABLE_HEIGHT = 450;
    private static final int SCROLLABLE_WIDTH = 820;
    private static final int SCROLL_PIXELS = 30;
    private static final int NUMBER_OF_PLAYERS = 8;
    private final Border boxBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
    private final List<PlayerInRound> listPlayers = CommonUtils.generatePlayerInRoundList(NUMBER_OF_PLAYERS);

    /**
     * Main panel of the gameplay view.
     * 
     * @param roundState is the current round state
     */
    public GameplayView(/* RoundStateImpl roundState */) {
        final JPanel gameUi = new JPanel();
        listPlayers.get(4).choose(PlayerChoice.EXIT);
        listPlayers.get(3).choose(PlayerChoice.EXIT);
        listPlayers.get(6).choose(PlayerChoice.EXIT);
        listPlayers.get(0).choose(PlayerChoice.EXIT);
        gameUi.setLayout(new BoxLayout(gameUi, BoxLayout.X_AXIS));
        gameUi.add(gameInfo(boxBorder/* , RoundStateImpl roundState */));
        gameUi.add(Box.createHorizontalStrut(COL_GAP));
        gameUi.add(gameBoard(boxBorder/* , RoundStateImpl roundState */));
        gameUi.add(Box.createHorizontalStrut(COL_GAP));
        gameUi.add(players(boxBorder, listPlayers));

        super.add(gameUi);
    }

    /**
     * Panel which contains the informations of the game's
     * current turn.
     * 
     * @param boxBorder is the border used for the JPanels
     * @param roundState is the current round state
     * 
     * @return the panel itself.
     */
    private JPanel gameInfo(final Border boxBorder/* , final RoundStateImpl roundState */) {
        final JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));


        final JLabel lblRoundTurn = new JLabel("Round n."
            /* + */
            + " | Turno n."
            /* +  */); //TODO add number of round and turn
        lblRoundTurn.setAlignmentX(LEFT_ALIGNMENT);
        gameInfo.add(lblRoundTurn);


        gameInfo.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel playerInfo = new JPanel();
        playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
        playerInfo.setBorder(boxBorder);
        gameInfo.add(playerInfo);

        final JLabel lblPlayerTurn = new JLabel("Turno di:"
            /* + roundState.getRoundPlayersManager().next().getName()*/); //TODO add player's name
        lblPlayerTurn.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(lblPlayerTurn);

        final JLabel lblSackGems = new JLabel("Gemme nella sacca: "
            /* + roundState.getRoundPlayersManager().next().getSackGems()*/); //TODO add player's sack gems
        lblSackGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(lblSackGems);

        final JLabel lblChestGems = new JLabel("Gemme nella cassa: "
            /* + roundState.getRoundPlayersManager().next().getChestGems()*/); //TODO add player's chest gems
        lblChestGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(lblChestGems);

        final JButton btnDraw = new JButton("PESCA");
        playerInfo.add(btnDraw);


        gameInfo.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel gameConditions = new JPanel();
        gameConditions.setLayout(new BoxLayout(gameConditions, BoxLayout.Y_AXIS));
        gameConditions.setBorder(boxBorder);
        gameInfo.add(gameConditions);

        final JLabel lblGameEndCond = new JLabel("Condizione fine round: "
            /* + */); //TODO add game end conditions
        lblGameEndCond.setAlignmentX(LEFT_ALIGNMENT);
        gameConditions.add(lblGameEndCond);

        final JLabel lblGemModifier = new JLabel("Modificatori gemme: "
            /* + */); //TODO add gems modifiers
        lblGemModifier.setAlignmentX(LEFT_ALIGNMENT);
        gameConditions.add(lblGemModifier);


        return gameInfo;
    }

    /**
     * Panel which contains the game board.
     * 
     * @param boxBorder is the border used for the JPanels
     * @param roundState is the current round state
     * 
     * @return the panel itself.
     */
    private JPanel gameBoard(final Border boxBorder/* , final RoundStateImpl roundState */) {
        final JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));


        final JLabel lblDrawnCards = new JLabel("Carte pescate: "
            /* + roundState.getDrawCards().size() */); //TODO add cards drawn
        lblDrawnCards.setAlignmentX(CENTER_ALIGNMENT);
        gameBoard.add(lblDrawnCards);


        gameBoard.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(CARDS_GAP, CARDS_GAP, CARDS_GAP, CARDS_GAP);

        //For testing, to be deleted
        for (int i = 0; i < 35; i++) {
            final ImageIcon icon = new ImageIcon(getClass().getResource("/imageCard/relic/relic.png"));
            final Image image = icon.getImage().getScaledInstance(CARDS_DIM, CARDS_DIM, Image.SCALE_SMOOTH);
            final ImageIcon imageResized = new ImageIcon(image);
            final JLabel labelLogo = new JLabel(imageResized);

            gbc.gridx = i % CARDS_PER_ROW; //column (max 5)
            gbc.gridy = i / CARDS_PER_ROW; //row
            gbc.weightx = 0;
            gbc.weighty = 0;

            cardsContainer.add(labelLogo, gbc);
        }

        gbc.gridx = CARDS_PER_ROW;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        cardsContainer.add(Box.createGlue(), gbc);

        gbc.gridx = 0;
        gbc.gridy = (35 / CARDS_PER_ROW) + 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        cardsContainer.add(Box.createGlue(), gbc);


        final JScrollPane scrollableBoard = new JScrollPane(cardsContainer);
        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.setPreferredSize(new Dimension(SCROLLABLE_WIDTH, SCROLLABLE_HEIGHT));
        scrollableBoard.getVerticalScrollBar().setUnitIncrement(SCROLL_PIXELS);
        gameBoard.add(scrollableBoard);


        gameBoard.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel caveGems = new JPanel();
        caveGems.setBorder(boxBorder);
        gameBoard.add(caveGems);

        final JLabel lblCaveGems = new JLabel("Gemme rimaste nel percorso: "
            //+ roundState.getPathGems()
            + ", Reliquie rimaste nel percorso: "
            /* + roundState.getDrawnRelics().size() */); //TODO add path gems and relics
        lblCaveGems.setAlignmentX(CENTER_ALIGNMENT);
        caveGems.add(lblCaveGems);


        return gameBoard;
    }

    /**
     * Panel which contains the list of active and exited players.
     * 
     * @param boxBorder is the border used for the JPanels
     * @param listPlayers is the list of the players playing
     * 
     * @return the panel itself.
     */
    private JPanel players(final Border boxBorder, final List<PlayerInRound> listPlayers) {
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.Y_AXIS));


        final JLabel lblListActivePlayers = new JLabel("Giocatori in gioco:");
        lblListActivePlayers.setAlignmentX(CENTER_ALIGNMENT);
        playersList.add(lblListActivePlayers);


        playersList.add(Box.createVerticalStrut(ROW_GAP));


        final DefaultListModel<String> activePlayers = new DefaultListModel<>();
        listPlayers.stream()
            .filter(player -> player.getChoice() == PlayerChoice.STAY)
            .map(PlayerInRound::getName)
            .forEach(activePlayers::addElement);
        final JList<String> activePlayerNamesList = new JList<>(activePlayers);
        activePlayerNamesList.setBorder(boxBorder);
        playersList.add(activePlayerNamesList);


        playersList.add(Box.createVerticalStrut(ROW_GAP));


        final JLabel lblListExitedPlayers = new JLabel("Giocatori usciti:");
        lblListExitedPlayers.setAlignmentX(CENTER_ALIGNMENT);
        playersList.add(lblListExitedPlayers);


        playersList.add(Box.createVerticalStrut(ROW_GAP));


        final DefaultListModel<String> exitedPlayers = new DefaultListModel<>();
        listPlayers.stream()
            .filter(player -> player.getChoice() == PlayerChoice.EXIT)
            .map(PlayerInRound::getName)
            .forEach(exitedPlayers::addElement);
        final JList<String> exitedPlayerNamesList = new JList<>(exitedPlayers);
        exitedPlayerNamesList.setBorder(boxBorder);
        playersList.add(exitedPlayerNamesList);

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
