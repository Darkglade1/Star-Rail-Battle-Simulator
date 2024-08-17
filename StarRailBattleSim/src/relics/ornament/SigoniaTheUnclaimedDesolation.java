package relics.ornament;

import characters.AbstractCharacter;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;

/**
 * Enemies don't actually die, so the CD boost can't be implemented.
 * You may pass a % uptime.
 */
public class SigoniaTheUnclaimedDesolation extends AbstractRelicSetBonus {

    private final float CD_BOOST;

    public SigoniaTheUnclaimedDesolation(AbstractCharacter owner, boolean fullSet) {
        this(owner, fullSet, 1);
    }

    public SigoniaTheUnclaimedDesolation(AbstractCharacter owner) {
        this(owner, 1);
    }

    public SigoniaTheUnclaimedDesolation(AbstractCharacter owner, boolean fullSet, float uptime) {
        super(owner, fullSet);

        this.CD_BOOST = Math.max(0, Math.min(1, uptime));
    }

    public SigoniaTheUnclaimedDesolation(AbstractCharacter owner, float uptime) {
        super(owner);

        this.CD_BOOST = Math.max(0, Math.min(1, uptime));
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_CHANCE, 4, "Sigonia The Unclaimed Desolation CR Boost"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 10 * this.CD_BOOST, "Sigonia The Unclaimed Desolation CD Boost"));
    }
}
