package characters.bronya;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysSkillGoal;
import characters.goal.shared.AlwaysUltGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;

public class Bronya extends AbstractCharacter<Bronya> {

    public static final String NAME = "Bronya";
    public static final String SKILL_POWER_NAME = "BronyaSkillPower";
    public static final String ULT_POWER_NAME = "BronyaUltPower";

    public Bronya() {
        super(NAME, 1242, 582, 534, 99, 80, ElementType.WIND, 120, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 22.4f)
                .setStat(PowerStat.CRIT_DAMAGE, 24)
                .setStat(PowerStat.EFFECT_RES, 10));

        this.registerGoal(0, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new AlwaysSkillGoal<>(this));
    }

    public void useSkill() {
        AbstractPower skillPower = TempPower.create(PowerStat.DAMAGE_BONUS, 66, 1, SKILL_POWER_NAME);
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            if (character.isDPS) {
                character.addPower(skillPower);
                getBattle().AdvanceEntity(character, 100);
                lightcone.onSpecificTrigger(character, null);
                break;
            }
        }
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        getBattle().AdvanceEntity(this, 30);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            AbstractPower ultPower = new TempPower();
            ultPower.name = ULT_POWER_NAME;
            ultPower.setStat(PowerStat.ATK_PERCENT, 55);
            ultPower.setStat(PowerStat.CRIT_DAMAGE, 20 + (this.getTotalCritDamage() * 0.16f));
            ultPower.turnDuration = 2;
            character.removePower(ultPower.name); // remove the old power in case bronya's crit damage changed so we get new snapshot of her buff
            character.addPower(ultPower);
        }
    }

    public void onCombatStart() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(PermPower.create(PowerStat.DAMAGE_BONUS, 10, "Bronya Trace Damage Bonus"));
        }
        this.addPower(new BronyaBasicCritPower());
    }

    public void useTechnique() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(TempPower.create(PowerStat.ATK_PERCENT, 15, 2, "Bronya Technique Power"));
        }
    }

    private static class BronyaBasicCritPower extends AbstractPower {
        public BronyaBasicCritPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        public float setFixedCritRate(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
            if (damageTypes.contains(DamageType.BASIC)) {
                return 100;
            }
            return currentCrit;
        }
    }
}
