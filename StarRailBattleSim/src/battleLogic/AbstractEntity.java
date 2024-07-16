package battleLogic;

import powers.AbstractPower;

import java.util.ArrayList;

public abstract class AbstractEntity {
    public String name;
    public int baseSpeed;
    public ArrayList<AbstractPower> powerList = new ArrayList<>();
    public int speedPriority = 99;

    public abstract float getBaseAV();

    public abstract void takeTurn();

    public void addPower(AbstractPower power) {
        for (AbstractPower ownedPowers : powerList) {
            if (ownedPowers.name.equals(power.name)) {
                if (ownedPowers.maxStacks > 0 && ownedPowers.stacks < ownedPowers.maxStacks) {
                    ownedPowers.stacks++;
                    ownedPowers.turnDuration = power.turnDuration;
                    Battle.battle.addToLog("Stacked " + power.name + " to " + ownedPowers.stacks);
                } else {
                    if (!ownedPowers.lastsForever) {
                        ownedPowers.turnDuration = power.turnDuration;
                        Battle.battle.addToLog("Refreshed " + power.name);
                    }
                }
                return;
            }
        }
        powerList.add(power);
        Battle.battle.addToLog(name + " gained " + power.name);
    }

    public void removePower(AbstractPower power) {
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
