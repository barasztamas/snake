package snake;

import snake.sprites.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private Sprite background;
    private Game game;

    public GamePanel(Game game) {
        super();
        this.game = game;
        setPreferredSize( new Dimension(game.size(), game.size()) );
        background = new Sprite(new Rectangle(game.size(), game.size()), "desert.jpg"); // Photo by Vincent Burkhead on Unsplash
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g);
        game.draw(g);
    }
}
