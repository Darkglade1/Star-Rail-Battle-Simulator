package report;

import battleLogic.Battle;
import teams.EnemyTeam;
import teams.PlayerTeam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private PlayerTeam baselineTeam;
    private ArrayList<PlayerTeam> otherTeams;
    private ArrayList<EnemyTeam> enemyTeams;
    private int AVLength;

    public Report(PlayerTeam baselineTeam, ArrayList<PlayerTeam> otherTeams, ArrayList<EnemyTeam> enemyTeams, int AVLength) {
        this.baselineTeam = baselineTeam;
        this.otherTeams = otherTeams;
        this.enemyTeams = enemyTeams;
        this.AVLength = AVLength;
    }

    public void generateCSV() {
        StringBuilder CSV = new StringBuilder();
        for (EnemyTeam enemyTeam : enemyTeams) {
            float baselineDPAV;
            HashMap<Float, PlayerTeam> DPAVtracker = new HashMap<>();
            HashMap<PlayerTeam, Float> diffTracker = new HashMap<>();
            Battle battle = new Battle();
            Battle.battle = battle;
            battle.setPlayerTeam(baselineTeam.getTeam());
            battle.setEnemyTeam(enemyTeam.getTeam());
            battle.Start(AVLength);

            baselineDPAV = battle.finalDPAV;
            DPAVtracker.put(baselineDPAV, baselineTeam);
            diffTracker.put(baselineTeam, 100.0f);

            for (PlayerTeam playerTeam : otherTeams) {
                battle = new Battle();
                Battle.battle = battle;
                battle.setPlayerTeam(playerTeam.getTeam());
                battle.setEnemyTeam(enemyTeam.getTeam());
                battle.Start(AVLength);

                float otherTeamDPAV = battle.finalDPAV;
                DPAVtracker.put(otherTeamDPAV, playerTeam);
                float diff = otherTeamDPAV / baselineDPAV * 100;
                diffTracker.put(playerTeam, diff);
            }

            CSV.append(enemyTeam).append("\n");
            CSV.append("Team,DPAV,%DIFF\n");
            ArrayList<Float> DPAVList = new ArrayList<>();
            for (Map.Entry<Float,PlayerTeam> entry : DPAVtracker.entrySet()) {
                DPAVList.add(entry.getKey());
            }
            Collections.sort(DPAVList);
            for (int i = DPAVList.size() - 1; i > 0; i--) {
                float DPAV = DPAVList.get(i);
                PlayerTeam t = DPAVtracker.get(DPAV);
                CSV.append(String.format("%s,%.3f,%.2f%%\n", t.toString(), DPAV, diffTracker.get(t)));
            }
            CSV.append("\n").append("\n").append("\n");
        }
        File csvOutputFile = new File("output.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(CSV);
        } catch (FileNotFoundException e) {
            System.out.println("gg");
        }
    }
}
