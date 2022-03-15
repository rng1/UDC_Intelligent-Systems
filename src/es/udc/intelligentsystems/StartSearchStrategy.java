package es.udc.intelligentsystems;

import java.util.*;

public class StartSearchStrategy implements InformedSearchStrategy {

    @Override
    public State solve(SearchProblem p, Heuristic h) throws Exception {
        List<Node> explored = new ArrayList<>();
        Queue<Node> frontier = new PriorityQueue<>();
        Node magicSquareNode = new MagicSquareWeightedNode(p.getInitialState(), null, null, 0, h.evaluate(p.getInitialState()));
        frontier.offer(magicSquareNode);
        int i = 0;
        int j = 1;

        while (!frontier.isEmpty()){
            magicSquareNode = frontier.poll();

            if(p.isGoal(magicSquareNode.getState())){
                System.out.println( "\n\nNumber of expanded nodes: " + i +
                        "\nNumber of created nodes: " + j +
                        "\nNumber of explored nodes: " + explored.size() +
                        "\nSolution:");
                System.out.println(magicSquareNode);
                //return reconstructSol(magicSquareNode);
                return magicSquareNode.getState();
            }

            explored.add(magicSquareNode); i++;

            System.out.println(magicSquareNode);

            Action[] availableActions = p.actions(magicSquareNode.getState());

            for (Action acc: availableActions) {
                State sc = p.result(magicSquareNode.getState(), acc);
                float cost = ((MagicSquareWeightedNode) magicSquareNode).getStateCost() + acc.getCost();
                if (notContainState(sc, explored) && notContainState(sc, frontier)) {
                    Node tempMagicSquareNode = new MagicSquareWeightedNode(sc, magicSquareNode, acc, cost, cost + h.evaluate(sc));
                    frontier.offer(tempMagicSquareNode); j++;
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
