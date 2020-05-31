package snake;

import snake.persistence.Database;

import javax.swing.*;
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
    private String name = null;
    private Database database = new Database();
    private final Dimension gameSize = new Dimension(660, 660);
    private final int FPS = 120;
    private final int speed = 2;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() throws HeadlessException {
        super("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game(660, 30);
        gamePanel = new GamePanel(game);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new MenuItem("Restart", KeyEvent.VK_R, this::start ));
        menuBar.add(new MenuItem("Pause", KeyEvent.VK_P, this::pause ));
        menuBar.add(new MenuItem("HighScores", KeyEvent.VK_H, this::showHighScores ));
        menuBar.add(new ShowScoreMenu());
        menuBar.add(Box.createHorizontalGlue());
        //menuBar.add(new AboutMenu());
        menuBar.add(new MenuItem("About", KeyEvent.VK_B, this::about));
        setJMenuBar(menuBar);

        getContentPane().add(gamePanel);


        setResizable(false);
        pack();
        setVisible(true);

        addKeyListener(new MyKeyAdapter());

        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }

    private void start() {
        game.start();
        name = null;
        paused = false;
    }

    private void pause() {
        if(!game.isOver()) {
            paused = !paused;
        }
    }

    private void showHighScores() {
        new HighScoreWindow(database.getHighScores(), this);
    }


    private void about() {
        JOptionPane.showMessageDialog(GUI.this, "Arrows keys or WASD - change direction\nP or space - pause\nR - restart\nH - highscores\nB - show this dialog");
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int kc = e.getKeyCode();
                switch (kc) {
                    case KeyEvent.VK_P:
                    case KeyEvent.VK_SPACE:     pause();                                break;
                    case KeyEvent.VK_R:         start();                                break;
                    case KeyEvent.VK_B:         about();                                break;
                    case KeyEvent.VK_H:         showHighScores();                       break;
                }
                if (!paused && !game.isOver()) {
                    switch (kc) {
                        case KeyEvent.VK_A:
                        case KeyEvent.VK_LEFT:      game.changeDirection(Direction.LEFT);   break;
                        case KeyEvent.VK_D:
                        case KeyEvent.VK_RIGHT:     game.changeDirection(Direction.RIGHT);  break;
                        case KeyEvent.VK_W:
                        case KeyEvent.VK_UP:        game.changeDirection(Direction.UP);     break;
                        case KeyEvent.VK_S:
                        case KeyEvent.VK_DOWN:      game.changeDirection(Direction.DOWN);   break;
                    }
                }

            repaint();
        }
    }

    private class NewFrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!paused && !game.isOver()) {
                for (int i = 0; i < speed; i++) {
                    game.move();
                    repaint();
                }
            }
            if (name==null && game.isOver() && game.getScore() > database.minScore()) {
                name = JOptionPane.showInputDialog("What's your name?");
                database.storeHighScore(name, game.getScore());
                showHighScores();
            }
        }
    }

    private class ShowScoreMenu extends JMenuItem {
        public ShowScoreMenu() {
            super();
        }

        @Override
        public void paint(Graphics g) {
            setText(String.valueOf(game.getScore()));
            super.paint(g);
        }
    }

}
