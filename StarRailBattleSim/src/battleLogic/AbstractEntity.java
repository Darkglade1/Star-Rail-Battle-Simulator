package battleLogic;

import powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;

public abstract class AbstractEntity implements BattleEvents,BattleParticipant {
    public String name;
    public int baseSpeed;
    public ArrayList<AbstractPower> powerList = new ArrayList<>();
    public static final int SPEED_PRIORITY_DEFAULT = 99;
    public int speedPriority = SPEED_PRIORITY_DEFAULT;
    protected int numTurnsMetric = 0;
    private IBattle battle;

    public void setBattle(IBattle battle) {
        this.battle = battle;
    }

    @Override
    public IBattle getBattle() {
        return this.battle;
    }

    private final Collection<BattleEvents> listeners = new ConcurrentLinkedQueue<>();

    protected Collection<BattleEvents> getListeners() {
        return listeners;
    }

    public void emit(Consumer<BattleEvents> event) {
        synchronized (this.listeners) {
            this.listeners.forEach(event);

            // Character itself should be last to receive the event, as buffs have to be applied first.
            event.accept(this);
        }
    }

    public float getBaseAV() {
        return (float)10000 / baseSpeed;
    }

    public void takeTurn() {
        resetSpeedPriority(); //reset speed priority if it was changed
    }

    public void addPower(AbstractPower power) {
        for (AbstractPower ownedPowers : powerList) {
            if (ownedPowers.name.equals(power.name)) {
                if (ownedPowers.maxStacks > 0 && ownedPowers.stacks < ownedPowers.maxStacks) {
                    ownedPowers.stacks++;
                    ownedPowers.turnDuration = power.turnDuration;
                    getBattle().addToLog(name + " stacked " + power.name + " to " + ownedPowers.stacks);
                } else {
                    if (!ownedPowers.lastsForever) {
                        ownedPowers.turnDuration = power.turnDuration;
                        if (power.justApplied) {
                            ownedPowers.justApplied = true;
                        }
                        getBattle().addToLog(name + " refreshed " + power.name + " (" + power.turnDuration + " turn(s))");
                    }
                }
                return;
            }
        }
        powerList.add(power);
        power.owner = this;
        if (inBattle()) {
            getBattle().addToLog(name + " gained " + power.name);
        }
        this.listeners.add(power);
    }

    public void removePower(AbstractPower power) {
        power.onRemove();
        powerList.remove(power);
        getBattle().addToLog(name + " lost " + power.name);
        this.listeners.remove(power);
    }

    public void removePower(String name) {
        for (AbstractPower power : powerList) {
            if (power.name.equals(name)) {
                removePower(power);
                return;
            }
        }
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

    public String toString() {
        return name;
    }

    public void resetSpeedPriority() {
        speedPriority = SPEED_PRIORITY_DEFAULT;
    }
}
