package view.page.utility;

import java.awt.Component;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListCellRenderer;

/**
 * A generic Swing component that displays a {@code JLabel} above a
 * {@code JComboBox}.
 * This class is useful for quickly creating selection fields with an associated
 * description.
 * 
 * @param <T> the type of elements used to populate the combobox
 * 
 * @author Andrea La Tosa
 */
@SuppressFBWarnings(value = { "EI_EXPOSE_REP",
        "EI_EXPOSE_REP2" }, justification = "The values returned by this object can be modified externally.")
public final class ComboboxWithLabel<T> extends JComboBox<T> {

    private static final long serialVersionUID = 1L;

    private static final int MAX_CHARACTERS = 40;

    /** The panel containing the descriptive label and the combobox. */
    private final JPanel panel;
    /** The label describing the contents of the combobox. */
    private final JLabel lbl;

    /**
     * Create a panel containing the label explaining the contents of the combobox
     * and the corresponding combobox.
     * 
     * @param lblText the description to add to the label explaining
     *                what the items in the combobox represent
     * 
     * @throws NullPointerException if lblText is null
     */
    public ComboboxWithLabel(final String lblText) {
        Objects.requireNonNull(lblText, "lblText cannot be null.");

        this.panel = new JPanel(
                new MigLayout(
                        "fillx, wrap 1, insets 0",
                        "[center]"));

        this.lbl = new JLabel(lblText);

        this.panel.add(lbl, "gapbottom rel");
        this.panel.add(this, "growx");

        // creates a custom render of the combobox to display the content following the
        // wrapTextHTML logic in HtmlUtil
        this.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                final Optional<Object> optionalValue = Optional.ofNullable(value);
                if (optionalValue.isPresent()) {
                    final String htmlText = HtmlUtils.wrapTextHTML(value.toString(), MAX_CHARACTERS);
                    label.setText(htmlText);
                } else {
                    label.setText("");
                }
                return label;
            }
        });
    }

    /**
     * Adds a list of items to the combobox.
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
     * Returns the panel containing the label and combobox.
     * 
     * @return the panel.
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Returns the combobox added to panel.
     * 
     * @return the combobox.
     */
    public JComboBox<T> getComboBox() {
        return this;
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
     * Returns the selected item in the combobox.
     * 
     * @return the selected item in the combobox.
     */
    @Override
    public T getSelectedItem() {
        final int index = this.getSelectedIndex();
        return this.getItemAt(index);
    }
}
