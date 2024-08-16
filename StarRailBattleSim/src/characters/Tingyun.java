package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Tingyun extends AbstractCharacter {
    private AbstractCharacter benefactor;
    public int skillProcs = 0;
    public int talentProcs = 0;
    private String skillProcsMetricName = "Skill Extra Damage Procs";
    private String talentProcsMetricName = "Talent Extra Damage Procs";

    public Tingyun() {
        super("Tingyun", 847, 529, 397, 112, 80, ElementType.LIGHTNING, 130, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.DEF_PERCENT, 22.5f)
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 8));
    }

    public void useSkill() {
        super.useSkill();
        TingyunSkillPower skillPower = new TingyunSkillPower();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS) {
                benefactor = character;
                character.addPower(skillPower);
            }
        }

        TempPower speedPower = TempPower.create(PowerStat.SPEED_PERCENT, 20, 1, "Tingyun Skill Speed Power");
        speedPower.justApplied = true;
        Battle.battle.IncreaseSpeed(this, speedPower);
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        BattleHelpers.hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        if (benefactor != null) {
            talentProcs++;
            BattleHelpers.tingyunSkillHitEnemy(benefactor, enemy, 0.66f, BattleHelpers.MultiplierStat.ATK);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS && character.currentEnergy < character.maxEnergy) {
                character.increaseEnergy(60, false);
                character.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 56, 2, "Tingyun Ult Damage Bonus"));
                break;
            }
        }
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && (benefactor == null || (lastMove(MoveType.BASIC) && lastMoveBefore(MoveType.BASIC)))) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void onTurnStart() {
        super.onTurnStart();
        increaseEnergy(5);
        if (currentEnergy >= ultCost) {
            useUltimate();
        }
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

        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            skillProcs++;
            AbstractEnemy target = enemiesHit.get(Battle.battle.getRandomEnemyRng.nextInt(enemiesHit.size()));
            BattleHelpers.tingyunSkillHitEnemy(character, target, 0.64f, BattleHelpers.MultiplierStat.ATK);
        }

        public void onUseUltimate() {
            if (benefactor != null) {
                Battle.battle.IncreaseSpeed(benefactor, TempPower.create(PowerStat.SPEED_PERCENT, 20, 1, "Tingyun E1 Speed Power"));
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
        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == DamageType.BASIC) {
                    return 40;
                }
            }
            return 0;
        }
    }
}
