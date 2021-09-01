package chessboard;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ChessLabel extends JLabel {

    Font font     = new Font("Ariel", Font.PLAIN, 20);
    Color bgLight = new Color(222, 184, 135);
    Color bgDark  = new Color(139, 69, 19);

    ChessLabel(String s) {
        super(s);
    } // ChessLabel()


    void set(int idx) {
        setFont(font);
        setOpaque(true);
        setBackground(idx == 0 ? bgDark : bgLight);

        setHorizontalAlignment( SwingConstants.CENTER );
    } // set()

} // class ChessLabel