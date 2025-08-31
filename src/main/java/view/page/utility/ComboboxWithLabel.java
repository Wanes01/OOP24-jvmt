package view.page.utility;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.DefaultListCellRenderer;

/**
 * A generic Swing component that displays a {@code JLabel} above a
 * {@code JComboBox}.
 * This class is useful for quickly creating selection fields with an associated
 * description.
 * 
 * @param <T> the type of elements used to populate the combo box
 * 
 * @author Andrea La Tosa
 */
@SuppressFBWarnings(value = { "EI_EXPOSE_REP",
    "EI_EXPOSE_REP2" }, justification = "The values returned by this object can be modified externally.")
public final class ComboboxWithLabel<T> extends JComboBox<T> {

    private static final long serialVersionUID = 1L;

    private static final int MAX_CHARACTERS = 40;
    /* represents the percentage of spacing applied between the label 
        and the jcombobox in relation to the height of the view */
    private static final double VERTICAL_SPACING_RATIO = 0.02;

    private final JPanel panel;
    private final JLabel lbl;

    /**
     * Create a panel containing the label explaining the contents of the combobox
     * and the corresponding combobox.
     * Renders for display using the {@code wrapTextHTML} method.
     * 
     * @param lblText    the description to add to the label explaining
     *                   what the items in the combobox represent
     * @param winDim    the size of the window.
     *  It is used to calculate the vertical spacing between jlabel and jcombobox.
     * 
     * @throws NullPointerException     if winDim is null
     */
    public ComboboxWithLabel(final String lblText, final Dimension winDim) {

        Objects.requireNonNull(winDim, "winDim cannot be null.");

        // The spacing between label and jcombobx.
        // Cast to int to convert pixel based calculation to integer value for spacing
        final int spacingY = (int) (winDim.height * VERTICAL_SPACING_RATIO);

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

        this.lbl = new JLabel(lblText);
        this.lbl.setAlignmentX(CENTER_ALIGNMENT);
        this.lbl.setHorizontalAlignment(SwingConstants.CENTER);

        this.panel.add(lbl);
        this.panel.add(Box.createRigidArea(
            new Dimension(0, spacingY)));
        this.panel.add(this);

        // creates a custom render of the combobox to display the content following the
        // wrapTextHTML logic
        this.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                final Optional<Object> optionalValue = Optional.ofNullable(value);
                if (optionalValue.isPresent()) {
                    final String htmlText = wrapTextHTML(value.toString());
                    label.setText(htmlText);
                } else {
                    label.setText("");
                }
                return label;
            }
        });
    }

    /**
     * Is intended to wrap the string to start a new line after MAX_CHARACTERS
     * without cutting the word and permit a correct display of the combobox
     * content.
     * 
     * @param str the string to be wrapped
     * @return the wrapped string
     */
    private String wrapTextHTML(final String str) {

        int lineLength = 0;
        final StringBuilder sb = new StringBuilder("<html>");

        for (final String word : str.split(" ")) {
            if (lineLength + word.length() > MAX_CHARACTERS) {
                sb.append("<br>");
                lineLength = 0;
            } else {
                sb.append(' ');
                lineLength++;
            }
            sb.append(word);
            lineLength += word.length();
        }
        sb.append("</html>");

        return sb.toString();
    }

    /**
     * Adds a list of items to the combo box.
     * 
     * @param list the items to add to the combobox
     * 
     * @throws NullPointerException if null is passed to the list parameter
     */
    public void addItems(final List<T> list) {
        Objects.requireNonNull(list, "list cannot be null.");
        for (final T el : list) {
            this.addItem(el);
        }
    }

    /**
     * @return the panel containing the label and combobox.
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * @return the combobox added to panel.
     */
    public JComboBox<T> getComboBox() {
        return this;
    }

    /**
     * @return the label added to the panel.
     */
    public JLabel getLabel() {
        return this.lbl;
    }

    /**
     * @return the selected item in the combobox.
     */
    @Override
    public T getSelectedItem() {
        final int index = this.getSelectedIndex();
        return this.getItemAt(index);
    }
}
