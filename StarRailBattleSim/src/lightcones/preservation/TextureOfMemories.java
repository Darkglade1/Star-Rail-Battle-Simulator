package lightcones.preservation;

import characters.AbstractCharacter;
import enemies.AbstractEnemy;
import lightcones.AbstractLightcone;
import powers.PermPower;
import powers.PowerStat;

import java.util.ArrayList;

public class TextureOfMemories extends AbstractLightcone {

    public TextureOfMemories(AbstractCharacter owner) {
        super(1058, 423, 529, owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.EFFECT_RES, 16, "Texture Of Memories Effect Resistance Boost"));
    }

    @Override
    public void onAttacked(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
        // TODO: Check shield
        // TODO: Reduce dmg taken power
    }
}
