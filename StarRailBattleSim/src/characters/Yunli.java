package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;

public class Yunli extends AbstractCharacter {

    public boolean isParrying;

    public Yunli() {
        super("Yunli", 1358, 679, 461, 94, 80, ElementType.PHYSICAL, 240, 125);
    }

    public void useSkill() {
        super.useSkill();
        float baseDamage = (1.2f * getFinalAttack());
        float baseDamageSplash = (0.6f * getFinalAttack());
        DamageType type = DamageType.SKILL;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex), baseDamage, type);
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex + 1), baseDamageSplash, type);
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex - 1), baseDamageSplash, type);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, type);
            if (Battle.battle.enemyTeam.size() == 2) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(1), baseDamageSplash, type);
            }
        }
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        float baseDamage = (1.0f * getFinalAttack());
        DamageType type = DamageType.BASIC;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex), baseDamage, type);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, type);
        }
    }

    public void useUltimate() {
        isParrying = true;
        super.useUltimate();
    }

    public void onAttacked(AbstractEnemy enemy, int energyFromAttacked) {
        System.out.println(name + " used Counter");
        if (isParrying) {
            int baseDamage = (int)(2.2f * getFinalAttack());
            int baseDamageSplash = (int)(1.1f * getFinalAttack());
            DamageType type = DamageType.FOLLOW_UP;

            int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, type);
            if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), baseDamageSplash, type);
            }
            if (enemyIndex - 1 >= 0) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), baseDamageSplash, type);
            }

            int baseDamageBounce = (int)(0.72f * getFinalAttack());
            int numBounces = 6;
            int counter = 0;
            while (numBounces > 0) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(counter), baseDamageBounce, type);
                numBounces--;
                counter++;
                if (counter >= Battle.battle.enemyTeam.size()) {
                    counter = 0;
                }
            }
            isParrying = false;
        } else {
            int baseDamage = (int)(1.2f * getFinalAttack());
            int baseDamageSplash = (int)(0.6f * getFinalAttack());
            DamageType type = DamageType.FOLLOW_UP;

            int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, type);
            if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), baseDamageSplash, type);
            }
            if (enemyIndex - 1 >= 0) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), baseDamageSplash, type);
            }
        }
        increaseEnergy(25);
        super.onAttacked(enemy, energyFromAttacked);
    }

    public void takeTurn() {
        if (Battle.battle.numSkillPoints > 0) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }
}
