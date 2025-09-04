package view.page.impl;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controller.api.SettingsController;
import controller.impl.SettingsControllerImpl;
import model.card.api.TypeDeck;
import model.player.api.CpuDifficulty;
import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.gemmodifier.GemModifier;
import net.miginfocom.swing.MigLayout;
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

        private static final int SPN_STEP_SIZE = 1;
        private static final String UNRELATED_SPACING_GROWX = "gaptop unrelated, growx";

        private JTextArea txtAreaName;
        private JButton btnPlay;
        private transient JSpinnerWithLabel numCPU;
        private ComboboxWithLabel<TypeDeck> deckType;
        private ComboboxWithLabel<EndCondition> endCond;
        private ComboboxWithLabel<GemModifier> gemMod;
        private ComboboxWithLabel<CpuDifficulty> difficulty;
        private transient JSpinnerWithLabel numRound;

        /**
         * Create the panel to be displayed by adding 3 columns.
         */
        public SettingsPage(final Dimension winDim) {
                super(winDim);

                super.setLayout(new MigLayout(
                                "fill, insets dialog",
                                "[grow, fill]paragraph[grow, fill]paragraph[grow, fill]"));

                super.add(settingsCol1());
                super.add(settingsCol2());
                super.add(settingsCol3());
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

                final int spnStartValue = 0;
                final int spnMinValue = 0;
                final int spnMaxValue = GameSettingsImpl.MAX_CPU_PLAYERS;

                settingsCol1 = new JPanel(new MigLayout(
                                "fillx, wrap 1, insets 0",
                                "[grow, fill]"));

                lblDescrName = new JLabel(
                                "<html><div style='text-align: center;'>"
                                                + "Enter the names of the players<br> (one per line):</div></html>");
                lblDescrName.setHorizontalAlignment(SwingConstants.CENTER);

                this.txtAreaName = new JTextArea();
                this.txtAreaName.setRows(GameSettingsImpl.MAX_PLAYERS);
                this.txtAreaName.setWrapStyleWord(true);
                scrlPaneName = new JScrollPane(txtAreaName);
                scrlPaneName.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrlPaneName.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                numCPU = new JSpinnerWithLabel(
                                "Enter the number of CPU players:",
                                spnStartValue,
                                spnMinValue,
                                spnMaxValue,
                                SPN_STEP_SIZE);

                difficulty = new ComboboxWithLabel<>("Enter CPU difficulty:");
                difficulty.addItems(Arrays.asList(CpuDifficulty.values()));

                settingsCol1.add(lblDescrName, "gaptop unrel");
                settingsCol1.add(scrlPaneName, "grow");
                settingsCol1.add(numCPU.getPanel(), "gaptop unrel");
                settingsCol1.add(difficulty.getPanel(), "gaptop unrel");

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

                settingsCol2 = new JPanel(new MigLayout(
                                "fillx, wrap 1, insets 0",
                                "[grow, fill]"));

                deckType = new ComboboxWithLabel<>(
                                "Select the type of deck:");
                deckType.addItems(Arrays.asList(TypeDeck.values()));
                // The special deck has been removed because it has not yet been implemented
                deckType.getComboBox().removeItem(TypeDeck.SPECIAL);

                endCond = new ComboboxWithLabel<>(
                                "<html><div style='text-align: center;'>"
                                                + "Select the end condition<br>for the rounds:</div></html>");
                endCond.addItems(SettingsControllerImpl.END_CONDITIONS);

                gemMod = new ComboboxWithLabel<>(
                                "<html><div style='text-align: center;'>"
                                                + "Select the gem modifier<br>for the rounds:</div></html>");
                gemMod.addItems(SettingsControllerImpl.GEM_MODIFIERS);

                numRound = new JSpinnerWithLabel(
                                "Enter the number of rounds:",
                                spnStartValue,
                                spnMinValue,
                                spnMaxValue,
                                SPN_STEP_SIZE);

                settingsCol2.add(deckType.getPanel(), UNRELATED_SPACING_GROWX);
                settingsCol2.add(endCond.getPanel(), UNRELATED_SPACING_GROWX);
                settingsCol2.add(gemMod.getPanel(), UNRELATED_SPACING_GROWX);
                settingsCol2.add(numRound.getPanel(), UNRELATED_SPACING_GROWX);

                return settingsCol2;
        }

        /**
         * @return a panel containing the third column to be displayed.
         *         It contains the button to start the game.
         */
        private JPanel settingsCol3() {
                final float btnFontScale = 3f; // font size multiplier
                // multiplier to calculate the square size of the button based on the font size
                final int btnSizeMultiplier = 4;

                final JPanel settingsCol3;

                settingsCol3 = new JPanel(new MigLayout(
                                "fill, wrap 1, insets 0",
                                "[grow, center]",
                                "[grow]"));

                btnPlay = new JButton("<html><center>START<br>GAME</center></html>");

                final Font baseFont = btnPlay.getFont();
                final float fontSize = baseFont.getSize2D() * btnFontScale;
                btnPlay.setFont(baseFont.deriveFont(Font.BOLD, fontSize));
                final int size = (int) (fontSize * btnSizeMultiplier);
                final Dimension squareDim = new Dimension(size, size);
                btnPlay.setMinimumSize(squareDim);

                settingsCol3.add(btnPlay, "grow, align center");

                return settingsCol3;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void setHandlers() {

                final SettingsController settingCtrl = this.getController(SettingsControllerImpl.class);

                btnPlay.addActionListener(e -> {
                        if (settingCtrl.areGameSettingOK(
                                        filterNamePlayer(txtAreaName.getText()),
                                        numCPU.getSpinnerValue(),
                                        deckType.getSelectedItem().getDeck(),
                                        endCond.getSelectedItem(),
                                        gemMod.getSelectedItem(),
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
         * 
         * @return a list consisting of the names of filtered players
         */
        private List<String> filterNamePlayer(final String txtPlayerName) {
                return Arrays.stream(txtPlayerName.split("\n"))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.toList());
        }

        /**
         * Creates a list of errors related to incorrect settings
         * and prepares the display for the user.
         * 
         * @param errorMessage the list of errors to display
         * 
         * @return the string containing the errors to be displayed to the user
         */
        private String showErrorMessage(final List<String> errorMessage) {
                final StringBuilder sb = new StringBuilder(
                                "Errors have been detected in the selected settings.\nSpecifically:\n");

                for (final String s : errorMessage) {
                        sb.append("> ")
                                        .append(s)
                                        .append('\n');
                }

                return sb.toString();
        }
}
