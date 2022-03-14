package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.*;

import java.util.ArrayList;
import java.util.List;

public class Strategy4 implements SearchStrategy {

    public Strategy4() {
    }

    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        List<Node> explored = new ArrayList<>();
        Node node = new Node(p.getInitialState(), null, null);
        explored.add(node);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + node.state());

        while (!p.isGoal(node.state())){
            System.out.println((i++) + " - " + node.state() + " is not a goal");
            Action[] availableActions = p.actions(node.state());
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(node.state(), acc);
                System.out.println((i++) + " - RESULT(" + node.state() + ","+ acc + ")=" + sc);
                if (!containState(sc, explored)) {
                    node = new Node(sc, node, acc);
                    System.out.println((i++) + " - " + sc + " NOT explored");
                    explored.add(node);
                    modified = true;
                    System.out.println((i++) + " - Current state changed to " + node.state());
                    break;
                }
                else
                    System.out.println((i++) + " - " + sc + " already explored");
            }
            if (!modified) throw new Exception("No solution could be found");
        }
        System.out.println((i) + " - END - " + node.state());
        return reconstructSol(explored);
    }

    boolean containState(State sc, List<Node> explored){
        for(Node node : explored)
            if(sc.equals(node.state()))
                return true;
        return false;
    }

    Node[] reconstructSol(List<Node> explored){
        int length = explored.size();
        Node[] solution = new Node[length];
        for(int i = 0; i < length; i++)
            solution[i] = explored.get(i);
        return solution;
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
