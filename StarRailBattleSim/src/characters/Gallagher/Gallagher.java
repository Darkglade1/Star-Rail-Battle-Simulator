package characters.Gallagher;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysBasicGoal;
import characters.goal.shared.AlwaysUltGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;

public class Gallagher extends AbstractCharacter<Gallagher> {
    public static String NAME = "Gallagher";

    private boolean isEnhanced = false;

    public Gallagher() {
        super(NAME, 1305, 529, 441, 98, 80, ElementType.FIRE, 110, 100,  Path.ABUNDANCE);

        this.addPower(new TracePower()
                .setStat(PowerStat.HP_PERCENT, 18)
                .setStat(PowerStat.BREAK_EFFECT, 13.3f)
                .setStat(PowerStat.EFFECT_RES, 28));
        this.hasAttackingUltimate = true;

        this.registerGoal(0, new AlwaysBasicGoal<>(this));
        this.registerGoal(0, new AlwaysUltGoal<>(this));
    }

    @Override
    public void useSkill() {
        // Healing is not implemented
    }

    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        if (isEnhanced) {
            getBattle().getHelper().hitEnemy(this, enemy, 2.75f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT * 3);
            AbstractPower atkDebuff = new TempPower();
            atkDebuff.type = AbstractPower.PowerType.DEBUFF;
            atkDebuff.turnDuration = 2;
            atkDebuff.name = "Gallagher Atk Debuff";
            enemy.addPower(atkDebuff);
            isEnhanced = false;
        } else {
            getBattle().getHelper().hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        getBattle().getHelper().PreAttackLogic(this, types);

        for (AbstractEnemy enemy : getBattle().getEnemies()) {
            getBattle().getHelper().hitEnemy(this, enemy, 1.65f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
            AbstractPower besotted = new Besotted();
            enemy.addPower(besotted);
            isEnhanced = true;
        }
        getBattle().AdvanceEntity(this, 100);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    /*public void takeTurn() {
        super.takeTurn();
        useBasic();
    }*/

    public void onCombatStart() {
        increaseEnergy(20);
        PermPower e6buff = new PermPower("Gallagher E6 Buff");
        e6buff.setStat(PowerStat.BREAK_EFFECT, 20);
        e6buff.setStat(PowerStat.WEAKNESS_BREAK_EFF, 20);
        addPower(e6buff);
    }

    private static class Besotted extends AbstractPower {
        public Besotted() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 3;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.BREAK)) {
                return 13.2f;
            }
            return 0;
        }
    }
}
