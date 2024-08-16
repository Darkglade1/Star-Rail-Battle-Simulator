package powers;

import battleLogic.AbstractEntity;
import characters.AbstractCharacter;
import com.sun.istack.internal.NotNull;
import enemies.AbstractEnemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPower {

    public enum PowerType {
        BUFF, DEBUFF
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

    public float getConditionalAtkBonus(AbstractCharacter character) {
        return 0;
    }

    public float getConditionalERR(AbstractCharacter character) {
        return 0;
    }

    /**
     * Increase damage dealt by the character when attacking the enemy
     */
    public float getConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    /**
     * Increases incoming damage
     */
    public float getConditionalDamageTaken(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    /**
     * Increase damage dealt by the character when attacking the enemy
     * Use this for DMG Boosts, that are applied as a debug on targets
     */
    public float receiveConditionalDamageBonus(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }
    public float getConditionalCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }
    public float receiveConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float getConditionDefenseIgnore(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
        return 0;
    }

    public float setFixedCritRate(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCrit) {
        return currentCrit;
    }

    public float setFixedCritDmg(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes, float currentCritDmg) {
        return currentCritDmg;
    }

    public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {

    }

    public void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {

    }
    public void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }

    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {

    }
    public void onEndTurn() {
        if (!lastsForever && durationBasedOnSelfTurns) {
            if (justApplied) {
                justApplied = false;
            } else {
                turnDuration--;
            }
        }
    }

    public void onTurnStart() {

    }

    public void onUseUltimate() {

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
     * Add a value to a stat, calls setStat internally, for chaining.
     * @param stat The stat to add to
     * @param value The value to add
     * @return The power object
     */
    public AbstractPower addStat(PowerStat stat, float value) {
        this.setStat(stat, value);
        return this;
    }

    /**
     * Set the value of a stat, this overwrites the previous value
     * @param stat The stat to set
     * @param value The value to set
     */
    public void setStat(PowerStat stat, float value) {
        this.stats.put(stat, value);
    }

}
