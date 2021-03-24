package gui;
import game.Puzzle;
import game.Solver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class SudokuGame extends JFrame{

    private final String instructions;

    JPanel numbers_panel;
    static SPanel sudoku_panel;
    int width, height;
    int font_size = 26;
    int button_dim = 45;

    public SudokuGame(int w, int h){
        // Dimensions
        width = w;
        height = h;

        // Instructions
        instructions = getInstructions("src/instructions.txt");

        // Creating The Frame
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);


        // Game Window Panel
        JPanel game_panel = new JPanel();
        game_panel.setLayout(new FlowLayout());
        game_panel.setPreferredSize(new Dimension(width, height));
        // Numbers Panel
        numbers_panel = new JPanel();
        numbers_panel.setPreferredSize(new Dimension(100, 500));
        // Sudoku Panel
        sudoku_panel = new SPanel();

        game_panel.add(sudoku_panel);
        game_panel.add(numbers_panel);
//        add(game_panel);

        // Help Dialog Box
        JDialog help_dialog = new JDialog(this, "Help Dialog Box", true);
        help_dialog.setLayout(new FlowLayout());
        help_dialog.setSize(width, height);

        JTextArea instr_text_field = new JTextArea();
        instr_text_field.setPreferredSize(new Dimension(width-50, height - 100));
        instr_text_field.setText(instructions);
        instr_text_field.setEditable(false);

        JButton dialog_close = new JButton("Close");
        dialog_close.addActionListener(e -> help_dialog.setVisible(false));

        help_dialog.add(instr_text_field);
        help_dialog.add(dialog_close);
        help_dialog.setResizable(false);
//        help_dialog.setVisible(true); // Uncomment this to load instructions before the game

        // The Menu Bar
        JMenuBar menu_bar = new JMenuBar();
        JMenu file_menu = new JMenu("File");
        JMenu game_menu = new JMenu("Game");
        menu_bar.add(file_menu);
        menu_bar.add(game_menu);
        // File Menu Items
//        JMenuItem file_menu_save = new JMenuItem("Save");
        JMenuItem file_menu_close = new JMenuItem("Close");
        file_menu_close.addActionListener(e -> dispose());
        JMenuItem file_menu_help = new JMenuItem("Help");
        file_menu_help.addActionListener(e -> help_dialog.setVisible(true));
//        file_menu.add(file_menu_save);
        file_menu.add(file_menu_help);
        file_menu.add(file_menu_close);
        // Game Menu Items
        JMenuItem game_menu_new = new JMenuItem("New Game");
        game_menu_new.addActionListener(new GameMenuListener(sudoku_panel, 0));
        JMenuItem game_menu_reset = new JMenuItem("Reset");
        game_menu_reset.addActionListener(new GameMenuListener(sudoku_panel, 1));
        JMenuItem game_menu_solve = new JMenuItem("Solve");
        game_menu_solve.addActionListener(new GameMenuListener(sudoku_panel, 2));
        game_menu.add(game_menu_new);
        game_menu.add(game_menu_reset);
        game_menu.add(game_menu_solve);
        // Add Menu Bar
        getContentPane().add(BorderLayout.NORTH, menu_bar);
        getContentPane().add(BorderLayout.CENTER, game_panel);

        buildGameInterface(font_size);
    }

    private String getInstructions(String path) {
        String instr = "---\n";
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                instr += scanner.nextLine();
                instr += "\n";
            }
            instr = "\n" + instr + "---\n";
        } catch (Exception e){
            System.out.println(instr);
        } finally {
            System.out.println(instr);
        }
        return instr;
    }

    public void buildGameInterface(int fontSize){
        Puzzle puzzle = Solver.generateRandomPuzzle();
        sudoku_panel.newSudokuPuzzle(puzzle);
        sudoku_panel.setFontSize(fontSize);
        numbers_panel.removeAll();
        for (int i : puzzle.getValidValues()){
            JButton b = new JButton(String.valueOf(i));
            b.setPreferredSize(new Dimension(button_dim, button_dim));
            b.addActionListener(sudoku_panel.new SudokuPanelNumInsert(sudoku_panel));
            numbers_panel.add(b);
        }
        JButton clear_button = new JButton("-");
        clear_button.setPreferredSize(new Dimension(button_dim, button_dim));
        clear_button.addActionListener(sudoku_panel.new SudokuPanelNumInsert(sudoku_panel));
        numbers_panel.add(clear_button);
        sudoku_panel.repaint();
        numbers_panel.revalidate();
        numbers_panel.repaint();

    }

    private static class GameMenuListener implements ActionListener {

        SPanel sudoku_panel;
        int command;

        GameMenuListener(SPanel sudoku_panel, int command){
            this.sudoku_panel = sudoku_panel;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (this.command) {
                case 0 -> this.sudoku_panel.newPuzzle();
                case 1 -> this.sudoku_panel.clearSolution();
                case 2 -> this.sudoku_panel.solvePuzzle();
            }
        }
    }


    public static void main(String[] args){
        System.out.println("LAUNCHING SUDOKU...");
        SwingUtilities.invokeLater(() -> {
            SudokuGame game = new SudokuGame(800, 600);
            game.setVisible(true);
            game.setResizable(false);
        });
    }
}
