package art.ameliah.hsr.teams;

import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.erudition.theherta.TheHerta;
import art.ameliah.hsr.characters.harmony.asta.Asta;
import art.ameliah.hsr.characters.preservation.aventurine.Aventurine;
import art.ameliah.hsr.characters.harmony.bronya.Bronya;
import art.ameliah.hsr.characters.hunt.feixiao.Feixiao;
import art.ameliah.hsr.characters.preservation.fuxuan.FuXuan;
import art.ameliah.hsr.characters.abundance.gallagher.Gallagher;
import art.ameliah.hsr.characters.harmony.hanya.Hanya;
import art.ameliah.hsr.characters.erudition.herta.Herta;
import art.ameliah.hsr.characters.abundance.huohuo.Huohuo;
import art.ameliah.hsr.characters.erudition.jade.Jade;
import art.ameliah.hsr.characters.abundance.lingsha.Lingsha;
import art.ameliah.hsr.characters.hunt.march.SwordMarch;
import art.ameliah.hsr.characters.hunt.moze.Moze;
import art.ameliah.hsr.characters.nihility.pela.Pela;
import art.ameliah.hsr.characters.harmony.robin.Robin;
import art.ameliah.hsr.characters.harmony.ruanmei.RuanMei;
import art.ameliah.hsr.characters.harmony.sparkle.Sparkle;
import art.ameliah.hsr.characters.harmony.tingyun.Tingyun;
import art.ameliah.hsr.characters.hunt.topaz.Topaz;
import art.ameliah.hsr.characters.destruction.yunli.Yunli;
import art.ameliah.hsr.lightcones.AbstractLightcone;
import art.ameliah.hsr.lightcones.abundance.Multiplication;
import art.ameliah.hsr.lightcones.abundance.PostOpConversation;
import art.ameliah.hsr.lightcones.abundance.SharedFeeling;
import art.ameliah.hsr.lightcones.abundance.WhatIsReal;
import art.ameliah.hsr.lightcones.destruction.DanceAtSunset;
import art.ameliah.hsr.lightcones.erudition.GeniusesRepose;
import art.ameliah.hsr.lightcones.erudition.YetHopeIsPriceless;
import art.ameliah.hsr.lightcones.harmony.FlowingNightglow;
import art.ameliah.hsr.lightcones.harmony.ForTomorrowsJourney;
import art.ameliah.hsr.lightcones.harmony.MemoriesOfThePast;
import art.ameliah.hsr.lightcones.harmony.PastAndFuture;
import art.ameliah.hsr.lightcones.hunt.BaptismOfPureThought;
import art.ameliah.hsr.lightcones.hunt.CruisingInTheStellarSea;
import art.ameliah.hsr.lightcones.hunt.IVentureForthToHunt;
import art.ameliah.hsr.lightcones.hunt.InTheNight;
import art.ameliah.hsr.lightcones.hunt.SleepLikeTheDead;
import art.ameliah.hsr.lightcones.hunt.Swordplay;
import art.ameliah.hsr.lightcones.hunt.WorrisomeBlissful;
import art.ameliah.hsr.lightcones.nihility.ResolutionShinesAsPearlsOfSweat;
import art.ameliah.hsr.lightcones.preservation.ConcertForTwo;
import art.ameliah.hsr.lightcones.preservation.DayOneOfMyNewLife;
import art.ameliah.hsr.relics.AbstractRelicSetBonus;
import art.ameliah.hsr.relics.RelicStats;
import art.ameliah.hsr.relics.Stats;
import art.ameliah.hsr.relics.ornament.*;
import art.ameliah.hsr.relics.relics.EagleOfTwilightLine;
import art.ameliah.hsr.relics.relics.GeniusOfBrilliantStars;
import art.ameliah.hsr.relics.relics.KnightOfPurityPalace;
import art.ameliah.hsr.relics.relics.LongevousDisciple;
import art.ameliah.hsr.relics.relics.MessengerTraversingHackerspace;
import art.ameliah.hsr.relics.relics.MusketeerOfWildWheat;
import art.ameliah.hsr.relics.relics.PasserbyOfWanderingCloud;
import art.ameliah.hsr.relics.relics.PrisonerInDeepConfinement;
import art.ameliah.hsr.relics.relics.ScholarLostInErudition;
import art.ameliah.hsr.relics.relics.TheAshblazingGrandDuke;
import art.ameliah.hsr.relics.relics.TheWindSoaringValorous;
import art.ameliah.hsr.relics.relics.ThiefOfShootingMeteor;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PlayerTeam {

    public static AbstractCharacter<?> getPreBuiltRobin() {
        Robin robin = new Robin();
        robin.EquipLightcone(new FlowingNightglow(robin));
        robin.EquipRelicSet(new TheWindSoaringValorous(robin, false));
        robin.EquipRelicSet(new PrisonerInDeepConfinement(robin, false));
        robin.EquipRelicSet(new SpringhtlyVonwacq(robin));
        new RelicStats().addMainStat(Stats.ATK_PER)
                .addMainStat(Stats.ATK_PER)
                .addMainStat(Stats.ATK_PER)
                .addMainStat(Stats.ERR)
                .addSubStat(Stats.ATK_PER, 5)
                .addSubStat(Stats.ATK_FLAT, 5)
                .addSubStat(Stats.SPEED, 6)
                .equipTo(robin);

        return robin;
    }

    public static AbstractCharacter<?> getPreBuiltJade() {
        Jade jade = new Jade();
        jade.EquipLightcone(new YetHopeIsPriceless(jade));
        jade.EquipRelicSet(new GeniusOfBrilliantStars(jade));
        jade.EquipRelicSet(new IzumoGenseiAndTakamaDivineRealm(jade));
        new RelicStats().addMainStat(Stats.CRIT_RATE)
                .addMainStat(Stats.ATK_PER)
                .addMainStat(Stats.QUANTUM_DAMAGE)
                .addMainStat(Stats.ATK_PER)
                .addSubStat(Stats.CRIT_RATE, 11)
                .addSubStat(Stats.CRIT_DAMAGE, 17)
                .addSubStat(Stats.ATK_PER, 1)
                .addSubStat(Stats.ATK_FLAT, 2)
                .equipTo(jade);

        return jade;
    }

    public static AbstractCharacter<?> getPreBuildTheHerta(Function<AbstractCharacter<?>, AbstractLightcone> lc) {
        TheHerta herta = new TheHerta();
        herta.isDPS = true;
        herta.EquipLightcone(lc.apply(herta));
        herta.EquipRelicSet(new ScholarLostInErudition(herta));
        herta.EquipRelicSet(new SigoniaTheUnclaimedDesolation(herta));
        new RelicStats().addMainStat(Stats.CRIT_RATE)
                .addMainStat(Stats.ATK_PER)
                .addMainStat(Stats.ICE_DAMAGE)
                .addMainStat(Stats.ATK_PER)
                .addSubStat(Stats.CRIT_RATE, 12)
                .addSubStat(Stats.CRIT_DAMAGE, 15)
                .addSubStat(Stats.SPEED, 3)
                .equipTo(herta);
        return herta;
    }

    public static AbstractCharacter<?> getPreBuiltHerta() {
        Herta herta = new Herta();
        herta.EquipLightcone(new GeniusesRepose(herta));
        herta.EquipRelicSet(new GeniusOfBrilliantStars(herta));
        herta.EquipRelicSet(new SigoniaTheUnclaimedDesolation(herta));
        new RelicStats().addMainStat(Stats.CRIT_RATE)
                .addMainStat(Stats.ATK_PER)
                .addMainStat(Stats.ICE_DAMAGE)
                .addMainStat(Stats.ATK_PER)
                .addSubStat(Stats.CRIT_RATE, 11)
                .addSubStat(Stats.CRIT_DAMAGE, 15)
                .addSubStat(Stats.SPEED, 3)
                .equipTo(herta);
        return herta;
    }

    public static AbstractCharacter<?> getPrebuiltLingshaCritSupport() {
        AbstractCharacter<?> character = new Lingsha();
        character.EquipLightcone(new WhatIsReal(character));
        character.EquipRelicSet(new ThiefOfShootingMeteor(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HEALING).addMainStat(Stats.SPEED).
                addMainStat(Stats.ATK_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.BREAK_EFFECT, 6).addSubStat(Stats.SPEED, 12).
                addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltMoze() {
        AbstractCharacter<?> character = new Moze();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.ATK_PER).
                addMainStat(Stats.LIGHTNING_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 17).addSubStat(Stats.CRIT_DAMAGE, 7);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltBronyaFei() {
        AbstractCharacter<?> character = new Bronya();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_DAMAGE).addMainStat(Stats.SPEED).
                addMainStat(Stats.HP_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.CRIT_DAMAGE, 15).addSubStat(Stats.SPEED, 2).
                addSubStat(Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltAsta() {
        AbstractCharacter<?> character = new Asta();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new MessengerTraversingHackerspace(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HP_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.DEF_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.ATK_PER, 8).addSubStat(Stats.SPEED, 8).
                addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltFuXuan() {
        AbstractCharacter<?> character = new FuXuan();
        character.EquipLightcone(new DayOneOfMyNewLife(character));
        character.EquipRelicSet(new KnightOfPurityPalace(character, false));
        character.EquipRelicSet(new LongevousDisciple(character, false));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HP_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.DEF_PER).addMainStat(Stats.HP_PER);
        relicStats.addSubStat(Stats.HP_PER, 8).addSubStat(Stats.SPEED, 8).
                addSubStat(Stats.DEF_PER, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltGallagher() {
        AbstractCharacter<?> character = new Gallagher();
        character.EquipLightcone(new Multiplication(character));
        character.EquipRelicSet(new ThiefOfShootingMeteor(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HEALING).addMainStat(Stats.SPEED).
                addMainStat(Stats.HP_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.BREAK_EFFECT, 6).addSubStat(Stats.SPEED, 14);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltHanya() {
        AbstractCharacter<?> character = new Hanya();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HP_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.DEF_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.ATK_PER, 6).addSubStat(Stats.SPEED, 10).
                addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltRuanMeiCritSupport() {
        AbstractCharacter<?> character = new RuanMei();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new ThiefOfShootingMeteor(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HP_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.DEF_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.BREAK_EFFECT, 10).addSubStat(Stats.SPEED, 7).
                addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSparkleFei() {
        AbstractCharacter<?> character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new PasserbyOfWanderingCloud(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_DAMAGE).addMainStat(Stats.SPEED).
                addMainStat(Stats.HP_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.CRIT_DAMAGE, 13).addSubStat(Stats.SPEED, 4).
                addSubStat(Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltFeixiao() {
        AbstractCharacter<?> character = new Feixiao();
        character.EquipLightcone(new IVentureForthToHunt(character));
        character.EquipRelicSet(new TheWindSoaringValorous(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.SPEED).
                addMainStat(Stats.WIND_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 6).addSubStat(Stats.CRIT_DAMAGE, 18);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltTopazSpeed() {
        AbstractCharacter<?> character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.SPEED).
                addMainStat(Stats.FIRE_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 13).addSubStat(Stats.CRIT_DAMAGE, 10).addSubStat(Stats.SPEED, 1);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltAventurineSpeed() {
        AbstractCharacter<?> character = new Aventurine(false);
        character.EquipLightcone(new ConcertForTwo(character));
        character.EquipRelicSet(new KnightOfPurityPalace(character, false));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.DEF_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.DEF_PER).addMainStat(Stats.DEF_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 10).addSubStat(Stats.DEF_PER, 3).
                addSubStat(Stats.CRIT_DAMAGE, 3).addSubStat(Stats.SPEED, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltAventurine() {
        AbstractCharacter<?> character = new Aventurine();
        character.EquipLightcone(new ConcertForTwo(character));
        character.EquipRelicSet(new KnightOfPurityPalace(character, false));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.DEF_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.DEF_PER).addMainStat(Stats.DEF_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 13).addSubStat(Stats.DEF_PER, 3).
                addSubStat(Stats.CRIT_DAMAGE, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltPela() {
        AbstractCharacter<?> character = new Pela();
        character.EquipLightcone(new ResolutionShinesAsPearlsOfSweat(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.EFFECT_HIT).addMainStat(Stats.SPEED).
                addMainStat(Stats.HP_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.EFFECT_HIT, 5).addSubStat(Stats.SPEED, 9).
                addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSparkle() {
        AbstractCharacter<?> character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_DAMAGE).addMainStat(Stats.SPEED).
                addMainStat(Stats.HP_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.CRIT_DAMAGE, 4).addSubStat(Stats.SPEED, 13).
                addSubStat(Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSwordMarchFei() {
        AbstractCharacter<?> character = new SwordMarch();
        character.EquipLightcone(new CruisingInTheStellarSea(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.SPEED).
                addMainStat(Stats.IMAGINARY_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 10).addSubStat(Stats.CRIT_DAMAGE, 11).addSubStat(Stats.SPEED, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSwordMarch() {
        AbstractCharacter<?> character = new SwordMarch();
        character.EquipLightcone(new CruisingInTheStellarSea(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.SPEED).
                addMainStat(Stats.IMAGINARY_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 10).addSubStat(Stats.CRIT_DAMAGE, 14);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltTopaz() {
        AbstractCharacter<?> character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.ATK_PER).
                addMainStat(Stats.FIRE_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 13).addSubStat(Stats.CRIT_DAMAGE, 11);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltTingyun() {
        AbstractCharacter<?> character = new Tingyun();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.ATK_PER).addMainStat(Stats.SPEED).
                addMainStat(Stats.ATK_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.ATK_PER, 7).addSubStat(Stats.SPEED, 9).
                addSubStat(Stats.ATK_FLAT, 2).addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltRobin() {
        AbstractCharacter<?> character = new Robin();
        character.EquipLightcone(new ForTomorrowsJourney(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character, false));
        character.EquipRelicSet(new TheWindSoaringValorous(character, false));
        character.EquipRelicSet(new LushakaTheSunkenSeas(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.ATK_PER).addMainStat(Stats.ATK_PER).
                addMainStat(Stats.ATK_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.ATK_PER, 7).addSubStat(Stats.SPEED, 8).
                addSubStat(Stats.ATK_FLAT, 3).addSubStat(Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltHuohuo() {
        AbstractCharacter<?> character = new Huohuo();
        character.EquipLightcone(new SharedFeeling(character));
        character.EquipRelicSet(new PasserbyOfWanderingCloud(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.HEALING).addMainStat(Stats.SPEED).
                addMainStat(Stats.HP_PER).addMainStat(Stats.ERR);
        relicStats.addSubStat(Stats.HP_PER, 5).addSubStat(Stats.SPEED, 15).
                addSubStat(Stats.EFFECT_RES, 1).addSubStat(Stats.HP_FLAT, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltYunli() {
        AbstractCharacter<?> character = new Yunli();
        character.EquipLightcone(new DanceAtSunset(character));
        character.EquipRelicSet(new TheWindSoaringValorous(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.ATK_PER).
                addMainStat(Stats.PHYSICAL_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 13).addSubStat(Stats.CRIT_DAMAGE, 11);
        relicStats.equipTo(character);
        return character;
    }

    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamLightconeCompare(Function<AbstractCharacter<?>, AbstractLightcone> lightconeSupplier) {
        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
        team.add(FeixiaoLightconeCompare(lightconeSupplier));
        team.add(getPrebuiltRobin());
        team.add(getPrebuiltAventurineSpeed());
        team.add(getPrebuiltTopazSpeed());

        return team;
    }

    private static AbstractCharacter<?> FeixiaoLightconeCompare(Function<AbstractCharacter<?>, AbstractLightcone> lightconeSupplier) {
        AbstractCharacter<?> character = new Feixiao();
        character.EquipLightcone(lightconeSupplier.apply(character));
        character.EquipRelicSet(new TheWindSoaringValorous(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));

        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.SPEED).
                addMainStat(Stats.WIND_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 6).addSubStat(Stats.CRIT_DAMAGE, 18);
        relicStats.equipTo(character);
        return character;
    }

    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamRelicCompare(BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> mainSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> planarSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> halfSet) {
        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
        team.add(getPrebuiltPela());
        team.add(FeixiaoRelicCompare(Swordplay::new, mainSet, planarSet, halfSet));
        team.add(getPrebuiltGallagher());
        team.add(getPrebuiltSwordMarchFei());

        return team;
    }

    private static AbstractCharacter<?> FeixiaoRelicCompare(Function<AbstractCharacter<?>, AbstractLightcone> lightconeSupplier, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> mainSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> planarSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> halfSet) {
        AbstractCharacter<?> character = new Feixiao();
        character.EquipLightcone(lightconeSupplier.apply(character));
        if (halfSet != null) {
            character.EquipRelicSet(mainSet.apply(character, false));
            character.EquipRelicSet(halfSet.apply(character, false));
        } else {
            character.EquipRelicSet(mainSet.apply(character, true));
        }
        character.EquipRelicSet(planarSet.apply(character, true));

        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(Stats.CRIT_RATE).addMainStat(Stats.SPEED).
                addMainStat(Stats.WIND_DAMAGE).addMainStat(Stats.ATK_PER);
        relicStats.addSubStat(Stats.CRIT_RATE, 6).addSubStat(Stats.CRIT_DAMAGE, 18);
        relicStats.equipTo(character);
        return character;
    }

    public ArrayList<AbstractCharacter<?>> getTeam() {
        return null;
    }

    public String toString() {
        return this.getClass().getSimpleName();
//        ArrayList<AbstractCharacter<?>> team = getTeam();
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < team.size(); i++) {
//            result.append(team.get(i).getName());
//            if (i < team.size() - 1) {
//                result.append(" | ");
//            }
//        }
//        return result.toString();
    }

    public static class PelaYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class PelaYunliSparkleHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltSparkle());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class MarchYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarch());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TopazYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTopaz());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TingyunYunliRobinAventurineTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltAventurine());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            return playerTeam;
        }
    }

    public static class MarchYunliRobinAventurineTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarch());
            playerTeam.add(getPrebuiltAventurine());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            return playerTeam;
        }
    }

    public static class TopazYunliRobinAventurineTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTopaz());
            playerTeam.add(getPrebuiltAventurine());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            return playerTeam;
        }
    }

    public static class PelaYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class SparkleYunliTingyunHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSparkle());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class SparkleYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSparkle());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class MarchYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltSwordMarch());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TopazYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTopaz());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class TingyunYunliRobinHuohuoTeam extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltTingyun());
            playerTeam.add(getPrebuiltYunli());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltHuohuo());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoSparkleAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltSparkleFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoSparkleAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltSparkleFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoSparkleAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltSparkleFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class PelaFeixiaoAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class PelaFeixiaoAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class PelaFeixiaoGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoTopazAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltTopazSpeed());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoMozeAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltMoze());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoMozeGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltMoze());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaGallagherMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiGallagherTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinTopazFuXuan extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltTopazSpeed());
            playerTeam.add(getPrebuiltFuXuan());
            return playerTeam;
        }
    }

//    public static AbstractCharacter<?> getPrebuiltFeixiao() {
//        AbstractCharacter<?> character = new Feixiao();
//        character.EquipLightcone(new Swordplay(character));
//        character.EquipRelicSet(new TheWindSoaringValorous(character));
//        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
//        RelicStats relicStats = new RelicStats();
//        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
//                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
//        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 11).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 13);
//        relicStats.equipTo(character);
//        return character;
//    }

    public static class FeixiaoRobinMarchFuXuan extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltSwordMarchFei());
            playerTeam.add(getPrebuiltFuXuan());
            return playerTeam;
        }
    }

    public static class AstaFeixiaoAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltAsta());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class AstaFeixiaoAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltAsta());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class AstaFeixiaoGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltAsta());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoBronyaAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltBronyaFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoBronyaAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltBronyaFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoBronyaAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltBronyaFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherBronya extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltBronyaFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinLingshaTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinLingshaMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinLingshaMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiLingshaTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiLingshaMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiLingshaMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            ArrayList<AbstractCharacter<?>> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoTeamLightconeCompareBaseline extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(Swordplay::new);
        }
    }

    public static class FeixiaoTeamLightconeCompareVenture extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(IVentureForthToHunt::new);
        }
    }

    public static class FeixiaoTeamLightconeCompareWorrisome extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(WorrisomeBlissful::new);
        }
    }

    public static class FeixiaoTeamLightconeCompareBaptism extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(BaptismOfPureThought::new);
        }
    }

//    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamLightconeCompare(Function<AbstractCharacter<?>, AbstractLightcone> lightconeSupplier) {
//        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
//        team.add(getPrebuiltPela());
//        team.add(FeixiaoLightconeCompare(lightconeSupplier));
//        team.add(getPrebuiltGallagher());
//        team.add(getPrebuiltSwordMarchFei());
//
//        return team;
//    }

    public static class FeixiaoTeamLightconeCompareCruising extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(CruisingInTheStellarSea::new);
        }
    }

    public static class FeixiaoTeamLightconeCompareInTheNight extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(InTheNight::new);
        }
    }

    public static class FeixiaoTeamLightconeCompareSleepDead extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamLightconeCompare(SleepLikeTheDead::new);
        }
    }

    public static class FeixiaoTeamRelicCompareBaseline extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheWindSoaringValorous::new, DuranDynastyOfRunningWolves::new, null);
        }
    }

    public static class FeixiaoTeamRelicCompareDuke extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheAshblazingGrandDuke::new, DuranDynastyOfRunningWolves::new, null);
        }
    }

    public static class FeixiaoTeamRelicCompareGenius extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(GeniusOfBrilliantStars::new, DuranDynastyOfRunningWolves::new, null);
        }
    }

    public static class FeixiaoTeamRelicCompare2PCDuke2PCAtk extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheAshblazingGrandDuke::new, DuranDynastyOfRunningWolves::new, MusketeerOfWildWheat::new);
        }
    }

    public static class FeixiaoTeamRelicCompare2PCDuke2PCWind extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheAshblazingGrandDuke::new, DuranDynastyOfRunningWolves::new, EagleOfTwilightLine::new);
        }
    }

    public static class FeixiaoTeamRelicCompareIzumo extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheWindSoaringValorous::new, IzumoGenseiAndTakamaDivineRealm::new, null);
        }
    }

    public static class FeixiaoTeamRelicCompareSalsotto extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheWindSoaringValorous::new, InertSalsotto::new, null);
        }
    }


//    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamRelicCompare(BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> mainSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> planarSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> halfSet) {
//        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
//        team.add(FeixiaoRelicCompare(IVentureForthToHunt::new, mainSet, planarSet, halfSet));
//        team.add(getPrebuiltRobin());
//        team.add(getPrebuiltAventurineSpeed());
//        team.add(getPrebuiltTopazSpeed());
//
//        return team;
//    }

    public static class FeixiaoTeamRelicCompareGlamoth extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheWindSoaringValorous::new, FirmamentFrontlineGlamoth::new, null);
        }
    }

    public static class FeixiaoTeamRelicCompareStation extends PlayerTeam {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        @Override
        public ArrayList<AbstractCharacter<?>> getTeam() {
            return FeixiaoTeamRelicCompare(TheWindSoaringValorous::new, SpaceSealingStation::new, null);
        }
    }
}
