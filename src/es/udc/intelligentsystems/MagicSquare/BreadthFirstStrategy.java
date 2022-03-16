package es.udc.intelligentsystems.MagicSquare;

import es.udc.intelligentsystems.*;

import java.util.*;

public class BreadthFirstStrategy implements SearchStrategy {

    @Override
    public Node[] solve(SearchProblem p) throws Exception {
        List<Node> explored = new ArrayList<>();
        Queue<Node> frontier = new LinkedList<>();
        Node magicSquareNode = new MagicSquareNode(p.getInitialState(), null, null);
        frontier.offer(magicSquareNode);
        int i = 0;
        int j = 1;

        while (!frontier.isEmpty()){
            magicSquareNode = frontier.remove();
            explored.add(magicSquareNode);

            i++;
            System.out.println(magicSquareNode);

            Action[] availableActions = p.actions(magicSquareNode.getState());

            for (Action acc: availableActions) {
                State sc = p.result(magicSquareNode.getState(), acc);
                Node tempMagicSquareNode = new MagicSquareNode(sc, magicSquareNode, acc);
                if(p.isGoal(sc)){
                    System.out.println( "\n\nNumber of expanded nodes: " + i +
                                        "\nNumber of created nodes: " + j +
                                        "\nSolution:");
                    System.out.println(tempMagicSquareNode);
                    return reconstructSol(tempMagicSquareNode);
                }
                if (notContainState(sc, explored) && notContainState(sc, frontier)) {
                    frontier.add(tempMagicSquareNode);
                    j++;
                }
            }
        }
        throw new Exception("No solution could be found");
    }

    boolean notContainState(State sc, Collection<Node> explored){
        for(Node magicSquareNode : explored)
            if(sc.equals(magicSquareNode.getState()))
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

}
