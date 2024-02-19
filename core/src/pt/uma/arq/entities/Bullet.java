package pt.uma.arq.entities;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends AnimatedSprite{
    public Bullet() {
        super();
    }
    public Bullet(int x, int y, SpriteBatch batch) {
        super(x,y,batch,"laser-bolts.png", 2, 1);
    }

    @Override
    public void update() {
        boundingBox.setPosition(x,y);
        y += 5;

        if (y > 800) {
           setOutOfBounds(true);
        }
    }
}
