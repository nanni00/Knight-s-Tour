import chessboard.BoardFrame;

import java.awt.*;

public class Test {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new BoardFrame(8, 8, null));
    }
}
