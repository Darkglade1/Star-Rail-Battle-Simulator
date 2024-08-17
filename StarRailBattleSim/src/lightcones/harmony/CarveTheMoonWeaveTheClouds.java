package lightcones.harmony;

import battleLogic.Battle;
import characters.AbstractCharacter;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.NoSuchElementException;
import java.util.Random;

public class CarveTheMoonWeaveTheClouds extends AbstractLightcone {

    public CarveTheMoonWeaveTheClouds(AbstractCharacter owner) {
        super(953, 476, 331, owner);
    }

    private void refreshEffects() {
        int type = Battle.battle.weaveEffectRng.nextInt(3) + 1;
        CarveTheMoonWeaveTheCloudsEffect effect = new CarveTheMoonWeaveTheCloudsEffect(type);
        for (AbstractCharacter character : Battle.battle.playerTeam) {
            character.removePower(effect.name);
            character.addPower(effect);
        }
    }

    @Override
    public void onCombatStart() {
        refreshEffects();
    }

    @Override
    public void onTurnStart() {
        refreshEffects();
    }

    public static class CarveTheMoonWeaveTheCloudsEffect extends PermPower {

        public CarveTheMoonWeaveTheCloudsEffect(int type) {
            super("Carve the Moon, Weave the Clouds effect");
            switch (type) {
                case 1:
                    this.setStat(PowerStat.ATK_PERCENT, 20);
                    break;
                case 2:
                    this.setStat(PowerStat.CRIT_DAMAGE, 24);
                    break;
                case 3:
                    this.setStat(PowerStat.ENERGY_REGEN, 12);
                    break;
                default:
                    throw new NoSuchElementException();
            }
        }
    }
}
