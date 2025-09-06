package jvmt.view.navigator.api;

/**
 * The IDs associated with a particular page in the navigator.
 * Each page that has to be registered in the navigator must
 * have a corresponding ID.
 * 
 * @see PageNavigator
 * 
 * @author Emir Wanes Aouioua
 */
public enum PageId {
    MENU("Welcome to Javamant!"),
    SETTINGS("Game settings"),
    GAMEPLAY("Cave exploration"),
    LEADERBOARD("Final results");

    private final String pageTitle;

    /**
     * Associate each page ID with the title that the window should display when
     * visiting the page.
     * 
     * @param title the title to be shown when navigating to the page associated
     *              with this {@code PageId}
     */
    PageId(final String title) {
        this.pageTitle = title;
    }

    /**
     * Returns the title bound to this page.
     * 
     * @return the title that should be displayed when navigating
     *         to to page bound to this {@code PageId}.
     */
    public String getPageTitle() {
        return this.pageTitle;
    }
}
