package art.ameliah.hsr.battleLogic;

import art.ameliah.hsr.battleLogic.combat.ally.AttackLogic;
import art.ameliah.hsr.battleLogic.combat.enemy.EnemyAttackLogic;
import art.ameliah.hsr.battleLogic.combat.hit.Hit;
import art.ameliah.hsr.battleLogic.combat.result.HitResult;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.enemies.AbstractEnemy;

import java.util.Collection;

public interface BattleEvents {

    /**
     * Called when combat starts.
     */
    default void onCombatStart() {
    }

    /**
     * Also called on battle start
     */
    default void onWaveStart() {}

    /**
     * Called when an ally joins the battle while it has already started (memosprites)
     */
    default void onPlayerJoinCombat(AbstractCharacter<?> ally) {
    }

    /**
     * Called when an enemy join the battle while it has already started
     */
    default void onEnemyJoinCombat(AbstractEnemy enemy) {
    }

    /**
     * Called when an enemy dies
     */
    default void onEnemyRemove(AbstractEnemy enemy, int idx) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, after attack ends
     *
     * @param attack the attack
     */
    default void afterAttacked(EnemyAttackLogic attack) {
    }

    default void afterHPLost(float amount) {
    }

    /**
     * Hook for {@link AbstractEnemy}, after attack ends
     *
     * @param attack the attack
     */
    default void afterAttacked(AttackLogic attack) {
    }

    /**
     * Called when the enemy has their weakness broken
     */
    default void onWeaknessBreak(BattleParticipant source) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, before memosprite attack starts
     *
     * @param attack the attack
     */
    default void beforeMemospriteAttack(AttackLogic attack) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, before attack starts
     *
     * @param attack the attack
     */
    default void beforeAttack(AttackLogic attack) {
    }

    /**
     * Hook for {@link AbstractEnemy}, before attack starts
     *
     * @param attack the attack
     */
    default void beforeAttack(EnemyAttackLogic attack) {
    }

    /**
     * Hook for {@link AbstractEnemy}, before attack starts
     *
     * @param attack the attack
     */
    default void beforeAttacked(AttackLogic attack) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, before attack starts
     *
     * @param attack the attack
     */
    default void beforeAttacked(EnemyAttackLogic attack) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, before hit happens
     *
     * @param hit the hit going to happen
     */
    default void beforeDoHit(Hit hit) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, after the hit has happened
     *
     * @param hit the resulting hti
     */
    default void afterDoHit(HitResult hit) {}

    /**
     * Hook for {@link AbstractEnemy}, before hit happens
     *
     * @param hit the hit going to happen
     */
    default void beforeReceiveHit(Hit hit) {
    }

    /**
     * Hook for {@link AbstractCharacter<>}, called after the attack has finished
     *
     * @param attack the performed attack
     */
    default void afterAttack(AttackLogic attack) {
    }

    /**
     * Hook for {@link AbstractEnemy}, called after the attack has finished
     *
     * @param attack the attack
     */
    default void afterAttack(EnemyAttackLogic attack) {
    }

    /**
     * Called before AbstractCharacter#takeTurn has been called.
     */
    default void onTurnStart() {
    }

    /**
     * Called after AbstractCharacter#takeTurn has been called.
     */
    default void onEndTurn() {
    }

    /**
     * Called when a character uses a basic attack, before the attack is executed.
     */
    default void onUseBasic() {
    }

    /**
     * Called after a character uses a basic attack.
     */
    default void afterUseBasic() {
    }

    /**
     * Called when a character uses a skill, before the skill is executed.
     */
    default void onUseSkill() {
    }

    /**
     * Called after a character uses a skill.
     */
    default void afterUseSkill() {
    }

    /**
     * Called when a character uses an ultimate, before the ultimate is executed.
     */
    default void onUseUltimate() {
    }

    /**
     * Called after a character uses an ultimate.
     */
    default void afterUseUltimate() {
    }

    /**
     * Called after the owner uses any ability on an ally. This is not implemented for everyone.
     * Double check before using, implement if needed.
     */
    default void afterUseOnAlly(Collection<AbstractCharacter<?>> allies) {
    }

    /**
     * Called after summoning a memosprite
     */
    default void afterSummon(Memosprite<?, ?> memosprite) {}

    /**
     * Called after gaining an amount of energy
     * @param amount the amount gained
     * @param overflow the amount that overflew
     */
    default void onGainEnergy(float amount, float overflow) {}

    /**
     * Called when the owner dies
     */
    default void onDeath(BattleParticipant reason) {
    }
}
