package impl.player;

import java.util.Random;

import api.player.PlayerInRound;

public class PlayerCPU extends PlayerInRound {

    /*Constructor*/
    public PlayerCPU(final String name) {
        super(name);
    }

    /* To be modified */
    public void choose() {
        Random rand = new Random();
        this.choice = rand.nextBoolean();
    }
}
