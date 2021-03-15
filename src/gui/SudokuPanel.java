package gui;

import game.SudokuPuzzle;
import game.SudokuSolver;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

public class SudokuPanel extends JPanel {

    private SudokuPuzzle puzzle;
    private int currCol, currRow, usedWidth, usedHeight, fontSize;
    private boolean playing = true;

    public SudokuPanel(){
        setPreferredSize(new Dimension(600, 500));
        addMouseListener(new SudokuPanelMouseAdapter());
        puzzle = SudokuSolver.generateRandomPuzzle();
        currCol = -1;
        currRow = -1;
        usedHeight = 0;
        usedWidth = 0;
        fontSize = 26;
    }

    public SudokuPanel(SudokuPuzzle puzzle){
        setPreferredSize(new Dimension(600, 500));
        addMouseListener(new SudokuPanelMouseAdapter());
        this.puzzle = puzzle;
        currCol = -1;
        currRow = -1;
        usedHeight = 0;
        usedWidth = 0;
        fontSize = 26;
    }

    public void newSudokuPuzzle(SudokuPuzzle puzzle){
        this.puzzle = puzzle;
    }

    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255,255,255)); // White

        int slotWidth = this.getWidth()/puzzle.getNumColumns();
        int slotHeight = this.getHeight()/puzzle.getNumRows();

        usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
        usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();

        g2d.fillRect(0, 0,usedWidth,usedHeight);

        g2d.setColor(new Color(0, 0, 0));
        for(int x = 0;x <= usedWidth;x+=slotWidth) {
            if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x, 0, x, usedHeight);
            }
            else {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(x, 0, x, usedHeight);
            }
        }
        //this will draw the right most line
        //g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
        for(int y = 0;y <= usedHeight;y+=slotHeight) {
            if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, y, usedWidth, y);
            }
            else {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(0, y, usedWidth, y);
            }
        }
        //this will draw the bottom line
        //g2d.drawLine(0, usedHeight - 1, usedWidth, usedHeight - 1);

        Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
        g2d.setFont(f);
        FontRenderContext fContext = g2d.getFontRenderContext();
        for(int row=0;row < puzzle.getNumRows();row++) {
            for(int col=0;col < puzzle.getNumColumns();col++) {
                if(!puzzle.isSlotAvailable(row, col)) {
                    int textWidth = (int) f.getStringBounds(String.valueOf(puzzle.getValue(row, col)), fContext).getWidth();
                    int textHeight = (int) f.getStringBounds(String.valueOf(puzzle.getValue(row, col)), fContext).getHeight();
                    g2d.drawString(String.valueOf(puzzle.getValue(row, col)), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
                }
            }
        }
        if(currCol != -1 && currRow != -1) {
            g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
            g2d.fillRect(currCol * slotWidth,currRow * slotHeight,slotWidth,slotHeight);
        }
    }

    // Insert Number Into Puzzle
    public void insertNumber(int num){
        puzzle.setSlotValue(currRow, currCol, num);
        repaint();
    }

    // Game Menu New Puzzle
    public void newPuzzle(){
        newSudokuPuzzle(SudokuSolver.generateRandomPuzzle());
        playing = true;
        repaint();
    }

    // Game Menu Clear Solution
    public void clearSolution(){
        puzzle.clearAll();
        repaint();
    }

    public boolean isSolved(){
        return puzzle.isSolved();
    }

    // Game Menu Solve Puzzle
    public void solvePuzzle(){
        SudokuSolver.solved(this.puzzle);
        playing = false;
        repaint();
    }

    public class SudokuPanelNumInsert implements ActionListener {

        private final SudokuPanel sudokuPanel;

        public SudokuPanelNumInsert(SudokuPanel sp){
            sudokuPanel = sp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int num = button.getText().equals("-") ?
                    0 : Integer.parseInt(button.getText());
            insertNumber(num);
            if (playing && isSolved()){
                JOptionPane.showMessageDialog(sudokuPanel, "Congratulations! You Win", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class SudokuPanelMouseAdapter extends MouseInputAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getButton() == MouseEvent.BUTTON1){
                int slotWidth = usedWidth/puzzle.getNumColumns();
                int slotHeight = usedHeight/puzzle.getNumRows();
                currRow = e.getY()/ slotHeight;
                currCol = e.getX()/ slotWidth;
                e.getComponent().repaint();
            }
        }
    }

}
