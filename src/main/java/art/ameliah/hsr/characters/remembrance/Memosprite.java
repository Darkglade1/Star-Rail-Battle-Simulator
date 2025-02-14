package art.ameliah.hsr.characters.remembrance;

import art.ameliah.hsr.battleLogic.log.lines.character.DoMove;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.ElementType;
import art.ameliah.hsr.characters.MoveType;
import art.ameliah.hsr.characters.Path;
import art.ameliah.hsr.characters.goal.shared.turn.AlwaysBasicGoal;
import art.ameliah.hsr.powers.AbstractPower;
import art.ameliah.hsr.powers.PermPower;
import art.ameliah.hsr.powers.PowerStat;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Memosprite<C extends Memosprite<C, M>, M extends Memomaster<M>> extends AbstractCharacter<C> {

    private final M master;

    @SuppressWarnings("unchecked")
    public Memosprite(M master, String name, int baseHP, int baseSpeed, int level, ElementType elementType,
                      float maxEnergy, int tauntValue, Path path) {
        super(name, baseHP, 0, 0, baseSpeed, level, elementType, maxEnergy, tauntValue, path);

        this.registerGoal(0, new AlwaysBasicGoal<>((C)this));
        this.master = master;
        this.addPower(this.masterStatsCopy());
    }

    private AbstractPower masterStatsCopy() {
        PermPower statsCopy = new PermPower(this.name + " master stats copy: " + this.getMaster().getName());

        var dontCopy = List.of(PowerStat.SPEED_PERCENT, PowerStat.FLAT_SPEED,
                PowerStat.FLAT_HP, PowerStat.HP_PERCENT);

        for (var power : this.getMaster().getPreCombatPowers()) {
            for (var stat : power.getStats().entrySet()) {
                if (dontCopy.contains(stat.getKey())) {
                    continue;
                }

                statsCopy.increaseStat(stat.getKey(), stat.getValue());
            }
        }

        statsCopy.increaseStat(PowerStat.FLAT_DEF, this.getMaster().getBaseDef());
        statsCopy.increaseStat(PowerStat.FLAT_DEF, this.getMaster().lightcone.baseDef);
        statsCopy.increaseStat(PowerStat.FLAT_ATK, this.getMaster().getBaseAtk());
        statsCopy.increaseStat(PowerStat.FLAT_ATK, this.getMaster().lightcone.baseAtk);

        return statsCopy;
    }

    protected abstract void memoSkill();

    @Override
    protected void basicSequence() {
        this.actionMetric.record(MoveType.MEMOSPRITE_SKILL);

        getBattle().addToLog(new DoMove(this, MoveType.MEMOSPRITE_SKILL));
        this.increaseEnergy(10, BASIC_ENERGY_GAIN+" (from memo)");
        this.memoSkill();
    }

    @Override
    protected final void useBasic() {
    }

    @Override
    protected final void useSkill() {
    }

    @Override
    protected final void useUltimate() {
    }

    @Override
    public void increaseEnergy(float amount, boolean ERRAffected, String source) {
        this.getMaster().increaseEnergy(amount, ERRAffected, source);
    }
}
