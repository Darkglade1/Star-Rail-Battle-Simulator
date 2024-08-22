package characters.huohuo;

import battleLogic.BattleHelpers;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysUltGoal;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Huohuo extends AbstractCharacter<Huohuo> {
    private static String NAME = "Huohuo";
    
    HuohuoTalentPower talentPower = new HuohuoTalentPower();
    int talentCounter = 0;
    private int numTalentProcs = 0;
    private String numTalentProcsMetricName = "Number of Talent Procs";

    public Huohuo() {
        super(NAME, 1358, 602, 509, 98, 80, ElementType.WIND, 140, 100, Path.ABUNDANCE);

        this.addPower(new TracePower()
                .setStat(PowerStat.HP_PERCENT, 28)
                .setStat(PowerStat.FLAT_SPEED, 5)
                .setStat(PowerStat.EFFECT_RES, 18));

        this.registerGoal(0, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new HuoHuoTurnGoal(this));
    }

    public void useSkill() {
        talentCounter = 2;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(talentPower);
        }
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 0.5f, BattleHelpers.MultiplierStat.HP, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);
        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            if (character != this) {
                character.increaseEnergy(character.maxEnergy * 0.2f, false);
                character.addPower(TempPower.create(PowerStat.ATK_PERCENT, 40, 2, "Tail Atk Bonus"));
            }
        }
    }

    public void onCombatStart() {
        talentCounter = 1;
        for (AbstractCharacter<?> character : getBattle().getPlayers()) {
            character.addPower(talentPower);
        }
    }

    public void onTurnStart() {
        
        talentCounter--;
        if (talentCounter <= 0) {
            for (AbstractCharacter<?> character : getBattle().getPlayers()) {
                character.removePower(talentPower);
            }
        }
        if (currentEnergy >= ultCost) {
            useUltimate();
        }
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(numTalentProcsMetricName, String.valueOf(numTalentProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(numTalentProcsMetricName);
        return list;
    }

    private class HuohuoTalentPower extends AbstractPower {
        public HuohuoTalentPower() {
            this.name = this.getClass().getSimpleName();
            lastsForever = true;
        }

        @Override
        public void onTurnStart() {
            Huohuo.this.increaseEnergy(1);
            numTalentProcs++;
        }

        @Override
        public void onUseUltimate() {
            Huohuo.this.increaseEnergy(1);
            numTalentProcs++;
        }
    }
}
