package chessboard;
import setup.FilesManager;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public class BoardFrame extends JFrame {

    int w = 600, h = 600;
    int settingH = 50;

    int rows;
    int cols;
    private ChessLabel[] labels;
    int[] path;

    Container contentPane;
    SettingsPanel settings;
    JPanel board;

    public BoardFrame(int rows, int cols, int[] path, FilesManager fileManager) {
        super();
        this.rows = rows;
        this.cols = cols;
        labels = new ChessLabel[rows * cols];
        this.path = Objects.requireNonNullElseGet(path, () -> new int[rows * cols]);

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        settings = new SettingsPanel(this, new Dimension(w, settingH), rows, cols, 1, 1, fileManager);

        display();

    } // BoardFrame()


    public void updateBoard(int m, int n, int[] path) {
        if (m == rows && n == cols) {
            for (int i = 0; i < m * n; ++i) {
                labels[i].setText(String.valueOf(path[i]));
            }
        } else {
            this.path = path;
            this.rows = m;
            this.cols = n;

            display();
        }
    } // updateBoard()


    public void display() {
        setTitle("Knight's Tour");

        labels = new ChessLabel[rows * cols];

        if (contentPane.isAncestorOf(board)) {
            contentPane.remove(board);
        }

        board = new JPanel(new GridLayout(rows, cols));
        board.setPreferredSize(new Dimension(w, h));

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                labels[r * cols + c] = new ChessLabel(Integer.toString(path[r * cols + c]));
                if (r % 2 == 0) {
                    if (c % 2 == 0) {
                        labels[r * cols + c].set(0);
                    } else {
                        labels[r * cols + c].set(1);
                    }
                } else {
                    if (c % 2 == 0) {
                        labels[r * cols + c].set(1);
                    } else {
                        labels[r * cols + c].set(0);
                    }
                }

                board.add(labels[r * cols + c]);
            }
        }

        contentPane.add(board, BorderLayout.CENTER);
        contentPane.add(settings, BorderLayout.SOUTH);

        setSize(w, h + settingH);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    } // display()

} // class BoardFrame