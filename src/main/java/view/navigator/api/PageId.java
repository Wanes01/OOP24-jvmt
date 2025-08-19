package view.navigator.api;

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
    /**
     * Id for the menu page.
     */
    MENU,
    /**
     * Id for the settings page.
     */
    SETTINGS,
    /**
     * Id for the round page.
     * The round page is the one that
     * will show the game progression.
     */
    ROUND,
    /**
     * Id for the choise page.
     * A page that makes one player
     * make a choise about the game.
     */
    CHOISE,
    /**
     * Id for the leaderboard page.
     */
    LEADERBOARD
}
