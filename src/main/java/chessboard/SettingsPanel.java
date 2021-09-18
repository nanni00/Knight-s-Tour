package chessboard;

import kthandler.KnightTourHandler;
import setup.FilesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ActionListener {

    int r, c, sr, sc;

    private final FilesManager filesManager;

    BoardFrame parent;

    private final JTextField rows;
    private final JTextField cols;
    private final JTextField startRow;
    private final JTextField startCol;

    private final JButton start;

    public SettingsPanel(BoardFrame parent, Dimension d, int r, int c, int sr, int sc, FilesManager filesManager) {
        super(new GridLayout(2, 9));
        this.parent = parent;
        this.r = r;
        this.c = c;
        this.sr = r;
        this.sc = sc;
        this.filesManager = filesManager;

        setPreferredSize(d);

        JLabel rowsLabel = new JLabel("Rows: ");
        JLabel colsLabel = new JLabel("Cols: ");
        JLabel startRowLabel = new JLabel("Start Row: ");
        JLabel startColLabel = new JLabel("Start Col: ");

        rows = new JTextField(String.valueOf(r));
        cols = new JTextField(String.valueOf(c));
        startRow = new JTextField(String.valueOf(sr));
        startCol = new JTextField(String.valueOf(sc));

        start = new JButton("start");
        start.addActionListener(this);

        // first row
        add(rowsLabel);
        add(rows);
        add(colsLabel);
        add(cols);
        add(start);

        // second row
        add(startRowLabel);
        add(startRow);
        add(startColLabel);
        add(startCol);
    } // SettingsPanel()


    private void failFoundPathMessage(int r, int c, int sr, int sc) {
        String s = String.format("Impossible find a solution with input:\nRows=%d, Columns=%d, Start Row=%d, Start Column=%d", r, c, sr, sc);
        JOptionPane.showMessageDialog(getParent(),
                s,
                "Impossible find a solution",
                JOptionPane.ERROR_MESSAGE);
    } // failFoundPathMessage()


    private void callKnightTourSolver(int r, int c, int sr, int sc, int[] path) {
        if (new KnightTourHandler().solveTour(r, c, sr, sc, path)) {
            parent.updateBoard(r, c, path);
        } else {
            failFoundPathMessage(r, c, sr, sc);
        }
    } // callKnightTourSolver()

    private boolean callKnightTourSolverNoUpdateBoard(int r, int c, int sr, int sc, int[] path) {
        if (new KnightTourHandler().solveTour(r, c, sr, sc, path)) {
            return true;
        } else {
            failFoundPathMessage(r, c, sr, sc);
        }
        return false;
    } // callKnightTourSolverNoUpdateBoard()


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            Object[] options = {"yes", "no"};

            try {
                r = Integer.parseInt(rows.getText());
                c = Integer.parseInt(cols.getText());

                sr = Integer.parseInt(startRow.getText());
                sc = Integer.parseInt(startCol.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(getParent(), "Invalid input value/s", "Input Error", JOptionPane.ERROR_MESSAGE);
            }

            sr--;
            sc--;

            int[] path = new int[r * c];

            if (sr < 0 || sr >= r || sc < 0 || sc >= c) {
                JOptionPane.showMessageDialog(getParent(), "Invalid starting row/column", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (r % 2 != 0 && c % 2 != 0) {
                if (JOptionPane.showOptionDialog(
                        getParent(),
                        "Input rows and cols both odd, is not guaranteed a path. Continue?",
                        "Warning input values",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, null
                ) == 0) {
                    callKnightTourSolver(r, c, sr, sc, path);

                }
            } else if ((r > 16 && c > 16) || (r > 14 && c > 10) || (r > 10 && c > 14)) {
                if (JOptionPane.showOptionDialog(
                        getParent(),
                        "Impossible draw the board with these rows/columns values; do you want to get the output on a file? (Located in your Desktop folder)",
                        "Warning input values",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, options, null
                ) == 0) {
                    if (callKnightTourSolverNoUpdateBoard(r, c, sr, sc, path)) {
                        filesManager.writePathOnFile(r, c, path);
                    }

                }
            } else {
                callKnightTourSolver(r, c, sr, sc, path);

            }
        }
    } // actionPerformed()

}
