package es.udc.intelligentsystems.MagicSquare;

import es.udc.intelligentsystems.Action;
import es.udc.intelligentsystems.SearchProblem;
import es.udc.intelligentsystems.State;

import java.util.ArrayList;
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
            MagicSquareState msSt = (MagicSquareState) st;
            List<Integer> square = msSt.square;
            int size = msSt.size;

            int[] sums = presentResults(st);
            int j;
            int objective = (size*((size*size) + 1))/2;

            int pos = this.position, num = this.number;

            int horizontalResult, verticalResult;
            boolean diagonal_1 = false;
            int diagonalResult_1 = 0;
            boolean diagonal_2 = false;
            int diagonalResult_2 = 0;

            int row = pos/size;
            int column = (pos%size) + size;

            horizontalResult = sums[row];
            verticalResult = sums[column];
            for(j = 0; j < square.size(); j = j + size + 1) {
                if (j == pos) {
                    diagonal_1 = true;
                    diagonalResult_1 = sums[size * 2];
                    break;
                }
            }
            for(j = size - 1; j < square.size() - 1; j = j + size - 1) {
                if (j == pos) {
                    diagonal_2 = true;
                    diagonalResult_2 = sums[size * 2 + 1];
                    break;
                }
            }

            int rowStart = 0 + ((row) * size);
            int rowCount = 0;
            for(j = rowStart; j < rowStart + size; j++)
                if(square.get(j) < 1) rowCount++;

            int columnStart = (pos%size);
            int columnCount = 0;
            for(j = columnStart; j <= (size * (size - 1)) + columnStart; j = j + size)
                if(square.get(j) < 1) columnCount++;

            if ((horizontalResult + num) > objective) return false;
            else if ((verticalResult + num) > objective) return false;
            else if (diagonal_1 && ((diagonalResult_1 + num) > objective) ) return false;
            else if (diagonal_2 && ((diagonalResult_2 + num) > objective) ) return false;

            // LAST NUMBER CAN BE ONLY ONE
            //if(((pos + 1) % size) == 0){
            if( (rowCount == 1)){
                return num == objective - horizontalResult;
            }
            //if(pos >= (square.size() - size)){
            if( (columnCount == 1)){
                return num == objective - verticalResult;
            }
            else {
                    //if(((pos + 2) % size) == 0) {
                    if(rowCount == 2) {
                        if ((objective - (horizontalResult + num)) > square.size()) return false;
                        if ((objective - (horizontalResult + num)) < 1) return false;
                    }

                    //if( (pos >= (square.size() - size * 2)) && (pos < (square.size() - size))) {
                    if(columnCount == 2) {
                        if ((objective - (verticalResult + num)) > square.size()) return false;
                        if ((objective - (verticalResult + num)) < 1) return false;
                    }
            }

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

        public List<Integer> getSquare() {
            return square;
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
        int j, i = 0;
        Action[] Action;
        MagicSquareAction tempMagicSquareAction;

        while(square.get(i) > 0){
            i++;
            if(i == (square.size() - 1)) break;
        }

        List<Integer> actions = new ArrayList<>();

        for(j = 1; j <= square.size(); j++)
            if(!square.contains(j)) actions.add(j);

        List<Action> tempAction = new ArrayList<>();

        for(j = 0; j < actions.size(); j++) {
            tempMagicSquareAction = new MagicSquareAction(actions.get(j), i);
            if(tempMagicSquareAction.isApplicable(st))
                tempAction.add(tempMagicSquareAction);
        }

        Action = new MagicSquareAction[tempAction.size()];

            for(j = 0; j < tempAction.size(); j++)
                Action[j] = tempAction.get(j);

        return Action;
    }

    private static int[] presentResults(State st){
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
