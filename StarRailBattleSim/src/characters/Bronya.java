package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;

public class Bronya extends AbstractCharacter {

    public static final String NAME = "Bronya";
    public static final String SKILL_POWER_NAME = "BronyaSkillPower";
    public static final String ULT_POWER_NAME = "BronyaUltPower";

    public Bronya() {
        super(NAME, 1242, 582, 534, 99, 80, ElementType.WIND, 120, 100);

        this.path = Path.HARMONY;

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusCritDamage = 24;
        tracesPower.bonusSameElementDamageBonus = 22.4f;
        tracesPower.bonusEffectRes = 10;
        this.addPower(tracesPower);
    }

    public void useSkill() {
        super.useSkill();
        AbstractPower skillPower = new TempPower();
        skillPower.name = SKILL_POWER_NAME;
        skillPower.bonusDamageBonus = 66;
        skillPower.turnDuration = 1;
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            if (character.isDPS) {
                character.addPower(skillPower);
                Battle.battle.AdvanceEntity(character, 100);
                lightcone.onSpecificTrigger(character, null);
                character.speedPriority = 1;
            }
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
        Battle.battle.AdvanceEntity(this, 30);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            AbstractPower ultPower = new TempPower();
            ultPower.name = ULT_POWER_NAME;
            ultPower.bonusAtkPercent = 55;
            ultPower.bonusCritDamage = 20 + (this.getTotalCritDamage() * 0.16f);
            ultPower.turnDuration = 2;
            character.removePower(ultPower.name); // remove the old power in case bronya's crit damage changed so we get new snapshot of her buff
            character.addPower(ultPower);
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

    public void onCombatStart() {
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            PermPower tracePower = new PermPower();
            tracePower.bonusDamageBonus = 10;
            tracePower.name = "Bronya Trace Damage Bonus";
            character.addPower(tracePower);
        }
        addPower(new BronyaBasicCritPower());
    }

    public void useTechnique() {
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            AbstractPower techniquePower = new TempPower();
            techniquePower.name = "Bronya Technique Power";
            techniquePower.bonusAtkPercent = 15;
            techniquePower.turnDuration = 2;
            character.addPower(techniquePower);
        }
    }

    private static class BronyaBasicCritPower extends AbstractPower {
        public BronyaBasicCritPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        public float setFixedCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
            if (damageTypes.contains(DamageType.BASIC)) {
                return 100;
            }
            return currentCrit;
        }
    }
}
