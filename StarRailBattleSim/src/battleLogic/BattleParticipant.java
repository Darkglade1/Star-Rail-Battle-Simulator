package battleLogic;

public interface BattleParticipant {

    IBattle getBattle();

    default boolean inBattle() {
        return getBattle() != null && getBattle().inCombat();
    }

}
