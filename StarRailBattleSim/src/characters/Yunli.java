package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.TauntPower;
import powers.TempPower;

import java.util.ArrayList;

public class Yunli extends AbstractCharacter {

    public boolean isParrying;
    TempPower cullPower;
    TempPower trueSunderAtkBonus;
    TempPower techniqueDamageBonus;
    TauntPower tauntPower = new TauntPower(this);

    private int numNormalCounters = 0;
    private int numCulls = 0;
    private int numSlashes = 0;

    public Yunli() {
        super("Yunli", 1358, 679, 461, 94, 80, ElementType.PHYSICAL, 240, 125);
        this.ultCost = 120;

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
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), baseDamage, types, 60);
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex + 1), baseDamageSplash, types, 30);
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex - 1), baseDamageSplash, types, 30);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, baseDamage, types, 60);
            if (Battle.battle.enemyTeam.size() == 2) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(1), baseDamageSplash, types, 30);
            }
        }
        BattleHelpers.PostAttackLogic(this, types);
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        float baseDamage = (1.0f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), baseDamage, types, 30);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, baseDamage, types, 30);
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        isParrying = true;
        addPower(cullPower);

        tauntPower.lastsForever = true; // used so I can manually remove later
        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            enemy.addPower(tauntPower);
        }
    }

    public void onAttacked(AbstractEnemy enemy, int energyFromAttacked) {
        addPower(trueSunderAtkBonus);
        if (isParrying) {
            useCull(enemy);
            removePower(cullPower);
            isParrying = false;
            for (AbstractEnemy e : Battle.battle.enemyTeam) {
                e.removePower(tauntPower);
            }
        } else {
            numNormalCounters++;
            Battle.battle.addToLog(name + " used Counter");
            int baseDamage = (int)(1.2f * getFinalAttack());
            int baseDamageSplash = (int)(0.6f * getFinalAttack());
            ArrayList<DamageType> types = new ArrayList<>();
            types.add(DamageType.FOLLOW_UP);
            BattleHelpers.PreAttackLogic(this, types);

            int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
            BattleHelpers.hitEnemy(this, enemy, baseDamage, types, 30);
            if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), baseDamageSplash, types, 30);
            }
            if (enemyIndex - 1 >= 0) {
                BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), baseDamageSplash, types, 30);
            }
            BattleHelpers.PostAttackLogic(this, types);
        }
        increaseEnergy(25);
        super.onAttacked(enemy, energyFromAttacked);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void useCull(AbstractEnemy enemy) {
        numCulls++;
        Battle.battle.addToLog(name + " used Cull");
        int baseDamage = (int)(2.2f * getFinalAttack());
        int baseDamageSplash = (int)(1.1f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        int enemyIndex = Battle.battle.enemyTeam.indexOf(enemy);
        BattleHelpers.hitEnemy(this, enemy, baseDamage, types, 60);
        if (enemyIndex + 1 < Battle.battle.enemyTeam.size()) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex + 1), baseDamageSplash, types, 30);
        }
        if (enemyIndex - 1 >= 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(enemyIndex - 1), baseDamageSplash, types, 30);
        }

        int baseDamageBounce = (int)(0.72f * getFinalAttack());
        int numBounces = 6;
        while (numBounces > 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.getRandomEnemy(), baseDamageBounce, types, 15);
            numBounces--;
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useTechnique() {
        addPower(trueSunderAtkBonus);
        addPower(techniqueDamageBonus);
        useCull(Battle.battle.getRandomEnemy());
        removePower(techniqueDamageBonus);
        increaseEnergy(10);
    }

    public String getMetrics() {
        String metrics = super.getMetrics();
        String charSpecificMetrics = String.format("\nNormal Counters: %d \nCulls: %d \nSlashes: %d", numNormalCounters, numCulls, numSlashes);
        return metrics + charSpecificMetrics;
    }
}
