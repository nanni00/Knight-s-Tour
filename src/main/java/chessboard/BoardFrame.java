package chessboard;
import java.awt.*;
import javax.swing.JFrame;


public class BoardFrame extends JFrame {

    //Initialise arrays to hold panels and images of the board

    int rows;
    int cols;
    private final ChessLabel[] labels;
    int[] path;

    public BoardFrame(int rows, int cols, int[] path) {
        this.rows = rows;
        this.cols = cols;
        labels = new ChessLabel[rows * cols];
        this.path = path;
    } // BoardFrame()

    public void display()
    {
        setTitle("Chess board with unicode images");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container contentPane = getContentPane();
        GridLayout gridLayout = new GridLayout(rows, cols);

        contentPane.setLayout(gridLayout);

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                labels[r * cols + c] = new ChessLabel(Integer.toString(path[r * cols + c]));
                //labels[r * cols + c] = new ChessLabel(Integer.toString(r * cols + c));
                labels[r * cols + c].set(r * cols + c, r);
                contentPane.add(labels[r * cols + c]);
            }
        }

        /*
        int row = -1;
        for (int i = 0; i < labels.length; i++)
        {
            if(i % 8 == 0) row ++; // increment row number
            labels[i].set(i, row);
            contentPane.add(labels[i]);
        } // i
        */

        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    } // display()

} // class BoardFrame