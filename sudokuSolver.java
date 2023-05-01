import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class sudokuSolver extends JFrame implements ActionListener {

    private JTextField[][] cells;

    public sudokuSolver() {
        super("Sudoku Solver");

        // Create the grid of text fields
        cells = new JTextField[9][9];
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField(1);
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                gridPanel.add(cells[row][col]);
            }
        }

        // Create the solve button
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(this);

        // Add the components to the frame
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gridPanel, BorderLayout.CENTER);
        contentPane.add(solveButton, BorderLayout.SOUTH);

        // Configure the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Solve")) {
            // Get the input from the text fields
            int[][] grid = new int[9][9];
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String text = cells[row][col].getText();
                    if (text.isEmpty()) {
                        grid[row][col] = 0;
                    } else {
                        grid[row][col] = Integer.parseInt(text);
                    }
                }
            }

            // Solve the puzzle
            if (solveSudoku(grid)) {
                // Display the solution in the text fields
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        cells[row][col].setText(String.valueOf(grid[row][col]));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No solution found.");
            }
        }
    }

    public static boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solveSudoku(grid)) {
                                return true;
                            } else {
                                grid[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        int boxRow = row / 3 * 3;
        int boxCol = col / 3 * 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (grid[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new sudokuSolver();
    }

}
