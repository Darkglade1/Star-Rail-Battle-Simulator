package powers;

public class PermPower extends AbstractPower {

    public PermPower() {
        this.lastsForever = true;
    }

    public PermPower(String name) {
        super(name);
        this.lastsForever = true;
    }

    /**
     * Create a new PermPower with the given stat and value, this is a helper method to make it easier to create PermPowers
     * The AbstractPower system is created a bit awkwardly, so this is a workaround
     * @param stat the stat to create the PermPower for, is forwarded most fittingly
     * @param value the value of the stat
     * @param name the name of the power
     * @return the created PermPower
     */
    public static PermPower create(PowerStat stat, float value, String name) {
        PermPower power = new PermPower();
        power.name = name;
        power.setStat(stat, value);
        return power;
    }
}
