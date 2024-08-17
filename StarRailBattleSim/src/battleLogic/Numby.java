package battleLogic;

import characters.Topaz;

import java.util.ArrayList;

public class Numby extends AbstractSummon {
    Topaz owner;
    public static final String NAME = "Numby";

    public Numby(Topaz owner) {
        this.baseSpeed = 80;
        this.name = NAME;
        this.owner = owner;
    }

    public void takeTurn() {
        super.takeTurn();
        owner.numbyAttacksMetrics++;
        owner.numbyAttack(new ArrayList<>());
    }

    public void AdvanceForward() {
        float initialAV = Battle.battle.actionValueMap.get(this);
        if (initialAV > 0) {
            speedPriority = 0;
            owner.numbyAdvancedTimesMetrics++;
            Battle.battle.AdvanceEntity(this, 50);
            owner.actualNumbyAdvanceMetric += Math.abs(initialAV - Battle.battle.actionValueMap.get(this));
        } else {
            owner.wastedNumbyAdvances++;
        }
    }
}
