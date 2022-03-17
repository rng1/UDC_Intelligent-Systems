package es.udc.intelligentsystems.MagicSquare;

import es.udc.intelligentsystems.Heuristic;
import es.udc.intelligentsystems.InformedSearchStrategy;
import es.udc.intelligentsystems.SearchProblem;
import es.udc.intelligentsystems.SearchStrategy;

import java.util.Arrays;
import java.util.List;

public class MainEx2B {

    public static void main(String[] args) throws Exception {
        //List<Integer> initialSquare = Arrays.asList(4,9,2, 3,5,0, 0,1,0); int size = 3;
        //List<Integer> initialSquare = Arrays.asList(2,0,0, 0,0,0, 0,0,0); int size = 3;
        List<Integer> initialSquare = Arrays.asList(2,0,0,0, 0,0,0,0, 0,0,0,0, 0,1,0,0); int size = 4;
        //2,8,15,9, 14,12,5,3, 11,13,4,6, 7,1,10,16
        //List<Integer> initialSquare = Arrays.asList(2,8,15,9, 0,0,0,0, 0,0,0,0, 0,1,0,0); int size = 4;
        //List<Integer> initialSquare = Arrays.asList(2,16,13,3, 12,7,6,0, 0,0,0,0, 0,1,0,0); int size = 4;
        //List<Integer> initialSquare = Arrays.asList(2,16,13,3, 12,7,6,9, 5,10,11,8, 15,1,4,0); int size = 4;

        MagicSquareProblem.MagicSquareState initialState = new MagicSquareProblem.MagicSquareState(size, initialSquare);
        SearchProblem magicSquare = new MagicSquareProblem(initialState);

        InformedSearchStrategy buscador = new ASearchStrategy();
        Heuristic heuristica = new MagicSquareHeuristic();
        System.out.println(buscador.solve(magicSquare, heuristica));
    }
}
