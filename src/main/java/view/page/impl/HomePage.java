package view.page.impl;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import controller.api.HomeController;
import controller.impl.HomeControllerImpl;
import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import view.page.api.SwingPage;
import view.page.utility.ImageLabel;

/**
 * Represents the home page of the application.
 * Displays the game logo and a button to access game options.
 * 
 * @author Andrea La Tosa
 */
public class HomePage extends SwingPage {

    private static final long serialVersionUID = 1L;


    private static final URL LOGO_IMAGE_PATH = 
        HomePage.class.getResource("/imageCard/logo/Diamant_Logo.png");

    private final JButton btnStartGame;
    private ImageLabel labelLogo;

    /**
     * Builds the home page display.
     */
    public HomePage(final Dimension winDim) {
        super(winDim);

        super.setLayout(new MigLayout(
            "fill, wrap 1, insets 0",
            "[center]",
            "push[]paragraph[]push"));

        // load the application logo image if possible
        loadImage(LOGO_IMAGE_PATH).ifPresent(image -> {
            this.labelLogo = new ImageLabel(image);
            this.add(labelLogo, "w 50%, h 50%, align center");
        });

        btnStartGame = new JButton("START GAME");
        super.add(btnStartGame, "w 25%, h 10%, align center, gaptop unrel");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setHandlers() {

        final HomeController homeCtrl = 
            this.getController(HomeControllerImpl.class);

        btnStartGame.addActionListener(e -> {
            homeCtrl.goToSettingPage();
        });

    }

    /**
     * Attempts to load an image from the specified URL.
     * 
     * @param urlImage the URL of the image to load
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
