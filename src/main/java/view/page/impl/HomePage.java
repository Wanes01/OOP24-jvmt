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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import view.page.api.SwingPage;
import view.page.utility.GuiScaler;

/**
 * Represents the home page of the application.
 * Displays the game logo and a button to access game options.
 * 
 * @author Andrea La Tosa
 */
public class HomePage extends SwingPage {

    private static final long serialVersionUID = 1L;

    // Layout ratios used to scale spacing, component sizes, 
    // and font sizes relative to the current window dimensions.
    private static final double TOP_SPACING_RATIO = 0.05;
    private static final double COMPONENT_SPACING_RATIO = 0.04;
    private static final double LOGO_SIZE_RATIO = 0.5;
    private static final double BTN_WIDTH_RATIO = 0.15;
    private static final double BTN_HEIGHT_RATIO = 0.15;
    private static final double BTN_FONT_RATIO = 0.06;

    private static final URL LOGO_IMAGE_PATH = HomePage.class.getResource("/imageCard/logo/Diamant_Logo.png");

    private final Dimension winDim;

    private JButton btnStartGame;

    /**
     * Builds the home page display and adds it to the panel.
     * 
     * @param winDim the width and height dimensions of the window
     */
    public HomePage(final Dimension winDim) {
        super(winDim);

        this.winDim = new Dimension(winDim);
        Objects.requireNonNull(winDim, "viewDim cannot be null.");

        final JPanel mainPanel = createMainPanel();
        super.add(mainPanel);
    }

    /**
     * @return the panel to be displayed on the home page.
     */
    private JPanel createMainPanel() {

        final GuiScaler guiScaler = new GuiScaler(this.winDim);

        // The spacing at the top of the page and between objects
        final int topSpacingY = guiScaler.scaleHeight(TOP_SPACING_RATIO);
        final int componentSpacingY = guiScaler.scaleHeight(COMPONENT_SPACING_RATIO);

        final int logoSize = guiScaler.scaleHeight(LOGO_SIZE_RATIO); 

        final int btnWidth = guiScaler.scaleWidth(BTN_WIDTH_RATIO);
        final int btnHeight = guiScaler.scaleHeight(BTN_HEIGHT_RATIO);
        final int btnFontSize = guiScaler.scaleHeight(BTN_FONT_RATIO);
        final Font btnFont = new Font("Arial", Font.BOLD, btnFontSize);

        final JPanel mainPanel;
        JLabel labelLogo;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create an image of the specified size
        try {
            final Image image = ImageIO.read(LOGO_IMAGE_PATH);
            final Image scaledImage = 
                image.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
            final ImageIcon imageResized = new ImageIcon(scaledImage);
            labelLogo = new JLabel(imageResized);
        } catch (final IOException e) {
            labelLogo = new JLabel("Logo image not found");
        }

        labelLogo.setAlignmentX(CENTER_ALIGNMENT);

        btnStartGame = new JButton("START GAME");
        btnStartGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnStartGame.setFont(btnFont);
        btnStartGame.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(
            Box.createRigidArea(new Dimension(0, topSpacingY)));
        mainPanel.add(labelLogo);
        mainPanel.add(
            Box.createRigidArea(new Dimension(0, componentSpacingY)));
        mainPanel.add(btnStartGame);

        return mainPanel;
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
}
