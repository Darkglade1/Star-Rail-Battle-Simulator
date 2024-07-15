package battleLogic;

public abstract class AbstractEntity {
    public String name;
    public int baseSpeed;

    public abstract float getBaseAV();

    public abstract void takeTurn();
}
