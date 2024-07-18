import battleLogic.Battle;
import characters.*;
import enemies.AbstractEnemy;
import enemies.PhysWeakEnemy;
import lightcones.*;
import powers.PermPower;
import relicSetBonus.*;

import java.util.ArrayList;

public class BattleSim {

    public static void main(String[] args) {
        Battle battle = new Battle();
        Battle.battle = battle;

        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();


        playerTeam.add(getPrebuiltTingyun());
        playerTeam.add(getPrebuiltYunli());
        playerTeam.add(getPrebuiltRobin());
        playerTeam.add(getPrebuiltHuohuo());

        battle.setPlayerTeam(playerTeam);

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new PhysWeakEnemy(0, 0));
        //enemyTeam.add(new PhysWeakEnemy(1, 2));
        //enemyTeam.add(new PhysWeakEnemy(2, 2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(300);
    }

    public static AbstractCharacter getPrebuiltTingyun() {
        AbstractCharacter character = new Tingyun();
        character.EquipLightcone(new Memories(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusAtkPercent = 117.6f;
        relicBonus.bonusFlatSpeed = 37;
        relicBonus.bonusFlatAtk = 432;
        relicBonus.bonusEnergyRegen = 19.4f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }

    public static AbstractCharacter getPrebuiltRobin() {
        AbstractCharacter character = new Robin();
        character.EquipLightcone(new TomorrowJourney(character));
        character.EquipRelicSet(new Musketeer(character, false));
        character.EquipRelicSet(new Valorous(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusAtkPercent = 160.8f;
        relicBonus.bonusFlatSpeed = 20;
        relicBonus.bonusFlatAtk = 432;
        relicBonus.bonusEnergyRegen = 19.4f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }

    public static AbstractCharacter getPrebuiltHuohuo() {
        AbstractCharacter character = new Huohuo();
        character.EquipLightcone(new PostOp(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusHPPercent = 117.6f;
        relicBonus.bonusFlatSpeed = 37;
        relicBonus.bonusFlatHP = 432;
        relicBonus.bonusEnergyRegen = 19.4f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }

    public static AbstractCharacter getPrebuiltYunli() {
        AbstractCharacter character = new Yunli();
        character.EquipLightcone(new DanceAtSunset(character));
        character.EquipRelicSet(new Valorous(character));
        character.EquipRelicSet(new Duran(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusCritChance = 100;
        relicBonus.bonusCritDamage = 0;
        relicBonus.bonusSameElementDamageBonus = 38.8f;
        relicBonus.bonusAtkPercent = 86.4f;
        relicBonus.bonusFlatAtk = 432;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }
}