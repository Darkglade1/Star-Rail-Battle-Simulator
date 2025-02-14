package art.ameliah.hsr;

import art.ameliah.hsr.battleLogic.Battle;
import art.ameliah.hsr.battleLogic.IBattle;
import art.ameliah.hsr.battleLogic.log.DefaultLogger;
import art.ameliah.hsr.battleLogic.log.lines.battle.TurnEnd;
import art.ameliah.hsr.battleLogic.log.lines.battle.TurnStart;
import art.ameliah.hsr.builder.ConfigLoader;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.enemies.AbstractEnemy;
import art.ameliah.hsr.enemies.AllWeakPassiveEnemy;
import art.ameliah.hsr.enemies.FireWindImgLightningWeakEnemy;
import art.ameliah.hsr.game.moc.ScalegorgeTidalflow11;
import art.ameliah.hsr.metrics.CounterMetric;
import art.ameliah.hsr.metrics.DmgContributionMetric;
import art.ameliah.hsr.teams.AglaeaTeams;
import art.ameliah.hsr.teams.PlayerTeam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static String commitHash = TestHelper.commitHash();

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get("./export/"));
            Files.createDirectories(Paths.get("./export_2/"));
        } catch (Exception e) {
            /* Swallow */
        }
        Locale.setDefault(Locale.UK);

        //run();
        darkgladeTestRun();

        //ameliasSanityCheck();
        //WaveTester.MocTest();
        //WaveTester.MocDivTest();
        //WaveTester.PfTest();
    }

    public static void run() {
        var battles = ConfigLoader.loadTeams();

        for (var battle : battles) {
            System.out.println(battle.getKey());
            var b = new ScalegorgeTidalflow11.FirstHalf(battle.characters);
            b.setLogger(bat -> new WaveTester.WaveTesterLogger(bat, battle.getKey()));
            b.Start(450);

            //DmgContributionMetric metric = b.getMetricRegistry().getMetric("battle-dmg-contribution");
            //System.out.printf("Total dmg: %,d%n", b.getTotalPlayerDmg());
            //System.out.println(metric.representation());
            System.out.println();
        }
    }

    public static void darkgladeTestRun() {
        Battle battle = new Battle();

        //battle.setPlayerTeam(new AglaeaTeams.DoubleSpeedAglaeaTeam().getTeam()); // 2 rotation ends in 270 AV
        //battle.setPlayerTeam(new AglaeaTeams.FastAglaeaSundayTeam().getTeam()); // 2 rotation ends in 262 AV
        battle.setPlayerTeam(new AglaeaTeams.SlowAglaeaHyperSundayTeam().getTeam()); // 2 rotation ends in 270 AV

        ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
        enemyTeam.add(new AllWeakPassiveEnemy(0));
        enemyTeam.add(new AllWeakPassiveEnemy(1));
        enemyTeam.add(new AllWeakPassiveEnemy(2));
        battle.setEnemyTeam(enemyTeam);
        battle.Start(270);

//        DmgContributionMetric metric = battle.getMetricRegistry().getMetric("battle-dmg-contribution");
        System.out.printf("Total dmg: %,d%n", battle.getTotalPlayerDmg());
        System.out.printf("DPAV: %.3f", battle.getTotalPlayerDmg() / battle.initialBattleLength);
//        System.out.println(metric.representation());
//        System.out.println();
    }

    @SuppressWarnings("unchecked")
    public static void ameliasSanityCheck() {
        AtomicInteger length = new AtomicInteger();

        var classes = TestHelper.getStaticClassesExtendingA(PlayerTeam.class, PlayerTeam.class);
        classes.addAll(TestHelper.getStaticClassesExtendingA(AglaeaTeams.class, PlayerTeam.class));

        List<Pair<String, ArrayList<AbstractCharacter<?>>>> teams = classes
                .stream()
                .map(c -> new Pair<>(c.getSimpleName(), (ArrayList<AbstractCharacter<?>>) TestHelper.callMethodOnClasses(c, "getTeam")))
                .sorted(Comparator.comparing(Pair::key))
                .peek(p -> length.set(Math.max(length.get(), p.key().length() + 3)))
                .toList();

        for (Pair<String, ArrayList<AbstractCharacter<?>>> p : teams) {
            IBattle battle = constructBattle(p);
            String Prefix = p.key() + ":" + " ".repeat(Math.max(0, length.get() - (p.key().length() + 1)));

            battle.setPlayerTeam(p.value());
            ArrayList<AbstractEnemy> enemyTeam = new ArrayList<>();
            enemyTeam.add(new FireWindImgLightningWeakEnemy(0, 0));
            battle.setEnemyTeam(enemyTeam);

            System.out.println(Prefix);
            battle.Start(550);

            CounterMetric<Float> playerDmg = battle.getMetricRegistry().getMetric("battle-total-player-dmg");
            DmgContributionMetric dmg = battle.getMetricRegistry().getMetric("battle-dmg-contribution");

            System.out.println(dmg.contribution() + "\n" + dmg.overflow() + "\nTotal Dmg: " + String.format("%,.3f", playerDmg.get()) + "\n");
        }
    }

    private static IBattle constructBattle(Pair<String, ArrayList<AbstractCharacter<?>>> p) {
        String path = "export/" + commitHash;
        File dir = new File(path);
        if (!dir.exists() && !dir.mkdir()) {
            throw new RuntimeException("Couldn't create directory " + path);
        }

        PrintStream printStream;
        try {
            printStream = new PrintStream(new FileOutputStream(path + "/" + p.key() + ".log"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new Battle((b) -> new DefaultLogger(b, printStream) {

            @Override
            public void handle(TurnStart turnStart) {
                this.out.println();
                this.log(turnStart);
            }

            @Override
            public void handle(TurnEnd turnEnd) {
                this.log(turnEnd);
                this.out.println();
            }
        });
    }

    private record Pair<K, V>(K key, V value) {
    }


}