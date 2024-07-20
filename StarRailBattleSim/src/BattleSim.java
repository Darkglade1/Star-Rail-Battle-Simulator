import battleLogic.Battle;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;

import java.util.ArrayList;

import static teams.Teams.*;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();
        Battle.battle = battle;

        battle.setPlayerTeam(TingyunYunliRobinHuohuo());
        //battle.setPlayerTeam(TopazYunliRobinHuohuo());
        //battle.setPlayerTeam(MarchYunliRobinHuohuo());

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new PhysWeakEnemy(0, 0));
        //enemyTeam.add(new PhysWeakEnemy(1, 2));
        //enemyTeam.add(new PhysWeakEnemy(2, 2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(5050);
    }


}