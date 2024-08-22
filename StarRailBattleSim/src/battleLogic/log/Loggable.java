package battleLogic.log;

public interface Loggable {

    String asString();

    void handle(Logger logger);

}
