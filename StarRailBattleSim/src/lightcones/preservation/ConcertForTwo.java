package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class ConcertForTwo extends AbstractLightcone {

    public ConcertForTwo(AbstractCharacter owner) {
        super(953, 370, 463, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.DEF_PERCENT, 32));
    }

    @Override
    public void onBeforeHitEnemy(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types) {
        // TODO: For every on-field character that has a Shield, the DMG dealt by the wearer increases by 8%.
    }
}
