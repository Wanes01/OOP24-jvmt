package jvmt.view.page.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jvmt.controller.api.GameplayController;
import jvmt.controller.impl.GameplayControllerImpl;
import jvmt.view.page.api.SwingPage;
import jvmt.view.page.utility.HtmlUtils;
import jvmt.view.page.utility.ImageLabel;
import jvmt.view.window.impl.SwingWindow;

/**
 * Represents the gameplay view of the application.
 * The user interaction is handled using a {@link GameplayController} that
 * specifies an action for every possible user interaction with this page.
 * 
 * @see SwingPage
 * @see GameplayController
 * 
 * @author Filippo Gaggi
 */
public class SwingGameplayPage extends SwingPage {

    private static final String TURN_TEXT = "Current turn: ";
    private static final String ROUND_TEXT = "Current round: ";
    private static final String PATH_CARDS_TEXT = "Cards drawn: ";
    private static final String PATH_GEMS_TEXT = "Gems on path: ";
    private static final String RELICS_TEXT = "Reedemable relics: ";
    private static final String PLAYER_NAME_TEXT = "Current player: ";
    private static final String SACK_TEXT = "Sack gems: ";
    private static final String CHEST_TEXT = "Chest gems: ";

    private static final int WAIT_TIME_MILLIS = 1000;

    private static final int CARDS_PER_ROW = 5;
    private static final int SCROLL_PIXELS = 30;
    private static final int MAX_CARDS = 35;
    private static final int MAX_LINE_LENGTH = 14;
    private static final Border BOX_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    /**
     * JLablel containing the round number.
     */
    private final JLabel roundNumber = new JLabel();
    /**
     * JLablel containing the turn number.
     */
    private final JLabel turnNumber = new JLabel();
    /**
     * JLablel containing the number of cards in the path.
     */
    private final JLabel pathCardsNumber = new JLabel();
    /**
     * JLablel containing the number of redeemable relics in the path.
     */
    private final JLabel redeemableRelics = new JLabel();
    /**
     * JLablel containing the number of gems in the path.
     */
    private final JLabel pathGems = new JLabel();
    /**
     * JLablel containing the current player's name.
     */
    private final JLabel playerName = new JLabel();
    /**
     * JLablel containing the current player's sack gems.
     */
    private final JLabel sackGems = new JLabel();
    /**
     * JLablel containing the current player's chest gems.
     */
    private final JLabel chestGems = new JLabel();
    /**
     * JLablel containing the game's end condition description.
     */
    private final JLabel endConditionDescription = new JLabel();
    /**
     * JLablel containing the game's gem modifier description.
     */
    private final JLabel gemModifierDescription = new JLabel();
    /**
     * JButton for drawing a card.
     */
    private final JButton drawBtn = new JButton("DRAW");
    /**
     * JPanel that contains the path info.
     */
    private final JPanel pathInfo = new JPanel();
    /**
     * JPanel that contains the cards images.
     */
    private JPanel cardsContainer = new JPanel();
    /**
     * List of PlayerInRound that contain the active players.
     */
    private DefaultListModel<String> activePlayers;
    /**
     * List of PlayerInRound that contain the exited players.
     */
    private DefaultListModel<String> exitedPlayers;
    /**
     * SwingWindow that represents the main application window.
     */
    private final SwingWindow toBlockWindow;
    /**
     * The GridBagContraints that contains the grid's informations.
     */
    private GridBagConstraints gbc;

    /**
     * Main panel of the gameplay page.
     * 
     * @param toBlockWindow the main application window.
     * 
     * @throws NullPointerException if @param toBlockWindow is null.
     */
    @SuppressFBWarnings(value = { "EI_EXPOSE_REP",
            "EI_EXPOSE_REP2" }, justification = "The mutable window is part of the view design")
    public SwingGameplayPage(final SwingWindow toBlockWindow) {
        Objects.requireNonNull(toBlockWindow);
        this.toBlockWindow = toBlockWindow;
        super.getPanel().setLayout(new BorderLayout());

        final JPanel pathCards = new JPanel();
        super.getPanel().add(pathCards, BorderLayout.NORTH);
        pathCards.add(this.pathCardsNumber);

        super.getPanel().add(gameInfo(BOX_BORDER), BorderLayout.WEST);
        super.getPanel().add(gameBoard(), BorderLayout.CENTER);
        super.getPanel().add(players(BOX_BORDER), BorderLayout.EAST);

        this.pathInfo.setBorder(BOX_BORDER);
        super.getPanel().add(this.pathInfo, BorderLayout.SOUTH);

        this.pathInfo.add(this.pathGems);
        this.pathInfo.add(this.redeemableRelics);
    }

    /**
     * Panel which contains the informations of the game's current turn and player
     * and the button for drawing a card.
     * 
     * @param boxBorder the border used for the JPanels.
     * 
     * @throws NullPointerException if @param boxBorder is null.
     * 
     * @return the panel itself.
     */
    private JPanel gameInfo(final Border boxBorder) {
        Objects.requireNonNull(boxBorder);
        final JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));

        // Round info panel.
        final JPanel roundTurnInfo = new JPanel();
        roundTurnInfo.setLayout(new BoxLayout(roundTurnInfo, BoxLayout.Y_AXIS));
        roundTurnInfo.setBorder(boxBorder);
        gameInfo.add(roundTurnInfo);

        // Round info panel elements.
        this.roundNumber.setAlignmentX(LEFT_ALIGNMENT);
        roundTurnInfo.add(this.roundNumber);
        this.turnNumber.setAlignmentX(LEFT_ALIGNMENT);
        roundTurnInfo.add(this.turnNumber);

        // Player info panel.
        final JPanel playerInfo = new JPanel();
        playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
        playerInfo.setBorder(boxBorder);
        gameInfo.add(playerInfo);

        // Player info panel elements.
        this.playerName.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.playerName);
        this.sackGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.sackGems);
        this.chestGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.chestGems);
        playerInfo.add(this.drawBtn);

        // Game conditions panel.
        final JPanel gameConditions = new JPanel();
        gameConditions.setLayout(new BoxLayout(gameConditions, BoxLayout.Y_AXIS));
        gameConditions.setBorder(boxBorder);
        gameInfo.add(gameConditions);

        // Game conditions panel elements.
        this.endConditionDescription.setAlignmentX(LEFT_ALIGNMENT);
        gameConditions.add(this.endConditionDescription);
        this.gemModifierDescription.setAlignmentX(LEFT_ALIGNMENT);
        gameConditions.add(this.gemModifierDescription);

        return gameInfo;
    }

    /**
     * Panel which contains the game board.
     * Every time a card is drawn it is added in the game board.
     * 
     * @return the panel itself.
     */
    private JPanel gameBoard() {
        final JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));

        // Cards container.
        this.cardsContainer = new JPanel();
        this.cardsContainer.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = CARDS_PER_ROW;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        this.cardsContainer.add(Box.createGlue(), gbc);

        gbc.gridx = 0;
        gbc.gridy = MAX_CARDS / CARDS_PER_ROW;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        this.cardsContainer.add(Box.createGlue(), gbc);

        final JScrollPane scrollableBoard = new JScrollPane(this.cardsContainer);
        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.getVerticalScrollBar().setUnitIncrement(SCROLL_PIXELS);
        gameBoard.add(scrollableBoard);

        return gameBoard;
    }

    /**
     * Panel which contains the list of active and exited players.
     * 
     * @param boxBorder the border used for the JPanels.
     * 
     * @throws NullPointerException if @param boxBorder is null.
     * 
     * @return the panel itself.
     */
    private JPanel players(final Border boxBorder) {
        Objects.requireNonNull(boxBorder);
        final JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.Y_AXIS));

        final JLabel lblListActivePlayers = new JLabel("Players in game:");
        lblListActivePlayers.setAlignmentX(LEFT_ALIGNMENT);
        playersList.add(lblListActivePlayers);

        // List of active players.
        this.activePlayers = new DefaultListModel<>();
        final JList<String> activePlayerNamesList = new JList<>(this.activePlayers);
        activePlayerNamesList.setBorder(boxBorder);
        playersList.add(activePlayerNamesList);

        final JLabel lblListExitedPlayers = new JLabel("Exited players:");
        lblListExitedPlayers.setAlignmentX(LEFT_ALIGNMENT);
        playersList.add(lblListExitedPlayers);

        // List of exited players.
        this.exitedPlayers = new DefaultListModel<>();
        final JList<String> exitedPlayerNamesList = new JList<>(this.exitedPlayers);
        exitedPlayerNamesList.setBorder(boxBorder);
        playersList.add(exitedPlayerNamesList);

        return playersList;
    }

    /**
     * Method for removing the cards from the game board.
     */
    public void cleanGameboard() {
        final Component[] components = this.cardsContainer.getComponents();
        for (final Component comp : components) {
            // Removes each JLabel inside cards container.
            if (comp instanceof JLabel) {
                this.cardsContainer.remove(comp);
            }
        }
        this.cardsContainer.revalidate();
        this.cardsContainer.repaint();
    }

    /**
     * Method that adds the image of the drawn card in the cards container.
     * 
     * @param gameplayCtrl the gameplay controller.
     * 
     * @throws NullPointerException if @param gameplayCtrl is null.
     */
    private void addCardToPath(final GameplayControllerImpl gameplayCtrl) {
        Objects.requireNonNull(gameplayCtrl);
        final int cardSize = (this.cardsContainer.getWidth() - 1) / CARDS_PER_ROW;
        final JLabel labelLogo;
        final Optional<Image> img = gameplayCtrl.getDrawnCardImage();

        if (img.isPresent()) {
            final Image scaledImage = img.get().getScaledInstance(cardSize, cardSize, Image.SCALE_SMOOTH);
            labelLogo = new ImageLabel(scaledImage);
            labelLogo.setPreferredSize(new Dimension(cardSize, cardSize));
        } else {
            labelLogo = new JLabel("Image not found.");
        }

        labelLogo.setSize(new Dimension(cardSize, cardSize));

        // X position in the grid of the card.
        this.gbc.gridx = (gameplayCtrl.getDrawnCardsNumber() - 1) % CARDS_PER_ROW;
        // Y position in the grid of the card.
        this.gbc.gridy = (gameplayCtrl.getDrawnCardsNumber() - 1) / CARDS_PER_ROW;
        this.gbc.weightx = 0;
        this.gbc.weighty = 0;

        this.cardsContainer.add(labelLogo, this.gbc);
    }

    /**
     * Method that populates the active players list in the GUI.
     * 
     * @param players list of the names of the active players to add.
     * 
     * @throws NullPointerException if @param players is null.
     */
    private void addActivePlayers(final List<String> players) {
        Objects.requireNonNull(players);
        this.activePlayers.clear();
        for (final String activePlayer : players) {
            this.activePlayers.addElement(activePlayer);
        }
    }

    /**
     * Method that populates the exited players list in the GUI.
     * 
     * @param players list of the names of the exited players to add.
     * 
     * @throws NullPointerException if @param players is null.
     */
    private void addExitedPlayers(final List<String> players) {
        Objects.requireNonNull(players);
        this.exitedPlayers.clear();
        for (final String exitedPlayer : players) {
            this.exitedPlayers.addElement(exitedPlayer);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        final GameplayControllerImpl gameplayCtrl = this.getController(GameplayControllerImpl.class);

        this.playerName.setText("<html>" + PLAYER_NAME_TEXT + "<br>" + gameplayCtrl.getCurrentPlayerName() + "</html>");
        this.sackGems.setText(SACK_TEXT + gameplayCtrl.getCurrentPlayerSackGems());
        this.chestGems.setText(CHEST_TEXT + gameplayCtrl.getCurrentPlayerChestGems());
        this.roundNumber.setText(ROUND_TEXT + gameplayCtrl.getCurrentRoundNumber());
        this.turnNumber.setText(TURN_TEXT + gameplayCtrl.getCurrentTurnNumber());
        this.redeemableRelics.setText(RELICS_TEXT + gameplayCtrl.getRedeemableRelicsNumber());
        this.pathGems.setText(PATH_GEMS_TEXT + gameplayCtrl.getPathGems());
        this.pathCardsNumber.setText(PATH_CARDS_TEXT + gameplayCtrl.getDrawnCardsNumber());
        addActivePlayers(gameplayCtrl.getActivePlayersNames());
        addExitedPlayers(gameplayCtrl.getExitedPlayersNames());
        super.refresh();
    }

    /**
     * Method for making the CPUs take choices without pressing the draw button
     * if there are any.
     * 
     * @param gameplayCtrl the gameplay controller.
     * 
     * @throws NullPointerException if @param gameplayCtrl is null.
     */
    private void cpuAutoplay(final GameplayControllerImpl gameplayCtrl) {
        Objects.requireNonNull(gameplayCtrl);
        if (gameplayCtrl.isCurrentPlayerACpu()) {
            final ActionListener al = this.drawBtn.getActionListeners()[0];
            // Timer for delaying the CPU's draw.
            this.drawBtn.setEnabled(false);
            final Timer timer = new Timer(WAIT_TIME_MILLIS, ev -> {
                al.actionPerformed(ev);
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            this.drawBtn.setEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {
        final GameplayControllerImpl ctrl = this.getController(GameplayControllerImpl.class);
        this.endConditionDescription
                .setText(HtmlUtils.wrapTextHTML("END CONDITION: " + ctrl.getEndConditionDescription() + ".",
                        MAX_LINE_LENGTH));
        this.gemModifierDescription
                .setText(HtmlUtils.wrapTextHTML("GEM MODIFIER: " + ctrl.getGemModifierDescrition() + ".",
                        MAX_LINE_LENGTH));
        this.refresh();
        // Resets action listeners.
        for (final ActionListener al : this.drawBtn.getActionListeners()) {
            this.drawBtn.removeActionListener(al);
        }
        this.drawBtn.setEnabled(true);
        this.drawBtn.addActionListener(e -> {
            // Execution of the draw phase.
            ctrl.executeDrawPhase();

            // Addition of the card image in the cards container.
            this.addCardToPath(ctrl);

            // Execution of the decision phase.
            ctrl.executeDecisionPhase(this.toBlockWindow);

            // Check if the round is over.
            if (!ctrl.canRoundContinue()) {
                ctrl.endRound();
                JOptionPane.showMessageDialog(
                        this.getPanel(),
                        "The round is over!",
                        "Round end info",
                        JOptionPane.INFORMATION_MESSAGE);
                this.cleanGameboard();
            }

            // Check if the game is over.
            if (!ctrl.canGameContinue()) {
                JOptionPane.showMessageDialog(
                        this.getPanel(),
                        "The game is over!",
                        "Game end info",
                        JOptionPane.INFORMATION_MESSAGE);
                this.cleanGameboard();
                ctrl.goToLeaderboard();
                return;
            }

            // Advance to the next turn.
            ctrl.advance();

            // CPU autoclick.
            this.cpuAutoplay(ctrl);
        });

        // CPU autoclick in case a CPU is the first player in a game.
        this.cpuAutoplay(ctrl);

        // Resize handler for the cards container
        this.cardsContainer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                SwingUtilities.invokeLater(SwingGameplayPage.this::resizeCards);
            }
        });
    }

    /**
     * Resizes the cards in the card container when the container gets resized.
     */
    private void resizeCards() {
        final int cardSize = (this.cardsContainer.getWidth() - 1) / CARDS_PER_ROW;
        for (final Component comp : this.cardsContainer.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setPreferredSize(new Dimension(cardSize, cardSize));
                comp.setSize(new Dimension(cardSize, cardSize));
            }
        }

        this.cardsContainer.revalidate();
        this.cardsContainer.repaint();
    }
}
