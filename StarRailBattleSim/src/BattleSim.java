import battleLogic.Battle;
import characters.AbstractCharacter;
import characters.Yunli;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;
import lightcones.Aeon;
import powers.RelicStatsPower;

import java.util.ArrayList;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();

        Yunli yunli = new Yunli();
        yunli.lightcone = new Aeon();
        RelicStatsPower yunliRelicBonus = new RelicStatsPower();
        yunliRelicBonus.bonusCritChance = 55;
        yunliRelicBonus.bonusCritDamage = 80;
        yunliRelicBonus.bonusDamageBonus = 38.8f;
        yunliRelicBonus.bonusAtkPercent = 86.4f;
        yunliRelicBonus.bonusFlatAtk = 352;
        yunli.addPower(yunliRelicBonus);

        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
        playerTeam.add(yunli);
        battle.setPlayerTeam(playerTeam);

        PhysWeakEnemy enemy = new PhysWeakEnemy();
        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(enemy);
        battle.setEnemyTeam(enemyTeam);

        Battle.battle = battle;
        battle.Start(550);
    }
}