package snake.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Food extends Sprite {
    private static Random rand = new Random();
    private static List<Rectangle> clips = Arrays.asList(
            new Rectangle(0,0,145,145),
            new Rectangle(175,0,155,155),
            new Rectangle(365,5,140,140),
            new Rectangle(564,0,145,145),
            new Rectangle(430,386,165,165)
    );

    private Rectangle clip;

    public Food(Rectangle coordinates) {
        super(coordinates, "food.png");
        clip = clips.get(rand.nextInt(clips.size()));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, coord.x, coord.y, coord.x+ coord.width, coord.y+ coord.height, clip.x, clip.y, clip.x+clip.width, clip.y+clip.height, null);
    }
}
