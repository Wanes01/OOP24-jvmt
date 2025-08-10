package model.others.api;

public interface RelicCard extends CardWithGems {
    // controlla che la reliquia è già stata riscattata
    boolean isAlreadyTaken();

    // imposta la reliquia come riscattata
    void setAsTaken();

    // imposta la reliquia come disponibile
    void setAsAvailable();
}
