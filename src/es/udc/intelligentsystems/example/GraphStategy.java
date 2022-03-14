package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.*;

import java.util.*;

public class GraphStategy extends Strategy4 {

    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        List<Node> explored = new ArrayList<>();
        Deque<Node> frontier = new ArrayDeque<>();
        Node node = new VacuumNode(p.getInitialState(), null, null);
        frontier.addFirst(node);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + node.getState());

        //while (!p.isGoal(node.state())){
        while (!frontier.isEmpty()){
            node = frontier.remove();
            System.out.println((i++) + " - Current state changed to " + node.getState());
            explored.add(node);
            //System.out.println((i++) + " - " + node.state() + " is not a goal");
            if(p.isGoal(node.getState())){
                System.out.println((i) + " - END - " + node.getState());
                return reconstructSol(node);
            }

            Action[] availableActions = p.actions(node.getState());
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(node.getState(), acc);
                System.out.println((i++) + " - RESULT(" + node.getState() + ","+ acc + ")=" + sc);
                if (notContainState(sc, explored) && !containState(sc, frontier)) {
                    Node tempNode = new VacuumNode(sc, node, acc);
                    System.out.println((i++) + " - " + sc + " NOT explored, added to frontier");
                    frontier.addFirst(tempNode);
                    modified = true;
                }
            }
            if (!modified) throw new Exception("No solution could be found");
        }
        return null;
    }

    boolean containState(State sc, Deque<Node> frontier){
        for(Node node : frontier)
            if(sc.equals(node.getState()))
                return true;
        return false;
    }
}
