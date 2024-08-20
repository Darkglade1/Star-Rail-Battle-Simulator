package battleLogic;

import characters.Lingsha;

import java.util.ArrayList;

public class FuYuan extends AbstractSummon<Lingsha> {

    public FuYuan(Lingsha owner) {
        super(owner);
        this.baseSpeed = 90;
        this.name = "Fu Yuan";
    }

    public void takeTurn() {
        super.takeTurn();
        this.summoner.FuYuanAttack(true);
    }
}
