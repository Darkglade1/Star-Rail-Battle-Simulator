package battleLogic;

import characters.Robin;

public class Concerto extends AbstractEntity {
    Robin owner;

    public Concerto(Robin owner) {
        this.baseSpeed = 90;
        this.name = "Concerto";
        this.owner = owner;
    }
}
