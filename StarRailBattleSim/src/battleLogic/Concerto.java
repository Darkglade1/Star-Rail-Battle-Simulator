package battleLogic;

import battleLogic.log.lines.character.ConcertoEnd;
import characters.robin.Robin;
import powers.PermPower;

public class Concerto extends AbstractEntity {
    Robin owner;

    public Concerto(Robin owner) {
        this.baseSpeed = 90;
        this.name = "Concerto";
        this.owner = owner;
        this.addPower(new ConcertoResetPower());
    }

    @Override
    public IBattle getBattle() {
        return this.owner.getBattle();
    }

    public class ConcertoResetPower extends PermPower {
        public ConcertoResetPower() {
            super("Concerto Reset Power");
        }

        @Override
        public void onTurnStart() {
            getBattle().addToLog(new ConcertoEnd());
            getBattle().getActionValueMap().remove(this.owner);
            Concerto.this.owner.onConcertoEnd();
            getBattle().setNextUnit(Concerto.this.owner);
            Concerto.this.owner.emit(BattleEvents::onTurnStart);
        }
    }
}
