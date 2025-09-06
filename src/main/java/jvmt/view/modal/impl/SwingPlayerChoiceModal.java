package jvmt.view.modal.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jvmt.model.player.api.PlayerChoice;
import jvmt.utils.CommonUtils;
import jvmt.view.modal.api.Modal;
import jvmt.view.window.impl.SwingWindow;
import jvmt.view.page.utility.ImageButton;

/**
 * A swing-based modal dialog that allows a player to choose to continue or not
 * in a round exploration.
 * <p>
 * This class extends {@link JDialog} and implements {@link Modal} to provide
 * a blocking GUI over a {@link SwingWindow} that requires the player to make
 * a choice before restoring the window interagibility. The available choices
 * are defined in {@link PlayerChoice}.
 * </p>
 * <p>
 * The dialog displays two images representing the player's options:
 * continue in the current round exploration or to leave the round.
 * This modal scales proportionally to the parent {@link SwingWindow}
 * by a fixed factor.
 * </p>
 * <p>
 * <strong>Note:</strong> {@link #waitUserInput()} must be called before
 * calling {@link #getUserInput()} or an
 * {@link IllegalStateException} will be thrown otherwise.
 * </p>
 * 
 * @see Modal
 * @see PlayerChoice
 * @see JDialog
 * @see SwingWindow
 * 
 * @author Emir Wanes Aouioua
 */
public class SwingPlayerChoiceModal extends JDialog implements Modal<PlayerChoice> {

    private static final long serialVersionUID = 1L;

    private static final String TITLE = "Player's choice";
    private static final String IMAGES_PATH = "/imageCard/choises/";
    private static final URL EXIT_URL = SwingPlayerChoiceModal.class.getResource(IMAGES_PATH + "Exit.png");
    private static final URL STAY_URL = SwingPlayerChoiceModal.class.getResource(IMAGES_PATH + "Stay.png");
    // size ratio between this modal and the main window
    private static final double WINDOW_SIZE_FACTOR = 0.58;
    // size ratio between this modal's width and height
    private static final double MODAL_WIDTH_HEIGHT_RATION = 0.8;
    private static final int MARGIN = 30;
    private static final int GAP = 10;

    private transient Optional<PlayerChoice> result = Optional.empty();

    /**
     * Creates a new {@code SwingPlayerChoiceModal} for the given player.
     * 
     * @param parent     the window whose interaction will be disabled while this
     *                   modal is active.
     * @param playerName the name of the player making the choice.
     * @throws NullPointerException     if {@code parent} or {@code playerName} is
     *                                  null.
     * @throws MissingResourceException if any of the used images are missing.
     */
    public SwingPlayerChoiceModal(
            final SwingWindow parent,
            final String playerName) {
        super(parent, TITLE, true);
        CommonUtils.requireNonNulls(parent, playerName);

        if (EXIT_URL == null || STAY_URL == null) {
            throw new MissingResourceException(
                    "One or more missing images in " + IMAGES_PATH,
                    SwingPlayerChoiceModal.class.getName(),
                    IMAGES_PATH);
        }

        final int width = (int) (WINDOW_SIZE_FACTOR * parent.getWidth());
        final int height = (int) (MODAL_WIDTH_HEIGHT_RATION * width);
        super.setSize(width, height);
        super.setLocationRelativeTo(parent);
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // main panel: uses border layout and sets an empty margin
        final JPanel main = new JPanel(new BorderLayout(GAP, GAP));
        main.setBorder(BorderFactory.createEmptyBorder(
                MARGIN, MARGIN, MARGIN, MARGIN));

        // player's data
        final JLabel playerTitle = new JLabel("Player choosing: " + playerName, SwingConstants.CENTER);
        main.add(playerTitle, BorderLayout.NORTH);

        // central panel with the two cards
        final JPanel cardsPanel = new JPanel(new GridLayout(1, 2, MARGIN + GAP, 0));
        final JPanel continueCard = this.createCard(STAY_URL, "Continue the round.", PlayerChoice.STAY);
        final JPanel exitCard = this.createCard(EXIT_URL, "Leave the round.", PlayerChoice.EXIT);
        cardsPanel.add(continueCard);
        cardsPanel.add(exitCard);

        main.add(cardsPanel, BorderLayout.CENTER);
        super.setContentPane(main);
    }

    /**
     * Creates a JPanel that contains the interactive cards
     * to make a player make a choice.
     * If the card image can't be retriven then an alt text
     * will replace it.
     * 
     * @param url         a url representing the image path from the rosource
     *                    folder.
     * @param description a description to put under the card image.
     * @param choice      the choice bound to the card.
     * @return the JPanel containing the clickable image bound to the choice,
     *         followed by the description.
     */
    private JPanel createCard(
            final URL url,
            final String description,
            final PlayerChoice choice) {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        Optional<Image> image;
        try {
            image = Optional.of(ImageIO.read(url));
        } catch (final IOException e) {
            image = Optional.empty();
        }

        final JButton button = this.createChoiceButton(
                image,
                "Image not found. Click here to select: " + choice,
                choice);

        panel.add(button, BorderLayout.CENTER);
        final JLabel cardDescription = new JLabel("<html><div style='text-align:center;'>"
                + description + "</div></html>", SwingConstants.CENTER);
        panel.add(cardDescription, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a JButton, with the specified image into into, or the
     * {@code altText} if the image is not available.
     * Sets an handler over the JButton that sets the result of this modal
     * as {@code choice} and disposes it.
     * 
     * @param image   an {@link Optional} with the image to display on the button.
     * @param altText the text to show id {@code image} is empty.
     * @param choice  the player's choice bound to the button.
     * @return a {@link JButton} with the given image (or altText) that is able to
     *         set the choice and dispose this modal.
     */
    private JButton createChoiceButton(
            final Optional<Image> image,
            final String altText,
            final PlayerChoice choice) {
        final JButton button = image.isPresent()
                ? new ImageButton(image.get())
                : new JButton(altText);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            this.result = Optional.of(choice);
            /*
             * Closes this modal and restores the
             * main window interaction.
             */
            this.dispose();
        });
        return button;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitUserInput() {
        this.setVisible(true);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if called before {@link #waitUserInput()}.
     */
    @Override
    public PlayerChoice getUserInput() {
        if (this.result.isEmpty()) {
            throw new IllegalStateException("No choice was made");
        }
        return this.result.get();
    }

}
