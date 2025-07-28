package impl.player;

import api.player.PlayerInRound;

public class RealPlayer extends PlayerInRound {

    /*Constructor*/
    public RealPlayer(final String name) {
        super(name);
    }

    /* To be modified */
    public void choose(final boolean choice) {
        this.choice = choice;
    }
    
}
