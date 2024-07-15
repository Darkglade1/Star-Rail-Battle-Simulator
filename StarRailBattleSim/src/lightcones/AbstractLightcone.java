package lightcones;

public abstract class AbstractLightcone {

    public int baseHP;
    public int baseAtk;
    public int baseDef;

    public AbstractLightcone(int baseHP, int baseAtk, int baseDef) {
        this.baseHP = baseHP;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
    }

}
