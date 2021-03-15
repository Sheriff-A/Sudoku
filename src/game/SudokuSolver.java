package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class is responsible for solving a sudoku puzzle
 */
public abstract class SudokuSolver {

    public static SudokuPuzzle generateRandomPuzzle(){

        SudokuPuzzle puzzle = new SudokuPuzzle(
                9,9,3,3,
                new int[]{1,2,3,4,5,6,7,8,9}
        );
        SudokuPuzzle copy = new SudokuPuzzle(puzzle);

        Random rnd = new Random();
        List<Integer> notUsedValidValues =  new ArrayList<>(9);
        for (int i: copy.getValidValues()){
            notUsedValidValues.add(i);
        }
        for (int r = 0; r < copy.getNumRows(); r++){
            int randomValue = rnd.nextInt(notUsedValidValues.size());
            copy.setSlotValue(r, 0, notUsedValidValues.get(randomValue));
            notUsedValidValues.remove(randomValue);
        }

        solved(copy);

        int numOfValuesToKeep = (int)(0.22222 * (copy.getNumRows()* copy.getNumRows()));

        for(int i = 0; i < numOfValuesToKeep;){
            int randomRow = rnd.nextInt(puzzle.getNumRows());
            int randomCol = rnd.nextInt(puzzle.getNumColumns());

            if (puzzle.isSlotAvailable(randomRow, randomCol)){
                puzzle.setSlotValue(randomRow, randomCol, copy.getValue(randomRow, randomCol));
                i++;
            }
        }
        puzzle.initializeMutable();
        return puzzle;
    }

    public static boolean solved(SudokuPuzzle puzzle) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle.getValue(row, col) == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (puzzle.isValid(row, col, num)) {
                            puzzle.setSlotValue(row, col, num);
                            if (solved(puzzle)) {
                                return true;
                            } else {
                                puzzle.resetSlotValue(row, col);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
