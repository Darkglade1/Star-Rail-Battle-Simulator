package battleLogic;

import characters.Topaz;

import java.util.ArrayList;

public class Numby extends AbstractEntity {
    Topaz owner;

    public Numby(Topaz owner) {
        this.baseSpeed = 80;
        this.name = "Numby";
        this.owner = owner;
    }

    public void takeTurn() {
        owner.numbyAttacksMetrics++;
        owner.numbyAttack(new ArrayList<>());
        speedPriority = 999; //reset speed priority if it was changed
    }

    public void AdvanceForward() {
        float initialAV = Battle.battle.actionValueMap.get(this);
        if (initialAV > 0) {
            speedPriority = 0;
            owner.numbyAdvancedTimesMetrics++;
            Battle.battle.AdvanceEntity(this, 50);
            owner.actualNumbyAdvanceMetric += Math.abs(initialAV - Battle.battle.actionValueMap.get(this));
        }
    }
}
