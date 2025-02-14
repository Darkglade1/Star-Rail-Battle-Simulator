package art.ameliah.hsr.game.moc.strifeofcreation;

import art.ameliah.hsr.battleLogic.wave.moc.MocTurbulence;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.powers.TempPower;

public class Turbulence extends MocTurbulence {

    public Turbulence() {
        super("Strife of Creation");
    }

    @Override
    protected void trigger() {
        getBattle().getPlayers().forEach(p -> {
            p.getCurrentEnergy().set(p.getCurrentEnergy().get() +  p.maxEnergy * 0.2f);

            if (p instanceof Memosprite<?, ?> memosprite) {
                memosprite.addPower(new DoubleTurn());
            }
        });
    }

    public static class DoubleTurn extends TempPower {
        public DoubleTurn() {
            super(1, "DoubleTurn");
        }

        @Override
        public void onEndTurn() {
            getBattle().AdvanceEntity(this.owner, 100);
            this.owner.removePower(this);
        }
    }
}
