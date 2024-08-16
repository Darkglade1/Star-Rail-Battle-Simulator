package characters;

import battleLogic.AbstractEntity;
import battleLogic.Battle;
import battleLogic.BattleHelpers;
import battleLogic.Concerto;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Robin extends AbstractCharacter {
    PermPower skillPower;
    RobinUltPower ultPower;
    RobinFixedCritPower fixedCritPower;
    private int skillCounter = 0;
    private int allyAttacksMetric = 0;
    private int concertoProcs = 0;
    private String allyAttacksMetricName = "Number of Ally Attacks";
    private String concertoProcsMetricName = "Number of Concerto Hits";
    public static final String NAME = "Robin";
    public static final String ULT_POWER_NAME = "RobinUltPower";

    public Robin() {
        super(NAME, 1281, 640, 485, 102, 80, ElementType.PHYSICAL, 160, 100, Path.HARMONY);

        this.skillEnergyGain = 35;
        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28;
        tracesPower.bonusHPPercent = 18;
        tracesPower.bonusFlatSpeed = 5;
        this.addPower(tracesPower);

        skillPower = new PermPower();
        skillPower.bonusDamageBonus = 50;
        skillPower.name = "Robin Skill Power";

        ultPower = new RobinUltPower();
        fixedCritPower = new RobinFixedCritPower();
    }

    public void useSkill() {
        super.useSkill();
        skillCounter = 3;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(skillPower);
        }
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, 1.0f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        AbstractEntity slowestAlly = null;
        float slowestAV = -1;
        AbstractEntity fastestAlly = null;
        float fastestAV = -1;
        AbstractEntity middleAlly = null;
        for (Map.Entry<AbstractEntity,Float> entry : Battle.battle.actionValueMap.entrySet()) {
            if (entry.getKey() instanceof AbstractCharacter && !(entry.getKey() instanceof Robin)) {
                if (slowestAlly == null) {
                    slowestAlly = entry.getKey();
                    fastestAlly = entry.getKey();
                    middleAlly = entry.getKey();
                    slowestAV = entry.getValue();
                    fastestAV = entry.getValue();
                } else {
                    if (entry.getValue() < fastestAV) {
                        fastestAlly = entry.getKey();
                        fastestAV = entry.getValue();
                    } else if (entry.getValue() > slowestAV) {
                        slowestAlly = entry.getKey();
                        slowestAV = entry.getValue();
                    }
                }
            }
        }
        for (Map.Entry<AbstractEntity,Float> entry : Battle.battle.actionValueMap.entrySet()) {
            if (entry.getKey() instanceof AbstractCharacter && !(entry.getKey() instanceof Robin)) {
                if (entry.getKey() != fastestAlly && entry.getKey() != slowestAlly) {
                    middleAlly = entry.getKey();
                }
            }
        }
        // preserves the order in which allies go next based on their original AVs
        fastestAlly.speedPriority = 1;
        middleAlly.speedPriority = 2;
        slowestAlly.speedPriority = 3;
        Battle.battle.AdvanceEntity(fastestAlly, 100);
        Battle.battle.AdvanceEntity(middleAlly, 100);
        Battle.battle.AdvanceEntity(slowestAlly, 100);
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(ultPower);
        }
        this.addPower(fixedCritPower);
        Battle.battle.actionValueMap.remove(this);
        Concerto concerto = new Concerto(this);
        Battle.battle.actionValueMap.put(concerto, concerto.getBaseAV());
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && skillCounter <= 0) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void onCombatStart() {
        Battle.battle.AdvanceEntity(this, 25);
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(new RobinTalentPower());
        }
    }

    public void onTurnStart() {
        super.onTurnStart();
        if (skillCounter > 0) {
            skillCounter--;
            if (skillCounter <= 0) {
                for (AbstractCharacter character : Battle.battle.playerTeam) {
                    character.removePower(skillPower);
                }
            }
        }
    }

    public void useTechnique() {
        increaseEnergy(5);
    }

    public void addPower(AbstractPower power) {
        super.addPower(power);
        if (ultPower != null) {
            ultPower.updateAtkBuff();
        }
    }

    public void onConcertoEnd() {
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.removePower(ultPower);
        }
        this.removePower(fixedCritPower);
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(allyAttacksMetricName, String.valueOf(allyAttacksMetric));
        map.put(concertoProcsMetricName, String.valueOf(concertoProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(allyAttacksMetricName);
        list.add(concertoProcsMetricName);
        return list;
    }

    private class RobinTalentPower extends AbstractPower {
        public RobinTalentPower() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
            this.bonusCritDamage = 20;
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            Robin.this.increaseEnergy(2);
            Robin.this.allyAttacksMetric++;
        }
    }

    private class RobinUltPower extends AbstractPower {
        public RobinUltPower() {
            this.name = ULT_POWER_NAME;
            lastsForever = true;
        }

        public void updateAtkBuff() {
            float atk = getFinalAttackWithoutConcerto();
            this.bonusFlatAtk = (int)(0.228 * atk) + 200;
        }

        private float getFinalAttackWithoutConcerto() {
            int totalBaseAtk = baseAtk + lightcone.baseAtk;
            float totalBonusAtkPercent = 0;
            int totalBonusFlatAtk = 0;
            for (AbstractPower power : powerList) {
                if (!power.name.equals(this.name)) {
                    totalBonusAtkPercent += power.bonusAtkPercent;
                    totalBonusFlatAtk += power.bonusFlatAtk;
                }
            }
            return (totalBaseAtk * (1 + totalBonusAtkPercent / 100) + totalBonusFlatAtk);
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            AbstractEnemy target = enemiesHit.get(Battle.battle.getRandomEnemyRng.nextInt(enemiesHit.size()));
            BattleHelpers.additionalDamageHitEnemy(Robin.this, target, 1.2f, BattleHelpers.MultiplierStat.ATK);
            concertoProcs++;
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.FOLLOW_UP)) {
                return 25;
            }
            return 0;
        }
    }

    private class RobinFixedCritPower extends AbstractPower {
        public RobinFixedCritPower() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
        }

        public float setFixedCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
            return 100;
        }

        public float setFixedCritDmg(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCritDmg) {
            return 150;
        }
    }
}
