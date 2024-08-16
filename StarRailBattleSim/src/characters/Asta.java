package characters;

import battleLogic.Battle;
import battleLogic.BattleHelpers;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PermPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;

public class Asta extends AbstractCharacter {

    public static final String NAME = "Asta";
    public static final String TALENT_BUFF_NAME = "Asta Talent Buff";
    private AstaTalentPower talentPower;
    public static final int MAX_STACKS = 5;
    private boolean justCastUlt = false;

    public Asta() {
        super(NAME, 1023, 512, 463, 106, 80, ElementType.FIRE, 120, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 22.4f)
                .setStat(PowerStat.DEF_PERCENT, 22.5f)
                .setStat(PowerStat.CRIT_CHANCE, 6.7f));

        talentPower = new AstaTalentPower();
        skillEnergyGain = 36;
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
        BattleHelpers.hitEnemy(this, enemy, 0.55f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        int numBounces = 5;
        while (numBounces > 0) {
            BattleHelpers.hitEnemy(this, Battle.battle.getRandomEnemy(), 0.55f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
            numBounces--;
        }

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
            Battle.battle.IncreaseSpeed(character, TempPower.create(PowerStat.FLAT_SPEED, 53, 2, "Asta Ult Speed Buff"));
        }
        justCastUlt = true;
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
            character.addPower(talentPower);
            if (character.elementType == ElementType.FIRE) {
                character.addPower(PermPower.create(PowerStat.SAME_ELEMENT_DAMAGE_BONUS, 18, "Asta Fire Damage Bonus"));
            }
        }
        addPower(new AstaERRPower());
    }

    public void onTurnStart() {
        if (justCastUlt) {
            justCastUlt = false;
            return;
        }
        // check for turn 1 since this metric is incremented in takeTurn, which happens after onTurnStart. So at the time of the 2nd onTurnStart, this metric will still be 1.
        if (numTurnsMetric >= 1) {
            decreaseStacks(2);
        }
    }

    public void useTechnique() {
        if (Battle.battle.usedEntryTechnique) {
            return;
        } else {
            Battle.battle.usedEntryTechnique = true;
        }
        ArrayList<DamageType> types = new ArrayList<>();
        BattleHelpers.PreAttackLogic(this, types);

        for (AbstractEnemy enemy : Battle.battle.enemyTeam) {
            BattleHelpers.hitEnemy(this, enemy, 0.5f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        }

        BattleHelpers.PostAttackLogic(this, types);
    }

    public void increaseStacks(int amount) {
        int initalStack = talentPower.stacks;
        talentPower.stacks += amount;
        if (talentPower.stacks > MAX_STACKS) {
            talentPower.stacks = MAX_STACKS;
        }
        Battle.battle.addToLog(String.format("Asta gained %d Stacks (%d -> %d)", amount, initalStack, talentPower.stacks));
    }

    public void decreaseStacks(int amount) {
        int initalStack = talentPower.stacks;
        talentPower.stacks -= amount;
        if (talentPower.stacks < 0) {
            talentPower.stacks = 0;
        }
        Battle.battle.addToLog(String.format("Asta lost %d Stacks (%d -> %d)", amount, initalStack, talentPower.stacks));
    }

    private class AstaTalentPower extends AbstractPower {

        protected int stacks = 0;
        public AstaTalentPower() {
            this.name = TALENT_BUFF_NAME;
            this.lastsForever = true;
        }

        @Override
        public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {
            if (character == Asta.this) {
                int chargeGain = enemiesHit.size();
                for (AbstractEnemy enemy : enemiesHit) {
                    if (enemy.weaknessMap.contains(ElementType.FIRE)) {
                        chargeGain++;
                    }
                }
               increaseStacks(chargeGain);
            }
        }

        @Override
        public float getConditionalAtkBonus(AbstractCharacter character) {
            return 15.4f * stacks;
        }
    }

    private class AstaERRPower extends AbstractPower {

        public AstaERRPower() {
            this.name = this.getClass().getSimpleName();
            this.lastsForever = true;
        }

        @Override
        public float getConditionalERR(AbstractCharacter character) {
            if (talentPower.stacks >= 2) {
                return 15;
            }
            return 0;
        }
    }
}
