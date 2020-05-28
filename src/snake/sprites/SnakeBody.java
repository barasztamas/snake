package snake.sprites;

import java.awt.*;
import java.util.Random;

public class SnakeBody extends Sprite {
    protected Color color;
    protected SnakeBody next;
    protected int size;
    protected Rectangle drawCoord;
    private static Random rand = new Random();

    public SnakeBody(int x, int y, int size, Color color) {
        super(new Rectangle(x+size/2, y+size/2, 1, 1), (Image) null);
        this.drawCoord = new Rectangle(x, y, size, size);
        this.size = size;
        this.color = color;
        this.next = null;
    }

    protected Color newColor() {
        switch (rand.nextInt(7)) {
            case 0:
            case 1:
                return color.brighter();
            case 2:
                return color.darker();
            default:
                return color;
        }
    }

    protected void move(boolean grow) {
        if (grow && next == null) {
            next = new SnakeBody(drawCoord.x, drawCoord.y, size, newColor());
        } else if (next != null) {
            next.move(grow);
            next.coord.x = this.coord.x;
            next.coord.y = this.coord.y;
            next.drawCoord.x = this.drawCoord.x;
            next.drawCoord.y = this.drawCoord.y;
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
        g.fillOval(drawCoord.x, drawCoord.y, size, size);
    }
}
