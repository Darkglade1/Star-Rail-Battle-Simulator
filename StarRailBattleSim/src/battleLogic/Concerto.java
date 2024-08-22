package battleLogic;

import characters.robin.Robin;

public class Concerto extends AbstractEntity {
    Robin owner;

    public Concerto(Robin owner) {
        this.baseSpeed = 90;
        this.name = "Concerto";
        this.owner = owner;
    }
}
