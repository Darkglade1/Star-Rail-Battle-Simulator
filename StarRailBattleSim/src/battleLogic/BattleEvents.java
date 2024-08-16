package battleLogic;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;

import java.util.ArrayList;

public interface BattleEvents {

    default void onCombatStart() {}

    default void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {}

    default void onBeforeUseAttack(ArrayList<AbstractCharacter.DamageType> damageTypes) {}
    default void onAttack(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {}
    default void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {}
    default void afterAttackFinish(AbstractCharacter character, ArrayList<AbstractEnemy> enemiesHit, ArrayList<AbstractCharacter.DamageType> types) {}

    default void onTurnStart() {}
    default void onEndTurn() {}

    default void onUseBasic() {}
    default void onUseSkill() {}
    default void onUseUltimate() {}
}
