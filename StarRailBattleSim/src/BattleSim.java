import battleLogic.Battle;
import characters.AbstractCharacter;
import characters.Huohuo;
import characters.Robin;
import characters.Yunli;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;
import lightcones.BlueSkyFullUptime;
import lightcones.DanceAtSunset;
import lightcones.PostOp;
import lightcones.TomorrowJourney;
import powers.PermPower;
import relicSetBonus.*;

import java.util.ArrayList;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();
        Battle.battle = battle;

        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();

        Robin robin = new Robin();
        robin.EquipLightcone(new TomorrowJourney(robin));
        robin.EquipRelicSet(new Musketeer(robin, false));
        robin.EquipRelicSet(new Valorous(robin, false));
        robin.EquipRelicSet(new BrokenKeel(robin));
        PermPower robinRelicBonus = new PermPower();
        robinRelicBonus.bonusAtkPercent = 160;
        robinRelicBonus.bonusFlatSpeed = 25;
        robinRelicBonus.bonusFlatAtk = 80;
        robinRelicBonus.bonusEnergyRegen = 19.4f;
        robinRelicBonus.name = "Relic Stats Bonuses";
        robin.addPower(robinRelicBonus);
        playerTeam.add(robin);

        Yunli yunli = new Yunli();
        yunli.EquipLightcone(new DanceAtSunset(yunli));
        yunli.EquipRelicSet(new Valorous(yunli));
        yunli.EquipRelicSet(new Duran(yunli));
        PermPower yunliRelicBonus = new PermPower();
        yunliRelicBonus.bonusCritChance = 100;
        yunliRelicBonus.bonusCritDamage = 0;
        yunliRelicBonus.bonusDamageBonus = 38.8f;
        yunliRelicBonus.bonusAtkPercent = 86.4f;
        yunliRelicBonus.bonusFlatAtk = 432;
        yunliRelicBonus.name = "Relic Stats Bonuses";
        yunli.addPower(yunliRelicBonus);
        playerTeam.add(yunli);

        Huohuo huohuo = new Huohuo();
        huohuo.EquipLightcone(new PostOp(huohuo));
        huohuo.EquipRelicSet(new Musketeer(huohuo));
        huohuo.EquipRelicSet(new BrokenKeel(huohuo));
        PermPower huohuoRelicBonus = new PermPower();
        huohuoRelicBonus.bonusHPPercent = 120;
        huohuoRelicBonus.bonusFlatSpeed = 45;
        huohuoRelicBonus.bonusFlatHP = 80;
        huohuoRelicBonus.bonusEnergyRegen = 19.4f;
        huohuoRelicBonus.name = "Relic Stats Bonuses";
        huohuo.addPower(huohuoRelicBonus);
        playerTeam.add(huohuo);

        battle.setPlayerTeam(playerTeam);

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new PhysWeakEnemy(0, 0));
        //enemyTeam.add(new PhysWeakEnemy(1, 2));
        //enemyTeam.add(new PhysWeakEnemy(2, 2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(300);
    }
}