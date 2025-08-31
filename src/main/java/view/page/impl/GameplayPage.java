package view.page.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;
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

import controller.api.GameplayController;
import controller.impl.GameplayControllerImpl;
import model.player.impl.PlayerInRound;
import view.page.api.SwingPage;
import view.window.impl.SwingWindow;

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
public class GameplayPage extends SwingPage {
    private static final long serialVersionUID = 1L;
    private static final int COL_GAP = 50;
    private static final int ROW_GAP = 20;
    private static final int CARDS_GAP = 5;
    private static final int CARDS_DIM = 150;
    private static final int CARDS_PER_ROW = 5;
    private static final Dimension SCROLLABLE_DIM = new Dimension(820, 450);
    private static final int SCROLL_PIXELS = 30;
    private static final int MAX_CARDS = 35;
    private static final Border BOX_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
    private final SwingWindow window;
    private JButton btnDraw;
    private JLabel lblRound;
    private JLabel lblTurn;
    private JLabel lblPlayerTurn;
    private JLabel lblSackGems;
    private JLabel lblChestGems;
    private JLabel lblGameEndCond;
    private JLabel lblGemModifier;
    private JLabel lblDrawnCards;
    private JLabel lblPathGems;
    private JLabel lblPathRelics;
    private DefaultListModel<String> activePlayers;
    private DefaultListModel<String> exitedPlayers;
    private JPanel cardsContainer;
    private GridBagConstraints gbc;

    /**
     * Main panel of the gameplay page.
     * 
     * @throws NullPointerException if {@link winDim} is null.
     * @throws NullPointerException if {@link window} is null.
     * 
     * @param winDim the main application window.
     * @param window the window's dimension.
     */
    public GameplayPage(final Dimension winDim, final SwingWindow window) {
        super(Objects.requireNonNull(winDim));
        this.window = Objects.requireNonNull(window);
        final JPanel gameUi = new JPanel();
        gameUi.setLayout(new BoxLayout(gameUi, BoxLayout.X_AXIS));
        gameUi.add(gameInfo(BOX_BORDER));
        gameUi.add(Box.createHorizontalStrut(COL_GAP));
        gameUi.add(gameBoard(BOX_BORDER));
        gameUi.add(Box.createHorizontalStrut(COL_GAP));
        gameUi.add(players(BOX_BORDER));

        super.add(gameUi);
    }

    /**
     * Panel which contains the informations of the game's current turn and player
     * and the button
     * for drawing a card.
     * 
     * @throws NullPointerException if {@link boxBorder} is null.
     * 
     * @param boxBorder the border used for the JPanels.
     * 
     * @return the panel itself.
     */
    private JPanel gameInfo(final Border boxBorder) {
        final JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));

        final JPanel roundTurnInfo = new JPanel();
        roundTurnInfo.setLayout(new BoxLayout(roundTurnInfo, BoxLayout.Y_AXIS));
        roundTurnInfo.setBorder(Objects.requireNonNull(boxBorder));
        gameInfo.add(roundTurnInfo);

        this.lblRound = new JLabel("Round n.");
        lblRound.setAlignmentX(LEFT_ALIGNMENT);
        roundTurnInfo.add(this.lblRound);

        this.lblTurn = new JLabel("Turn n.");
        lblTurn.setAlignmentX(LEFT_ALIGNMENT);
        roundTurnInfo.add(this.lblTurn);

        gameInfo.add(Box.createVerticalStrut(ROW_GAP));

        final JPanel playerInfo = new JPanel();
        playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
        playerInfo.setBorder(Objects.requireNonNull(boxBorder));
        gameInfo.add(playerInfo);

        this.lblPlayerTurn = new JLabel("Turn of: ");
        lblPlayerTurn.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.lblPlayerTurn);

        this.lblSackGems = new JLabel("Gems in the sack: ");
        lblSackGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.lblSackGems);

        this.lblChestGems = new JLabel("Gems in the chest: ");
        lblChestGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.lblChestGems);

        this.btnDraw = new JButton("DRAW");
        playerInfo.add(this.btnDraw);

        gameInfo.add(Box.createVerticalStrut(ROW_GAP));

        final JPanel gameConditions = new JPanel();
        gameConditions.setLayout(new BoxLayout(gameConditions, BoxLayout.Y_AXIS));
        gameConditions.setBorder(Objects.requireNonNull(boxBorder));
        gameInfo.add(gameConditions);

        this.lblGameEndCond = new JLabel("End round condition: ");
        lblGameEndCond.setAlignmentX(LEFT_ALIGNMENT);
        gameConditions.add(this.lblGameEndCond);

        this.lblGemModifier = new JLabel("Gem modifier: ");
        lblGemModifier.setAlignmentX(LEFT_ALIGNMENT);
        gameConditions.add(this.lblGemModifier);

        return gameInfo;
    }

    /**
     * Panel which contains the game board.
     * Every time a card is drawn it is added in the game board.
     * 
     * @throws NullPointerException if {@link boxBorder} is null.
     * 
     * @param boxBorder the border used for the JPanels.
     * 
     * @return the panel itself.
     */
    private JPanel gameBoard(final Border boxBorder) {
        final JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));

        this.lblDrawnCards = new JLabel("Cards drawn: ");
        this.lblDrawnCards.setAlignmentX(CENTER_ALIGNMENT);
        gameBoard.add(this.lblDrawnCards);

        gameBoard.add(Box.createVerticalStrut(ROW_GAP));

        this.cardsContainer = new JPanel();
        cardsContainer.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(CARDS_GAP, CARDS_GAP, CARDS_GAP, CARDS_GAP);
        gbc.gridx = CARDS_PER_ROW;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        cardsContainer.add(Box.createGlue(), gbc);

        gbc.gridx = 0;
        gbc.gridy = (MAX_CARDS / CARDS_PER_ROW) + 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        cardsContainer.add(Box.createGlue(), gbc);

        final JScrollPane scrollableBoard = new JScrollPane(cardsContainer);
        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.setPreferredSize(SCROLLABLE_DIM);
        scrollableBoard.getVerticalScrollBar().setUnitIncrement(SCROLL_PIXELS);
        gameBoard.add(scrollableBoard);

        gameBoard.add(Box.createVerticalStrut(ROW_GAP));

        final JPanel pathGems = new JPanel();
        pathGems.setBorder(Objects.requireNonNull(boxBorder));
        gameBoard.add(pathGems);

        this.lblPathGems = new JLabel("Gems in the path: ");
        pathGems.add(this.lblPathGems);

        this.lblPathRelics = new JLabel(", Relics in the path: ");
        pathGems.add(this.lblPathRelics);

        return gameBoard;
    }

    /**
     * Panel which contains the list of active and exited players.
     * 
     * @throws NullPointerException if {@link boxBorder} is null.
     * 
     * @param boxBorder the border used for the JPanels.
     * 
     * @return the panel itself.
     */
    private JPanel players(final Border boxBorder) {
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.Y_AXIS));

        final JLabel lblListActivePlayers = new JLabel("Players in game:");
        lblListActivePlayers.setAlignmentX(CENTER_ALIGNMENT);
        playersList.add(lblListActivePlayers);

        playersList.add(Box.createVerticalStrut(ROW_GAP));

        this.activePlayers = new DefaultListModel<>();
        final JList<String> activePlayerNamesList = new JList<>(this.activePlayers);
        activePlayerNamesList.setBorder(Objects.requireNonNull(boxBorder));
        playersList.add(activePlayerNamesList);

        playersList.add(Box.createVerticalStrut(ROW_GAP));

        final JLabel lblListExitedPlayers = new JLabel("Exited players:");
        lblListExitedPlayers.setAlignmentX(CENTER_ALIGNMENT);
        playersList.add(lblListExitedPlayers);

        playersList.add(Box.createVerticalStrut(ROW_GAP));

        this.exitedPlayers = new DefaultListModel<>();
        final JList<String> exitedPlayerNamesList = new JList<>(this.exitedPlayers);
        exitedPlayerNamesList.setBorder(Objects.requireNonNull(boxBorder));
        playersList.add(exitedPlayerNamesList);

        return playersList;
    }

    /**
     * Method that draws a card from the deck and adds it in the path.
     * 
     * @throws NullPointerException if {@link gameplayCtrl} is null.
     * 
     * @param gameplayCtrl the gameplay controller.
     */
    private void addCardToPath(final GameplayControllerImpl gameplayCtrl) {
        Objects.requireNonNull(gameplayCtrl).drawPhase();
        JLabel labelLogo;
        try {
            final Image image = ImageIO.read(Objects.requireNonNull(gameplayCtrl).getDrawnCard().getImagePath());
            final Image scaledImage = image.getScaledInstance(CARDS_DIM, CARDS_DIM, Image.SCALE_SMOOTH);
            final ImageIcon imageResized = new ImageIcon(scaledImage);
            labelLogo = new JLabel(imageResized);
        } catch (final IOException e) {
            labelLogo = new JLabel("Logo image not found");
        }

        this.gbc.gridx = Objects.requireNonNull(gameplayCtrl).getDrawnCardsNumber() + 1 % CARDS_PER_ROW; // column (max 5)
        this.gbc.gridy = Objects.requireNonNull(gameplayCtrl).getDrawnCardsNumber() + 1 / CARDS_PER_ROW; // row
        this.gbc.weightx = 0;
        this.gbc.weighty = 0;

        this.cardsContainer.add(labelLogo, this.gbc);

        updateDraw(Objects.requireNonNull(gameplayCtrl));
    }

    /**
     * Method that populates the active players list in the GUI.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players the active players to add.
     */
    private void addActivePlayers(final List<PlayerInRound> players) {
        for (final PlayerInRound activePlayer : Objects.requireNonNull(players)) {
            this.activePlayers.addElement(activePlayer.getName());
        }
    }

    /**
     * Method that populates the exited players list in the GUI.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players the exited players to add.
     */
    private void addExitedPlayers(final List<PlayerInRound> players) {
        for (final PlayerInRound exitedPlayer : Objects.requireNonNull(players)) {
            this.exitedPlayers.addElement(exitedPlayer.getName());
        }
    }

    /**
     * Method for updating the game page's info after drawing a card.
     * 
     *@throws NullPointerException if {@link gameplayCtrl} is null.
     * 
     * @param gameplayCtrl the gameplay controller.
     */
    private void updateDraw(final GameplayControllerImpl gameplayCtrl) {
        this.lblSackGems
            .setText("Gems in the sack: " + Objects.requireNonNull(gameplayCtrl).getCurrentPlayerSackGems());
        this.lblChestGems
            .setText("Gems in the chest: " + Objects.requireNonNull(gameplayCtrl).getCurrentPlayerChestGems());
        this.lblDrawnCards.setText("Cards drawn: " + Objects.requireNonNull(gameplayCtrl).getDrawnCardsNumber());
        this.lblPathGems.setText("Gems in the path: " + Objects.requireNonNull(gameplayCtrl).getPathGems());
        this.lblPathRelics.setText(", Relics in the path: " + Objects.requireNonNull(gameplayCtrl).getPathRelics());
    }

    /**
     * Method for updating some game page's info at the end of the turns.
     * 
     * @throws NullPointerException if {@link gameplayCtrl} is null.
     * 
     * @param gameplayCtrl the gameplay controller.
     */
    private void updateInfo(final GameplayControllerImpl gameplayCtrl) {
        this.lblPlayerTurn.setText("Turn of: " + Objects.requireNonNull(gameplayCtrl).getCurrentPlayerName());
        this.lblTurn.setText("Turn n." + Objects.requireNonNull(gameplayCtrl).getGame().getCurrentRoundNumber());
        this.lblRound.setText("Round n." + Objects.requireNonNull(gameplayCtrl).getGame().getCurrentRoundNumber());
        this.lblSackGems
            .setText("Gems in the sack: " + Objects.requireNonNull(gameplayCtrl).getCurrentPlayerSackGems());
        this.lblChestGems
            .setText("Gems in the chest: " + Objects.requireNonNull(gameplayCtrl).getCurrentPlayerChestGems());
        addActivePlayers(Objects.requireNonNull(gameplayCtrl).getActivePlayersList());
        addExitedPlayers(Objects.requireNonNull(gameplayCtrl).getExitedPlayersList());
    }

    /**
     * Method for initializing the game page's lables.
     * 
     * @throws NullPointerException if {@link gameplayCtrl} is null.
     * 
     * @param gameplayCtrl the gameplay controller.
     */
    private void initializeInfo(final GameplayControllerImpl gameplayCtrl) {
        updateInfo(Objects.requireNonNull(gameplayCtrl));
        updateDraw(Objects.requireNonNull(gameplayCtrl));
        this.lblGameEndCond
            .setText("End round condition: " + Objects.requireNonNull(gameplayCtrl).getGameEndCondition());
        this.lblGemModifier.setText("Gem modifier: " + Objects.requireNonNull(gameplayCtrl).getGemModifier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {
        final GameplayControllerImpl gameplayCtrl = this.getController(GameplayControllerImpl.class);
        initializeInfo(gameplayCtrl);

        this.btnDraw.addActionListener(e -> {

            addCardToPath(gameplayCtrl);

            final Set<PlayerInRound> exitingPlayers = gameplayCtrl.choicePhase(this.window);

            gameplayCtrl.advanceTurn(exitingPlayers);

            if (gameplayCtrl.isGameOver()) {
                gameplayCtrl.goToLeaderboardPage();
            }

            updateInfo(gameplayCtrl);
        });
    }
}
