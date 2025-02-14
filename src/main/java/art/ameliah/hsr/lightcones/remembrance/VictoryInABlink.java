package art.ameliah.hsr.lightcones.remembrance;

import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.lightcones.AbstractLightcone;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TempPower;

import java.util.Collection;

public class VictoryInABlink extends AbstractLightcone {

    public VictoryInABlink(AbstractCharacter<?> owner) {
        super(847, 476, 397, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 24, "Victory In a Blink CD Boost"));
    }

    @Override
    public void afterSummon(Memosprite<?, ?> memosprite) {
        memosprite.addPower(new VictoryInABlinkListener());
    }

    public static class VictoryInABlinkListener extends PermPower {
        public VictoryInABlinkListener() {
            super("Victory In a Blink Listener");
        }

        @Override
        public void afterUseOnAlly(Collection<AbstractCharacter<?>> allies) {
            // Any implies one? Not sure
            if (allies.size() != 1) {
                return;
            }

            getBattle().getPlayers().forEach(player -> {
                player.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 16, 3, "Victory In a Blink DMG Boost"));
            });
        }
    }
}
