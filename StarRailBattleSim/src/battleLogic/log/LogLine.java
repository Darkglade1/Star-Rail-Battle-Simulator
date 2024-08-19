package battleLogic.log;

public abstract class LogLine {

    public abstract String asString();

    public abstract void handle(Logger logger);

}
