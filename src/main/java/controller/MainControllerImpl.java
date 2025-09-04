package controller;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import controller.api.MainController;
import controller.api.PageController;
import controller.impl.GameplayControllerImpl;
import controller.impl.HomeControllerImpl;
import controller.impl.LeaderboardControllerImpl;
import controller.impl.SettingsControllerImpl;
import model.game.api.Game;
import model.game.api.GameSettings;
import model.game.impl.GameImpl;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.navigator.impl.PageNavigatorImpl;
import view.page.api.Page;
import view.page.impl.GameplayPage;
import view.page.impl.HomePage;
import view.page.impl.LeaderboardPage;
import view.page.impl.SettingsPage;
import view.window.api.Window;
import view.window.impl.SwingWindow;

/**
 * Concrete implementation of {@link MainController}.
 * <p>
 * This class is responsible for creating and storing application's
 * {@link Page}s,
 * setting up a {@link PageNavigator} for page transitions, initializing the
 * bound
 * {@link PageController} for each page and starting the application by
 * displaying
 * the main window and navigating to the initial page.
 * </p>
 * <p>
 * This implementation uses swing-based implementations of {@code Window}
 * and {@code Page}.
 * </p>
 * <p>
 * <strong>Note:</strong> the controllers for certain pages are
 * initialized only after the game settings are available through
 * {@link GameSettings}.
 * </p>
 * 
 * @see Window
 * @see Page
 * @see PageController
 * @see PageNavigator
 * 
 * @author Emir Wanes Aouioua
 * 
 */
public class MainControllerImpl implements MainController {

    // the main application window.
    private final Window window;
    // the navigator responsible of page transitions.
    private final PageNavigator navigator;
    // the pages that can be browsed.
    private final Map<PageId, Page> pages;
    // the controllers bound to the pages.
    private final Map<PageId, PageController> controllers = new EnumMap<>(PageId.class);

    private Optional<Game> game = Optional.empty();

    /**
     * Constructs the {@code MainControllerImpl}.
     * Initializes the window, the navigator, the pages
     * and the controllers that do not require user input.
     */
    public MainControllerImpl() {
        this.window = new SwingWindow();
        this.navigator = new PageNavigatorImpl(this.window);
        this.pages = this.createPages();
        this.setupNavigator();
        this.createStartupControllers();
    }

    /**
     * Creates all the pages in the application and returns them
     * as a map linking each {@link PageId} to a specific page.
     * 
     * @return a map containing all the application pages.
     */
    private Map<PageId, Page> createPages() {
        return Map.of(
                PageId.MENU, new HomePage(this.window.getDimension()),
                PageId.SETTINGS, new SettingsPage(this.window.getDimension()),
                PageId.ROUND, new GameplayPage(this.window.getDimension(), (SwingWindow) this.window),
                PageId.LEADERBOARD, new LeaderboardPage(this.window.getDimension()));
    }

    /**
     * Registers all pages into the navigator.
     */
    private void setupNavigator() {
        this.pages.forEach(this.navigator::registerPage);
    }

    /**
     * Sets up the controllers that do not need user input
     * to be configured and binds those controllers to the
     * correspondig pages.
     */
    private void createStartupControllers() {
        final Page home = this.pages.get(PageId.MENU);
        final Page settings = this.pages.get(PageId.SETTINGS);
        final PageController homeController = new HomeControllerImpl(home, navigator);
        final PageController settingsController = new SettingsControllerImpl(
                settings,
                navigator,
                this::finishControllersSetup);

        this.controllers.putAll(
                Map.of(
                        PageId.MENU, homeController,
                        PageId.SETTINGS, settingsController));

        home.setController(homeController);
        settings.setController(settingsController);
    }

    /**
     * Completes the setup of the controllers that depend on
     * {@link GameSettings} after user's input, such as
     * the gameplay and leaderboard controllers.
     * 
     * @param settings
     */
    private void finishControllersSetup(final GameSettings settings) {
        this.game = Optional.of(new GameImpl(settings));

        final Page gameplay = (GameplayPage) pages.get(PageId.ROUND);
        final Page leaderboard = (LeaderboardPage) pages.get(PageId.LEADERBOARD);

        final PageController gameplayController = new GameplayControllerImpl(
                gameplay,
                navigator,
                this.game.get(),
                () -> {
                    final PageController leaderboardController = new LeaderboardControllerImpl(
                            leaderboard,
                            navigator,
                            this.game.get());
                    leaderboard.setController(leaderboardController);
                    controllers.put(PageId.LEADERBOARD, leaderboardController);
                });

        controllers.put(PageId.ROUND, gameplayController);
        gameplay.setController(gameplayController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startApp() {
        this.navigator.navigateTo(PageId.MENU);
        window.display();
    }

}
