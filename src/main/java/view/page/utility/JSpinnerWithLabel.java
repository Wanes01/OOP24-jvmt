package view.page.utility;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
     * @param spnDim the size of the spinner
     * @param spacing the spacing applied between the label and the spinner
     * 
     * @throws NullPointerException if lblText, spnDim, or spacing are null
     */
    public JSpinnerWithLabel(
        final String lblText,
        final int spnStartValue,
        final int spnMinValue,
        final int spnMaxValue,
        final int  spnStepSize,
        final Dimension spnDim,
        final Dimension spacing) {
            Objects.requireNonNull(lblText, "lblText cannot be null.");
            Objects.requireNonNull(spnDim, "spnDim cannot be null.");
            Objects.requireNonNull(spacing, "spacing cannot be null.");

            final SpinnerNumberModel modelSpinner;

            panel = new JPanel();
            this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

            this.lbl = new JLabel(lblText);
            this.lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.lbl.setHorizontalAlignment(SwingConstants.CENTER);

            modelSpinner = new SpinnerNumberModel(spnStartValue, spnMinValue, spnMaxValue, spnStepSize);
            this.spn = new JSpinner(modelSpinner);
            // Makes the spinner textbox read-only
            ((JSpinner.DefaultEditor) spn.getEditor()).getTextField().setEditable(false);
            this.spn.setPreferredSize(spnDim);
            this.spn.setMaximumSize(spnDim);
            this.spn.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.panel.add(lbl);
            this.panel.add(Box.createRigidArea(spacing));
            this.panel.add(spn);
    }

    /**
     * @return the panel containing the label and jspinner.
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * @return the label added to the panel.
     */
    public JLabel getLabel() {
        return this.lbl;
    }

    /**
     * @return the spinner added to the panel.
     */
    public JSpinner getSpinner() {
        return this.spn;
    }

    /**
     * @return the current value of the spinner
     */
    public int getSpinnerValue() {
        return (Integer) this.spn.getValue();
    }
}
