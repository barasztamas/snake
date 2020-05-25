package snake.sprites;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Sprite {
    static final String imageFolder = "data/images/";
    protected Rectangle coord;
    protected Image image;

    public Sprite(Rectangle coord, Image image) {
        this.coord = coord;
        this.image = image;
    }

    public Sprite(Rectangle coord, String imageFile) {
        this(coord,new ImageIcon(imageFolder +imageFile).getImage());
    }

    public void draw(Graphics g) {
        g.drawImage(image, coord.x, coord.y, coord.width, coord.height, null);
    }

    public boolean collides(Sprite other) {
        if (other==null) {return false;}
        return coord.intersects(other.coord);
    }
    public boolean collides(List<Sprite> others) {
        for (Sprite other:others) {
            if (this.collides(other)) { return true; }
        }
        return false;
    }
    public boolean collides(SnakeHead other) {
        return other.collides(this);
    }
}
