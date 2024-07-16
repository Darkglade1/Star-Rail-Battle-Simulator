package battleLogic;

import powers.AbstractPower;

import java.util.ArrayList;

public abstract class AbstractEntity {
    public String name;
    public int baseSpeed;
    public ArrayList<AbstractPower> powerList = new ArrayList<>();

    public abstract float getBaseAV();

    public abstract void takeTurn();

    public void addPower(AbstractPower power) {
        for (AbstractPower ownedPowers : powerList) {
            if (ownedPowers.name.equals(power.name)) {
                if (ownedPowers.stackable && ownedPowers.stacks < ownedPowers.maxStacks) {
                    ownedPowers.stacks++;
                    ownedPowers.turnDuration = power.turnDuration;
                    Battle.battle.addToLog("Stacked " + power.name + " to " + ownedPowers.stacks);
                } else {
                    ownedPowers.turnDuration = power.turnDuration;
                    Battle.battle.addToLog("Refreshed " + power.name);
                }
                return;
            }
        }
        powerList.add(power);
        Battle.battle.addToLog("Gained " + power.name);
    }

    public void removePower(AbstractPower power) {
        powerList.remove(power);
        Battle.battle.addToLog("Removed " + power.name);
    }
}
