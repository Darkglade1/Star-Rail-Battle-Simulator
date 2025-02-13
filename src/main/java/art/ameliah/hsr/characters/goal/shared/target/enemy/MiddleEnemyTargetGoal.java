package art.ameliah.hsr.characters.goal.shared.target.enemy;

import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.MoveType;
import art.ameliah.hsr.characters.goal.EnemyTargetGoal;
import art.ameliah.hsr.enemies.AbstractEnemy;

import java.util.Optional;

public class MiddleEnemyTargetGoal<C extends AbstractCharacter<C>> extends EnemyTargetGoal<C> {

    public MiddleEnemyTargetGoal(C character) {
        super(character);
    }

    @Override
    public Optional<AbstractEnemy> getTarget(MoveType type) {
        return Optional.of(getBattle().getMiddleEnemy());
    }
}
