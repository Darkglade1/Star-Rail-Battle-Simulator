package relics.ornament;

import battleLogic.AbstractEntity;
import battleLogic.AbstractSummon;
import battleLogic.Battle;
import characters.AbstractCharacter;
import characters.AbstractSummoner;
import enemies.AbstractEnemy;
import powers.PermPower;
import powers.PowerStat;
import relics.AbstractRelicSetBonus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class TheWondrousBananAmusementPark extends AbstractRelicSetBonus {
    public TheWondrousBananAmusementPark(AbstractCharacter owner, boolean fullSet) {
        super(owner, fullSet);
    }

    public TheWondrousBananAmusementPark(AbstractCharacter owner) {
        super(owner);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.CRIT_DAMAGE, 16, "The Wondrous Banan Amusement Park CD boost"));
    }

    @Override
    public void onCombatStart() {
        this.owner.addPower(new TheWondrousBananAmusementParkCD());
    }

    public static class TheWondrousBananAmusementParkCD extends PermPower {
        public TheWondrousBananAmusementParkCD() {
            super("The Wondrous Banan Amusement extra Park CD boost");
        }

        @Override
        public float getConditionalCritDamage(AbstractCharacter character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            if (!(character instanceof AbstractSummoner)) {
                return 0;
            }

            List<AbstractSummon> allSummons = ((AbstractSummoner) character).getSummons();
            boolean activeSummon = Battle.battle.actionValueMap
                    .keySet()
                    .stream()
                    .filter(c -> c instanceof AbstractSummon)
                    .map(c -> (AbstractSummon) c)
                    .anyMatch(allSummons::contains);

            if (activeSummon) {
                return 32;
            }

            return 0;
        }
    }
}
