package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import battleLogic.Numby;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;

import java.util.ArrayList;

public class Topaz extends AbstractCharacter {
    AbstractPower proofOfDebt;
    Numby numby;
    PermPower stonksPower;
    private int ultCounter = 0;
    private boolean techniqueActive = false;

    public int numbyAttacksMetrics = 0;
    public int numbyAdvancedTimesMetrics = 0;
    public int actualNumbyAdvanceMetric = 0;

    public Topaz() {
        super("Topaz", 931, 621, 412, 110, 80, ElementType.FIRE, 130, 75);

        this.isDPS = true;
        proofOfDebt = new ProofOfDebt();

        PermPower tracesPower = new PermPower();
        tracesPower.name = "Traces Stat Bonus";
        tracesPower.bonusSameElementDamageBonus = 22.4f;
        tracesPower.bonusCritChance = 12.0f;
        tracesPower.bonusHPPercent = 10;
        this.addPower(tracesPower);

        this.addPower(new FireWeaknessBonusDamage());

        numby = new Numby(this);
        stonksPower = new PermPower();
    }

    public void useSkill() {
        super.useSkill();

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);

        AbstractEnemy target;
        if (Battle.battle.enemyTeam.size() >= 3) {
            int middleIndex = Battle.battle.enemyTeam.size() / 2;
            target = Battle.battle.enemyTeam.get(middleIndex);
            target.addPower(proofOfDebt);
        } else {
            target = Battle.battle.enemyTeam.get(0);
            target.addPower(proofOfDebt);
        }

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            if (enemy.hasPower(proofOfDebt.name) && enemy != target) {
                enemy.removePower(proofOfDebt);
                break;
            }
        }

        numbyAttack(types);
    }
    public void useBasicAttack() {
        super.useBasicAttack();
        float baseDamage = (1.0f * getFinalAttack());
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy target = null;
        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            if (enemy.hasPower(proofOfDebt.name)) {
                target = enemy;
                break;
            }
        }
        BattleHelpers.hitEnemy(this, target, baseDamage, types, 30);

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useUltimate() {
        // only ult if numby isn't about to spin so we don't waste action forward as much
        if (Battle.battle.actionValueMap.get(numby) >= numby.getBaseAV() * 0.25) {
            super.useUltimate();
            stonksPower = new PermPower();
            stonksPower.bonusCritDamage = 25;
            stonksPower.name = "Topaz Ult Power";
            ultCounter = 2;
            addPower(stonksPower);
        }
    }

    public void onCombatStart() {
        Battle.battle.actionValueMap.put(numby, numby.getBaseAV());
        Battle.battle.getRandomEnemy().addPower(proofOfDebt);
    }

    public void takeTurn() {
        super.takeTurn();
        if (Battle.battle.numSkillPoints > 0) {
            if (firstMove) {
                useSkill();
                firstMove = false;
            } else if (lastMove(MoveType.SKILL) || ultCounter > 0) {
                useBasicAttack();
            } else {
                useSkill();
            }
        } else {
            useBasicAttack();
        }
    }

    public void numbyAttack(ArrayList<DamageType> types) {
        float multiplier;
        float baseDamage;
        float toughnessDamage;
        if (ultCounter > 0) {
            multiplier = 2.1f;
            toughnessDamage = 60.0f / 8;
        } else {
            multiplier = 1.5f;
            toughnessDamage = 60.0f / 7;
        }
        baseDamage = multiplier * getFinalAttack() / 7;
        float ultFinalHitBaseDamage = 0.9f * getFinalAttack();
        types.add(DamageType.FOLLOW_UP);
        BattleHelpers.PreAttackLogic(this, types);

        AbstractEnemy target = null;
        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            if (enemy.hasPower(proofOfDebt.name)) {
                target = enemy;
                break;
            }
        }

        for (int i = 0; i < 7; i++) {
            BattleHelpers.hitEnemy(this, target, baseDamage, types, toughnessDamage);
        }
        if (ultCounter > 0) {
            BattleHelpers.hitEnemy(this, target, ultFinalHitBaseDamage, types, toughnessDamage);
            increaseEnergy(10);
            ultCounter--;
            if (ultCounter <= 0) {
                removePower(stonksPower);
            }
        }

        if (techniqueActive) {
            techniqueActive = false;
            increaseEnergy(60);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void useTechnique() {
        techniqueActive = true;
    }

    public String getMetrics() {
        String metrics = super.getMetrics();
        String charSpecificMetrics = String.format("\nNumby Attacks: %d \nNumby Advanced times: %d \nNumby Advanced AV: %d", numbyAttacksMetrics, numbyAdvancedTimesMetrics, actualNumbyAdvanceMetric);
        return metrics + charSpecificMetrics;
    }

    private class ProofOfDebt extends AbstractPower {
        public ProofOfDebt() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
            this.type = PowerType.DEBUFF;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 50;
                }
            }
            return 0;
        }

        @Override
        public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {
            for (AbstractCharacter.DamageType type : types) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    Topaz.this.numby.AdvanceForward();
                    break;
                }
            }
            if (ultCounter > 0) {
                for (AbstractCharacter.DamageType type : types) {
                    if (type == DamageType.BASIC || type == DamageType.SKILL || type == DamageType.ULTIMATE) {
                        Topaz.this.numby.AdvanceForward();
                        break;
                    }
                }
            }
        }
    }

    private static class FireWeaknessBonusDamage extends AbstractPower {
        public FireWeaknessBonusDamage() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
        }

        public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (enemy.weaknessMap.contains(ElementType.FIRE)) {
                return 15;
            }
            return 0;
        }
    }
}
