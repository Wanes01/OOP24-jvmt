package view.page.utility;

import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.miginfocom.swing.MigLayout;

/**
 * A generic Swing component that displays a {@code JLabel} above a {@code JSpinner}.
 * This class is useful for quickly creating spinners with an associated description.
 * 
 * @author Andrea La Tosa
 */
@SuppressFBWarnings(value = { "EI_EXPOSE_REP",
    "EI_EXPOSE_REP2" }, justification = "The values returned by this object can be modified externally.")
public class JSpinnerWithLabel {

    private final JPanel panel;
    private final JLabel lbl;
    private final JSpinner spn;

    /**
     * Create a panel containing the descriptive label and a spinner.
     * The spinner is read-only.
     * 
     * @param lblText the text to be added to the label
     * @param spnStartValue the starting value of the spinner
     * @param spnMinValue the minimum value allowed by the spinner
     * @param spnMaxValue the maximum value allowed by the spinner
     * @param spnStepSize the size of the spinner step
     * 
     * @throws NullPointerException if lblText is null
     */
    public JSpinnerWithLabel(
        final String lblText,
        final int spnStartValue,
        final int spnMinValue,
        final int spnMaxValue,
        final int  spnStepSize) {
            Objects.requireNonNull(lblText, "lblText cannot be null.");

            final SpinnerNumberModel modelSpinner;

            this.panel = new JPanel(
                new MigLayout(
                    "fillx, wrap 1, insets 0",
                    "[center]"));

            this.lbl = new JLabel(lblText);

            modelSpinner = new SpinnerNumberModel(spnStartValue, spnMinValue, spnMaxValue, spnStepSize);
            this.spn = new JSpinner(modelSpinner);
            // Makes the spinner textbox read-only
            ((JSpinner.DefaultEditor) spn.getEditor()).getTextField().setEditable(false);

            panel.add(lbl, "gapbottom rel");
            panel.add(spn, "growx, wmax 50%");
    }

    /**
     * Returns the panel containing the label and spinner.
     * 
     * @return the panel.
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Returns the label added to the panel.
     * 
     * @return the label.
     */
    public JLabel getLabel() {
        return this.lbl;
    }

    /**
     * Returns the spinner added to the panel.
     * 
     * @return the spinner.
     */
    public JSpinner getSpinner() {
        return this.spn;
    }

    /**
     * Returns the current value of the spinner.
     * 
     * @return the current value of the spinner.
     */
    public int getSpinnerValue() {
        return (Integer) this.spn.getValue();
    }
}
