package battleLogic;

import characters.Topaz;

import java.util.ArrayList;

public class Numby extends AbstractSummon<Topaz> {
    public static final String NAME = "Numby";

    public Numby(Topaz owner) {
        super(owner);
        this.baseSpeed = 80;
        this.name = NAME;
    }

    public void takeTurn() {
        super.takeTurn();
        this.summoner.numbyAttacksMetrics++;
        this.summoner.numbyAttack(new ArrayList<>());
    }

    public void AdvanceForward() {
        float initialAV = getBattle().getActionValueMap().get(this);
        if (initialAV > 0) {
            speedPriority = 0;
            this.summoner.numbyAdvancedTimesMetrics++;
            getBattle().AdvanceEntity(this, 50);
            this.summoner.actualNumbyAdvanceMetric += (int) Math.abs(initialAV - getBattle().getActionValueMap().get(this));
        } else {
            this.summoner.wastedNumbyAdvances++;
        }
    }
}
