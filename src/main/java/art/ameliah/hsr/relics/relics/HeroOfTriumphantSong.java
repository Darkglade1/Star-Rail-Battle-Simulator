package art.ameliah.hsr.relics.relics;

import art.ameliah.hsr.battleLogic.combat.ally.AttackLogic;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.DamageType;
import art.ameliah.hsr.characters.remembrance.Memomaster;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.enemies.AbstractEnemy;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import art.ameliah.hsr.powers.TempPower;
import art.ameliah.hsr.relics.AbstractRelicSetBonus;

import java.util.List;

public class HeroOfTriumphantSong extends AbstractRelicSetBonus {

    public HeroOfTriumphantSong(AbstractCharacter<?> owner) {
        super(owner);
    }

    public HeroOfTriumphantSong(AbstractCharacter<?> owner, boolean fullSet) {
        super(owner, fullSet);
    }

    @Override
    public void onEquip() {
        this.owner.addPower(PermPower.create(PowerStat.ATK_PERCENT, 12, "Hero of Triumphant Song ATK boost"));

        if (isFullSet) {
            this.owner.addPower(new HeroOfTriumphantSong4PC());
        }
    }

    public static class HeroOfTriumphantSong4PC extends PermPower {
        public HeroOfTriumphantSong4PC() {
            super("Hero of Triumphant Song 4PC");

            this.setConditionalStat(PowerStat.SPEED_PERCENT, this::speedBoost);
        }

        @Override
        public void afterSummon(Memosprite<?, ?> memosprite) {
            getBattle().IncreaseSpeed(this.owner, this);
        }

        @Override
        public void beforeMemospriteAttack(AttackLogic attack) {
            if (this.getOwner() instanceof Memomaster<?> memomaster) {
                if (memomaster.getMemo() != null) {
                    memomaster.getMemo().addPower(TempPower.create(PowerStat.CRIT_DAMAGE, 30, 2, "Hero of Triumphant Song 4PC CD"));
                    memomaster.addPower(TempPower.create(PowerStat.CRIT_DAMAGE, 30, 2, "Hero of Triumphant Song 4PC CD"));
                }
            }
        }

        public float speedBoost(AbstractCharacter<?> character) {
            if (character instanceof Memomaster<?> memomaster) {
                if (memomaster.getMemo() != null) {
                    return 6;
                }
            }

            return 0;
        }
    }
}
