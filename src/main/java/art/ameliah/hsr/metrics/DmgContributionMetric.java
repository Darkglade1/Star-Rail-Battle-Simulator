package art.ameliah.hsr.metrics;

import art.ameliah.hsr.battleLogic.BattleParticipant;
import art.ameliah.hsr.battleLogic.IBattle;
import art.ameliah.hsr.battleLogic.combat.hit.Hit;
import art.ameliah.hsr.battleLogic.combat.result.HitResult;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.DamageType;
import com.sun.source.tree.Tree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DmgContributionMetric extends AbstractMetric{

    private final Map<BattleParticipant, Float> map = new ConcurrentHashMap<>();
    private final Map<BattleParticipant, TreeMap<DamageType, Float>> dmgPerType = new ConcurrentHashMap<>();
    private final Map<BattleParticipant, Float> overFlowMap = new ConcurrentHashMap<>();
    private final IBattle battle;

    public DmgContributionMetric(IBattle battle, String key, String desc) {
        super(key, desc);
        this.battle = battle;
    }

    public void record(BattleParticipant participant, HitResult res) {
        this.map.put(participant, this.map.getOrDefault(participant, 0.0f) + res.getHit().finalDmg());

        var damageTypeFloatMap = this.dmgPerType.computeIfAbsent(participant, _ -> {
            return new TreeMap<>(Comparator.comparing(DamageType::name));
        });
        for (var type : res.getHit().getTypes()) {
            damageTypeFloatMap.put(type, damageTypeFloatMap.getOrDefault(type, 0.0f) + res.getHit().finalDmg());
        }
        this.dmgPerType.put(participant, damageTypeFloatMap);

        float overflow = res.getHit().finalDmg() - res.getDmgDealt();
        this.overFlowMap.put(participant, this.overFlowMap.getOrDefault(participant, 0.0f) + overflow);
    }

    @Override
    public String representation() {
        return String.format("Damage Contribution: %s \nOverflow damage %s\n%s",
                this.contribution(), this.overFlowMap, this.characterBreakDown());
    }

    public String overflow() {
        return overFlowMap.entrySet()
                .stream()
                .sorted(Map.Entry.<BattleParticipant, Float>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(entry -> entry.getKey().getName()))
                .map(entry -> {
                    float percent = entry.getValue() / this.map.get(entry.getKey()) * 100;
                    return String.format("%s: %,.0f DMG (%.3f%%)", entry.getKey().getName(), entry.getValue(), percent);
                })
                .collect(Collectors.joining(" | "));
    }

    public String contribution() {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<BattleParticipant, Float>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(entry -> entry.getKey().getName()))
                .map(entry -> {
                    float percent = entry.getValue() / this.battle.getTotalPlayerDmg() * 100;
                    return String.format("%s: %,.3f DPAV (%.3f%%)", entry.getKey().getName(), entry.getValue() / this.battle.getActionValueUsed(), percent);
                })
                .collect(Collectors.joining(" | "));
    }

    private String characterBreakDown() {
        return this.dmgPerType.entrySet().stream()
                .filter(e -> e.getKey() instanceof AbstractCharacter<?>)
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(BattleParticipant::getName)))
                .map(e -> {
                    String d = e.getValue().entrySet().stream()
                            .map(inner -> String.format("%s: %,.3f", inner.getKey(), inner.getValue()))
                            .collect(Collectors.joining("\n\t"));
                    return String.format("%s:\n\t%s", e.getKey(), d);
                })
                .collect(Collectors.joining("\n"));
    }
}
