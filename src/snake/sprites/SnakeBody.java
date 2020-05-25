package snake.sprites;

import java.awt.*;
import java.util.Random;

public class SnakeBody extends Sprite {
    protected Color color;
    protected SnakeBody next;
    protected int size;
    private static Random rand = new Random();

    public SnakeBody(int x, int y, int size, Color color, SnakeBody next) {
        super(new Rectangle(x, y, size, size), (Image) null);
        this.size = size;
        this.color = color;
        this.next = next;
    }

    protected Color newColor() {
        switch (rand.nextInt(5)) {
            case 0:
                return color.brighter();
            case 1:
                return color.darker();
            default:
                return color;
        }
    }

    protected void move(boolean grow) {

        if (grow && next == null) {
            color = newColor();
        }

        if (next != null) {
            this.color = next.color;
            if (!grow && next.next==null) {
                next = null;
            } else {
                next.move(grow);
            }
        }
    }

    @Override
    public boolean collides(Sprite other) {
        return (super.collides(other) || (next!=null && next.collides(other)));
    }

    protected boolean collides(Sprite other, int after) {
        if (after <= 0)     { return collides(other); }
        if (next == null)   { return false; }
        /*otherwise*/       { return next.collides(other, after-1); }
    }

    @Override
    public void draw(Graphics g) {
        draw((Graphics2D) g);
    }

    protected void draw(Graphics2D g) {
        if (next != null) { next.draw(g); }
        g.setColor(color);
        g.fillOval(coord.x, coord.y, size, size);
    }
}