package jvmt.view.page.impl;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import jvmt.controller.api.HomeController;
import jvmt.controller.impl.HomeControllerImpl;
import net.miginfocom.swing.MigLayout;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import jvmt.view.page.api.SwingPage;
import jvmt.view.page.utility.ImageLabel;

/**
 * Represents the home page of the application.
 * Displays the game logo and a button to access game options.
 * 
 * @author Andrea La Tosa
 */
public class SwingHomePage extends SwingPage {

    private static final URL LOGO_IMAGE_PATH = SwingHomePage.class.getResource("/imageCard/logo/Diamant_Logo.png");

    /** The button that provides access to the game configuration settings.
        <p>
        When clicked, it navigates the user to the game configuration page,
        which must be completed correctly before starting a new game session.
        </p>  */
    private final JButton btnStartGame;

    /** The label that displays the logo image, if loaded successfully. */
    private ImageLabel labelLogo;

    /**
     * Builds the home page display.
     */
    public SwingHomePage() {

        super.getPanel().setLayout(new MigLayout(
                "fill, wrap 1, insets 0",
                "[center]",
                "push[]paragraph[]push"));

        // load the application logo image if possible
        loadImage(LOGO_IMAGE_PATH).ifPresent(image -> {
            this.labelLogo = new ImageLabel(image);
            super.getPanel().add(labelLogo, "w 50%, h 50%, align center");
        });

        btnStartGame = new JButton("START GAME");
        super.getPanel().add(btnStartGame, "w 25%, h 10%, align center, gaptop unrel");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {

        final HomeController homeCtrl = this.getController(HomeControllerImpl.class);

        btnStartGame.addActionListener(e -> {
            homeCtrl.goToSettingPage();
        });

    }

    /**
     * Attempts to load an image from the specified URL.
     * 
     * @param urlImage the URL of the image to load
     * 
     * @return an Optional containing the loaded Image,
     *         or an empty Optional if the image could not be loaded
     */
    private Optional<Image> loadImage(final URL urlImage) {
        try {
            return Optional.ofNullable(ImageIO.read(urlImage));
        } catch (final IOException ex) {
            return Optional.empty();
        }
    }
}
