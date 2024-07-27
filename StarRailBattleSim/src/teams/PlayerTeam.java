package teams;

import characters.*;
import lightcones.*;
import powers.PermPower;
import relics.*;

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

    public static class PelaYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class PelaYunliSparkleHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltSparkle());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class MarchYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarch());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TopazYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTopaz());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TingyunYunliRobinAventurineTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltAventurine());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            return playerTeam;
        }
    }

    public static class MarchYunliRobinAventurineTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarch());
            playerTeam.add(getPrebuiltAventurine());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            return playerTeam;
        }
    }

    public static class TopazYunliRobinAventurineTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTopaz());
            playerTeam.add(getPrebuiltAventurine());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            return playerTeam;
        }
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

    public static AbstractCharacter getPrebuiltAventurine() {
        AbstractCharacter character = new Aventurine();
        character.EquipLightcone(new ConcertForTwo(character));
        character.EquipRelicSet(new Knight(character, false));
        character.EquipRelicSet(new Duke(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.DEF_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.DEF_PER, 3).
                addSubStat(RelicStats.Stats.CRIT_DAMAGE, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltPela() {
        AbstractCharacter character = new Pela();
        character.EquipLightcone(new Resolution(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.EFFECT_HIT).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.EFFECT_HIT, 5).addSubStat(RelicStats.Stats.SPEED, 9).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltSparkle() {
        AbstractCharacter character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_DAMAGE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 1).addSubStat(RelicStats.Stats.SPEED, 13).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltSwordMarch() {
        AbstractCharacter character = new SwordMarch();
        character.EquipLightcone(new Cruising(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 14);
        relicStats.equipTo(character);
        return character;
    }
    public static AbstractCharacter getPrebuiltTopaz() {
        AbstractCharacter character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new Duke(character));
        character.EquipRelicSet(new Duran(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11);
        relicStats.equipTo(character);
        return character;
    }
    public static AbstractCharacter getPrebuiltTingyun() {
        AbstractCharacter character = new Tingyun();
        character.EquipLightcone(new Memories(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 7).addSubStat(RelicStats.Stats.SPEED, 9).
                addSubStat(RelicStats.Stats.ATK_FLAT, 2).addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltRobin() {
        AbstractCharacter character = new Robin();
        character.EquipLightcone(new TomorrowJourney(character));
        character.EquipRelicSet(new Musketeer(character, false));
        character.EquipRelicSet(new Valorous(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 7).addSubStat(RelicStats.Stats.SPEED, 8).
                addSubStat(RelicStats.Stats.ATK_FLAT, 3).addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltHuohuo() {
        AbstractCharacter character = new Huohuo();
        character.EquipLightcone(new PostOp(character));
        character.EquipRelicSet(new Passerby(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HEALING).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.HP_PER, 10).addSubStat(RelicStats.Stats.SPEED, 5).
                addSubStat(RelicStats.Stats.EFFECT_RES, 1).addSubStat(RelicStats.Stats.HP_FLAT, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltYunli() {
        AbstractCharacter character = new Yunli();
        character.EquipLightcone(new DanceAtSunset(character));
        character.EquipRelicSet(new Valorous(character));
        character.EquipRelicSet(new Duran(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11);
        relicStats.equipTo(character);
        return character;
    }
}
