package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;

public class Tingyun extends AbstractCharacter {
    private AbstractCharacter benefactor;

    public Tingyun() {
        super("Tingyun", 847, 529, 397, 112, 80, ElementType.LIGHTNING, 130, 100);

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28;
        tracesPower.bonusDefPercent = 22.5f;
        tracesPower.bonusSameElementDamageBonus = 8;
        this.addPower(tracesPower);
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

        TempPower speedPower = new TempPower();
        speedPower.bonusSpeedPercent = 20;
        speedPower.turnDuration = 1;
        speedPower.justApplied = true;
        speedPower.name = "Tingyun Skill Speed Power";
        Battle.battle.IncreaseSpeed(this, speedPower);
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        float baseDamage = (1.1f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy enemy;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            enemy = Battle.battle.enemyTeam.get(middleIndex);
            BattleHelpers.hitEnemy(this, enemy, baseDamage, types, 30);
        } else {
            enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, baseDamage, types, 30);
        }
        if (benefactor != null) {
            float bonusBaseDamage = 0.66f * benefactor.getFinalAttack();
            BattleHelpers.tingyunSkillHitEnemy(benefactor, enemy, bonusBaseDamage);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS && character.currentEnergy < character.maxEnergy) {
                character.increaseEnergy(60, false);
                TempPower ultDamageBonus = new TempPower();
                ultDamageBonus.bonusDamageBonus = 76;
                ultDamageBonus.turnDuration = 2;
                ultDamageBonus.name = "Tingyun Ult Damage Bonus";
                character.addPower(ultDamageBonus);
            }
        }
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && benefactor == null) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public String toString() {
        return name;
    }

    public void onTurnStart() {
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

    private class TingyunSkillPower extends AbstractPower {
        public TingyunSkillPower() {
            this.name = this.getClass().getSimpleName();
            this.bonusAtkPercent = 55;
            this.turnDuration = 3;
        }

        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            AbstractEnemy target = enemiesHit.get(Battle.battle.getRandomEnemyRng.nextInt(enemiesHit.size()));
            float baseDamage = 0.44f * character.getFinalAttack();
            BattleHelpers.tingyunSkillHitEnemy(character, target, baseDamage);
        }

        public void onUseUltimate() {
            if (benefactor != null) {
                TempPower speedPower = new TempPower();
                speedPower.bonusSpeedPercent = 20;
                speedPower.turnDuration = 1;
                speedPower.name = "Tingyun E1 Speed Power";
                Battle.battle.IncreaseSpeed(benefactor, speedPower);
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
