package report;

import battleLogic.Battle;
import characters.AbstractCharacter;
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
        HashMap<String, String> characterCSVs = new HashMap<>();
        for (EnemyTeam enemyTeam : enemyTeams) {
            HashMap<String, HashMap<String, ArrayList<String>>> characterMetricsMap = new HashMap<>();
            HashMap<String, ArrayList<String>> characterTeamList = new HashMap<>();
            HashMap<String, ArrayList<String>> characterMetricOrderList = new HashMap<>();

            ArrayList<AbstractCharacter> baseline = baselineTeam.getTeam();
            float baselineDPAV;
            HashMap<Float, PlayerTeam> DPAVtracker = new HashMap<>();
            HashMap<PlayerTeam, Float> diffTracker = new HashMap<>();
            Battle battle = new Battle();
            Battle.battle = battle;
            battle.setPlayerTeam(baseline);
            battle.setEnemyTeam(enemyTeam.getTeam());
            battle.Start(AVLength);

            baselineDPAV = battle.finalDPAV;
            DPAVtracker.put(baselineDPAV, baselineTeam);
            diffTracker.put(baselineTeam, 100.0f);
            updateCharacterCSVs(characterMetricsMap, characterTeamList, characterMetricOrderList, baseline, baselineTeam);

            for (PlayerTeam playerTeam : otherTeams) {
                ArrayList<AbstractCharacter> otherTeam = playerTeam.getTeam();
                battle = new Battle();
                Battle.battle = battle;
                battle.setPlayerTeam(otherTeam);
                battle.setEnemyTeam(enemyTeam.getTeam());
                battle.Start(AVLength);

                float otherTeamDPAV = battle.finalDPAV;
                DPAVtracker.put(otherTeamDPAV, playerTeam);
                float diff = otherTeamDPAV / baselineDPAV * 100;
                diffTracker.put(playerTeam, diff);
                updateCharacterCSVs(characterMetricsMap, characterTeamList, characterMetricOrderList, otherTeam, playerTeam);
            }

            CSV.append(enemyTeam).append("\n");
            CSV.append("Team,DPAV,%DIFF\n");
            ArrayList<Float> DPAVList = new ArrayList<>();
            for (Map.Entry<Float,PlayerTeam> entry : DPAVtracker.entrySet()) {
                DPAVList.add(entry.getKey());
            }
            Collections.sort(DPAVList);
            for (int i = DPAVList.size() - 1; i >= 0; i--) {
                float DPAV = DPAVList.get(i);
                PlayerTeam t = DPAVtracker.get(DPAV);
                CSV.append(String.format("%s,%.3f,%.2f%%\n", t.toString(), DPAV, diffTracker.get(t)));
            }
            CSV.append("\n").append("\n").append("\n");

            for (Map.Entry<String, HashMap<String, ArrayList<String>>> entry : characterMetricsMap.entrySet()) {
                StringBuilder characterCSV = new StringBuilder();
                if (characterCSVs.containsKey(entry.getKey())) {
                    characterCSV = new StringBuilder(characterCSVs.get(entry.getKey()));
                }
                characterCSV.append(enemyTeam).append("\n").append(entry.getKey() + " Metrics,");
                ArrayList<String> teamRow = characterTeamList.get(entry.getKey());
                for (String team : teamRow) {
                    characterCSV.append(team).append(",");
                }
                characterCSV.append("\n");
                HashMap<String, ArrayList<String>> metricRows = entry.getValue();
                ArrayList<String> metricOrder = characterMetricOrderList.get(entry.getKey());
                for (String metric : metricOrder) {
                    ArrayList<String> metricRow = metricRows.get(metric);
                    characterCSV.append(metric).append(",");
                    for (String metricValue : metricRow) {
                        characterCSV.append(metricValue).append(",");
                    }
                    characterCSV.append("\n");
                }
                characterCSV.append("\n").append("\n").append("\n");
                characterCSVs.put(entry.getKey(), characterCSV.toString());
            }
        }
        File csvOutputFile = new File("summary.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(CSV);
        } catch (FileNotFoundException e) {
            System.out.println("gg");
        }

        for (Map.Entry<String, String> entry : characterCSVs.entrySet()) {
            File characterCSV = new File(entry.getKey() + "Metrics.csv");
            try (PrintWriter pw = new PrintWriter(characterCSV)) {
                pw.println(entry.getValue());
            } catch (FileNotFoundException e) {
                System.out.println("gg 2");
            }
        }
    }

    public void updateCharacterCSVs(HashMap<String, HashMap<String, ArrayList<String>>> characterMetricsMap, HashMap<String, ArrayList<String>> characterTeamList, HashMap<String, ArrayList<String>> characterMetricOrderList, ArrayList<AbstractCharacter> team, PlayerTeam playerTeam) {
        for (AbstractCharacter character : team) {
            if (!characterMetricsMap.containsKey(character.name)) {
                HashMap<String, ArrayList<String>> map = new HashMap<>();
                characterMetricsMap.put(character.name, map);
            }
            HashMap<String, ArrayList<String>> characterMetricMap = characterMetricsMap.get(character.name);
            HashMap<String, String> characterMetrics = character.getCharacterSpecificMetricMap();
            for (Map.Entry<String,String> entry : characterMetrics.entrySet()) {
                if (!characterMetricMap.containsKey(entry.getKey())) {
                    ArrayList<String> list = new ArrayList<>();
                    characterMetricMap.put(entry.getKey(), list);
                }
                ArrayList<String> metricList = characterMetricMap.get(entry.getKey());
                metricList.add(entry.getValue());
            }

            if (!characterTeamList.containsKey(character.name)) {
                ArrayList<String> teamList = new ArrayList<>();
                characterTeamList.put(character.name, teamList);
            }
            ArrayList<String> list = characterTeamList.get(character.name);
            list.add(playerTeam.toString());

            characterMetricOrderList.put(character.name, character.getOrderedCharacterSpecificMetricsKeys());
        }
    }
}
