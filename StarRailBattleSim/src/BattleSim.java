import battleLogic.Battle;
import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import enemies.FireWindImgLightningWeakEnemy;
import enemies.PhysWeakEnemy;
import report.Report;
import teams.EnemyTeam;
import teams.PlayerTeam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import static teams.EnemyTeam.*;
import static teams.PlayerTeam.*;

public class BattleSim {

    public static void main(String[] args) {
        debugTeam();
        //generateReportYunli();
        //generateReportFeixiao();
        //ameliasSuperDump();
    }

    public static void debugTeam() {
        Battle battle = new Battle();
        Battle.battle = battle;

//        battle.setPlayerTeam(new TingyunYunliRobinHuohuoTeam().getTeam());
//        //battle.setPlayerTeam(new TopazYunliRobinHuohuoTeam().getTeam());
//        //battle.setPlayerTeam(new MarchYunliRobinHuohuoTeam().getTeam());
//       battle.setPlayerTeam(new SparkleYunliRobinHuohuoTeam().getTeam());
////        battle.setPlayerTeam(new SparkleYunliTingyunHuohuoTeam().getTeam());
////        battle.setPlayerTeam(new PelaYunliTingyunHuohuoTeam().getTeam());
////        battle.setPlayerTeam(new TopazYunliRobinAventurineTeam().getTeam());
//
//        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
//        enemyTeam.add(new PhysWeakEnemy(0, 2));
//        enemyTeam.add(new PhysWeakEnemy(1, 2));
//        enemyTeam.add(new PhysWeakEnemy(2, 2));
//        battle.setEnemyTeam(enemyTeam);

        battle.setPlayerTeam(new FeixiaoRobinAventurineTopaz().getTeam());
        battle.setPlayerTeam(new FeixiaoRobinAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoSparkleAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoSparkleAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRuanMeiAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoRuanMeiAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoPelaAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoPelaAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinAventurinePela().getTeam());
        //battle.setPlayerTeam(new FeixiaoTopazAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoHanyaAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoHanyaAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRuanMeiGallagherTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoRuanMeiGallagherMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinGallagherTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinGallagherMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinTopazFuXuan().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinMarchFuXuan().getTeam());
        //battle.setPlayerTeam(new FeixiaoHanyaGallagherMarch().getTeam());
        //battle.setPlayerTeam(new AstaFeixiaoGallagherMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoBronyaAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoBronyaAventurineMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoHanyaGallagherMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinAventurineMoze().getTeam());
        //battle.setPlayerTeam(new FeixiaoHanyaGallagherMarch().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinGallagherBronya().getTeam());
        battle.setPlayerTeam(new FeixiaoRobinLingshaTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoRuanMeiLingshaTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinLingshaMarch().getTeam());

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        //enemyTeam.add(new WindWeakEnemy(0, 0));
        enemyTeam.add(new FireWindImgLightningWeakEnemy(0, 0));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(5050);
    }

    public static void generateReportFeixiao() {
        PlayerTeam baselineTeam = new FeixiaoHanyaGallagherMarch();
        ArrayList<PlayerTeam> otherTeams = new ArrayList<>();
        otherTeams.add(new FeixiaoRobinAventurineTopaz());
        otherTeams.add(new FeixiaoRobinAventurineMarch());
        otherTeams.add(new FeixiaoRobinAventurineMoze());
        otherTeams.add(new FeixiaoRobinLingshaTopaz());
        otherTeams.add(new FeixiaoRobinLingshaMarch());
        otherTeams.add(new FeixiaoRobinLingshaMoze());
        otherTeams.add(new FeixiaoRobinGallagherTopaz());
        otherTeams.add(new FeixiaoRobinGallagherMarch());
        otherTeams.add(new FeixiaoRobinGallagherMoze());
        otherTeams.add(new FeixiaoRobinTopazFuXuan());
        otherTeams.add(new FeixiaoRobinMarchFuXuan());
        otherTeams.add(new FeixiaoRobinGallagherBronya());
        otherTeams.add(new FeixiaoRuanMeiAventurineTopaz());
        otherTeams.add(new FeixiaoRuanMeiAventurineMarch());
        otherTeams.add(new FeixiaoRuanMeiAventurineMoze());
        otherTeams.add(new FeixiaoRuanMeiLingshaTopaz());
        otherTeams.add(new FeixiaoRuanMeiLingshaMarch());
        otherTeams.add(new FeixiaoRuanMeiLingshaMoze());
        otherTeams.add(new FeixiaoRuanMeiGallagherTopaz());
        otherTeams.add(new FeixiaoRuanMeiGallagherMarch());
        otherTeams.add(new FeixiaoSparkleAventurineTopaz());
        otherTeams.add(new FeixiaoSparkleAventurineMarch());
        otherTeams.add(new FeixiaoSparkleAventurineMoze());
        otherTeams.add(new FeixiaoBronyaAventurineTopaz());
        otherTeams.add(new FeixiaoBronyaAventurineMarch());
        otherTeams.add(new FeixiaoBronyaAventurineMoze());
        otherTeams.add(new FeixiaoHanyaAventurineTopaz());
        otherTeams.add(new FeixiaoHanyaAventurineMarch());
        otherTeams.add(new FeixiaoHanyaGallagherMoze());
        otherTeams.add(new AstaFeixiaoAventurineTopaz());
        otherTeams.add(new AstaFeixiaoAventurineMarch());
        otherTeams.add(new AstaFeixiaoGallagherMarch());
        otherTeams.add(new FeixiaoPelaAventurineTopaz());
        otherTeams.add(new FeixiaoPelaAventurineMarch());
        otherTeams.add(new FeixiaoTopazAventurineMarch());
        otherTeams.add(new FeixiaoMozeAventurineMarch());

        ArrayList<EnemyTeam> enemyTeams = new ArrayList<>();
        enemyTeams.add(new FireWindImgLightningWeakTarget1());
        enemyTeams.add(new WindWeakTarget1());

        String notes = "E0S0 other 5 stars. E6 4 stars. Maxed out traces and levels. Enemies are level 95. Relics are +15 with relatively relatable rolls. Simulations run for 50 cycles to reduce the impact of RNG and leftover AV/Energy at the end of combat. \n Feixiao will wait until the team's buffs or debuffs are present before using Ultimate. This results in wasting some stacks but waiting to maximize each ultimate's damage is more damage in the long term.";
        Report report = new Report(baselineTeam, otherTeams, enemyTeams, 5050, notes);
        report.generateCSV();
    }

    public static void generateReportYunli() {
        PlayerTeam baselineTeam = new PelaYunliTingyunHuohuoTeam();
        ArrayList<PlayerTeam> otherTeams = new ArrayList<>();
        otherTeams.add(new TingyunYunliRobinHuohuoTeam());
        otherTeams.add(new TopazYunliRobinHuohuoTeam());
        otherTeams.add(new MarchYunliRobinHuohuoTeam());
        otherTeams.add(new SparkleYunliRobinHuohuoTeam());
        otherTeams.add(new SparkleYunliTingyunHuohuoTeam());
        otherTeams.add(new TopazYunliRobinAventurineTeam());
        otherTeams.add(new MarchYunliRobinAventurineTeam());
        otherTeams.add(new TingyunYunliRobinAventurineTeam());
        otherTeams.add(new TopazYunliTingyunHuohuoTeam());
        otherTeams.add(new MarchYunliTingyunHuohuoTeam());
        otherTeams.add(new PelaYunliRobinHuohuoTeam());
        otherTeams.add(new PelaYunliSparkleHuohuoTeam());

        ArrayList<EnemyTeam> enemyTeams = new ArrayList<>();
        enemyTeams.add(new PhysWeakTargets3());
        enemyTeams.add(new PhysWeakTargets2());
        enemyTeams.add(new PhysWeakTargets1());

        //enemyTeams.add(new PhysFireWeakTargets3());
        //enemyTeams.add(new PhysFireWeakTargets2());
        //enemyTeams.add(new PhysFireWeakTargets1());

        String notes = "Notes: E0S1 Yunli, E0S0 other 5 stars. E6 4 stars. Maxed out traces and levels. Enemies are level 95. Relics are +15 with relatively relatable rolls. Simulations run for 50 cycles to reduce the impact of RNG and leftover AV/Energy at the end of combat.";
        Report report = new Report(baselineTeam, otherTeams, enemyTeams, 5050, notes);
        report.generateCSV();
    }

    public static void ameliasSuperDump() {
        TestHelper.getStaticClassesExtendingA(PlayerTeam.class, PlayerTeam.class)
                .stream()
                .map(c -> TestHelper.callMethodOnClasses(c, "getTeam"))
                .map(l -> (ArrayList<AbstractCharacter>) l)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(team -> team.getClass().getSimpleName()))
                .forEach(team -> {
                    Battle battle = new Battle();
                    Battle.battle = battle;
                    battle.setPlayerTeam(team);

                    ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
                    enemyTeam.add(new FireWindImgLightningWeakEnemy(0, 0));
                    battle.setEnemyTeam(enemyTeam);

                    battle.Start(500, true);
                });
    }


}