package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;

public class Yunli extends AbstractCharacter {

    public boolean isParrying;
    TempPower cullPower;
    TempPower trueSunderAtkBonus;
    TempPower techniqueDamageBonus;

    public Yunli() {
        super("Yunli", 1358, 679, 461, 94, 80, ElementType.PHYSICAL, 240, 125);
        cullPower = new TempPower();
        cullPower.bonusCritDamage = 100.0f;
        cullPower.name = "Cull Crit Damage Buff";

        trueSunderAtkBonus = new TempPower();
        trueSunderAtkBonus.bonusAtkPercent = 30;
        trueSunderAtkBonus.turnDuration = 1;
        trueSunderAtkBonus.name = "True Sunder Atk Bonus";

        techniqueDamageBonus = new TempPower();
        techniqueDamageBonus.bonusDamageBonus = 80;
        techniqueDamageBonus.name = "Technique Damage Bonus";

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusAtkPercent = 28;
        tracesPower.bonusCritChance = 6.7f;
        tracesPower.bonusHPPercent = 18;
        this.addPower(tracesPower);
    }

    public void useSkill() {
        super.useSkill();
        float baseDamage = (1.2f * getFinalAttack());
        float baseDamageSplash = (0.6f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex), baseDamage, types);
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex + 1), baseDamageSplash, types);
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex - 1), baseDamageSplash, types);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, types);
            if (Battle.battle.enemyTeam.size() == 2) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(1), baseDamageSplash, types);
            }
        }
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        float baseDamage = (1.0f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(middleIndex), baseDamage, types);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, types);
        }
    }

    public void useUltimate() {
        isParrying = true;
        addPower(cullPower);
        super.useUltimate();
    }

    public void onAttacked(AbstractEnemy enemy, int energyFromAttacked) {
        addPower(trueSunderAtkBonus);
        if (isParrying) {
            useCull(enemy);
            removePower(cullPower);
            isParrying = false;
        } else {
            Battle.battle.addToLog(name + " used Counter");
            int baseDamage = (int)(1.2f * getFinalAttack());
            int baseDamageSplash = (int)(0.6f * getFinalAttack());
            ArrayList<DamageType> types = new ArrayList<>();
            types.add(DamageType.FOLLOW_UP);

            int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
            BattleHelpers.attackEnemy(this, enemy, baseDamage, types);
            if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), baseDamageSplash, types);
            }
            if (enemyIndex - 1 >= 0) {
                BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), baseDamageSplash, types);
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

    public String toString() {
        return name;
    }

    public void useCull(AbstractEnemy enemy) {
        Battle.battle.addToLog(name + " used Cull");
        int baseDamage = (int)(2.0f * getFinalAttack());
        int baseDamageSplash = (int)(1.0f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        types.add(DamageType.ULTIMATE);

        int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
        BattleHelpers.attackEnemy(this, enemy, baseDamage, types);
        if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), baseDamageSplash, types);
        }
        if (enemyIndex - 1 >= 0) {
            BattleHelpers.attackEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), baseDamageSplash, types);
        }

        int baseDamageBounce = (int)(0.60f * getFinalAttack());
        int numBounces = 6;
        while (numBounces > 0) {
            BattleHelpers.attackEnemy(this, Battle.battle.getRandomEnemy(), baseDamageBounce, types);
            numBounces--;
        }
    }

    public void useTechnique() {
        addPower(trueSunderAtkBonus);
        addPower(techniqueDamageBonus);
        useCull(Battle.battle.getRandomEnemy());
        removePower(techniqueDamageBonus);
    }
}
