package snake;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI extends JFrame {
    private Game game;
    private boolean paused = false;
    private GamePanel gamePanel;
    private Timer newFrameTimer;
    private final Dimension gameSize = new Dimension(660, 660);
    private final int FPS = 120;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() throws HeadlessException {
        super("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game(660, 30);
        gamePanel = new GamePanel(game);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new RestartMenuItem());
        menuBar.add(new PauseMenuItem());
        menuBar.add(new ScoreMenuItem());
        setJMenuBar(menuBar);

        getContentPane().add(gamePanel);


        setResizable(false);
        pack();
        setVisible(true);

        addKeyListener(new MyKeyAdapter());

        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }

    public Game getGame() { return game;  }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int kc = e.getKeyCode();
            if(paused) {
                if (kc==KeyEvent.VK_R) {game.start();}
                paused = false;
            } else {
                switch (kc) {
                    case KeyEvent.VK_LEFT:      game.changeDirection(Direction.LEFT);   break;
                    case KeyEvent.VK_RIGHT:     game.changeDirection(Direction.RIGHT);  break;
                    case KeyEvent.VK_UP:        game.changeDirection(Direction.UP);     break;
                    case KeyEvent.VK_DOWN:      game.changeDirection(Direction.DOWN);   break;
                    case KeyEvent.VK_P:
                    case KeyEvent.VK_SPACE:     paused = true;                          break;
                    case KeyEvent.VK_R:         game.start();                           break;
                }
            }

            repaint();
        }
    }

    private class NewFrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!paused) {
                game.move();
                repaint();
            }
        }
    }

    private class RestartMenuItem extends JMenu implements ActionListener {
        public RestartMenuItem() {
            super("Restart");
            setMnemonic(KeyEvent.VK_R);
            setPreferredSize(new Dimension(100, getPreferredSize().height));
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.start();
        }
    }

    private class PauseMenuItem extends JMenu implements ActionListener {
        public PauseMenuItem() {
            super("Pause");
            setMnemonic(KeyEvent.VK_P);
            setPreferredSize(new Dimension(100, getPreferredSize().height));
            this.addActionListener(this);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            paused = !paused;
        }
    }

    private class ScoreMenuItem extends JMenuItem {
        public ScoreMenuItem() {
            super();
        }

        @Override
        public void paint(Graphics g) {
            setText(String.valueOf(game.getScore()));
            super.paint(g);
        }
    }


}
