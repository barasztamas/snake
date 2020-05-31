package snake.sprites;

import snake.Direction;

import java.awt.*;

public class SnakeHead extends SnakeBody {
    private Direction direction;
    private Direction previousDirection;
    private int bodySize;
    private int bodyOffset;

    public SnakeHead(int x, int y, int size, Direction direction) {
        super(x, y, size, Color.green);
        this.coord = this.drawCoord;
        this.previousDirection = this.direction = direction;
        this.bodySize = size * 3 / 4;
        this.bodyOffset = (size-bodySize) / 2;
    }

    public void changeDirection(Direction d) {
        if (!previousDirection.isOpposite(d)) {
            direction = d;
        }
    }

    @Override
    public boolean collides(Sprite other) {
        if (other==this) {
            return super.collides(other, size + bodySize);
        } else {
            return super.collides(other);
        }
    }

    @Override
    public void move(boolean grow) {
        if (grow && next == null) {
            next = new SnakeBody(coord.x+bodyOffset, coord.y+bodyOffset, bodySize, newColor());
        } else if (next != null) {
            next.move(grow);
            next.coord.translate(previousDirection.getX(), previousDirection.getY());
            next.drawCoord.translate(previousDirection.getX(), previousDirection.getY());
        }
        // to avoid errors from a keypress changing the direction during move, we introduce a local variable
        Direction d = direction;
        coord.translate(d.getX(), d.getY());
        previousDirection = d;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        int r = size/2;
        int mx = coord.x + r;
        int my = coord.y + r;

        g.setColor(Color.red);
        g.drawLine(mx + (int)(direction.getX()*r*0.8),  my + (int)(direction.getY()*r*0.8), mx + (int)(direction.getX()*r*1.1),  my + (int)(direction.getY()*r*1.1));
        g.setColor(Color.black);
        g.fillOval(mx + (int)(direction.getX()*r*0.6 + direction.getY()*r*0.3),  my + (int)(direction.getY()*r*0.6 + direction.getX()*r*0.3), 2,  2);
        g.fillOval(mx + (int)(direction.getX()*r*0.6 - direction.getY()*r*0.3),  my + (int)(direction.getY()*r*0.6 - direction.getX()*r*0.3), 2,  2);
    }
}
