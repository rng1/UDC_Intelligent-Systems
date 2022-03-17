package es.udc.intelligentsystems.MagicSquare;

import es.udc.intelligentsystems.Action;
import es.udc.intelligentsystems.Node;
import es.udc.intelligentsystems.State;

public class MagicSquareWeightedNode extends MagicSquareNode implements Comparable<MagicSquareWeightedNode>{

    private final float realCost;
    private final float estimatedCost;

    public MagicSquareWeightedNode(State state, Node parent, Action action, float realCost, float estimatedCost) {
        super(state, parent, action);
        this.realCost = realCost;
        this.estimatedCost = estimatedCost;
    }

    public float getRealCost(){ return this.realCost; }

    public float getEstimatedCost(){ return this.estimatedCost; }

    @Override
    public int compareTo(MagicSquareWeightedNode that) {
        int result = (int) (that.realCost - this.realCost);
        return result;
    }
}
