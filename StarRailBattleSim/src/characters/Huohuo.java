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

public class Huohuo extends AbstractCharacter {
    HuohuoTalentPower talentPower = new HuohuoTalentPower();
    private int talentCounter = 0;
    private int numTalentProcs = 0;
    private String numTalentProcsMetricName = "Number of Talent Procs";

    public Huohuo() {
        super("Huohuo", 1358, 602, 509, 98, 80, ElementType.WIND, 140, 100, Path.ABUNDANCE);

        this.addPower(new TracePower()
                .setStat(PowerStat.HP_PERCENT, 28)
                .setStat(PowerStat.FLAT_SPEED, 5)
                .setStat(PowerStat.EFFECT_RES, 18));
    }

    public void useSkill() {
        super.useSkill();
        talentCounter = 2;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(talentPower);
        }
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        BattleHelpers.PreAttackLogic(this, types);

        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            BattleHelpers.hitEnemy(this, Battle.battle.enemyTeam.get(middleIndex), 0.5f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        } else {
            AbstractEnemy enemy = Battle.battle.enemyTeam.get(0);
            BattleHelpers.hitEnemy(this, enemy, 0.5f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character != this) {
                character.increaseEnergy(character.maxEnergy * 0.2f, false);
                character.addPower(TempPower.create(PowerStat.ATK_PERCENT, 40, 2, "Tail Atk Bonus"));
            }
        }
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0 && talentCounter <= 0) {
            useSkill();
        } else {
            useBasicAttack();
        }
    }

    public void onCombatStart() {
        talentCounter = 1;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.addPower(talentPower);
        }
    }

    public void onTurnStart() {
        
        talentCounter--;
        if (talentCounter <= 0) {
            for (AbstractCharacter character : Battle.battle.playerTeam) {
                character.removePower(talentPower);
            }
        }
        if (currentEnergy >= ultCost) {
            useUltimate();
        }
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numTalentProcsMetricName, String.valueOf(numTalentProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numTalentProcsMetricName);
        return list;
    }

    private class HuohuoTalentPower extends AbstractPower {
        public HuohuoTalentPower() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
        }

        @Override
        public void onTurnStart() {
            Huohuo.this.increaseEnergy(1);
            numTalentProcs++;
        }

        @Override
        public void onUseUltimate() {
            Huohuo.this.increaseEnergy(1);
            numTalentProcs++;
        }
    }
}
