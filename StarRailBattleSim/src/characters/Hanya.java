package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;

public class Hanya extends AbstractCharacter {

    public static final String NAME = "Hanya";
    public static final String ULT_BUFF_NAME = "Hanya Ult Buff";

    public Hanya() {
        super(NAME, 917, 564, 353, 110, 80, ElementType.PHYSICAL, 140, 100);

        this.path = Path.HARMONY;

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28;
        tracesPower.bonusFlatSpeed = 9;
        tracesPower.bonusHPPercent = 10;
        this.addPower(tracesPower);
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
        BattleHelpers.hitEnemy(this, enemy, 2.64f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        AbstractPower burden = new BurdenPower();
        burden.owner = enemy;
        if (enemy.hasPower(burden.name)) {
            enemy.removePower(burden.name);
        }
        enemy.addPower(burden);

        TempPower speedPower = new TempPower();
        speedPower.bonusSpeedPercent = 20;
        speedPower.turnDuration = 1;
        speedPower.justApplied = true;
        speedPower.name = "Hanya Skill Speed Power";
        Battle.battle.IncreaseSpeed(this, speedPower);

        BattleHelpers.PostAttackLogic(this, types);
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
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS) {
                AbstractPower existingPower = character.getPower(ULT_BUFF_NAME);
                if (existingPower != null) {
                    Battle.battle.DecreaseSpeed(character, existingPower);
                }
                TempPower ultBuff = new TempPower();
                ultBuff.bonusAtkPercent = 65;
                ultBuff.bonusFlatSpeed = this.getFinalSpeed() * 0.21f;
                ultBuff.turnDuration = 3;
                ultBuff.name = ULT_BUFF_NAME;
                Battle.battle.IncreaseSpeed(character, ultBuff);
                break;
            }
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

    private class BurdenPower extends AbstractPower {

        private int triggersLeft = 2;
        private int hitsToTrigger = 2;
        private int hitCount = 0;
        public BurdenPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.BASIC) || damageTypes.contains(DamageType.SKILL) || damageTypes.contains(DamageType.ULTIMATE)) {
                TempPower talentPower = new TempPower();
                talentPower.turnDuration = 2;
                talentPower.bonusDamageBonus = 43;
                talentPower.justApplied = true;
                talentPower.name = "Hanya Talent Power";
                character.addPower(talentPower);
            }
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.BASIC) || damageTypes.contains(DamageType.SKILL) || damageTypes.contains(DamageType.ULTIMATE)) {
                hitCount++;
                Battle.battle.addToLog(String.format("Burden is at %d/%d hits", hitCount, hitsToTrigger));
                if (hitCount >= hitsToTrigger) {
                    triggersLeft--;
                    Battle.battle.generateSkillPoint(character, 1);
                    Hanya.this.increaseEnergy(2);
                    TempPower tracePower = new TempPower();
                    tracePower.turnDuration = 1;
                    if (Battle.battle.nextUnit == character) {
                        tracePower.justApplied = true;
                    }
                    tracePower.bonusAtkPercent = 10;
                    tracePower.name = "Hanya Trace Atk Power";
                    character.addPower(tracePower);
                    hitCount = 0;
                    if (triggersLeft <= 0) {
                        owner.removePower(this);
                    }
                }
            }
        }
    }
}
