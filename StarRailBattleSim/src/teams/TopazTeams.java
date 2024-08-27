package teams;

import characters.AbstractCharacter;
import characters.aventurine.Aventurine;
import characters.drRatio.DrRatio;
import characters.feixiao.Feixiao;
import characters.huohuo.Huohuo;
import characters.march.SwordMarch;
import characters.moze.Moze;
import characters.robin.Robin;
import characters.topaz.Topaz;
import characters.yunli.Yunli;
import lightcones.abundance.PostOpConversation;
import lightcones.destruction.DanceAtSunset;
import lightcones.harmony.ForTomorrowsJourney;
import lightcones.hunt.*;
import lightcones.preservation.ConcertForTwo;
import relics.RelicStats;
import relics.ornament.BrokenKeel;
import relics.ornament.DuranDynastyOfRunningWolves;
import relics.ornament.RutilentArena;
import relics.relics.*;

import java.util.ArrayList;

public class TopazTeams {
    public static class RatioRobinAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltRatioTT());
            playerTeam.add(getPrebuiltRobinTT());
            playerTeam.add(getPrebuiltAventurineTT());
            playerTeam.add(getPrebuiltTopazTT());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiaoTT());
            playerTeam.add(getPrebuiltRobinTT());
            playerTeam.add(getPrebuiltAventurineTT());
            playerTeam.add(getPrebuiltTopazTT());
            return playerTeam;
        }
    }

    public static class MarchRobinAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarchTT());
            playerTeam.add(getPrebuiltRobinTT());
            playerTeam.add(getPrebuiltAventurineTT());
            AbstractCharacter<?> topaz = getPrebuiltTopazTT();
            topaz.isDPS = true;
            playerTeam.add(topaz);
            return playerTeam;
        }
    }

    public static class MozeRobinAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltMozeTT());
            playerTeam.add(getPrebuiltRobinTT());
            playerTeam.add(getPrebuiltAventurineTT());
            AbstractCharacter<?> topaz = getPrebuiltTopazTT();
            topaz.isDPS = true;
            playerTeam.add(topaz);
            return playerTeam;
        }
    }

    public static class HuohuoYunliRobinTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltHuohuoTT());
            playerTeam.add(getPrebuiltYunliTT());
            playerTeam.add(getPrebuiltRobinTT());
            AbstractCharacter<?> topaz = getPrebuiltTopazTT();
            topaz.isDPS = true;
            playerTeam.add(topaz);
            return playerTeam;
        }
    }

    public static AbstractCharacter<?> getPrebuiltRatioTT() {
        AbstractCharacter<?> character = new DrRatio();
        character.EquipLightcone(new BaptismOfPureThought(character));
        character.EquipRelicSet(new PioneerDiverOfDeadWaters(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 9).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 12).addSubStat(RelicStats.Stats.SPEED, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltRobinTT() {
        AbstractCharacter<?> character = new Robin();
        character.EquipLightcone(new ForTomorrowsJourney(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character, false));
        character.EquipRelicSet(new TheWindSoaringValorous(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 7).addSubStat(RelicStats.Stats.SPEED, 8).
                addSubStat(RelicStats.Stats.ATK_FLAT, 3).addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltTopazTT() {
        AbstractCharacter<?> character = new Topaz();
        character.EquipLightcone(new WorrisomeBlissful(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 7).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 16).addSubStat(RelicStats.Stats.SPEED, 1);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltAventurineTT() {
        AbstractCharacter<?> character = new Aventurine(false);
        character.EquipLightcone(new ConcertForTwo(character));
        character.EquipRelicSet(new KnightOfPurityPalace(character, false));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.DEF_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.DEF_PER, 3).
                addSubStat(RelicStats.Stats.CRIT_DAMAGE, 7).addSubStat(RelicStats.Stats.SPEED, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltFeixiaoTT() {
        AbstractCharacter<?> character = new Feixiao();
        character.EquipLightcone(new IVentureForthToHunt(character));
        character.EquipRelicSet(new TheWindSoaringValorous(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 6).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 18);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSwordMarchTT() {
        AbstractCharacter<?> character = new SwordMarch();
        character.EquipLightcone(new WorrisomeBlissful(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11).addSubStat(RelicStats.Stats.SPEED, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltMozeTT() {
        AbstractCharacter<?> character = new Moze();
        character.EquipLightcone(new WorrisomeBlissful(character));
        character.EquipRelicSet(new PioneerDiverOfDeadWaters(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 14);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltYunliTT() {
        AbstractCharacter<?> character = new Yunli();
        character.isDPS = false;
        character.EquipLightcone(new DanceAtSunset(character));
        character.EquipRelicSet(new TheWindSoaringValorous(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltHuohuoTT() {
        AbstractCharacter<?> character = new Huohuo();
        character.EquipLightcone(new PostOpConversation(character));
        character.EquipRelicSet(new PasserbyOfWanderingCloud(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HEALING).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.HP_PER, 10).addSubStat(RelicStats.Stats.SPEED, 5).
                addSubStat(RelicStats.Stats.EFFECT_RES, 1).addSubStat(RelicStats.Stats.HP_FLAT, 8);
        relicStats.equipTo(character);
        return character;
    }
}
