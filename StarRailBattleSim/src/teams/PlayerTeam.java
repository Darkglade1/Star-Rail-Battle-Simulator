package teams;

import characters.AbstractCharacter;
import characters.aventurine.Aventurine;
import characters.asta.Asta;
import characters.bronya.Bronya;
import characters.drRatio.DrRatio;
import characters.feixiao.Feixiao;
import characters.fuxuan.FuXuan;
import characters.gallagher.Gallagher;
import characters.hanya.Hanya;
import characters.huohuo.Huohuo;
import characters.lingsha.Lingsha;
import characters.march.SwordMarch;
import characters.moze.Moze;
import characters.pela.Pela;
import characters.robin.Robin;
import characters.ruanmei.RuanMei;
import characters.sparkle.Sparkle;
import characters.tingyun.Tingyun;
import characters.topaz.Topaz;
import characters.yunli.Yunli;
import lightcones.AbstractLightcone;
import lightcones.abundance.Multiplication;
import lightcones.abundance.PostOpConversation;
import lightcones.abundance.WhatIsReal;
import lightcones.destruction.DanceAtSunset;
import lightcones.harmony.*;
import lightcones.hunt.*;
import lightcones.nihility.ResolutionShinesAsPearlsOfSweat;
import lightcones.preservation.ConcertForTwo;
import lightcones.preservation.DayOneOfMyNewLife;
import relics.AbstractRelicSetBonus;
import relics.RelicStats;
import relics.ornament.*;
import relics.relics.*;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PlayerTeam {

    public ArrayList<AbstractCharacter<?>> getTeam() {
        return null;
    }
    public String toString() {
        ArrayList<AbstractCharacter<?>> team = getTeam();
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

    public static AbstractCharacter<?> getPrebuiltLingshaCritSupport() {
        AbstractCharacter<?> character = new Lingsha();
        character.EquipLightcone(new WhatIsReal(character));
        character.EquipRelicSet(new ThiefOfShootingMeteor(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HEALING).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.BREAK_EFFECT, 6).addSubStat(RelicStats.Stats.SPEED, 12).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltMoze() {
        AbstractCharacter<?> character = new Moze();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 17).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 7);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltBronyaFei() {
        AbstractCharacter<?> character = new Bronya();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_DAMAGE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 15).addSubStat(RelicStats.Stats.SPEED, 2).
                addSubStat(RelicStats.Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltAsta() {
        AbstractCharacter<?> character = new Asta();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new MessengerTraversingHackerspace(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 8).addSubStat(RelicStats.Stats.SPEED, 8).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltFuXuan() {
        AbstractCharacter<?> character = new FuXuan();
        character.EquipLightcone(new DayOneOfMyNewLife(character));
        character.EquipRelicSet(new KnightOfPurityPalace(character, false));
        character.EquipRelicSet(new LongevousDisciple(character, false));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.HP_PER);
        relicStats.addSubStat(RelicStats.Stats.HP_PER, 8).addSubStat(RelicStats.Stats.SPEED, 8).
                addSubStat(RelicStats.Stats.DEF_PER, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltGallagher() {
        AbstractCharacter<?> character = new Gallagher();
        character.EquipLightcone(new Multiplication(character));
        character.EquipRelicSet(new ThiefOfShootingMeteor(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HEALING).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.BREAK_EFFECT, 6).addSubStat(RelicStats.Stats.SPEED, 14);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltHanya() {
        AbstractCharacter<?> character = new Hanya();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 6).addSubStat(RelicStats.Stats.SPEED, 10).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltRuanMeiCritSupport() {
        AbstractCharacter<?> character = new RuanMei();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new ThiefOfShootingMeteor(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.BREAK_EFFECT, 10).addSubStat(RelicStats.Stats.SPEED, 7).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSparkleFei() {
        AbstractCharacter<?> character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new PasserbyOfWanderingCloud(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_DAMAGE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 13).addSubStat(RelicStats.Stats.SPEED, 4).
                addSubStat(RelicStats.Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltFeixiao() {
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

    public static AbstractCharacter<?> getPrebuiltTopazSpeed() {
        AbstractCharacter<?> character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 10).addSubStat(RelicStats.Stats.SPEED, 1);
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
        relicStats.addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.DEF_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.DEF_PER, 3).
                addSubStat(RelicStats.Stats.CRIT_DAMAGE, 3).addSubStat(RelicStats.Stats.SPEED, 8);
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
        relicStats.addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.DEF_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.DEF_PER, 3).
                addSubStat(RelicStats.Stats.CRIT_DAMAGE, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltPela() {
        AbstractCharacter<?> character = new Pela();
        character.EquipLightcone(new ResolutionShinesAsPearlsOfSweat(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.EFFECT_HIT).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.EFFECT_HIT, 5).addSubStat(RelicStats.Stats.SPEED, 9).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSparkle() {
        AbstractCharacter<?> character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_DAMAGE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 4).addSubStat(RelicStats.Stats.SPEED, 13).
                addSubStat(RelicStats.Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSwordMarchFei() {
        AbstractCharacter<?> character = new SwordMarch();
        character.EquipLightcone(new CruisingInTheStellarSea(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11).addSubStat(RelicStats.Stats.SPEED, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltSwordMarch() {
        AbstractCharacter<?> character = new SwordMarch();
        character.EquipLightcone(new CruisingInTheStellarSea(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 14);
        relicStats.equipTo(character);
        return character;
    }
    public static AbstractCharacter<?> getPrebuiltTopaz() {
        AbstractCharacter<?> character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new TheAshblazingGrandDuke(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11);
        relicStats.equipTo(character);
        return character;
    }
    public static AbstractCharacter<?> getPrebuiltTingyun() {
        AbstractCharacter<?> character = new Tingyun();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new MusketeerOfWildWheat(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 7).addSubStat(RelicStats.Stats.SPEED, 9).
                addSubStat(RelicStats.Stats.ATK_FLAT, 2).addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter<?> getPrebuiltRobin() {
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

    public static AbstractCharacter<?> getPrebuiltHuohuo() {
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

    public static AbstractCharacter<?> getPrebuiltYunli() {
        AbstractCharacter<?> character = new Yunli();
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

    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamLightconeCompare(Function<AbstractCharacter<?>, AbstractLightcone> lightconeSupplier) {
        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
        team.add(FeixiaoLightconeCompare(lightconeSupplier));
        team.add(getPrebuiltRobin());
        team.add(getPrebuiltAventurineSpeed());
        team.add(getPrebuiltTopazSpeed());

        return team;
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

    private static AbstractCharacter<?> FeixiaoLightconeCompare(Function<AbstractCharacter<?>,AbstractLightcone> lightconeSupplier) {
        AbstractCharacter<?> character = new Feixiao();
        character.EquipLightcone(lightconeSupplier.apply(character));
        character.EquipRelicSet(new TheWindSoaringValorous(character));
        character.EquipRelicSet(new DuranDynastyOfRunningWolves(character));

        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 6).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 18);
        relicStats.equipTo(character);
        return character;
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


//    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamRelicCompare(BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> mainSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> planarSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> halfSet) {
//        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
//        team.add(FeixiaoRelicCompare(IVentureForthToHunt::new, mainSet, planarSet, halfSet));
//        team.add(getPrebuiltRobin());
//        team.add(getPrebuiltAventurineSpeed());
//        team.add(getPrebuiltTopazSpeed());
//
//        return team;
//    }

    public static ArrayList<AbstractCharacter<?>> FeixiaoTeamRelicCompare(BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> mainSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> planarSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> halfSet) {
        ArrayList<AbstractCharacter<?>> team = new ArrayList<>();
        team.add(getPrebuiltPela());
        team.add(FeixiaoRelicCompare(Swordplay::new, mainSet, planarSet, halfSet));
        team.add(getPrebuiltGallagher());
        team.add(getPrebuiltSwordMarchFei());

        return team;
    }

    private static AbstractCharacter<?> FeixiaoRelicCompare(Function<AbstractCharacter<?>,AbstractLightcone> lightconeSupplier, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> mainSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> planarSet, BiFunction<AbstractCharacter<?>, Boolean, AbstractRelicSetBonus> halfSet) {
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
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 6).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 18);
        relicStats.equipTo(character);
        return character;
    }
}
