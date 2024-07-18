package battleLogic;

import powers.AbstractPower;

import java.util.ArrayList;

public abstract class AbstractEntity {
    public String name;
    public int baseSpeed;
    public ArrayList<AbstractPower> powerList = new ArrayList<>();
    public int speedPriority = 99;
    protected int numTurnsMetric = 0;

    public float getBaseAV() {
        return (float)10000 / baseSpeed;
    }

    public void takeTurn() {

    }

    public void addPower(AbstractPower power) {
        for (AbstractPower ownedPowers : powerList) {
            if (ownedPowers.name.equals(power.name)) {
                if (ownedPowers.maxStacks > 0 && ownedPowers.stacks < ownedPowers.maxStacks) {
                    ownedPowers.stacks++;
                    ownedPowers.turnDuration = power.turnDuration;
                    Battle.battle.addToLog(name + " stacked " + power.name + " to " + ownedPowers.stacks);
                } else {
                    if (!ownedPowers.lastsForever) {
                        ownedPowers.turnDuration = power.turnDuration;
                        Battle.battle.addToLog(name + " refreshed " + power.name + " (" + power.turnDuration + " turn(s))");
                    }
                }
                return;
            }
        }
        powerList.add(power);
        Battle.battle.addToLog(name + " gained " + power.name);
    }

    public void removePower(AbstractPower power) {
        power.onRemove();
        powerList.remove(power);
        Battle.battle.addToLog(name + " lost " + power.name);
    }

    public boolean hasPower(String powerName) {
        for (AbstractPower power : powerList) {
            if (power.name.equals(powerName)) {
                return true;
            }
        }
        return false;
    }

    public AbstractPower getPower(String powerName) {
        for (AbstractPower power : powerList) {
            if (power.name.equals(powerName)) {
                return power;
            }
        }
        return null;
    }

    public void onTurnStart() {

    }

    public String toString() {
        return name;
    }
}
