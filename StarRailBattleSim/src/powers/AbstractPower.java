package powers;

import battleLogic.AbstractEntity;
import battleLogic.BattleEvents;
import battleLogic.BattleParticipant;
import battleLogic.IBattle;
import characters.AbstractCharacter;
import com.sun.istack.internal.NotNull;
import enemies.AbstractEnemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPower implements BattleEvents,BattleParticipant {

    // TODO: Implement DOT
    public enum PowerType {
        BUFF, DEBUFF,DOT
    }

    public String name;

    private final Map<PowerStat, Float> stats = new HashMap<>();

    public int turnDuration;
    public PowerType type = PowerType.BUFF;
    public boolean durationBasedOnSelfTurns = true;
    public boolean lastsForever = false;
    public boolean justApplied = false;
    public int maxStacks = 0;
    public int stacks = 1;
    public AbstractEntity owner;

    public AbstractPower() {
    }

    public AbstractPower(String name) {
        this.name = name;
    }

    @Override
    public IBattle getBattle() {
        return this.owner.getBattle();
    }

    public float getConditionalAtkBonus(AbstractCharacter<?> character) {
        return 0;
    }

    public float getConditionalERR(AbstractCharacter<?> character) {
        return 0;
    }

    /**
     * Increase damage dealt by the character when attacking the enemy
     */
    public float getConditionalDamageBonus(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    /**
     * Increases incoming damage
     */
    public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    /**
     * Increase damage dealt by the character when attacking the enemy
     * Use this for DMG Boosts, that are applied as a debug on targets
     */
    public float receiveConditionalDamageBonus(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionalCritDamage(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }
    public float getConditionalCritRate(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }
    public float receiveConditionalCritDamage(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionDefenseIgnore(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float setFixedCritRate(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
        return currentCrit;
    }

    public float setFixedCritDmg(AbstractCharacter<?>  character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCritDmg) {
        return currentCritDmg;
    }

    /**
     * @return A def% bonus
     */
    public float getConditionalDefenseBonus(AbstractCharacter<?>  character) {
        return 0;
    }

    /**
     * @return A break% bonus
     */
    public float getConditionalBreakEffectBonus(AbstractCharacter<?>  character) {
        return 0;
    }

    @Override
    public void onEndTurn() {
        if (!lastsForever && durationBasedOnSelfTurns) {
            if (justApplied) {
                justApplied = false;
            } else {
                turnDuration--;
            }
        }
    }

    public void onRemove() {

    }

    /**
     * Get the value of a stat
     * @param stat The stat to get
     * @return The value of the stat, 0 if the stat is not set
     */
    @NotNull
    public float getStat(PowerStat stat) {
        return this.stats.getOrDefault(stat, 0f);
    }

    /**
     * Increase the value of a stat, calls getStat internally
     * @param stat The stat to increase
     * @param value The value to increase by
     */
    public void increaseStat(PowerStat stat, float value) {
        this.setStat(stat, this.getStat(stat) + value);
    }

    /**
     * Set the value of a stat, this overwrites the previous value
     * @param stat The stat to set
     * @param value The value to set
     * @return The power object
     */
    public AbstractPower setStat(PowerStat stat, float value) {
        this.stats.put(stat, value);
        return this;
    }

}
