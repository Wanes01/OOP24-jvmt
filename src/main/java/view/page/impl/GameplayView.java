package view.page.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

    public GameplayView() {
        final JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));
        JLabel lblRoundTurn = new JLabel("Round n." + " | Turno n.");//TODO add number of round and turn
        gameInfo.add(lblRoundTurn);

        final JPanel playerInfo = new JPanel();
        playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
        Border boxBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        playerInfo.setBorder(boxBorder);
        JLabel lblPlayerTurn = new JLabel("Turno di:");//TODO add player's name
        JLabel lblSackGems = new JLabel("Gemme nella sacca: ");//TODO add player's sack gems
        JLabel lblSackChest = new JLabel("Gemme nella cassa: ");//TODO add player's chest gems
        JButton btnDraw = new JButton("PESCA");
        playerInfo.add(lblPlayerTurn);
        playerInfo.add(lblSackGems);
        playerInfo.add(lblSackChest);
        playerInfo.add(btnDraw);
        gameInfo.add(playerInfo);

        final JPanel gameConditions = new JPanel();
        gameConditions.setLayout(new BoxLayout(gameConditions, BoxLayout.Y_AXIS));
        gameConditions.setBorder(boxBorder);
        JLabel lblGameEndCond = new JLabel("Condizione fine round: ");//TODO add game end conditions
        JLabel lblGemModifier = new JLabel("Modificatori gemme: ");//TODO add gems modifiers
        gameConditions.add(lblGameEndCond);
        gameConditions.add(lblGemModifier);
        gameInfo.add(gameConditions);

        this.add(gameInfo, BorderLayout.WEST);

        final JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.Y_AXIS));
        JLabel lblDrawnCards = new JLabel("Carte pescate: ");//TODO add cards drawn
        gameBoard.add(lblDrawnCards);

        final JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        //For testing, to be deleted
        for(int i = 0; i < 35; i++) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imageCard/relic/relic.png"));
            Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon imageResized = new ImageIcon(image);
            JLabel labelLogo = new JLabel(imageResized);
            
            gbc.gridx = i % 5; //column (max 5)
            gbc.gridy = i / 5; //row
            gbc.weightx = 0;
            gbc.weighty = 0;
            
            cardsContainer.add(labelLogo, gbc);
        }

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        cardsContainer.add(Box.createGlue(), gbc);

        gbc.gridx = 0;
        gbc.gridy = (35 / 5) + 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        cardsContainer.add(Box.createGlue(), gbc);


        JScrollPane scrollableBoard = new JScrollPane(cardsContainer);
        scrollableBoard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableBoard.setPreferredSize(new Dimension(820, 450));
        scrollableBoard.getVerticalScrollBar().setUnitIncrement(30);
        gameBoard.add(scrollableBoard);

        this.add(gameBoard, BorderLayout.CENTER);

        final JPanel caveGems = new JPanel();
        caveGems.setBorder(boxBorder);
        JLabel lblCaveGems = new JLabel("Gemme rimaste nel percorso: " + ", Reliquie rimaste nel percorso: ");//TODO add gems and relics in the cave
        caveGems.add(lblCaveGems);

        this.add(caveGems, BorderLayout.SOUTH);
    }

    @Override
    protected void setHandlers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHandlers'");
    }

}
