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
import java.awt.Toolkit;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.DefaultListCellRenderer;

/**
 * A generic Swing component that displays a {@code JLabel} above a
 * {@code JComboBox}.
 * The combo box is populated by the list provided as input.
 * This class is useful for quickly creating selection fields with an associated
 * description.
 * 
 * @param <T> the type of elements used to populate the combo box
 * 
 * @author Andrea La Tosa
 */
@SuppressFBWarnings(value = { "EI_EXPOSE_REP",
    "EI_EXPOSE_REP2" }, justification = "The values returned by this object can be modified externally.")
public class ComboboxWithLabel<T> {

    private static final int MAX_CHARACTERS = 40;
    /* represents the percentage of spacing applied between the label 
        and the jcombobox in relation to the height of the view */
    private static final double VERTICAL_SPACING_RATIO = 0.02;

    private final JPanel panel;
    private final JLabel lbl;
    private final JComboBox<T> cmb;

    /**
     * Create a panel containing the label explaining the contents of the combobox
     * and the corresponding combobox.
     * Set the combo box with the data to be displayed and render it for
     * visualization
     * using the {@code wrapTextHTML} method.
     * 
     * @param lblText    the description to add to the label explaining
     *                   what the items in the combo box represent
     * @param listObject represents the list of items to be added to the combobox
     *                   and can be of any type
     * @param viewDim    the size of the view.
     *  It is used to calculate the vertical spacing between jlabel and jcombobox.
     * 
     * @throws NullPointerException     if listObjetc or viewDim are null
     * @throws IllegalArgumentException if listObject is an empty list
     */
    public ComboboxWithLabel(final String lblText, final List<T> listObject, Dimension viewDim) {

        Objects.requireNonNull(listObject, "listObject cannot be null.");
        Objects.requireNonNull(viewDim, "viewDim cannot be null.");

        if (listObject.isEmpty()) {
            throw new IllegalArgumentException("listObject cannot be empty.");
        }

        /* The spacing between label and jcombobx.
         * The cast to int is done because it works with pixels */
        final int spacingY = (int) (viewDim.height * VERTICAL_SPACING_RATIO);

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

        this.lbl = new JLabel(lblText);
        this.lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.lbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.cmb = new JComboBox<>();

        for (final T o : listObject) {
            cmb.addItem(o);
        }

        this.panel.add(lbl);
        this.panel.add(Box.createRigidArea(
            new Dimension(0, spacingY)));
        this.panel.add(cmb);

        // creates a custom render of the combobox to display the content following the
        // wrapTextHTML logic
        cmb.setRenderer(new DefaultListCellRenderer() {
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
     * @return the panel containing the label and combobox.
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * @return the combobox added to panel.
     */
    public JComboBox<T> getComboBox() {
        return this.cmb;
    }

    /**
     * @return the label added to the panel.
     */
    public JLabel getLabel() {
        return this.lbl;
    }

    /**
     * @return the selected item in the combo box.
     */
    public T getSelectedItem() {
        final int index = this.cmb.getSelectedIndex();
        return this.cmb.getItemAt(index);
    }
}
