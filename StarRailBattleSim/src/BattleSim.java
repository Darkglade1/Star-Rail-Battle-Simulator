import battleLogic.Battle;
import characters.AbstractCharacter;
import characters.Yunli;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;
import lightcones.BlueSkyFullUptime;
import powers.PermPower;
import relicSetBonus.InertSalsotto;
import relicSetBonus.Musketeer;
import relicSetBonus.Valorous;

import java.util.ArrayList;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();
        Battle.battle = battle;

        Yunli yunli = new Yunli();
        yunli.EquipLightcone(new BlueSkyFullUptime(yunli));
        yunli.EquipRelicSet(new Valorous(yunli));
        yunli.EquipRelicSet(new InertSalsotto(yunli));
        PermPower yunliRelicBonus = new PermPower();
        yunliRelicBonus.bonusCritChance = 100;
        yunliRelicBonus.bonusCritDamage = 0;
        yunliRelicBonus.bonusDamageBonus = 38.8f;
        yunliRelicBonus.bonusAtkPercent = 86.4f;
        yunliRelicBonus.bonusFlatAtk = 432;
        yunliRelicBonus.name = "Relic Stats Bonuses";
        yunli.addPower(yunliRelicBonus);

        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
        playerTeam.add(yunli);
        battle.setPlayerTeam(playerTeam);

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new PhysWeakEnemy());
        enemyTeam.add(new PhysWeakEnemy(1));
        enemyTeam.add(new PhysWeakEnemy(2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(200);
    }
}