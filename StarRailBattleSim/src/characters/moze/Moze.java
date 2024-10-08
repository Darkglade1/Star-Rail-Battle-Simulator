package characters.moze;

import battleLogic.BattleHelpers;
import battleLogic.log.lines.character.DoMove;
import battleLogic.log.lines.entity.GainCharge;
import characters.AbstractCharacter;
import characters.Path;
import characters.goal.shared.AlwaysSkillGoal;
import characters.goal.shared.AlwaysUltGoal;
import characters.goal.shared.DontUltMissingPowerGoal;
import characters.goal.shared.UltAtEndOfBattle;
import enemies.AbstractEnemy;
import powers.AbstractPower;
import powers.PowerStat;
import powers.TempPower;
import powers.TracePower;

import java.util.ArrayList;
import java.util.HashMap;

public class Moze extends AbstractCharacter<Moze> {
    public static String NAME = "Moze";
    
    public int FUAs = 0;
    public int talentProcs = 0;
    private String FUAsMetricName = "Number of Follow Up Attacks Used";
    private String talentProcsMetricName = "Talent Extra Damage Procs";
    private MozePreyPower preyPower;
    private int chargeCount;
    private int MAX_CHARGE = 9;
    private int chargeLost = 0;
    private int CHARGE_ATTACK_THRESHOLD = 3;
    private boolean skillPointRecovered = false;
    public boolean isDeparted = false;

    public Moze() {
        super(NAME, 811, 600, 353, 111, 80, ElementType.LIGHTNING, 120, 75, Path.HUNT);

        this.addPower(new TracePower()
                .setStat(PowerStat.ATK_PERCENT, 18)
                .setStat(PowerStat.CRIT_DAMAGE, 37.3f)
                .setStat(PowerStat.HP_PERCENT, 10));
        this.hasAttackingUltimate = true;

        preyPower = new MozePreyPower();

        this.registerGoal(0, new UltAtEndOfBattle<>(this));
        this.registerGoal(10, DontUltMissingPowerGoal.robin(this));
        this.registerGoal(20, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new AlwaysSkillGoal<>(this));
    }

    public void useSkill() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.SKILL);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        preyPower.owner = enemy;
        enemy.addPower(preyPower);
        getBattle().getHelper().hitEnemy(this, enemy, 1.65f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_TWO_UNITS);
        increaseCharge(MAX_CHARGE);

        getBattle().getHelper().PostAttackLogic(this, types);
        getBattle().getActionValueMap().remove(this);
        isDeparted = true;
    }
    public void useBasic() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.BASIC);
        getBattle().getHelper().PreAttackLogic(this, types);

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 1.1f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        getBattle().getHelper().PostAttackLogic(this, types);
    }

    public void useUltimate() {
        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.ULTIMATE);
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 30, 2, "Moze Damage Bonus"));

        AbstractEnemy enemy = getBattle().getMiddleEnemy();
        getBattle().getHelper().hitEnemy(this, enemy, 2.92f, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_THREE_UNITs);

        getBattle().getHelper().PostAttackLogic(this, types);

        useFollowUp();
    }

    public void useFollowUp() {
        AbstractEnemy enemy = (AbstractEnemy) preyPower.owner;
        moveHistory.add(MoveType.FOLLOW_UP);
        FUAs++;
        getBattle().addToLog(new DoMove(this, MoveType.FOLLOW_UP));
        increaseEnergy(10, FUA_ENERGY_GAIN);

        ArrayList<DamageType> types = new ArrayList<>();
        types.add(DamageType.FOLLOW_UP);
        getBattle().getHelper().PreAttackLogic(this, types);

        float totalMult = 2.01f;
        float initialHitsMult = totalMult * 0.08f;
        float finalHitMult = totalMult * 0.6f;

        for (int i = 0; i < 5; i++) {
            getBattle().getHelper().hitEnemy(this, enemy, initialHitsMult, BattleHelpers.MultiplierStat.ATK, types, 0);
        }
        getBattle().getHelper().hitEnemy(this, enemy, finalHitMult, BattleHelpers.MultiplierStat.ATK, types, TOUGHNESS_DAMAGE_SINGLE_UNIT);

        if (chargeCount == 0) {
            preyPower.owner.removePower(preyPower);
        }

        getBattle().getHelper().PostAttackLogic(this, types);

        if (!skillPointRecovered) {
            getBattle().generateSkillPoint(this, 1);
            skillPointRecovered = true;
        }
    }

    public void onTurnStart() {
        skillPointRecovered = false;
    }

    public void increaseCharge(int amount) {
        chargeLost = 0;
        int initalStack = chargeCount;
        chargeCount += amount;
        if (chargeCount > MAX_CHARGE) {
            chargeCount = MAX_CHARGE;
        }
        getBattle().addToLog(new GainCharge(this, amount, initalStack, chargeCount));
    }

    public void decreaseCharge(int amount) {
        if (chargeCount >= 1) {
            int initalStack = chargeCount;
            chargeCount -= amount;
            chargeLost += amount;
            if (chargeCount < 0) {
                chargeCount = 0;
            }
            getBattle().addToLog(new GainCharge(this, amount, initalStack, chargeCount));
            if (chargeLost >= CHARGE_ATTACK_THRESHOLD) {
                chargeLost -= CHARGE_ATTACK_THRESHOLD;
                useFollowUp();
            }
        }
    }

    public void onCombatStart() {
        getBattle().AdvanceEntity(this, 30);
        increaseEnergy(20, "from E1");
    }

    public HashMap<String, String> getCharacterSpecificMetricMap() {
        HashMap<String, String> map = super.getCharacterSpecificMetricMap();
        map.put(FUAsMetricName, String.valueOf(FUAs));
        map.put(talentProcsMetricName, String.valueOf(talentProcs));
        return map;
    }

    public ArrayList<String> getOrderedCharacterSpecificMetricsKeys() {
        ArrayList<String> list = super.getOrderedCharacterSpecificMetricsKeys();
        list.add(FUAsMetricName);
        list.add(talentProcsMetricName);
        return list;
    }

    public HashMap<String, String> addLeftoverCharacterAVMetric(HashMap<String, String> metricMap) {
        Float leftoverAV = getBattle().getActionValueMap().get(this);
        if (leftoverAV == null) {
            metricMap.put(leftoverAVMetricName, String.format("%d (Charge Left)", chargeCount));
        } else {
            return super.addLeftoverCharacterAVMetric(metricMap);
        }

        return metricMap;
    }

    private class MozePreyPower extends AbstractPower {
        public MozePreyPower() {
            this.name = this.getClass().getSimpleName();
            this.type = PowerType.DEBUFF;
            this.lastsForever = true;
        }
        @Override
        public float receiveConditionalCritDamage(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            return 40;
        }

        @Override
        public float getConditionalDamageTaken(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> damageTypes) {
            for (AbstractCharacter.DamageType type : damageTypes) {
                if (type == AbstractCharacter.DamageType.FOLLOW_UP) {
                    return 25;
                }
            }
            return 0;
        }

        @Override
        public void onAttacked(AbstractCharacter<?> character, AbstractEnemy enemy, ArrayList<AbstractCharacter.DamageType> types, int energyFromAttacked) {
            boolean trigger = true;
            if (character instanceof Moze) {
                if (types.contains(DamageType.ULTIMATE)) {
                    trigger = true;
                } else if ((types.contains(DamageType.SKILL) || types.contains(DamageType.FOLLOW_UP))) {
                    trigger = false;
                }
            }
            if (trigger) {
                talentProcs++;
                getBattle().getHelper().additionalDamageHitEnemy(Moze.this, enemy, 0.33f, BattleHelpers.MultiplierStat.ATK);
                increaseEnergy(2, TALENT_ENERGY_GAIN);
                decreaseCharge(1);
            }
        }

        @Override
        public void onRemove() {
            getBattle().getActionValueMap().put(Moze.this, Moze.this.getBaseAV());
            getBattle().AdvanceEntity(Moze.this, 20);
            isDeparted = false;
        }
    }
}
