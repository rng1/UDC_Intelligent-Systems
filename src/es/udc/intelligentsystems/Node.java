package es.udc.intelligentsystems;

public abstract class Node {

    @Override
    public abstract java.lang.String toString();

    public abstract State getState();

    public abstract Node getParent();

    public abstract boolean isChild();
}
