package view.page.impl;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import model.card.api.TypeDeck;
import model.player.api.CpuDifficulty;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.endcondition.EndConditionFactory;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.roundeffect.gemmodifier.GemModifierFactory;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import view.page.api.SwingPage;

/**
 * Displays the game settings page.
 * <p>The following options are available:</p>
 * <ul>
 *  <li>Player names</li>
 *  <li>Number of CPU players</li>
 *  <li>CPU difficulty</li>
 *  <li>Deck type</li>
 *  <li>Round end condition</li>
 *  <li>Round gem modifier</li>
 *  <li>Total number of rounds</li>
 * </ul>
 * 
 * @author Andrea La Tosa
 */
public class SettingsPageView extends SwingPage {

    private static final long serialVersionUID = 1L;

    private static final List<GemModifier> GEM_MODIFIERS = createGemModifiers();
    private static final List<EndCondition> END_CONDITIONS = createEndConditions();

    private static final Dimension RELATED_SPACE = new Dimension(0, 10);
    private static final Dimension UNRELATED_SPACE = new Dimension(0, 50);
    private static final Dimension SPACE_BETWEEN_COL = new Dimension(100, 0);

    // Behaviors common to all JSpinners
    private static final int SPN_STEP_SIZE = 1;
    private static final Dimension SPN_DIMENSION = new Dimension(200, 40);

    /**
     * Create the panel to be displayed by adding 3 columns.
     */
    public SettingsPageView() {
        final JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));

        settingsPanel.add(settingsCol1());
        settingsPanel.add(Box.createRigidArea(SPACE_BETWEEN_COL));
        settingsPanel.add(settingsCol2());
        settingsPanel.add(Box.createRigidArea(SPACE_BETWEEN_COL));
        settingsPanel.add(settingsCol3());

        super.add(settingsPanel);
    }

    // Create the list with the end of round conditions
    private static List<EndCondition> createEndConditions() {
        final EndConditionFactory endCond = new EndConditionFactoryImpl();
        return List.of(
            endCond.standard(),
            endCond.firstTrapEnds());
    }

    // Create the list of gem modifiers to apply to the rounds
    private static List<GemModifier> createGemModifiers() {
        final GemModifierFactory gemFact = new GemModifierFactoryImpl();
        return List.of(
            gemFact.standard(),
            gemFact.gemMultiplier(2),
            gemFact.gemMultiplier(3),
            gemFact.riskyReward(10),
            gemFact.leftReward(ABORT));
    }

    /**
     * @return a panel containing the first column to be displayed.
     * <p>This contains:</p>
     * <ul>
     *  <li>player names</li>
     *  <li>CPU number</li>
     *  <li>CPU difficulty</li>
     * </ul>
     */
    private JPanel settingsCol1() {

        final JPanel settingsCol1;
        final JLabel lblDescrName;
        final JTextArea txtAreaName;
        final JScrollPane scrlPaneName;
        final JLabel lblNumPlayer;
        final SpinnerNumberModel modelSpinner;
        final JSpinner jspNumCPU;
        final ComboboxWithLabel<CpuDifficulty> difficulty;

        final int jtxtareaWidth = 10;
        final int jtxtareaHeight = 30;

        final int spnStartValue = 0;
        final int spnMinValue = 0;
        final int spnMaxValue = 8;

        settingsCol1 = new JPanel();
        settingsCol1.setLayout(new BoxLayout(settingsCol1, BoxLayout.Y_AXIS));

        lblDescrName = new JLabel(
            "<html><div style='text-align: center;'>"
                + "Inserire i nomi dei giocatori<br>(uno per riga):</div></html>");
        lblDescrName.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescrName.setAlignmentX(CENTER_ALIGNMENT);

        txtAreaName = new JTextArea(jtxtareaWidth, jtxtareaHeight);
        txtAreaName.setWrapStyleWord(true);
        scrlPaneName = new JScrollPane(txtAreaName);
        scrlPaneName.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrlPaneName.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        lblNumPlayer = new JLabel("Inserire il numero di CPU:");
        lblNumPlayer.setAlignmentX(CENTER_ALIGNMENT);

        modelSpinner = new SpinnerNumberModel(spnStartValue, spnMinValue, spnMaxValue, SPN_STEP_SIZE);
        jspNumCPU = new JSpinner(modelSpinner);
        jspNumCPU.setPreferredSize(SPN_DIMENSION);
        jspNumCPU.setMaximumSize(SPN_DIMENSION);
        jspNumCPU.setAlignmentX(CENTER_ALIGNMENT);

        difficulty = new ComboboxWithLabel<>(
            "Inserire difficoltà CPU:",
            Arrays.asList(CpuDifficulty.values()),
            RELATED_SPACE);

        settingsCol1.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol1.add(lblDescrName);
        settingsCol1.add(Box.createRigidArea(RELATED_SPACE));
        settingsCol1.add(scrlPaneName);
        settingsCol1.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol1.add(lblNumPlayer);
        settingsCol1.add(Box.createRigidArea(RELATED_SPACE));
        settingsCol1.add(jspNumCPU);
        settingsCol1.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol1.add(difficulty.getPanel());

        return settingsCol1;
    }

    /**
     * @return a panel containing the second column to be displayed.
     * <p>This contains:</p>
     * <ul>
     *  <li>Deck type</li>
     *  <li>Round end condition</li>
     *  <li>Round gem modifier</li>
     *  <li>Total number of rounds</li>
     * </ul>
     */
    private JPanel settingsCol2() {
        final JPanel settingsCol2;
        final ComboboxWithLabel<TypeDeck> deckType;
        final JLabel lblNumRound;
        final SpinnerNumberModel modelSpinner;
        final JSpinner jspNumRound;
        final ComboboxWithLabel<String> gemMod;
        final ComboboxWithLabel<String> endCond;

        final int spnStartValue = 1;
        final int spnMinValue = 1;
        final int spnMaxValue = 20;
        final List<String> gemModifiersDescr;
        final List<String> endCondDescr;

        settingsCol2 = new JPanel();
        settingsCol2.setLayout(new BoxLayout(settingsCol2, BoxLayout.Y_AXIS));

        deckType = new ComboboxWithLabel<>(
            "Selezionare il tipo di mazzo:",
            Arrays.asList(TypeDeck.values()),
            RELATED_SPACE);

        lblNumRound = new JLabel("Inserire il numero di round:");
        lblNumRound.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumRound.setAlignmentX(CENTER_ALIGNMENT);
        modelSpinner = new SpinnerNumberModel(spnStartValue, spnMinValue, spnMaxValue, SPN_STEP_SIZE);
        jspNumRound = new JSpinner(modelSpinner);
        jspNumRound.setPreferredSize(SPN_DIMENSION);
        jspNumRound.setMaximumSize(SPN_DIMENSION);
        jspNumRound.setAlignmentX(CENTER_ALIGNMENT);

        gemModifiersDescr = GEM_MODIFIERS.stream()
            .map(GemModifier::getDescription)
            .collect(Collectors.toList());

        endCondDescr = END_CONDITIONS.stream()
            .map(EndCondition::getDescription)
            .collect(Collectors.toList());

        endCond = new ComboboxWithLabel<>(
            "<html><div style='text-align: center;'>"
                + "Selezionare la condizione di fine<br>dei round:</div></html>",
            endCondDescr,
            RELATED_SPACE);

        gemMod = new ComboboxWithLabel<>(
            "<html><div style='text-align: center;'>"
                + "Selezionare il modificatore<br>di gemme dei round:</div></html>",
                gemModifiersDescr,
                RELATED_SPACE);

        settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol2.add(deckType.getPanel());
        settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol2.add(endCond.getPanel());
        settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol2.add(gemMod.getPanel());
        settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
        settingsCol2.add(lblNumRound);
        settingsCol2.add(Box.createRigidArea(RELATED_SPACE));
        settingsCol2.add(jspNumRound);

        return  settingsCol2;
    }

    /**
     * @return a panel containing the third column to be displayed.
     * It contains the button to start the game.
     * 
     */
    private JPanel settingsCol3() { 
        final JPanel settingsCol3;
        final JButton btnPlay;

        final Font btnFont = new Font("Arial", Font.BOLD, 50);

        settingsCol3 = new JPanel();
        settingsCol3.setLayout(new BoxLayout(settingsCol3, BoxLayout.Y_AXIS));


        btnPlay = new JButton("Avvia Partita");
        btnPlay.setFont(btnFont);
        btnPlay.setAlignmentX(CENTER_ALIGNMENT);

        settingsCol3.add(btnPlay);

        return  settingsCol3;
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
