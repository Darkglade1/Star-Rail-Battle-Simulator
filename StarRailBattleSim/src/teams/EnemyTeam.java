package teams;

import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;

import java.util.ArrayList;

public class EnemyTeam {
    protected String description;

    public ArrayList<AbstractEnemy> getTeam() {
        return null;
    }

    public String toString() {
        return description;
    }

    public static class PhysWeakTargets3 extends EnemyTeam {
        public PhysWeakTargets3() {
            description = "3 Physical Weak Targets with 150 Speed (1.33 attacks per turn)";
        }
        @Override
        public ArrayList<AbstractEnemy> getTeam() {
            ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
            enemyTeam.add(new PhysWeakEnemy(0, 2));
            enemyTeam.add(new PhysWeakEnemy(1, 2));
            enemyTeam.add(new PhysWeakEnemy(2, 2));
            return enemyTeam;
        }
    }

    public static class PhysWeakTargets2 extends EnemyTeam {
        public PhysWeakTargets2() {
            description = "2 Physical Weak Targets with 150 Speed (1.33 attacks per turn)";
        }
        @Override
        public ArrayList<AbstractEnemy> getTeam() {
            ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
            enemyTeam.add(new PhysWeakEnemy(0, 2));
            enemyTeam.add(new PhysWeakEnemy(1, 2));
            return enemyTeam;
        }
    }

    public static class PhysWeakTargets1 extends EnemyTeam {
        public PhysWeakTargets1() {
            description = "1 Physical Weak Target with 150 Speed (2 attacks per turn)";
        }
        @Override
        public ArrayList<AbstractEnemy> getTeam() {
            ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
            enemyTeam.add(new PhysWeakEnemy(0, 0));
            return enemyTeam;
        }
    }
}
