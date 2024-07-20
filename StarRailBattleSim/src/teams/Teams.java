package teams;

import characters.*;
import lightcones.*;
import powers.PermPower;
import relicSetBonus.*;

import java.util.ArrayList;

public class Teams {

    public static ArrayList<AbstractCharacter> MarchYunliRobinHuohuo() {
        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
        playerTeam.add(getPrebuiltSwordMarch());
        playerTeam.add(getPrebuiltYunli());
        playerTeam.add(getPrebuiltRobin());
        playerTeam.add(getPrebuiltHuohuo());
        return playerTeam;
    }
    public static ArrayList<AbstractCharacter> TopazYunliRobinHuohuo() {
        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
        playerTeam.add(getPrebuiltTopaz());
        playerTeam.add(getPrebuiltYunli());
        playerTeam.add(getPrebuiltRobin());
        playerTeam.add(getPrebuiltHuohuo());
        return playerTeam;
    }
    public static ArrayList<AbstractCharacter> TingyunYunliRobinHuohuo() {
        ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
        playerTeam.add(getPrebuiltTingyun());
        playerTeam.add(getPrebuiltYunli());
        playerTeam.add(getPrebuiltRobin());
        playerTeam.add(getPrebuiltHuohuo());
        return playerTeam;
    }

    public static AbstractCharacter getPrebuiltSwordMarch() {
        AbstractCharacter character = new SwordMarch();
        character.EquipLightcone(new Cruising(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new RutilentArena(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusCritChance = 60.2f;
        relicBonus.bonusCritDamage = 84.1f;
        relicBonus.bonusSameElementDamageBonus = 38.8f;
        relicBonus.bonusAtkPercent = 51f;
        relicBonus.bonusFlatAtk = 367;
        relicBonus.bonusFlatSpeed = 25f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }
    public static AbstractCharacter getPrebuiltTopaz() {
        AbstractCharacter character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new Duke(character));
        character.EquipRelicSet(new Duran(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusCritChance = 70.2f;
        relicBonus.bonusCritDamage = 64.1f;
        relicBonus.bonusSameElementDamageBonus = 38.8f;
        relicBonus.bonusAtkPercent = 94.2f;
        //relicBonus.bonusAtkPercent = 51f;
        relicBonus.bonusFlatAtk = 367;
        //relicBonus.bonusFlatSpeed = 25f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }
    public static AbstractCharacter getPrebuiltTingyun() {
        AbstractCharacter character = new Tingyun();
        character.EquipLightcone(new Memories(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusAtkPercent = 90f;
        relicBonus.bonusFlatSpeed = 46;
        relicBonus.bonusFlatAtk = 502;
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
        character.EquipRelicSet(new Passerby(character));
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
        relicBonus.bonusCritChance = 70.2f;
        relicBonus.bonusCritDamage = 64.1f;
        relicBonus.bonusSameElementDamageBonus = 38.8f;
        relicBonus.bonusAtkPercent = 86.4f;
        relicBonus.bonusFlatAtk = 367;
        relicBonus.bonusFlatSpeed = 4.6f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }
}
