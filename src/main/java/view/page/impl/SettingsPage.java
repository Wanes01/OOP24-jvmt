package view.page.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controller.api.PageController;
import controller.api.SettingsController;
import controller.impl.SettingsControllerImpl;
import model.card.api.TypeDeck;
import model.card.impl.DeckFactoryImpl;
import model.player.api.CpuDifficulty;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.endcondition.EndConditionFactory;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.roundeffect.gemmodifier.GemModifierFactory;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import model.game.api.GameSettings;
import model.game.impl.GameSettingsImpl;
import view.page.api.SwingPage;
import view.page.utility.ComboboxWithLabel;
import view.page.utility.JSpinnerWithLabel;

/**
 * Displays the game settings page.
 * <p>
 * The following options are available:
 * </p>
 * <ul>
 * <li>Player names</li>
 * <li>Number of CPU players</li>
 * <li>CPU difficulty</li>
 * <li>Deck type</li>
 * <li>Round end condition</li>
 * <li>Round gem modifier</li>
 * <li>Total number of rounds</li>
 * </ul>
 * 
 * @author Andrea La Tosa
 */
public class SettingsPage extends SwingPage {

        private static final long serialVersionUID = 1L;

        // private static final List<GemModifier> GEM_MODIFIERS = createGemModifiers();
        // private static final List<EndCondition> END_CONDITIONS =
        // createEndConditions();

        private static final Map<String, GemModifier> GEM_MODIFIERS = createGemModifiers2();
        private static final Map<String, EndCondition> END_CONDITIONS = createEndConditions2();

        private static final Dimension RELATED_SPACE = new Dimension(0, 10);
        private static final Dimension UNRELATED_SPACE = new Dimension(0, 50);
        // private static final Dimension SPACE_BETWEEN_COL = new Dimension(50, 0);

        // Behaviors common to all JSpinners
        private static final int SPN_STEP_SIZE = 1;
        private static final Dimension SPN_DIMENSION = new Dimension(200, 40);

        private final Dimension VIEW_DIMENSION;

        private JTextArea txtAreaName;
        private JButton btnPlay;
        private JSpinnerWithLabel numCPU;
        private ComboboxWithLabel<TypeDeck> deckType;
        private ComboboxWithLabel<String> endCond;
        private ComboboxWithLabel<String> gemMod;
        private ComboboxWithLabel<CpuDifficulty> difficulty;
        private JSpinnerWithLabel numRound;

        /**
         * Create the panel to be displayed by adding 3 columns.
         * 
         * @throws NullPointerException if if the newDim parameter receives null
         */
        public SettingsPage(Dimension viewDim) {
                super(viewDim);

                VIEW_DIMENSION = Objects.requireNonNull(viewDim, "viewDim cannot be null.");

                final JPanel mainPanel = new JPanel(new GridLayout(1, 3, 20, 0));

                mainPanel.add(settingsCol1());
                mainPanel.add(settingsCol2());
                mainPanel.add(settingsCol3());

                super.add(mainPanel);

        }

        /*
         * // Create the list with the end of round conditions
         * private static List<EndCondition> createEndConditions() {
         * final EndConditionFactory endCond = new EndConditionFactoryImpl();
         * return List.of(
         * endCond.standard(),
         * endCond.firstTrapEnds());
         * }
         * 
         * // Create the list of gem modifiers to apply to the rounds
         * private static List<GemModifier> createGemModifiers() {
         * final GemModifierFactory gemFact = new GemModifierFactoryImpl();
         * return List.of(
         * gemFact.standard(),
         * gemFact.gemMultiplier(2),
         * gemFact.gemMultiplier(3),
         * gemFact.riskyReward(10),
         * gemFact.leftReward(ABORT));
         * }
         */

        private static Map<String, EndCondition> createEndConditions2() {
                final EndConditionFactory endCond = new EndConditionFactoryImpl();
                return Map.of(
                                endCond.standard().getDescription(), endCond.standard(),
                                endCond.firstTrapEnds().getDescription(), endCond.firstTrapEnds());
        }

        private static Map<String, GemModifier> createGemModifiers2() {
                final GemModifierFactory gemFact = new GemModifierFactoryImpl();
                return Map.of(
                                gemFact.standard().getDescription(), gemFact.standard(),
                                gemFact.gemMultiplier(2).getDescription(), gemFact.gemMultiplier(2),
                                gemFact.gemMultiplier(3).getDescription(), gemFact.gemMultiplier(3),
                                gemFact.riskyReward(10).getDescription(), gemFact.riskyReward(10),
                                gemFact.leftReward(3).getDescription(), gemFact.leftReward(3));
        }

        /**
         * @return a panel containing the first column to be displayed.
         *         <p>
         *         This contains:
         *         </p>
         *         <ul>
         *         <li>player names</li>
         *         <li>CPU number</li>
         *         <li>CPU difficulty</li>
         *         </ul>
         */
        private JPanel settingsCol1() {
                final JPanel settingsCol1;
                final JLabel lblDescrName;
                final JScrollPane scrlPaneName;

                // final int jtxtareaHeight = 10;
                // final int jtxtareaWidth = 30;

                final int spnStartValue = 0;
                final int spnMinValue = 0;
                final int spnMaxValue = GameSettingsImpl.MAX_CPU_PLAYERS;

                settingsCol1 = new JPanel();
                settingsCol1.setLayout(new BoxLayout(settingsCol1, BoxLayout.Y_AXIS));

                lblDescrName = new JLabel(
                                "<html><div style='text-align: center;'>"
                                                + "Enter the names of the players<br> (one per line):</div></html>");
                lblDescrName.setHorizontalAlignment(SwingConstants.CENTER);
                lblDescrName.setAlignmentX(CENTER_ALIGNMENT);

                // this.txtAreaName = new JTextArea(jtxtareaWidth, jtxtareaHeight);
                this.txtAreaName = new JTextArea();
                this.txtAreaName.setWrapStyleWord(true);
                scrlPaneName = new JScrollPane(txtAreaName);
                scrlPaneName.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrlPaneName.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                numCPU = new JSpinnerWithLabel(
                                "Enter the number of CPU players:",
                                spnStartValue,
                                spnMinValue,
                                spnMaxValue,
                                SPN_STEP_SIZE,
                                SPN_DIMENSION,
                                RELATED_SPACE);

                difficulty = new ComboboxWithLabel<>(
                                "Enter CPU difficulty:",
                                Arrays.asList(CpuDifficulty.values()),
                                VIEW_DIMENSION);

                settingsCol1.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol1.add(lblDescrName);
                settingsCol1.add(Box.createRigidArea(RELATED_SPACE));
                settingsCol1.add(scrlPaneName);
                settingsCol1.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol1.add(numCPU.getPanel());
                settingsCol1.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol1.add(difficulty.getPanel());

                return settingsCol1;
        }

        /**
         * @return a panel containing the second column to be displayed.
         *         <p>
         *         This contains:
         *         </p>
         *         <ul>
         *         <li>Deck type</li>
         *         <li>Round end condition</li>
         *         <li>Round gem modifier</li>
         *         <li>Total number of rounds</li>
         *         </ul>
         */
        private JPanel settingsCol2() {
                final JPanel settingsCol2;

                final int spnStartValue = 1;
                final int spnMinValue = 1;
                final int spnMaxValue = GameSettingsImpl.MAX_ROUNDS;
                // final List<String> gemModifiersDescr;
                // final List<String> endCondDescr;

                settingsCol2 = new JPanel();
                settingsCol2.setLayout(new BoxLayout(settingsCol2, BoxLayout.Y_AXIS));

                deckType = new ComboboxWithLabel<>(
                                "Select the type of deck:",
                                Arrays.asList(TypeDeck.values()),
                                VIEW_DIMENSION);

                // The special deck has been removed because it has not yet been implemented
                deckType.getComboBox().removeItem(TypeDeck.SPECIAL);

                /*
                 * endCondDescr = END_CONDITIONS.stream()
                 * .map(EndCondition::getDescription)
                 * .collect(Collectors.toList());
                 * 
                 * 
                 * endCond = new ComboboxWithLabel<>(
                 * "<html><div style='text-align: center;'>"
                 * + "Select the end condition<br>for the rounds:</div></html>",
                 * endCondDescr,
                 * VIEW_DIMENSION);
                 */

                endCond = new ComboboxWithLabel<>(
                                "<html><div style='text-align: center;'>"
                                                + "Select the end condition<br>for the rounds:</div></html>",
                                new ArrayList<>(END_CONDITIONS.keySet()),
                                VIEW_DIMENSION);

                /*
                 * gemModifiersDescr = GEM_MODIFIERS.stream()
                 * .map(GemModifier::getDescription)
                 * .collect(Collectors.toList());
                 * 
                 * gemMod = new ComboboxWithLabel<>(
                 * "<html><div style='text-align: center;'>"
                 * + "Select the gem modifier<br>for the rounds:</div></html>",
                 * gemModifiersDescr,
                 * VIEW_DIMENSION);
                 */

                gemMod = new ComboboxWithLabel<>(
                                "<html><div style='text-align: center;'>"
                                                + "Select the gem modifier<br>for the rounds:</div></html>",
                                new ArrayList<>(GEM_MODIFIERS.keySet()),
                                VIEW_DIMENSION);

                numRound = new JSpinnerWithLabel(
                                "Enter the number of rounds:",
                                spnStartValue,
                                spnMinValue,
                                spnMaxValue,
                                SPN_STEP_SIZE,
                                SPN_DIMENSION,
                                RELATED_SPACE);

                settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol2.add(deckType.getPanel());
                settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol2.add(endCond.getPanel());
                settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol2.add(gemMod.getPanel());
                settingsCol2.add(Box.createRigidArea(UNRELATED_SPACE));
                settingsCol2.add(numRound.getPanel());

                return settingsCol2;
        }

        /**
         * @return a panel containing the third column to be displayed.
         *         It contains the button to start the game.
         */
        private JPanel settingsCol3() {
                final JPanel settingsCol3;
                final int sizeFont = 50;
                final Font btnFont = new Font("Arial", Font.BOLD, sizeFont);

                settingsCol3 = new JPanel();
                settingsCol3.setLayout(new BoxLayout(settingsCol3, BoxLayout.Y_AXIS));

                btnPlay = new JButton("Avvia Partita");
                btnPlay.setFont(btnFont);
                btnPlay.setAlignmentX(CENTER_ALIGNMENT);

                settingsCol3.add(Box.createVerticalGlue());
                settingsCol3.add(btnPlay);
                settingsCol3.add(Box.createVerticalGlue());

                return settingsCol3;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void setHandlers() {

                final SettingsController settingCtrl = this.getController(SettingsControllerImpl.class);

                btnPlay.addActionListener(e -> {
                        if (settingCtrl.setGameSetting(
                                        filterNamePlayer(txtAreaName.getText()),
                                        numCPU.getSpinnerValue(),
                                        // TODO: sostituire con typdeck convertito
                                        new DeckFactoryImpl().standardDeck(),
                                        END_CONDITIONS.get(endCond.getSelectedItem()),
                                        GEM_MODIFIERS.get(gemMod.getSelectedItem()),
                                        difficulty.getSelectedItem(),
                                        numRound.getSpinnerValue())) {
                                settingCtrl.goToGamePlayPage();
                        } else {
                                JOptionPane.showMessageDialog(
                                                null,
                                                showErrorMessage(settingCtrl.getErrors().get()),
                                                "Settings errors",
                                                JOptionPane.WARNING_MESSAGE);
                        }
                });
        }

        /**
         * Filters the string passed in as input to obtain a list of valid player names.
         * To do this, it removes spaces before and after the name and ignores empty
         * strings.
         * 
         * @param txtPlayerName the string of player names to filter
         * @return a list consisting of the names of filtered players
         */
        private List<String> filterNamePlayer(String txtPlayerName) {
                return Arrays.stream(txtPlayerName.split("\n"))
                                .map(s -> s.trim())
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.toList());
        }

        private String showErrorMessage(List<String> errorMessage) {
                final StringBuilder sb = new StringBuilder(
                                "Errors have been detected in the selected settings.\nSpecifically:\n");

                for (String s : errorMessage) {
                        sb.append("> " + s + "\n");
                }

                return sb.toString();
        }
}
