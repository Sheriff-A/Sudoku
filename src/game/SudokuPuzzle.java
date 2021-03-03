package game;

/**
 * This class holds any and all information about a sudoku puzzle
 */

public class SudokuPuzzle {

    protected int[][] board;
    protected boolean[][] mutable;
    private final int ROWS;
    private final int COLS;
    private final int BOXWIDTH;
    private final int BOXHEIGHT;
    private final int[] VALIDVALUES;

    public SudokuPuzzle(int rows, int cols, int box_width, int box_height, int[] valid_inputs){
        this.ROWS = rows;
        this.COLS = cols;
        this.BOXHEIGHT = box_height;
        this.BOXWIDTH = box_width;
        this.VALIDVALUES = valid_inputs;
        this.board = new int[ROWS][COLS];
        this.mutable = new boolean[ROWS][COLS];
        initializeBoard();
        initializeMutable();
    }

    public SudokuPuzzle(SudokuPuzzle puzzle){
        this.ROWS = puzzle.ROWS;
        this.COLS = puzzle.COLS;
        this.BOXHEIGHT = puzzle.BOXHEIGHT;
        this.BOXWIDTH = puzzle.BOXWIDTH;
        this.VALIDVALUES = puzzle.VALIDVALUES;
        this.board = new int[ROWS][COLS];
        this.mutable = new boolean[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = puzzle.board[r][c];
                mutable[r][c] = puzzle.mutable[r][c];
            }

        }
    }

    // Getters
    public int getValue(int row,int col) {
        return this.board[row][col];
    }

    public int getNumRows(){
        return this.ROWS;
    }
    public int getNumColumns(){
        return this.COLS;
    }
    public int getBoxWidth(){
        return this.BOXWIDTH;
    }
    public int getBoxHeight(){
        return this.BOXHEIGHT;
    }
    public int[] getValidValues(){
        return this.VALIDVALUES;
    }

    public int[][] getBoard(){
        return this.board;
    }

    public boolean[][] getMutable(){
        return this.mutable;
    }

    // Solver Values
    public boolean isValid(int r, int c, int num){
        return !(inRow(r, num) || inCol(c, num) || inSquare(r, c, num));
    }

    private boolean inRow(int r, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[r][i] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean inCol(int c, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[i][c] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean inSquare(int r, int c, int num) {
        int row = r - (r % 3);
        int col = c - (c % 3);
        for (int i = row; i < (row + 3); i++) {
            for (int j = col; j < (col + 3); j++) {
                if (board[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSlotAvailable(int row,int col) {
        return (this.board[row][col] == 0 && this.isSlotMutable(row, col));
    }

    public boolean isSlotMutable(int row,int col) {
        return this.mutable[row][col];
    }


    // Setters
    public void setSlotValue(int row, int col, int num){
        this.board[row][col] = num;
    }


    public void resetSlotValue(int row,int col) {
        this.board[row][col] = 0;
    }

    public void clearAll(){
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if(isSlotMutable(r, c)){
                    resetSlotValue(r, c);
                }
            }
        }
    }

    @Override
    public String toString() {
        String str = "Game Board:\n";
        for(int row=0;row < this.ROWS;row++) {
            for(int col=0;col < this.COLS;col++) {
                str += this.board[row][col] + "\t";
            }
            str += "\n";
        }
        return str+"\n";
    }

    // TO BE REWORKED
    private void initializeBoard(){
        /*

          Example Board:

          5 3 0 0 7 0 0 0 0
          6 0 0 1 9 5 0 0 0
          0 9 8 0 0 0 0 6 0
          8 0 0 0 6 0 0 0 3
          4 0 0 8 0 3 0 0 1
          7 0 0 0 2 0 0 0 6
          0 6 0 0 0 0 2 8 0
          0 0 0 4 1 9 0 0 5
          0 0 0 0 8 0 0 7 9

         */

        board = new int[][]{
                {5, 3, 0, 0, 7, 0, 0, 0, 0}, // Row 1
                {6, 0, 0, 1, 9, 5, 0, 0, 0}, // Row 2
                {0, 9, 8, 0, 0, 0, 0, 6, 0}, // Row 3
                {8, 0, 0, 0, 6, 0, 0, 0, 3}, // Row 4
                {4, 0, 0, 8, 0, 3, 0, 0, 1}, // Row 5
                {7, 0, 0, 0, 2, 0, 0, 0, 6}, // Row 6
                {0, 6, 0, 0, 0, 0, 2, 8, 0}, // Row 7
                {0, 0, 0, 4, 1, 9, 0, 0, 5}, // Row 8
                {0, 0, 0, 0, 8, 0, 0, 7, 9}  // Row 9
        };
    }

    private void initializeMutable(){
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                mutable[r][c] = board[r][c] == 0;
            }
        }
    }
}
