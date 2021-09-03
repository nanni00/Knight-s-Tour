import chessboard.BoardFrame;
import kthandler.KnightTourHandler;
import setup.SetUpFiles;

import java.awt.*;

public class Test {
    public static void main(String[] args) {

        new SetUpFiles();

        new KnightTourHandler();

        EventQueue.invokeLater(() -> new BoardFrame(8, 8, null));
    }
}
