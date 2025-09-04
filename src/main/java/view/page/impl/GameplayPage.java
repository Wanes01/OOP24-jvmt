package view.page.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
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
import javax.swing.Timer;
import javax.swing.border.Border;

import controller.api.GameplayController;
import controller.impl.GameplayControllerImpl;
import view.page.api.SwingPage;
import view.page.utility.HtmlUtils;
import view.page.utility.ImageLabel;
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

    private static final String TURN_TEXT = "Current turn: ";
    private static final String ROUND_TEXT = "Current round: ";
    private static final String PATH_CARDS_TEXT = "Cards drawn: ";
    private static final String PATH_GEMS_TEXT = "Gems on path: ";
    private static final String RELICS_TEXT = "Reedemable relics: ";
    private static final String PLAYER_NAME_TEXT = "Current player: ";
    private static final String SACK_TEXT = "Sack gems: ";
    private static final String CHEST_TEXT = "Chest gems: ";

    private static final int WAIT_TIME_MILLIS = 1000;

    private static final long serialVersionUID = 1L;
    private static final int CARDS_GAP = 2;
    private static final int CARDS_PER_ROW = 5;
    private static final int SCROLL_PIXELS = 30;
    private static final int MAX_CARDS = 35;
    private static final int MAX_LINE_LENGTH = 14;
    private static final Border BOX_BORDER = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

    private final JLabel roundNumber = new JLabel();
    private final JLabel turnNumber = new JLabel();
    private final JLabel pathCardsNumber = new JLabel();
    private final JLabel redeemableRelics = new JLabel();
    private final JLabel pathGems = new JLabel();
    private final JLabel playerName = new JLabel();
    private final JLabel sackGems = new JLabel();
    private final JLabel chestGems = new JLabel();
    private final JLabel endConditionDescription = new JLabel();
    private final JLabel gemModifierDescription = new JLabel();
    private DefaultListModel<String> activePlayers;
    private DefaultListModel<String> exitedPlayers;

    private final JButton drawBtn = new JButton("DRAW");
    private final SwingWindow toBlockWindow;
    private final JPanel pathInfo = new JPanel();
    private JPanel cardsContainer = new JPanel();
    private GridBagConstraints gbc;

    /**
     * Main panel of the gameplay page.
     * 
     * @throws NullPointerException if {@link winDim} is null.
     * @throws NullPointerException if {@link window} is null.
     * 
     * @param winDim the window's dimension.
     * @param toBlockWindow the main application window.
     */
    public GameplayPage(final Dimension winDim, final SwingWindow toBlockWindow) {
        super(Objects.requireNonNull(winDim));
        this.toBlockWindow = Objects.requireNonNull(toBlockWindow);
        super.setLayout(new BorderLayout());

        final JPanel pathCards = new JPanel();
        super.add(pathCards, BorderLayout.NORTH);
        pathCards.add(this.pathCardsNumber);

        super.add(gameInfo(BOX_BORDER), BorderLayout.WEST);
        super.add(gameBoard(), BorderLayout.CENTER);
        super.add(players(BOX_BORDER), BorderLayout.EAST);

        this.pathInfo.setBorder(BOX_BORDER);
        super.add(this.pathInfo, BorderLayout.SOUTH);

        this.pathInfo.add(this.pathGems);
        this.pathInfo.add(this.redeemableRelics);
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

        this.roundNumber.setAlignmentX(LEFT_ALIGNMENT);
        roundTurnInfo.add(this.roundNumber);

        this.turnNumber.setAlignmentX(LEFT_ALIGNMENT);
        roundTurnInfo.add(this.turnNumber);

        final JPanel playerInfo = new JPanel();
        playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
        playerInfo.setBorder(Objects.requireNonNull(boxBorder));
        gameInfo.add(playerInfo);

        this.playerName.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.playerName);

        this.sackGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.sackGems);

        this.chestGems.setAlignmentX(LEFT_ALIGNMENT);
        playerInfo.add(this.chestGems);

        playerInfo.add(this.drawBtn);

        final JPanel gameConditions = new JPanel();
        gameConditions.setLayout(new BoxLayout(gameConditions, BoxLayout.Y_AXIS));
        gameConditions.setBorder(Objects.requireNonNull(boxBorder));
        gameInfo.add(gameConditions);

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

        this.cardsContainer = new JPanel();
        this.cardsContainer.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(CARDS_GAP, CARDS_GAP, CARDS_GAP, CARDS_GAP);
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
        lblListActivePlayers.setAlignmentX(LEFT_ALIGNMENT);
        playersList.add(lblListActivePlayers);

        this.activePlayers = new DefaultListModel<>();
        final JList<String> activePlayerNamesList = new JList<>(this.activePlayers);
        activePlayerNamesList.setBorder(Objects.requireNonNull(boxBorder));
        playersList.add(activePlayerNamesList);

        final JLabel lblListExitedPlayers = new JLabel("Exited players:");
        lblListExitedPlayers.setAlignmentX(LEFT_ALIGNMENT);
        playersList.add(lblListExitedPlayers);

        this.exitedPlayers = new DefaultListModel<>();
        final JList<String> exitedPlayerNamesList = new JList<>(this.exitedPlayers);
        exitedPlayerNamesList.setBorder(Objects.requireNonNull(boxBorder));
        playersList.add(exitedPlayerNamesList);

        return playersList;
    }

    /**
     * Method for removing the cards from the game board.
     * It initializes the game page's lables for the next round.
     */
    public void cleanGameboard() {
        final Component[] components = this.cardsContainer.getComponents();
            for (final Component comp : components) {
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
     * @throws NullPointerException if {@link gameplayCtrl} is null.
     * 
     * @param gameplayCtrl the gameplay controller.
     */
    private void addCardToPath(final GameplayControllerImpl gameplayCtrl) {
        final int cardSize = this.cardsContainer.getWidth() / CARDS_PER_ROW - CARDS_GAP * 2;
        final JLabel labelLogo;
        final Optional<Image> img = Objects.requireNonNull(gameplayCtrl).getDrawnCardImage();

        if (img.isPresent()) {
            final Image scaledImage = img.get().getScaledInstance(cardSize, cardSize, Image.SCALE_SMOOTH);
            labelLogo = new ImageLabel(scaledImage);
            labelLogo.setPreferredSize(new Dimension(cardSize, cardSize));
        } else {
            labelLogo = new JLabel("Image not found.");
        }

        labelLogo.setSize(new Dimension(cardSize, cardSize));

        this.gbc.gridx = (Objects.requireNonNull(gameplayCtrl).getDrawnCardsNumber() - 1) % CARDS_PER_ROW; // column (max 5)
        this.gbc.gridy = (Objects.requireNonNull(gameplayCtrl).getDrawnCardsNumber() - 1) / CARDS_PER_ROW; // row
        this.gbc.weightx = 0;
        this.gbc.weighty = 0;

        this.cardsContainer.add(labelLogo, this.gbc);
    }

    /**
     * Method that populates the active players list in the GUI.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players list of the names of the active players to add.
     */
    private void addActivePlayers(final List<String> players) {
        this.activePlayers.clear();
        for (final String activePlayer : Objects.requireNonNull(players)) {
            this.activePlayers.addElement(activePlayer);
        }
    }

    /**
     * Method that populates the exited players list in the GUI.
     * 
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param players list of the names of the exited players to add.
     */
    private void addExitedPlayers(final List<String> players) {
        this.exitedPlayers.clear();
        for (final String exitedPlayer : Objects.requireNonNull(players)) {
            this.exitedPlayers.addElement(exitedPlayer);
        }
    }

    /**
     * Method for making the CPUs take choices without pressing the draw button,
     * if there are any.
     * 
     * @throws NullPointerException if {@link gameplayCtrl} is null.
     * 
     * @param gameplayCtrl the gameplay controller.
     */
    private void cpuAutoplay(final GameplayControllerImpl gameplayCtrl) {
        if (Objects.requireNonNull(gameplayCtrl).isCurrentPlayerACpu()) {
            final Timer timer = new Timer(WAIT_TIME_MILLIS, ev -> this.drawBtn.doClick());
            timer.setRepeats(false);
            timer.start();
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
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {
        final GameplayControllerImpl ctrl = this.getController(GameplayControllerImpl.class);
        this.endConditionDescription
                .setText(HtmlUtils.wrapTextHTML("END CONDITION: " + ctrl.getEndConditionDescription() + ".", MAX_LINE_LENGTH));
        this.gemModifierDescription
                .setText(HtmlUtils.wrapTextHTML("GEM MODIFIER: " + ctrl.getGemModifierDescrition() + ".", MAX_LINE_LENGTH));
        this.refresh();
        // resets action listeners
        for (final ActionListener al : this.drawBtn.getActionListeners()) {
            this.drawBtn.removeActionListener(al);
        }
        this.drawBtn.addActionListener(e -> {
            ctrl.executeDrawPhase();
            this.addCardToPath(ctrl);
            ctrl.executeDecisionPhase(this.toBlockWindow);
            if (!ctrl.canGameContinue()) {
                JOptionPane.showMessageDialog(
                        this,
                        "The game is over!");
                ctrl.goToLeaderboard(() -> this.cleanGameboard());
                return;
            }
            if (!ctrl.canRoundContinue()) {
                JOptionPane.showMessageDialog(
                        this,
                        "The round is over!");
                this.cleanGameboard();
            }
            ctrl.advance();
            this.cpuAutoplay(ctrl);
        });
        this.cpuAutoplay(ctrl);
    }
}
