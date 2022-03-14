package es.udc.intelligentsystems;

import es.udc.intelligentsystems.example.VacuumCleanerProblem;

import java.util.List;

public class MagicSquareProblem extends SearchProblem {

    public MagicSquareProblem(State initialState) {
        super(initialState);
    }

    public static class MagicSquareAction extends Action {

        private final int number;
        //private List<Integer> restriction;

        public MagicSquareAction(int number) {
            this.number = number;
            //this.restriction = restriction;
        }

        @Override
        public String toString() {
            return null;
        }

        @Override
        public boolean isApplicable(State st) {
            return true;
        }

        @Override
        public State applyTo(State st) {
            MagicSquareState msSt = (MagicSquareState) st;
            List<Integer> newSquare = msSt.square;
            int size = msSt.size;
            int i = 0;

            if(size*size < number) throw new IndexOutOfBoundsException();

            while(newSquare.get(i) > 0){
                i++;
                if(i == (newSquare.size() - 1)) break;
            }

            newSquare.set(i, number);

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
            for(int i = 0; i < square.size(); i = i + square.size()) {
                for (int j = i; j < this.size + i; j++) {
                    string.append("[").append(square.get(j)).append("] ");
                }
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

        return result != objective;
    }

    @Override
    public Action[] actions(State st) {
        MagicSquareState msSt = (MagicSquareState) st;
        List<Integer> square = msSt.square;
        int size = msSt.size;
        int arraySize = size * 2 + 2;
        int[] sums = presentResults(st);
        int j, i = 0;

        while(square.get(i) > 0){
            i++;
            if(i == (square.size() - 1)) break;
        }




        return new Action[0];
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
