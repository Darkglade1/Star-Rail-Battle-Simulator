package teams;

import characters.*;
import lightcones.abundance.Multiplication;
import lightcones.abundance.PostOpConversation;
import lightcones.abundance.WhatIsReal;
import lightcones.destruction.DanceAtSunset;
import lightcones.harmony.FlowingNightglow;
import lightcones.harmony.ForTomorrowsJourney;
import lightcones.harmony.MemoriesOfThePast;
import lightcones.harmony.PastAndFuture;
import lightcones.hunt.CruisingInTheStellarSea;
import lightcones.hunt.IVentureForthToHunt;
import lightcones.hunt.Swordplay;
import lightcones.nihility.ResolutionShinesAsPearlsOfSweat;
import lightcones.preservation.ConcertForTwo;
import lightcones.preservation.DayOneOfMyNewLife;
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

    public static class FeixiaoRobinAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoSparkleAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltSparkleFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoSparkleAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltSparkleFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoSparkleAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltSparkleFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoPelaAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoPelaAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltPela());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoTopazAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltTopazSpeed());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoMozeAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltMoze());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaGallagherMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiGallagherTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoHanyaGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltHanya());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinTopazFuXuan extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltTopazSpeed());
            playerTeam.add(getPrebuiltFuXuan());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinMarchFuXuan extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltSwordMarchFei());
            playerTeam.add(getPrebuiltFuXuan());
            return playerTeam;
        }
    }

    public static class AstaFeixiaoAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltAsta());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class AstaFeixiaoAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltAsta());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class AstaFeixiaoGallagherMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltAsta());
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoBronyaAventurineTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltBronyaFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoBronyaAventurineMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltBronyaFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoBronyaAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltBronyaFei());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherBronya extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltBronyaFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinAventurineMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltAventurineSpeed());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinGallagherMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltGallagher());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinLingshaTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinLingshaMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRobinLingshaMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRobin());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiLingshaTopaz extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltTopazSpeed());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiLingshaMarch extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltSwordMarchFei());
            return playerTeam;
        }
    }

    public static class FeixiaoRuanMeiLingshaMoze extends PlayerTeam {
        @Override
        public ArrayList<AbstractCharacter> getTeam() {
            ArrayList<AbstractCharacter> playerTeam = new ArrayList<>();
            playerTeam.add(getPrebuiltFeixiao());
            playerTeam.add(getPrebuiltRuanMeiCritSupport());
            playerTeam.add(getPrebuiltLingshaCritSupport());
            playerTeam.add(getPrebuiltMoze());
            return playerTeam;
        }
    }

    public static AbstractCharacter getPrebuiltLingshaCritSupport() {
        AbstractCharacter character = new Lingsha();
        character.EquipLightcone(new WhatIsReal(character));
        character.EquipRelicSet(new Thief(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HEALING).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ATK_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.BREAK_EFFECT, 6).addSubStat(RelicStats.Stats.SPEED, 12).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltMoze() {
        AbstractCharacter character = new Moze();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new Duke(character));
        character.EquipRelicSet(new Duran(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.ATK_PER).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 17).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 7);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltBronyaFei() {
        AbstractCharacter character = new Bronya();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_DAMAGE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 15).addSubStat(RelicStats.Stats.SPEED, 2).
                addSubStat(RelicStats.Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltAsta() {
        AbstractCharacter character = new Asta();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 8).addSubStat(RelicStats.Stats.SPEED, 8).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltFuXuan() {
        AbstractCharacter character = new FuXuan();
        character.EquipLightcone(new DayOneOfMyNewLife(character));
        character.EquipRelicSet(new Knight(character, false));
        character.EquipRelicSet(new Longevous(character, false));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.HP_PER);
        relicStats.addSubStat(RelicStats.Stats.HP_PER, 8).addSubStat(RelicStats.Stats.SPEED, 8).
                addSubStat(RelicStats.Stats.DEF_PER, 8);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltGallagher() {
        AbstractCharacter character = new Gallagher();
        character.EquipLightcone(new Multiplication(character));
        character.EquipRelicSet(new Thief(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HEALING).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.BREAK_EFFECT, 6).addSubStat(RelicStats.Stats.SPEED, 14);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltHanya() {
        AbstractCharacter character = new Hanya();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.ATK_PER, 6).addSubStat(RelicStats.Stats.SPEED, 10).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltRuanMeiCritSupport() {
        AbstractCharacter character = new RuanMei();
        character.EquipLightcone(new MemoriesOfThePast(character));
        character.EquipRelicSet(new Thief(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.BREAK_EFFECT, 10).addSubStat(RelicStats.Stats.SPEED, 7).
                addSubStat(RelicStats.Stats.EFFECT_RES, 6);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltSparkleFei() {
        AbstractCharacter character = new Sparkle();
        character.EquipLightcone(new PastAndFuture(character));
        character.EquipRelicSet(new Passerby(character));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_DAMAGE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.HP_PER).addMainStat(RelicStats.Stats.ERR);
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 13).addSubStat(RelicStats.Stats.SPEED, 4).
                addSubStat(RelicStats.Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltFeixiao() {
        AbstractCharacter character = new Feixiao();
        character.EquipLightcone(new IVentureForthToHunt(character));
        character.EquipRelicSet(new Valorous(character));
        character.EquipRelicSet(new Duran(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 11).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 13);
        relicStats.equipTo(character);
        return character;
    }

//    public static AbstractCharacter getPrebuiltFeixiao() {
//        AbstractCharacter character = new Feixiao();
//        character.EquipLightcone(new Swordplay(character));
//        character.EquipRelicSet(new Valorous(character));
//        character.EquipRelicSet(new Duran(character));
//        RelicStats relicStats = new RelicStats();
//        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
//                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
//        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 11).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 13);
//        relicStats.equipTo(character);
//        return character;
//    }

    public static AbstractCharacter getPrebuiltTopazSpeed() {
        AbstractCharacter character = new Topaz();
        character.EquipLightcone(new Swordplay(character));
        character.EquipRelicSet(new Duke(character));
        character.EquipRelicSet(new Duran(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 10).addSubStat(RelicStats.Stats.SPEED, 1);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltAventurineSpeed() {
        AbstractCharacter character = new Aventurine(false);
        character.EquipLightcone(new ConcertForTwo(character));
        character.EquipRelicSet(new Knight(character, false));
        character.EquipRelicSet(new Duke(character, false));
        character.EquipRelicSet(new BrokenKeel(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.DEF_PER).addMainStat(RelicStats.Stats.DEF_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 13).addSubStat(RelicStats.Stats.DEF_PER, 3).
                addSubStat(RelicStats.Stats.CRIT_DAMAGE, 5).addSubStat(RelicStats.Stats.SPEED, 3);
        relicStats.equipTo(character);
        return character;
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
        character.EquipLightcone(new ResolutionShinesAsPearlsOfSweat(character));
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
        relicStats.addSubStat(RelicStats.Stats.CRIT_DAMAGE, 4).addSubStat(RelicStats.Stats.SPEED, 13).
                addSubStat(RelicStats.Stats.EFFECT_RES, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltSwordMarchFei() {
        AbstractCharacter character = new SwordMarch();
        character.EquipLightcone(new CruisingInTheStellarSea(character));
        character.EquipRelicSet(new Musketeer(character));
        character.EquipRelicSet(new RutilentArena(character));
        RelicStats relicStats = new RelicStats();
        relicStats.addMainStat(RelicStats.Stats.CRIT_RATE).addMainStat(RelicStats.Stats.SPEED).
                addMainStat(RelicStats.Stats.ELEMENT_DAMAGE).addMainStat(RelicStats.Stats.ATK_PER);
        relicStats.addSubStat(RelicStats.Stats.CRIT_RATE, 10).addSubStat(RelicStats.Stats.CRIT_DAMAGE, 11).addSubStat(RelicStats.Stats.SPEED, 3);
        relicStats.equipTo(character);
        return character;
    }

    public static AbstractCharacter getPrebuiltSwordMarch() {
        AbstractCharacter character = new SwordMarch();
        character.EquipLightcone(new CruisingInTheStellarSea(character));
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
        character.EquipLightcone(new MemoriesOfThePast(character));
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
        character.EquipLightcone(new ForTomorrowsJourney(character));
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
        character.EquipLightcone(new PostOpConversation(character));
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
