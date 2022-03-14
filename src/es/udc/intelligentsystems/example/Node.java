package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.Action;
import es.udc.intelligentsystems.State;

public record Node(State state, es.udc.intelligentsystems.example.Node parent,
                   Action action) {

    @Override
    public String toString() {
        return "NODE{" +
                "state=" + state +
                ", action=" + action +
                '}';
    }
}
