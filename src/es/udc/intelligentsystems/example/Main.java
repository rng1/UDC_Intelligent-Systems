package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.SearchProblem;
import es.udc.intelligentsystems.SearchStrategy;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        VacuumCleanerProblem.VacuumCleanerState initialState = new VacuumCleanerProblem.VacuumCleanerState(VacuumCleanerProblem.VacuumCleanerState.RobotPosition.LEFT,
                                                                                                    VacuumCleanerProblem.VacuumCleanerState.DirtPosition.BOTH);
        SearchProblem aspiradora = new VacuumCleanerProblem(initialState);

        SearchStrategy buscador = new GraphStategy();
        System.out.println(Arrays.toString(buscador.solve(aspiradora)));
    }
}
