package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.TempPower;

import java.util.ArrayList;

public class Gallagher extends AbstractCharacter {
    private boolean isEnhanced = false;

    public Gallagher() {
        super("Gallagher", 1305, 529, 441, 98, 80, ElementType.FIRE, 110, 100);
        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusHPPercent = 18;
        tracesPower.bonusBreakEffect = 13.3f;
        tracesPower.bonusEffectRes = 28;
        this.addPower(tracesPower);
        this.hasAttackingUltimate = true;
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
        if (isEnhanced) {
            BattleHelpers.hitEnemy(this, enemy, 2.75f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT * 3);
            AbstractPower atkDebuff = new TempPower();
            atkDebuff.type = AbstractPower.PowerType.DEBUFF;
            atkDebuff.turnDuration = 2;
            atkDebuff.name = "Gallagher Atk Debuff";
            enemy.addPower(atkDebuff);
            isEnhanced = false;
        } else {
            BattleHelpers.hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }
        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        super.useUltimate();
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        BattleHelpers.PreAttackLogic(this, types);

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            BattleHelpers.hitEnemy(this, enemy, 1.65f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
            AbstractPower besotted = new Besotted();
            enemy.addPower(besotted);
            isEnhanced = true;
        }
        Battle.battle.AdvanceEntity(this, 100);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void takeTurn() {
        super.takeTurn();
        useBasicAttack();
    }

    public void onCombatStart() {
        increaseEnergy(20);
        PermPower e6buff = new PermPower();
        e6buff.bonusBreakEffect = 20;
        e6buff.bonusWeaknessBreakEff = 20;
        e6buff.name = "Gallagher E6 Buff";
        addPower(e6buff);
    }

    private static class Besotted extends AbstractPower {
        public Besotted() {
            this.name = this.getClass().getSimpleName();
            this.turnDuration = 3;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (damageTypes.contains(DamageType.BREAK)) {
                return 13.2f;
            }
            return 0;
        }
    }
}
