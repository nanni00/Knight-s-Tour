package chessboard;
import java.awt.*;
import java.util.Objects;
import javax.swing.*;


public class BoardFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    int w = 600, h = 600;
    int settingH = 50;

    int rows;
    int cols;
    private ChessLabel[] labels;
    int[] path;

    Container contentPane;
    SettingsPanel settings;
    JPanel board;

    public BoardFrame(int rows, int cols, int[] path) {
        super();
        this.rows = rows;
        this.cols = cols;
        labels = new ChessLabel[rows * cols];
        this.path = Objects.requireNonNullElseGet(path, () -> new int[rows * cols]);

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        settings = new SettingsPanel(this, new Dimension(w, settingH), rows, cols, 1, 1);

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
                labels[r * cols + c].set(r * cols + c, r);
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