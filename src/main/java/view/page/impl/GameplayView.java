package view.page.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import view.page.api.SwingPage;

public class GameplayView extends SwingPage{
    final static int COL_GAP = 50;
    final static int ROW_GAP = 20;
    final static int CARDS_GAP = 5;
    final static int CARDS_DIM = 150;
    final static int CARDS_PER_ROW = 5;
    final static int SCROLLABLE_HEIGHT = 450;
    final static int SCROLLABLE_WIDTH = 820;
    final static int SCROLL_PIXELS = 30;
    final static int FONT_SIZE = 20;

    public GameplayView(/* RoundStateImpl roundState */) {
        final JPanel gameUi = new JPanel();
        final Font uiFont = new Font("Arial", Font.PLAIN, FONT_SIZE);
        final Border boxBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        gameUi.setLayout(new BoxLayout(gameUi, BoxLayout.X_AXIS));

        gameUi.add(gameInfo(uiFont, boxBorder/* , RoundStateImpl roundState */));
        gameUi.add(Box.createHorizontalStrut(COL_GAP));
        gameUi.add(gameBoard(uiFont, boxBorder/* , RoundStateImpl roundState */));

        this.add(gameUi);
    }

    private JPanel gameInfo(Font uiFont, Border boxBorder/* , RoundStateImpl roundState */) {
        final JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));


        JLabel lblRoundTurn = new JLabel("Round n."
            /* + */
            + " | Turno n."
            /* +  */);//TODO add number of round and turn
        lblRoundTurn.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblRoundTurn.setFont(uiFont);
        gameInfo.add(lblRoundTurn);


        gameInfo.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel playerInfo = new JPanel();
        playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
        playerInfo.setBorder(boxBorder);
        playerInfo.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        gameInfo.add(playerInfo);

        JLabel lblPlayerTurn = new JLabel("Turno di:"
            /* + */);//TODO add player's name
        lblPlayerTurn.setFont(uiFont);
        playerInfo.add(lblPlayerTurn);

        JLabel lblSackGems = new JLabel("Gemme nella sacca: "
            /* + */);//TODO add player's sack gems
        lblSackGems.setFont(uiFont);
        playerInfo.add(lblSackGems);

        JLabel lblSackChest = new JLabel("Gemme nella cassa: "
            /* + */);//TODO add player's chest gems
        lblSackChest.setFont(uiFont);
        playerInfo.add(lblSackChest);

        final JButton btnDraw = new JButton("PESCA");
        btnDraw.setFont(uiFont);
        playerInfo.add(btnDraw);


        gameInfo.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel gameConditions = new JPanel();
        gameConditions.setLayout(new BoxLayout(gameConditions, BoxLayout.Y_AXIS));
        gameConditions.setBorder(boxBorder);
        gameConditions.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        gameInfo.add(gameConditions);

        JLabel lblGameEndCond = new JLabel("Condizione fine round: "
            /* + */);//TODO add game end conditions
        lblGameEndCond.setFont(uiFont);
        gameConditions.add(lblGameEndCond);

        JLabel lblGemModifier = new JLabel("Modificatori gemme: "
            /* + */);//TODO add gems modifiers
        lblGemModifier.setFont(uiFont);
        gameConditions.add(lblGemModifier);


        return gameInfo;
    }

    private JPanel gameBoard(Font uiFont, Border boxBorder/* , RoundStateImpl roundState */) {
        final JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));


        JLabel lblDrawnCards = new JLabel("Carte pescate: "
            /* + roundState.getDrawCards().size() */);//TODO add cards drawn
        lblDrawnCards.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblDrawnCards.setFont(uiFont);
        gameBoard.add(lblDrawnCards);


        gameBoard.add(Box.createVerticalStrut(ROW_GAP));


        final JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(CARDS_GAP, CARDS_GAP, CARDS_GAP, CARDS_GAP);

        //For testing, to be deleted
        for(int i = 0; i < 35; i++) {
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

        JLabel lblCaveGems = new JLabel("Gemme rimaste nel percorso: "
            //+ roundState.getPathGems()
            + ", Reliquie rimaste nel percorso: "
            /* + roundState.getDrawnRelics().size() */);//TODO add path gems and relics
        lblCaveGems.setFont(uiFont);
        caveGems.add(lblCaveGems);


        return gameBoard;
    }

    @Override
    protected void setHandlers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHandlers'");
    }

}
