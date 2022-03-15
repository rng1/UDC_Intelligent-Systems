package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Strategy4 implements SearchStrategy {

    public Strategy4() {
    }

    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        List<Node> explored = new ArrayList<>();
        Node node = new VacuumNode(p.getInitialState(), null, null);
        explored.add(node);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + node.getState());

        while (!p.isGoal(node.getState())){
            System.out.println((i++) + " - " + node.getState() + " is not a goal");
            Action[] availableActions = p.actions(node.getState());
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(node.getState(), acc);
                System.out.println((i++) + " - RESULT(" + node.getState() + ","+ acc + ")=" + sc);
                if (notContainState(sc, explored)) {
                    node = new VacuumNode(sc, node, acc);
                    System.out.println((i++) + " - " + sc + " NOT explored");
                    explored.add(node);
                    modified = true;
                    System.out.println((i++) + " - Current state changed to " + node.getState());
                    break;
                }
                else
                    System.out.println((i++) + " - " + sc + " already explored");
            }
            if (!modified) throw new Exception("No solution could be found");
        }
        System.out.println((i) + " - END - " + node.getState());
        return reconstructSol(node);
    }

    boolean notContainState(State sc, List<Node> explored){
        for(Node node : explored)
            if(sc.equals(node.getState()))
                return false;
        return true;
    }

    Node[] reconstructSol(Node node){
        List<Node> tempSolution = new ArrayList<>();
        while(node.isChild()){
            tempSolution.add(node);
            node = node.getParent();
        }
        tempSolution.add(node);
        Collections.reverse(tempSolution);
        return tempSolution.toArray(new Node[0]);
    }

    /*@Override
    public State solve(SearchProblem p) throws Exception{
        ArrayList<State> explored = new ArrayList<State>();
        State currentState = p.getInitialState();
        explored.add(currentState);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + currentState);

        while (!p.isGoal(currentState)){
            System.out.println((i++) + " - " + currentState + " is not a goal");
            Action[] availableActions = p.actions(currentState);
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(currentState, acc);
                System.out.println((i++) + " - RESULT(" + currentState + ","+ acc + ")=" + sc);
                if (!explored.contains(sc)) {
                    currentState = sc;
                    System.out.println((i++) + " - " + sc + " NOT explored");
                    explored.add(currentState);
                    modified = true;
                    System.out.println((i++) + " - Current state changed to " + currentState);
                    break;
                }
                else
                    System.out.println((i++) + " - " + sc + " already explored");
            }
            if (!modified) throw new Exception("No solution could be found");
        }
        System.out.println((i++) + " - END - " + currentState);
        return currentState;
    }*/
}
