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
    public int[] getValidValues(){ return this.VALIDVALUES; }
    public int[][] getBoard(){ return this.board; }
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
        return (this.inRange(row,col) && this.board[row][col] == 0 && this.isSlotMutable(row, col));
    }

    public boolean inRange(int row,int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public boolean isSlotMutable(int row,int col) {
        return this.mutable[row][col];
    }


    // Setters
    public void setSlotValue(int row, int col, int num){
        if (inRange(row, col) && isSlotMutable(row, col)){
            if(isValid(row, col, num)){
                this.board[row][col] = num;
            } else if (num == 0) {
                clearSlot(row, col);
            }
        }
    }

    public void resetSlotValue(int row,int col) {
        this.board[row][col] = 0;
    }

    public void clearSlot(int row, int col){
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

    public boolean isSolved(){
        for (int[] i: board){
            for (int j: i){
                if (j == 0) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Game Board:\n");
        for(int row=0;row < this.ROWS;row++) {
            for(int col=0;col < this.COLS;col++) {
                str.append(this.board[row][col]).append("\t");
            }
            str.append("\n");
        }
        return str+"\n";
    }

    // TO BE REWORKED
    private void initializeBoard(){
        board = new int[][]{
                {0,0,0,0,0,0,0,0,0}, // Row 1
                {0,0,0,0,0,0,0,0,0}, // Row 2
                {0,0,0,0,0,0,0,0,0}, // Row 3
                {0,0,0,0,0,0,0,0,0}, // Row 4
                {0,0,0,0,0,0,0,0,0}, // Row 5
                {0,0,0,0,0,0,0,0,0}, // Row 6
                {0,0,0,0,0,0,0,0,0}, // Row 7
                {0,0,0,0,0,0,0,0,0}, // Row 8
                {0,0,0,0,0,0,0,0,0}  // Row 9

        };
    }

    public void initializeMutable(){
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                mutable[r][c] = board[r][c] == 0;
            }
        }
    }
}
