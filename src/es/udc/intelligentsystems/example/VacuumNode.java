package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.Action;
import es.udc.intelligentsystems.Node;
import es.udc.intelligentsystems.State;

public class VacuumNode extends Node{

    State state;
    Node parent;
    Action action;

    public VacuumNode(State state, Node parent, Action action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
    }

    public State getState() {
        return state;
    }

    public Node getParent() { return parent; }

    @Override
    public boolean isChild() {
        return this.parent != null;
    }

    @Override
    public String toString() {
        return "NODE{" +
                "state=" + state +
                ", action=" + action +
                '}';
    }
}
