package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;

public class Hanya extends AbstractCharacter {

    public static final String NAME = "Hanya";
    public static final String ULT_BUFF_NAME = "Hanya Ult Buff";

    public Hanya() {
        super(NAME, 917, 564, 353, 110, 80, ElementType.PHYSICAL, 140, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 28)
                .setStat(PowerStat.FLAT_SPEED, 9)
                .setStat(PowerStat.HP_PERCENT, 10));
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

        TempPower speedPower = TempPower.create(PowerStat.SPEED_PERCENT, 20, 1, "Hanya Skill Speed Power");
        speedPower.justApplied = true;
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
                TempPower ultBuff = new TempPower(3, ULT_BUFF_NAME);
                ultBuff.setStat(PowerStat.ATK_PERCENT, 65);
                ultBuff.setStat(PowerStat.FLAT_SPEED, this.getFinalSpeed() * 0.21f);
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

        @Override
        public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.BASIC) || damageTypes.contains(DamageType.SKILL) || damageTypes.contains(DamageType.ULTIMATE)) {
                TempPower talentPower = TempPower.create(PowerStat.DAMAGE_BONUS, 43, 2, "Hanya Talent Power");
                talentPower.justApplied = true;
                character.addPower(talentPower);
            }
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
            if (types.contains(DamageType.BASIC) || types.contains(DamageType.SKILL) || types.contains(DamageType.ULTIMATE)) {
                hitCount++;
                Battle.battle.addToLog(String.format("Burden is at %d/%d hits", hitCount, hitsToTrigger));

                if (hitCount >= hitsToTrigger) {
                    triggersLeft--;
                    Battle.battle.generateSkillPoint(character, 1);
                    Hanya.this.increaseEnergy(2);

                    TempPower tracePower = TempPower.create(PowerStat.ATK_PERCENT, 10, 1, "Hanya Trace Atk Power");
                    if (Battle.battle.nextUnit == character) {
                        tracePower.justApplied = true;
                    }
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
