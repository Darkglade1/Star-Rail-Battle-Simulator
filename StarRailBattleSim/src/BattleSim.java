import battleLogic.Battle;
import characters.AbstractCharacter;
import characters.Huohuo;
import characters.Yunli;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;
import lightcones.BlueSkyFullUptime;
import lightcones.DanceAtSunset;
import lightcones.PostOp;
import powers.PermPower;
import relicSetBonus.*;

import java.util.ArrayList;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();
        Battle.battle = battle;

        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();

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
        enemyTeam.add(new PhysWeakEnemy());
        enemyTeam.add(new PhysWeakEnemy(1));
        enemyTeam.add(new PhysWeakEnemy(2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(300);
    }
}