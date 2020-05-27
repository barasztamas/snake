package snake;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
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
        menuBar.add(new MenuItem("Restart", KeyEvent.VK_R, this::start ));
        menuBar.add(new MenuItem("Pause", KeyEvent.VK_P, this::pause ));
        menuBar.add(new ShowScoreMenu());
        menuBar.add(Box.createHorizontalGlue());
        //menuBar.add(new AboutMenu());
        menuBar.add(new MenuItem("About", KeyEvent.VK_R, this::about));
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

    private void start() {
        game.start();
        paused = false;
    }

    private void pause() {
        if(!game.isOver()) {
            paused = !paused;
        }
    }

    private void about() {
        JOptionPane.showMessageDialog(GUI.this, "use arrow keys to change direction, P or space to pause, R to restart");
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
                    case KeyEvent.VK_A:         about();                                break;
                }
                if (!paused && !game.isOver()) {
                    switch (kc) {
                        case KeyEvent.VK_LEFT:      game.changeDirection(Direction.LEFT);   break;
                        case KeyEvent.VK_RIGHT:     game.changeDirection(Direction.RIGHT);  break;
                        case KeyEvent.VK_UP:        game.changeDirection(Direction.UP);     break;
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
                game.move();
                repaint();
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

    private class AboutMenu extends JMenuItem implements ActionListener {
        public AboutMenu() {
            super("About");
            setMnemonic(KeyEvent.VK_A);
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(GUI.this, "use arrow keys to change direction, P or space to pause, R to restart");
        }
    }

}
