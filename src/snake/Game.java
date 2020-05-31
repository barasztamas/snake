package snake;

import snake.sprites.Food;
import snake.sprites.SnakeHead;
import snake.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Game {
    private int gameSize;
    private int unitSize;
    private SnakeHead snake;
    private List<Sprite> stones;
    private Sprite food;
    private List<Sprite> borders;
    private boolean over = false;
    private int score;
    private int grow;
    private static Random rand = new Random();

    public Game(int gameSize, int unitSize) {
        this.gameSize = gameSize;
        this.unitSize = unitSize;

        setBorders();
        start();
    }

    public void start() {
        setSnake();
        setStones();
        setFood();
        score = 0;
        grow = 0;
        over = false;
    }

    public void move() {
        if (!over) {
            if (grow>0) {
                grow--;
                snake.move(true);
            } else {
                snake.move(false);
            }
            if ( snake.collides(stones) || snake.collides(snake) || snake.collides(borders) ) {
                over = true;
                return;
            }

            if (snake.collides(food)) {
                score++;
                grow += unitSize;
                setFood();
            }
        }
    }

    public void draw(Graphics g) {
        for (Sprite stone : stones) {stone.draw(g); }
        food.draw(g);
        snake.draw(g);
    }

    public boolean isOver() { return over; }
    public int getScore() { return score; }
    public int size() { return gameSize; }
    public void changeDirection(Direction d) { snake.changeDirection(d); }

    private void setSnake() {
        stones = new ArrayList<>();
        food = null;
        snake = new SnakeHead((gameSize-unitSize)/2, (gameSize-unitSize)/2, unitSize, Direction.randomDirection());
        for (int i = 0; i < unitSize; i++) {
            snake.move(true);
        }
    }

    private void setStones() {
        for (int i = 0; i < gameSize / (unitSize * 2) - 2; i++) {
            stones.add(newRandomSprite(r -> new Sprite(r,"stone.png")));
        }
    }

    private void setFood() {
        food = newRandomSprite(r -> new Food(r));
    }

    private void setBorders() {
        borders = Arrays.asList(
                new Sprite(new Rectangle(-1,    -1, 1, gameSize), (Image) null),
                new Sprite(new Rectangle(-1,    -1, gameSize, 1), (Image) null),
                new Sprite(new Rectangle(gameSize, -1, 1, gameSize), (Image) null),
                new Sprite(new Rectangle(-1, gameSize, gameSize, 1), (Image) null)
        );
    }

    private Sprite newRandomSprite(Function<Rectangle,Sprite> spriteFactory) {
        Sprite s = null;
        while (s == null) {
            s = spriteFactory.apply(new Rectangle(randomPlace(),randomPlace(), unitSize, unitSize));
            if (s.collides(snake) || s.collides(stones)) {
                s = null;
            }
        }
        return s;
    }

    private int randomPlace() { return rand.nextInt(gameSize-unitSize); }
}
