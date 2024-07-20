import battleLogic.Battle;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;

import java.util.ArrayList;

import static teams.Teams.*;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();
        Battle.battle = battle;

        //battle.setPlayerTeam(TingyunYunliRobinHuohuo());
        //battle.setPlayerTeam(TopazYunliRobinHuohuo());
        //battle.setPlayerTeam(MarchYunliRobinHuohuo());
        //battle.setPlayerTeam(SparkleYunliRobinHuohuo());
        //battle.setPlayerTeam(SparkleYunliTingyunHuohuo());
        battle.setPlayerTeam(PelaYunliTingyunHuohuo());

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new PhysWeakEnemy(0, 2));
        enemyTeam.add(new PhysWeakEnemy(1, 2));
        //enemyTeam.add(new PhysWeakEnemy(2, 2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(5050);

        //Public calcs doc for Yunli: https://docs.google.com/document/d/1cCSiRX7-lfWp-fzhsaiUyhJuax0J9Dp2oU2t0CuVBDg/edit
    }


}