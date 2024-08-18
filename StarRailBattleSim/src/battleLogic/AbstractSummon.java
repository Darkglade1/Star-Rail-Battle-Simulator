package battleLogic;


import characters.AbstractSummoner;

public class AbstractSummon<O extends AbstractSummoner> extends AbstractEntity  {

    protected final O summoner;

    public AbstractSummon(O summoner) {
        this.summoner = summoner;
    }

    @Override
    public IBattle getBattle() {
        return this.summoner.getBattle();
    }
}
