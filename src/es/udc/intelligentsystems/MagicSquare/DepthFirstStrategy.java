package es.udc.intelligentsystems.MagicSquare;

import es.udc.intelligentsystems.*;

import java.util.*;

public class DepthFirstStrategy implements SearchStrategy {

    @Override
    public Node[] solve(SearchProblem p) throws Exception {
        List<Node> explored = new ArrayList<>();
        Stack<Node> frontier = new Stack<>();
        Node magicSquareNode = new MagicSquareNode(p.getInitialState(), null, null);
        frontier.push(magicSquareNode);
        int i = 0;
        int j = 1;

        while (!frontier.isEmpty()){
            magicSquareNode = frontier.pop();

            if(p.isGoal(magicSquareNode.getState())){
                System.out.println( "\n\nNumber of expanded nodes: " + i +
                        "\nNumber of created nodes: " + j +
                        "\nNumber of explored nodes: " + explored.size() +
                        "\nSolution:");
                System.out.println(magicSquareNode);
                return reconstructSol(magicSquareNode);
            }

            explored.add(magicSquareNode); i++;

            System.out.println(magicSquareNode);

            Action[] availableActions = p.actions(magicSquareNode.getState());

            for (Action acc: availableActions) {
                State sc = p.result(magicSquareNode.getState(), acc);
                if (notContainState(sc, explored) && notContainState(sc, frontier)) {
                    Node tempMagicSquareNode = new MagicSquareNode(sc, magicSquareNode, acc);
                    frontier.push(tempMagicSquareNode); j++;
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
