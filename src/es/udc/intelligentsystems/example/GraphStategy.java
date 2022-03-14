package es.udc.intelligentsystems.example;

import es.udc.intelligentsystems.Action;
import es.udc.intelligentsystems.SearchProblem;
import es.udc.intelligentsystems.State;

import java.util.*;

public class GraphStategy extends Strategy4 {

    @Override
    public Node[] solve(SearchProblem p) throws Exception{
        List<Node> explored = new ArrayList<>();
        Deque<Node> frontier = new ArrayDeque<>();
        Node node = new Node(p.getInitialState(), null, null);
        frontier.addFirst(node);

        int i = 1;

        System.out.println((i++) + " - Starting search at " + node.state());

        //while (!p.isGoal(node.state())){
        while (!frontier.isEmpty()){
            node = frontier.remove();
            System.out.println((i++) + " - Current state changed to " + node.state());
            explored.add(node);
            //System.out.println((i++) + " - " + node.state() + " is not a goal");
            if(p.isGoal(node.state())){
                System.out.println((i) + " - END - " + node.state());
                return reconstructSol(explored);
            }

            Action[] availableActions = p.actions(node.state());
            boolean modified = false;
            for (Action acc: availableActions) {
                State sc = p.result(node.state(), acc);
                System.out.println((i++) + " - RESULT(" + node.state() + ","+ acc + ")=" + sc);
                if (!containState(sc, explored) && !containState(sc, frontier)) {
                    Node tempNode = new Node(sc, node, acc);
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
            if(sc.equals(node.state()))
                return true;
        return false;
    }
}
