package battleLogic;

import characters.Lingsha;

import java.util.ArrayList;

public class FuYuan extends AbstractSummon {
    Lingsha owner;

    public FuYuan(Lingsha owner) {
        this.baseSpeed = 90;
        this.name = "Fu Yuan";
        this.owner = owner;
    }

    public void takeTurn() {
        super.takeTurn();
        owner.FuYuanAttack(true);
    }
}
