package art.ameliah.hsr.battleLogic.combat.ally;

import art.ameliah.hsr.battleLogic.BattleParticipant;
import art.ameliah.hsr.battleLogic.combat.base.AbstractAttack;
import art.ameliah.hsr.battleLogic.combat.hit.Hit;
import art.ameliah.hsr.battleLogic.combat.result.HitResult;
import art.ameliah.hsr.battleLogic.log.lines.character.FailedHit;
import art.ameliah.hsr.battleLogic.log.lines.character.HitResultLine;
import art.ameliah.hsr.characters.AbstractCharacter;
import art.ameliah.hsr.characters.remembrance.Memosprite;
import art.ameliah.hsr.enemies.AbstractEnemy;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

public class Attack extends AbstractAttack<AbstractCharacter<?>, AbstractEnemy, AttackLogic,DelayAttack> {

    @Getter
    private final Collection<Hit> pastHits = new ArrayList<>();

    public Attack(AbstractCharacter<?> source) {
        super(source);
    }

    @Override
    protected DelayAttack newDelayAttack(AbstractCharacter<?> source) {
        return new DelayAttack(source);
    }

    @Override
    protected void attack(DelayAttack dh) {
        AttackLogic attackLogic = new AttackLogic(this.source, this.targets, this.types, this, this::handleHit);

        this.source.emit(l -> l.beforeAttack(attackLogic));
        if (this.source instanceof Memosprite) {
            Memosprite<?, ?> memosprite = (Memosprite<?, ?>)source;
            memosprite.getMaster().emit(l -> l.beforeMemospriteAttack(attackLogic));
        }
        this.targets.forEach(t -> t.emit(l -> l.beforeAttacked(attackLogic)));

        dh.getLogic().accept(attackLogic);

        this.source.emit(l -> l.afterAttack(attackLogic));
        this.targets.addAll(attackLogic.getTargets());

        this.targets.forEach(t -> t.emit(l -> l.afterAttacked(attackLogic)));
    }

    private HitResult handleHit(Hit hit) {
        BattleParticipant source = hit.getSource();
        if (source instanceof AbstractCharacter<?> e) {
            e.emit(l -> l.beforeDoHit(hit));
        }
        hit.getTarget().emit(l -> l.beforeReceiveHit(hit));

        HitResult res = hit.getTarget().hit(hit);

        if (res.success()) {
            this.dmgDealt += hit.finalDmg();

            getBattle().increaseTotalPlayerDmg(hit.finalDmg());
            getBattle().updateContribution(hit.getSource(), res);
            getBattle().addToLog(new HitResultLine(res));
        } else {
            getBattle().addToLog(new FailedHit(hit));
        }

        if (source instanceof AbstractCharacter<?> e) {
            e.emit(l -> l.afterDoHit(res));
        }

        this.pastHits.add(hit);
        return res;
    }



}
