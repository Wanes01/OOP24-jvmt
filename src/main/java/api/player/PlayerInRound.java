package api.player;

public abstract class PlayerInRound implements Player {

    protected final String name;
    protected int chestGems;
    protected int sackGems;
    protected boolean choice;
    protected boolean inGame;

    /*Method that returns a player's name*/
    @Override
    public String getName() {
        return this.name;
    }

    /**Method that returns the amount of gems in a player's chest*/
    @Override
    public int getChestGems() {
        return this.chestGems;
    }

    /*Constructor*/
    protected PlayerInRound(final String name) {
        this.name = name;
        this.chestGems = 0;
        this.sackGems = 0;
        this.inGame = true;
    }

    /*Method returns a player's status*/
    public boolean hasLeft() {
        return !this.inGame;
    }

    /*Method returns a player's choice*/
    public boolean getChoice() {
        return this.choice;
    }
    
    /*Method that returns the amount of gems in a player's sack*/
    public int getSackGems() {
        return this.sackGems;
    }
    
    /*Method that adds gems to a player's sack*/
    public void addSackGems(final int gems) {
        this.sackGems += gems;
    }

    /*Method that resets the gems in a player's sack*/
    public void resetSack() {
        this.sackGems = 0;
    }

    /*Method that adds the sack's gems to a player's chest*/
    public void addSackToChest() {
        this.chestGems += this.sackGems;
        resetSack();
    }

    /*Method that resets a player's status in a round*/
    public void resetStatus() {
        this.inGame = true;
    }

    /*Method for resetting a player to their default after the end of a round*/
    public void resetRoundPlayer() {
        resetSack();
        resetStatus();
    }

}
