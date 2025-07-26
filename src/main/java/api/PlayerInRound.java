package api;

public interface PlayerInRound extends Player {
    
    int getSackGems();

    void addSackGems(int gems);

    void addSackToChest();

    boolean hasLeft();

    void resetSack();
}
