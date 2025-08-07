package api.others;

public interface PlayerInRound {
    String getName();

    boolean isCPU();

    int getChestGems();

    int getSackGems();

    void addSackGems(int gems);

    void addSackToChest();

    boolean hasLeft();

    void resetSack();

    void leave();

    void resetRoundPlayer();
}
