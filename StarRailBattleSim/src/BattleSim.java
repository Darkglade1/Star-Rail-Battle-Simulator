import battleLogic.Battle;
import enemies.AbstractEnemy;
import enemies.FireWindImgWeakEnemy;
import enemies.PhysWeakEnemy;
import enemies.WindWeakEnemy;
import report.Report;
import teams.EnemyTeam;
import teams.PlayerTeam;

import java.util.ArrayList;

import static teams.EnemyTeam.*;
import static teams.PlayerTeam.*;

public class BattleSim {

    public static void main(String[] args) {
        debugTeam();
        //generateReportYunli();
        //generateReportFeixiao();
    }
    
    public static void debugTeam() {
        Battle battle = new Battle();
        Battle.battle = battle;

        //battle.setPlayerTeam(new TingyunYunliRobinHuohuoTeam().getTeam());
       // battle.setPlayerTeam(new TopazYunliRobinHuohuoTeam().getTeam());
        //battle.setPlayerTeam(new MarchYunliRobinHuohuoTeam().getTeam());
        //battle.setPlayerTeam(new SparkleYunliRobinHuohuoTeam().getTeam());
        //battle.setPlayerTeam(new SparkleYunliTingyunHuohuoTeam().getTeam());
        //battle.setPlayerTeam(new PelaYunliTingyunHuohuoTeam().getTeam());
        //battle.setPlayerTeam(new TopazYunliRobinAventurineTeam().getTeam());

//        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
//        enemyTeam.add(new PhysWeakEnemy(0, 2));
//        enemyTeam.add(new PhysWeakEnemy(1, 2));
//        enemyTeam.add(new PhysWeakEnemy(2, 2));
//        battle.setEnemyTeam(enemyTeam);

        battle.setPlayerTeam(new FeixiaoRobinAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoRobinAventurineMarch().getTeam());
        battle.setPlayerTeam(new FeixiaoSparkleAventurineTopaz().getTeam());
        //battle.setPlayerTeam(new FeixiaoSparkleAventurineMarch().getTeam());

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new WindWeakEnemy(0, 2));
        //enemyTeam.add(new FireWindImgWeakEnemy(0, 2));
        battle.setEnemyTeam(enemyTeam);

        battle.Start(5050);
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

    public static void generateReportFeixiao() {
        PlayerTeam baselineTeam = new FeixiaoRobinAventurineMarch();
        ArrayList<PlayerTeam> otherTeams = new ArrayList<>();
        otherTeams.add(new FeixiaoRobinAventurineTopaz());
        otherTeams.add(new FeixiaoSparkleAventurineTopaz());
        otherTeams.add(new FeixiaoSparkleAventurineMarch());

        ArrayList<EnemyTeam> enemyTeams = new ArrayList<>();
        enemyTeams.add(new WindWeakTarget1());
        enemyTeams.add(new FireWindImgWeakTarget1());

        String notes = "Notes: E0S0 Feixiao, E0S0 other 5 stars. E6 4 stars. Maxed out traces and levels. Enemies are level 95. Relics are +15 with relatively relatable rolls. Simulations run for 50 cycles to reduce the impact of RNG and leftover AV/Energy at the end of combat.";
        Report report = new Report(baselineTeam, otherTeams, enemyTeams, 5050, notes);
        report.generateCSV();
    }


}