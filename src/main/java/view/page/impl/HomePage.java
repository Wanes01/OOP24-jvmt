package view.page.impl;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.api.HomeController;
import controller.impl.HomeControllerImpl;
import controller.impl.SettingsControllerImpl;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import view.navigator.api.PageNavigator;
import view.page.api.SwingPage;

/**
 * Represents the home page of the application.
 * Displays the game logo and a button to access game options.
 * 
 * @author Andrea La Tosa
 */
public class HomePage extends SwingPage {

    private static final long serialVersionUID = 1L;

    // The spacing at the top of the page and between objects
    private static final Dimension TOP_SPACING = new Dimension(0, 50);
    private static final Dimension COMPONENT_SPACING = new Dimension(0, 40);

    private static final Dimension BTN_DIM = new Dimension(100, 150);
    private static final Font BTN_FONT = new Font("Arial", Font.BOLD, 50);

    private static final URL LOGO_IMAGE_PATH = HomePage.class.getResource("/imageCard/logo/Diamant_Logo.png");
    private static final int LOGO_WIDTH = 400;
    private static final int LOGO_HEIGHT = 400;

    private JButton btnStartGame;

    /**
     * Builds the home page display and adds it to the panel.
     */
    public HomePage(Dimension winDim) {
        super(winDim);
        final JPanel mainPanel = createMainPanel();
        super.add(mainPanel);
    }

    /**
     * @return the panel to be displayed on the home page.
     */
    private JPanel createMainPanel() {

        final JPanel mainPanel;
        JLabel labelLogo;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create an image of the specified size
        try {
            final Image image = ImageIO.read(LOGO_IMAGE_PATH);
            final Image scaledImage = image.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);
            final ImageIcon imageResized = new ImageIcon(scaledImage);
            labelLogo = new JLabel(imageResized);
        } catch (final IOException e) {
            labelLogo = new JLabel("Logo image not found");
        }

        labelLogo.setAlignmentX(CENTER_ALIGNMENT);

        btnStartGame = new JButton("START GAME");
        btnStartGame.setPreferredSize(BTN_DIM);
        btnStartGame.setFont(BTN_FONT);
        btnStartGame.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(TOP_SPACING));
        mainPanel.add(labelLogo);
        mainPanel.add(Box.createRigidArea(COMPONENT_SPACING));
        mainPanel.add(btnStartGame);

        return mainPanel;
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
}
