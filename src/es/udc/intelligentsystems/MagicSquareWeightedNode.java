package es.udc.intelligentsystems;

public class MagicSquareWeightedNode extends MagicSquareNode{

    private float stateCost;
    private float pathCost;

    public MagicSquareWeightedNode(State state, Node parent, Action action, float stateCost, float pathCost) {
        super(state, parent, action);
        this.stateCost = stateCost;
        this.pathCost = pathCost;
    }

    public float getStateCost(){ return this.stateCost; }
}
