package view.page.impl;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
import model.game.impl.GameSettingsImpl;
import view.page.api.SwingPage;
import view.page.utility.ComboboxWithLabel;
import view.page.utility.GuiScaler;
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

    private static final double SPACING_COLUMN_RATIO = 0.05;
    private static final double RELATED_SPACE_HIGHT_RATIO = 0.01;
    private static final double UNRELATED_SPACE_HIGHT_RATIO = 0.06;
    private static final int SPN_STEP_SIZE = 1;
    private static final double SPN_DIM_WIDTH_RATIO = 0.13;
    private static final double SPN_DIM_HEIGHT_RATIO = 0.046;
    private static final double BTN_FONT_SIZE = 0.07;
    private static final double BTN_MARGIN_SCALAR_FACTOR = 4.5;

    private final Dimension winDim;
    private final transient GuiScaler guiScaler;

    private final Dimension relatedSpace;
    private final Dimension unrelatedSpace;
    // Behaviors common to all JSpinners
    private final Dimension spnDimension;

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
     * 
     * @param winDim the width and height dimensions of the window
     * 
     * @throws NullPointerException if if the newDim parameter receives null
     */
    public SettingsPage(final Dimension winDim) {
        super(winDim);

        this.winDim = Objects.requireNonNull(winDim, "viewDim cannot be null.");

        this.guiScaler = new GuiScaler(winDim);
        this.relatedSpace = guiScaler.scaleDim(0, RELATED_SPACE_HIGHT_RATIO);
        this.unrelatedSpace =  guiScaler.scaleDim(0, UNRELATED_SPACE_HIGHT_RATIO);
        this.spnDimension = guiScaler.scaleDim(SPN_DIM_WIDTH_RATIO, SPN_DIM_HEIGHT_RATIO);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
            new FlowLayout(FlowLayout.LEFT, guiScaler.scaleWidth(SPACING_COLUMN_RATIO), 0));

        mainPanel.add(settingsCol1());
        mainPanel.add(settingsCol2());
        mainPanel.add(settingsCol3());

        super.add(mainPanel);

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

        settingsCol1 = new JPanel();
        settingsCol1.setLayout(new BoxLayout(settingsCol1, BoxLayout.Y_AXIS));

        lblDescrName = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Enter the names of the players<br> (one per line):</div></html>");
        lblDescrName.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescrName.setAlignmentX(CENTER_ALIGNMENT);

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
                SPN_STEP_SIZE,
                spnDimension,
                relatedSpace);

        difficulty = new ComboboxWithLabel<>(
                "Enter CPU difficulty:",
                this.winDim);

        difficulty.addItems(Arrays.asList(CpuDifficulty.values()));

        settingsCol1.add(Box.createRigidArea(unrelatedSpace));
        settingsCol1.add(lblDescrName);
        settingsCol1.add(Box.createRigidArea(relatedSpace));
        settingsCol1.add(scrlPaneName);
        settingsCol1.add(Box.createRigidArea(unrelatedSpace));
        settingsCol1.add(numCPU.getPanel());
        settingsCol1.add(Box.createRigidArea(unrelatedSpace));
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

        settingsCol2 = new JPanel();
        settingsCol2.setLayout(new BoxLayout(settingsCol2, BoxLayout.Y_AXIS));

        deckType = new ComboboxWithLabel<>(
                "Select the type of deck:",
                this.winDim);

        deckType.addItems(Arrays.asList(TypeDeck.values()));
        // The special deck has been removed because it has not yet been implemented
        deckType.getComboBox().removeItem(TypeDeck.SPECIAL);

        endCond = new ComboboxWithLabel<>(
            "<html><div style='text-align: center;'>"
                    + "Select the end condition<br>for the rounds:</div></html>",
            this.winDim);
        endCond.addItems(SettingsControllerImpl.END_CONDITIONS);

        gemMod = new ComboboxWithLabel<>(
            "<html><div style='text-align: center;'>"
                    + "Select the gem modifier<br>for the rounds:</div></html>",
            this.winDim);
        gemMod.addItems(SettingsControllerImpl.GEM_MODIFIERS);

        numRound = new JSpinnerWithLabel(
            "Enter the number of rounds:",
            spnStartValue,
            spnMinValue,
            spnMaxValue,
            SPN_STEP_SIZE,
            spnDimension,
            relatedSpace);

        settingsCol2.add(Box.createRigidArea(unrelatedSpace));
        settingsCol2.add(deckType.getPanel());
        settingsCol2.add(Box.createRigidArea(unrelatedSpace));
        settingsCol2.add(endCond.getPanel());
        settingsCol2.add(Box.createRigidArea(unrelatedSpace));
        settingsCol2.add(gemMod.getPanel());
        settingsCol2.add(Box.createRigidArea(unrelatedSpace));
        settingsCol2.add(numRound.getPanel());

        return settingsCol2;
    }

    /**
     * @return a panel containing the third column to be displayed.
     *         It contains the button to start the game.
     */
    private JPanel settingsCol3() {
        final JPanel settingsCol3;
        final int btnFontSize = guiScaler.scaleHeight(BTN_FONT_SIZE);
        final int btnDim = (int) (btnFontSize * BTN_MARGIN_SCALAR_FACTOR);
        final Font btnFont = new Font("Arial", Font.BOLD, btnFontSize);

        settingsCol3 = new JPanel();
        settingsCol3.setLayout(new BoxLayout(settingsCol3, BoxLayout.Y_AXIS));

        btnPlay = new JButton("<html><center>START<br>GAME</center></html>");
        btnPlay.setPreferredSize(new Dimension(btnDim, btnDim));
        btnPlay.setMinimumSize(new Dimension(btnDim, btnDim));
        btnPlay.setMaximumSize(new Dimension(btnDim, btnDim));
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

        final SettingsController settingCtrl = 
            this.getController(SettingsControllerImpl.class);

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
