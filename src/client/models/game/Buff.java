package client.models.game;

import java.util.ArrayList;

public class Buff {
    private BuffType type;
    private int numberOfChanges;
    private int turnsRemaining;
    private ArrayList<Troop> targetTroops;

    public BuffType getType() {
        return this.type;
    }

    public int getNumberOfChanges() {
        return this.numberOfChanges;
    }

    public int getTurnsRemaining() {
        return this.turnsRemaining;
    }

    public ArrayList<Troop> getTargetTroops() {
        return this.targetTroops;
    }
}