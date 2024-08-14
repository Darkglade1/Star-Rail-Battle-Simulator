package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;
import java.util.HashMap;

public class Moze extends AbstractCharacter {
    public int FUAs = 0;
    public int talentProcs = 0;
    private String FUAsMetricName = "Number of Follow Up Attacks Used";
    private String talentProcsMetricName = "Talent Extra Damage Procs";
    private MozePreyPower preyPower;
    private int chargeCount;
    private int MAX_CHARGE = 9;
    private int chargeLost = 0;
    private int CHARGE_ATTACK_THRESHOLD = 3;
    private boolean skillPointRecovered = false;
    public boolean isDeparted = false;

    public Moze() {
        super("Moze", 811, 547, 353, 114, 80, ElementType.LIGHTNING, 120, 75);
        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 18;
        tracesPower.bonusCritDamage = 37.3f;
        tracesPower.bonusHPPercent = 10;
        this.addPower(tracesPower);
        this.hasAttackingUltimate = true;

        preyPower = new MozePreyPower();
    }

    public void useSkill() {
        super.useSkill();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        preyPower.owner = enemy;
        enemy.addPower(preyPower);
        BattleHelpers.hitEnemy(this, enemy, 1.65f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        increaseCharge(MAX_CHARGE);

        BattleHelpers.PostAttackLogic(this, types);
        Battle.battle.actionValueMap.remove(this);
        isDeparted = true;
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

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        TempPower dmgBonus = new TempPower();
        dmgBonus.bonusDamageBonus = 30;
        dmgBonus.turnDuration = 2;
        dmgBonus.name = "Moze Damage Bonus";
        addPower(dmgBonus);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
        }
        BattleHelpers.hitEnemy(this, enemy, 2.7f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_THREE_UNITs);

        BattleHelpers.PostAttackLogic(this, types);

        useFollowUp();
    }

    public void useFollowUp() {
        AbstractEnemy enemy = (AbstractEnemy) preyPower.owner;
        moveHistory.add(MoveType.FOLLOW_UP);
        FUAs++;
        Battle.battle.addToLog(name + " used Follow Up");
        increaseEnergy(10);

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        BattleHelpers.hitEnemy(this, enemy, 2.01f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        if (chargeCount == 0) {
            preyPower.owner.removePower(preyPower);
        }

        BattleHelpers.PostAttackLogic(this, types);

        if (!skillPointRecovered) {
            Battle.battle.generateSkillPoint(this, 1);
            skillPointRecovered = true;
        }
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void onTurnStart() {
        skillPointRecovered = false;
    }

    public void increaseCharge(int amount) {
        chargeLost = 0;
        int initalStack = chargeCount;
        chargeCount += amount;
        if (chargeCount > MAX_CHARGE) {
            chargeCount = MAX_CHARGE;
        }
        Battle.battle.addToLog(String.format("Moze gained %d Charge (%d -> %d)", amount, initalStack, chargeCount));
    }

    public void decreaseCharge(int amount) {
        int initalStack = chargeCount;
        chargeCount -= amount;
        chargeLost += amount;
        if (chargeCount < 0) {
            chargeCount = 0;
        }
        Battle.battle.addToLog(String.format("Moze decremented %d Charge (%d -> %d)", amount, initalStack, chargeCount));
        if (chargeLost >= CHARGE_ATTACK_THRESHOLD) {
            chargeLost -= CHARGE_ATTACK_THRESHOLD;
            useFollowUp();
        }
    }

    public void onCombatStart() {
        Battle.battle.AdvanceEntity(this, 30);
        increaseEnergy(20);
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(FUAsMetricName, String.valueOf(FUAs));
        map.put(talentProcsMetricName, String.valueOf(talentProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(FUAsMetricName);
        list.add(talentProcsMetricName);
        return list;
    }

    private class MozePreyPower extends AbstractPower {
        public MozePreyPower() {
            this.name = this.getClass().getSimpleName();
            this.type = PowerType.DEBUFF;
            this.lastsForever = true;
        }
        @Override
        public float receiveConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 40;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 25;
                }
            }
            return 0;
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {
            boolean trigger = true;
            if (character instanceof Moze) {
                if (types.contains(DamageType.ULTIMATE)) {
                    trigger = true;
                } else if ((types.contains(DamageType.SKILL) || types.contains(DamageType.FOLLOW_UP))) {
                    trigger = false;
                }
            }
            if (trigger) {
                talentProcs++;
                BattleHelpers.additionalDamageHitEnemy(Moze.this, enemy, 0.33f, BattleHelpers.MultiplierStat.ATK);
                increaseEnergy(2);
                decreaseCharge(1);
            }
        }

        @Override
        public void onRemove() {
            Battle.battle.actionValueMap.put(Moze.this, Moze.this.getBaseAV());
            Battle.battle.AdvanceEntity(Moze.this, 30);
            isDeparted = false;
        }
    }
}
