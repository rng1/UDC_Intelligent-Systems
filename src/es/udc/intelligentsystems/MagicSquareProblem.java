package es.udc.intelligentsystems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MagicSquareProblem extends SearchProblem {

    public MagicSquareProblem(State initialState) {
        super(initialState);
    }

    public static class MagicSquareAction extends Action {

        private final int number;
        private final int position;

        public MagicSquareAction(int number, int position) {
            this.number = number;
            this.position = position;
        }

        @Override
        public String toString() {
            return number + " at " + position;
        }

        @Override
        public boolean isApplicable(State st) {
            return true;
        }

        @Override
        public State applyTo(State st) {
            MagicSquareState msSt = (MagicSquareState) st;
            List<Integer> newSquare = new ArrayList<>(msSt.square);
            int size = msSt.size;


            if(size*size < number) throw new IndexOutOfBoundsException();

            newSquare.set(position, number);

            return new MagicSquareState(size, newSquare);
        }
    }

    public static class MagicSquareState extends State {
        private final List<Integer> square;
        private final int size;

        public MagicSquareState(int size, List<Integer> square) {
            this.size = size;
            this.square = square;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            for(int i = 0; i < square.size(); i = i + size) {
                for (int j = i; j < (size + i); j++)
                    string.append("[").append(square.get(j)).append("]");
                string.append("\n");
            }
            return String.valueOf(string);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MagicSquareState that = (MagicSquareState) o;

            if (size != that.size) return false;
            return square == that.square;
        }

        @Override
        public int hashCode() {
            int result = size;
            result = 31 * result + square.hashCode();
            return result;
        }
    }

    @Override
    public boolean isGoal(State st) {
        int length = ((MagicSquareState) st).square.size();
        int size = ((MagicSquareState) st).size;
        int result = 0;
        int objective = (size*((size*size) + 1))/2;

        int i, j;

        // CHECK HORIZONTALS
        for(i = 0; i < length; i = i + size) {
            for (j = i; j < size + i; j++) {
                result += ((MagicSquareState) st).square.get(j);
            }
            if(result != objective) return false;
            result = 0;
        }

        // CHECK VERTICALS
        for(i = 0; i < size; i++) {
            for (j = i; j < length; j = j + size) {
                result += ((MagicSquareState) st).square.get(j);
            }
            if(result != objective) return false;
            result = 0;
        }

        // CHECK DIAGONALS
        for(i = 0; i < length; i = i + size + 1)
            result += ((MagicSquareState) st).square.get(i);
        if(result != objective) return false;
        result = 0;
        for(i = size - 1; i < (length - 1); i = i + size - 1)
            result += ((MagicSquareState) st).square.get(i);

        return result == objective;
    }

    @Override
    public Action[] actions(State st) {
        MagicSquareState msSt = (MagicSquareState) st;
        List<Integer> square = msSt.square;
        int size = msSt.size;
        int[] sums = presentResults(st);
        int j, i = 0;
        int objective = (size*((size*size) + 1))/2;
        int target;
        MagicSquareAction[] Action;

        while(square.get(i) > 0){
            i++;
            if(i == (square.size() - 1)) break;
        }

        int horizontalResult, verticalResult;
        boolean diagonal_1 = false;
        int diagonalResult_1 = 0;
        boolean diagonal_2 = false;
        int diagonalResult_2 = 0;

        horizontalResult = sums[i/size];
        verticalResult = sums[(i%size) + size];
        for(j = 0; j < square.size(); j = j + size + 1) {
            if (j == i) {
                diagonal_1 = true;
                diagonalResult_1 = sums[size * 2];
                break;
            }
        }
        for(j = size - 1; j < square.size(); j = j + size - 1) {
            if (j == i) {
                diagonal_2 = true;
                diagonalResult_2 = sums[size * 2 + 1];
                break;
            }
        }

        List<Integer> actions = new ArrayList<>();

        for(j = 1; j <= square.size(); j++)
            if(!square.contains(j)) actions.add(j);

        List<Integer> tempActions = actions;

        Iterator<Integer> iterator = actions.iterator();

        // LAST NUMBER CAN BE ONLY ONE
        if(((i + 1) % size) == 0){
            target = objective - horizontalResult;
            if(actions.contains(target)){
                actions.removeAll(tempActions);
                actions.add(target);
            }
            else
                actions.removeAll(tempActions);
        }
        else if(i >= (square.size() - size)){
            target = objective - verticalResult;
            if(actions.contains(target)){
                actions.removeAll(tempActions);
                actions.add(target);
            }
            else
                actions.removeAll(tempActions);
        }

        else {
            while (iterator.hasNext()) {
                Integer acc = iterator.next();
                if ((horizontalResult + acc) > objective)
                    iterator.remove();
                else if(((i + 2) % size) == 0){
                    if((objective - (horizontalResult + acc)) > square.size())
                        iterator.remove();
                }

                else if ((verticalResult + acc) > objective)
                    iterator.remove();
                else if(i >= (square.size() - size * 2)){
                    if((objective - (verticalResult + acc)) > square.size())
                        iterator.remove();
                }

                else if (diagonal_1) {
                    if ((diagonalResult_1 + acc) > objective)
                    iterator.remove();
                }

                else if (diagonal_2) {
                    if ((diagonalResult_2 + acc) > objective)
                        iterator.remove();
                }
            }
        }

        Action = new MagicSquareAction[actions.size()];

        for(j = 0; j < actions.size(); j++)
            Action[j] = new MagicSquareAction(actions.get(j), i);

        return Action;
    }

    private int[] presentResults(State st){
        int length = ((MagicSquareState) st).square.size();
        int size = ((MagicSquareState) st).size;
        int arraySize = size * 2 + 2;
        int[] sums = new int[arraySize];
        int result = 0;
        int i, j, k = 0;

        // CHECK HORIZONTALS
        for(i = 0; i < length; i = i + size) {
            for (j = i; j < size + i; j++) {
                result += ((MagicSquareState) st).square.get(j);
            }
            sums[k] = result;
            k++;
            result = 0;
        }

        // CHECK VERTICALS
        for(i = 0; i < size; i++) {
            for (j = i; j < length; j = j + size) {
                result += ((MagicSquareState) st).square.get(j);
            }
            sums[k] = result;
            k++;
            result = 0;
        }

        // CHECK DIAGONALS
        for(i = 0; i < length; i = i + size + 1)
            result += ((MagicSquareState) st).square.get(i);
        sums[k] = result;
        k++;
        result = 0;
        for(i = size - 1; i < (length - 1); i = i + size - 1)
            result += ((MagicSquareState) st).square.get(i);
        sums[k] = result;

        return sums;
    }

}
