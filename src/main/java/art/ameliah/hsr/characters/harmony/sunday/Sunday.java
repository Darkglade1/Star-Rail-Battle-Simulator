package art.ameliah.hsr.characters.harmony.sunday;

import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.DamageType;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.MoveType;
import art.ameliah.hsr.characters.Path;
import art.ameliah.hsr.characters.Summoner;
import art.ameliah.hsr.characters.goal.shared.target.ally.DpsAllyTargetGoal;
import art.ameliah.hsr.characters.goal.shared.target.enemy.HighestEnemyTargetGoal;
import art.ameliah.hsr.characters.goal.shared.turn.AlwaysSkillGoal;
import art.ameliah.hsr.characters.goal.shared.ult.AlwaysUltGoal;
import art.ameliah.hsr.characters.remembrance.Memomaster;
import art.ameliah.hsr.powers.AbstractPower;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TempPower;
import art.ameliah.hsr.powers.TracePower;

/**
 * Note: If you're planning to use a relic or lc with sunday. Check if it needs a check to also deploy to
 */
public class Sunday extends AbstractCharacter<Sunday> {

    public static final String NAME = "Sunday";
    public static final String SKILL_POWER_NAME = "SundaySkillPower";
    public static final String TALENT_POWER_NAME = "The Sorrowing Body";
    public static final String TECHNIQUE_POWER_NAME = "SundayTechniquePower";
    public static final String TheBeatified = "The Beatified";

    private boolean firstAbilityUse = true;
    private int theBeatifiedTurnsRemaining = 0;

    public Sunday() {
        super(NAME, 1242, 640, 534, 96, 80, ElementType.IMAGINARY, 130, 100, Path.HARMONY);

        this.addPower(new TracePower()
                .setStat(PowerStat.CRIT_DAMAGE, 37.7f)
                .setStat(PowerStat.EFFECT_RES, 18)
                .setStat(PowerStat.DEF_PERCENT, 12.5f)
        );

        this.registerGoal(0, new AlwaysSkillGoal<>(this));
        this.registerGoal(0, new AlwaysUltGoal<>(this));
        this.registerGoal(0, new SummonerTargetGoal(this));
        this.registerGoal(10, new DpsAllyTargetGoal<>(this));
        this.registerGoal(0, new HighestEnemyTargetGoal<>(this));
    }

    @Override
    public void onCombatStart() {
        this.increaseEnergy(25, "Exalted Sweep");
    }

    @Override
    protected void useSkill() {
        var target = this.getAllyTarget();

        if (firstAbilityUse) {
            this.firstAbilityUse = false;
            target.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 50, 2, TECHNIQUE_POWER_NAME));
        }

        target.addPower(new SundaySkillPower());
        target.addPower(new SundayTalentPower());
        if (target instanceof Summoner summoner) {
            var summon = summoner.getSummon();
            if (summon != null) {
                summon.addPower(new SundaySkillPower());
                summon.addPower(new SundayTalentPower());
            }
        }

        this.relicSetBonus.forEach(rs -> rs.useOnAlly(target, MoveType.SKILL));
        this.lightcone.useOnAlly(target, MoveType.SKILL);

        var debuff = target.powerList.stream()
                .filter(p -> p.type.equals(AbstractPower.PowerType.DEBUFF))
                .filter(p -> !p.lastsForever)
                .findFirst();
        debuff.ifPresent(target::removePower);

        if (target.hasPower(TheBeatified)) {
            getBattle().generateSkillPoint(this, 1);
        }

        if (target.getPath().equals(Path.HARMONY)) {
            return;
        }

        // Character should act first, so advanced second
        if (target instanceof Summoner summoner) {
            var summon = summoner.getSummon();
            if (summon != null) {
                getBattle().AdvanceEntity(summon, 100);
            }
        }
        getBattle().AdvanceEntity(target, 100);
    }

    @Override
    protected void useBasic() {
        this.doAttack(DamageType.BASIC, MoveType.BASIC, (e, al) -> al.hit(e, 1, TOUGHNESS_DAMAGE_SINGLE_UNIT));
    }

    @Override
    protected void useUltimate() {
        AbstractCharacter<?> target = this.getAllyTarget();
        if (firstAbilityUse) {
            this.firstAbilityUse = false;
            target.addPower(TempPower.create(PowerStat.DAMAGE_BONUS, 50, 2, TECHNIQUE_POWER_NAME));
        }

        target.increaseEnergy(this.ultimateEnergyCharge(target), false, "Sunday Ultimate Energy increase");

        getBattle().getPlayers().forEach(p -> p.removePower(Sunday.TheBeatified));
        target.addPower(new TheBeatified());
        if (target instanceof Memomaster<?> memomaster) {
            if (memomaster.getMemo() != null) {
                memomaster.getMemo().addPower(new TheBeatified());
            }
        }
        this.theBeatifiedTurnsRemaining = 3;
        this.relicSetBonus.forEach(rs -> rs.useOnAlly(target, MoveType.ULTIMATE));
        this.lightcone.useOnAlly(target, MoveType.ULTIMATE);

        increaseEnergy(10, "Test"); // fake hit energy to ensure 3 turn ult
    }

    private float ultimateEnergyCharge(AbstractCharacter<?> target) {
        float per = target.maxEnergy * 0.2f;

        return Math.max(per, 40);
    }

    @Override
    public void onTurnStart() {
        this.theBeatifiedTurnsRemaining--;

        if (this.theBeatifiedTurnsRemaining <= 0) {
            getBattle().getPlayers().forEach(p -> p.removePower(Sunday.TheBeatified));
        }
    }

    public static class SundayTalentPower extends TempPower {
        public SundayTalentPower() {
            super(3, TALENT_POWER_NAME);
            this.setStat(PowerStat.CRIT_CHANCE, 20);
        }
    }

    public static class SundaySkillPower extends TempPower {

        public SundaySkillPower() {
            super(2, SKILL_POWER_NAME);

            // Does the summon have the be present?
            float dmgBoost = this.getOwner() instanceof Summoner ? 80 : 30;

            this.setStat(PowerStat.DAMAGE_BONUS, dmgBoost);
        }

    }


    public class TheBeatified extends PermPower {
        public TheBeatified() {
            super(TheBeatified);
            float cdBoost = 12 + 0.3f * Sunday.this.getTotalCritDamage();
            this.setStat(PowerStat.CRIT_DAMAGE, cdBoost);
        }
    }
}
