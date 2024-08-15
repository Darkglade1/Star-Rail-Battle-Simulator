package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

public class TextureOfMemories extends AbstractLightcone {

    public TextureOfMemories(AbstractCharacter owner) {
        super(1058, 423, 529, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_RES, 16));
    }

    @Override
    public void onAttacked(AbstractEnemy enemy) {
        // TODO: Check shield
        // TODO: Reduce dmg taken power
    }
}
