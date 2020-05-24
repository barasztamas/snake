package snake;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public static void main(String[] args) {
        new GUI();
    }

    public GUI() throws HeadlessException {
        super("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
