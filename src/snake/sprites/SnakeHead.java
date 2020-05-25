package snake.sprites;

import snake.Direction;

import java.awt.*;

public class SnakeHead extends SnakeBody {
    private Direction direction;
    private Direction previousDirection;
    private int bodySize;
    private int bodyOffset;

    public SnakeHead(int x, int y, int size, Direction direction) {
        super(x, y, size, Color.green, null);
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
        if (grow || next != null) {
            next = new SnakeBody(coord.x+bodyOffset, coord.y+bodyOffset, bodySize, newColor(), next);
        }
        // to avoid errors from a keypress changing the direction during move, we introduce a local variable
        Direction d = direction;
        coord.translate(2*d.getX(), 2*d.getY());
        previousDirection = d;
        next.move(grow);
    }

    @Override
    protected void draw(Graphics2D g) {
        super.draw(g);
    }
}
