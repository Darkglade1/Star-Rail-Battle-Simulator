package characters.tingyun;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Tingyun extends AbstractCharacter<Tingyun> {
    public static String NAME = "Tingyun";

    AbstractCharacter<?> benefactor;
    public int skillProcs = 0;
    public int talentProcs = 0;
    private String skillProcsMetricName = "Skill Extra Damage Procs";
    private String talentProcsMetricName = "Talent Extra Damage Procs";

    public Tingyun() {
        super(NAME, 847, 529, 397, 112, 80, ElementType.LIGHTNING, 130, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.DEF_PERCENT, 22.5f)
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 8));

        this.registerGoal(0, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new TingyunTurnGoal(this));
    }

    public void useSkill() {
        TingyunSkillPower skillPower = new TingyunSkillPower();
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            if (character.isDPS) {
                benefactor = character;
                character.addPower(skillPower);
                break;
            }
        }

        TempPower speedPower = TempPower.create(PowerStat.SPEED_PERCENT, 20, 1, "Tingyun Skill Speed Power");
        speedPower.justApplied = true;
        getBattle().IncreaseSpeed(this, speedPower);
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        if (benefactor != null) {
            talentProcs++;
            getBattle().getHelper().tingyunSkillHitEnemy(benefactor, enemy, 0.66f, BattleHelpers.MultiplierStat.ATK);
        }

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            if (character.isDPS && character.currentEnergy < character.maxEnergy) {
                character.increaseEnergy(60, false);
                character.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 56, 2, "Tingyun Ult Damage Bonus"));
                break;
            }
        }
    }

    public void onTurnStart() {
        increaseEnergy(5);
        tryUltimate();
    }

    public void useTechnique() {
        increaseEnergy(maxEnergy, false);
    }

    public void onCombatStart() {
        addPower(new TingyunBonusBasicDamagePower());
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(skillProcsMetricName, String.valueOf(skillProcs));
        map.put(talentProcsMetricName, String.valueOf(talentProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(skillProcsMetricName);
        list.add(talentProcsMetricName);
        return list;
    }

    private class TingyunSkillPower extends TempPower {
        public TingyunSkillPower() {
            super(3);

            this.name = this.getClass().getSimpleName();
            this.setStat(PowerStat.ATK_PERCENT, 55);
        }

        public void onAttack(AbstractCharacter<?> character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            skillProcs++;
            AbstractEnemy target = enemiesHit.get(getBattle().getGetRandomEnemyRng().nextInt(enemiesHit.size()));
            getBattle().getHelper().tingyunSkillHitEnemy(character, target, 0.64f, BattleHelpers.MultiplierStat.ATK);
        }

        public void onUseUltimate() {
            if (benefactor != null) {
                getBattle().IncreaseSpeed(benefactor, TempPower.create(PowerStat.SPEED_PERCENT, 20, 1, "Tingyun E1 Speed Power"));
            }
        }

        public void onRemove() {
            benefactor = null;
        }
    }

    private static class TingyunBonusBasicDamagePower extends AbstractPower {
        public TingyunBonusBasicDamagePower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }
        @Override
        public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == DamageType.BASIC) {
                    return 40;
                }
            }
            return 0;
        }
    }
}
