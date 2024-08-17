package battleLogic;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public interface BattleEvents {

    /**
     * Called when combat starts.
     */
    default void onCombatStart() {}

    /**
     * Called when a character is attacked, or when an enemy is attacked.
     * @param character The character that was attacked/is attacking
     * @param enemy The enemy that attacked/is being attacked
     * @param types The types of damage dealt
     * @param energyFromAttacked The energy gained from being attacked
     */
    default void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {}

    /**
     * Called from BattleHelpers#PreAttackLogic
     * @param damageTypes The types of damage that will be dealt
     */
    default void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {}

    /**
     * Called before enemies have received the onAttacked event
     * This hook is for after a character has attacked and dealt damage to the enemy. Most effects that need this sort of timing should use this hook.
     * @param character The character that attacked
     * @param enemiesHit The enemies that were hit
     * @param types The types of damage dealt
     */
    default void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {}

    /**
     * Called before an enemy is hit by an attack
     * @param character The character that is attacking
     * @param enemy The enemy that is being attacked
     * @param damageTypes The types of damage that will be dealt
     */
    default void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {}

    /**
     * Called after enemies have received the onAttack event
     * @param character The character that attacked
     * @param enemiesHit The enemies that were hit
     * @param types The types of damage dealt
     */
    default void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {}

    /**
     * Called before AbstractCharacter#takeTurn has been called.
     */
    default void onTurnStart() {}

    /**
     * Called after AbstractCharacter#takeTurn has been called.
     */
    default void onEndTurn() {}

    /**
     * Called when a character uses a basic attack, before the attack is executed.
     */
    default void onUseBasic() {}

    /**
     * Called when a character uses a skill, before the skill is executed.
     */
    default void onUseSkill() {}

    /**
     * Called when a character uses an ultimate, before the ultimate is executed.
     */
    default void onUseUltimate() {}
}
