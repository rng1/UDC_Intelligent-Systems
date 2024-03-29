package es.udc.intelligentsystems.MagicSquare;

import es.udc.intelligentsystems.Heuristic;
import es.udc.intelligentsystems.State;

public class MagicSquareHeuristic extends Heuristic {

    @Override
    public float evaluate(State e) {
        int i;
        float cost = 0;
        MagicSquareProblem.MagicSquareState mse = (MagicSquareProblem.MagicSquareState) e;
        for(i = 0; i < mse.getSquare().size(); i++){
            if(mse.getSquare().get(i) == 0) cost++;
        }
        return cost;
    }
}
