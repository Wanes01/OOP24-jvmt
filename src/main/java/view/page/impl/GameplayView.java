package view.page.impl;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

        
    }

    @Override
    protected void setHandlers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHandlers'");
    }

}
