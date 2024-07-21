package teams;

import characters.*;
import lightcones.*;
import powers.PermPower;
import relicSetBonus.*;

import java.util.ArrayList;

public class PlayerTeam {

    public ArrayList<AbstractCharacter> getTeam() {
        return null;
    }
    public String toString() {
        ArrayList<AbstractCharacter> team = getTeam();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < team.size(); i++) {
            result.append(team.get(i).name);
            if (i < team.size() - 1) {
                result.append(" | ");
            }
        }
        return result.toString();
    }

    public static class PelaYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class SparkleYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSparkle());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class SparkleYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSparkle());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class MarchYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarch());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TopazYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTopaz());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TingyunYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static AbstractCharacter getPrebuiltPela() {
        AbstractCharacter character = new Pela();
        character.EquipLightcone(new Resolution(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusAtkPercent = 20f;
        relicBonus.bonusFlatSpeed = 40;
        relicBonus.bonusFlatAtk = 502;
        relicBonus.bonusEnergyRegen = 19.4f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
    }

    public static AbstractCharacter getPrebuiltSparkle() {
        AbstractCharacter character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        PermPower relicBonus = new PermPower();
        relicBonus.bonusCritDamage = 80f;
        relicBonus.bonusFlatSpeed = 54;
        relicBonus.bonusFlatAtk = 502;
        relicBonus.bonusEnergyRegen = 19.4f;
        relicBonus.name = "Relic Stats Bonuses";
        character.addPower(relicBonus);
        return character;
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
