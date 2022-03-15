package es.udc.intelligentsystems;

public class MagicSquareNode extends Node{

    State state;
    Node parent;
    Action action;

    public MagicSquareNode(State state, Node parent, Action action) {
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
        return parent != null;
    }

    @Override
    public String toString() {
        return "NODE{\n" + state + "Got here by " + action + "}\n";
    }
}
