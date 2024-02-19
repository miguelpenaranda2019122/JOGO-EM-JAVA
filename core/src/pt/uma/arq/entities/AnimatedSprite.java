package pt.uma.arq.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.arq.game.Animator;


public abstract class AnimatedSprite extends Animator {
    protected Animator animator;
    protected int x, y;
    protected Rectangle boundingBox;
    private boolean collided;
    private boolean outOfBounds;


    // Constructor por omissão
    public AnimatedSprite() {
        x = 0;
        y = 0;
        boundingBox = new Rectangle();
        collided = false;
        animator = new Animator();
        outOfBounds = false;
    }

    // Constructor por paramentros
    public AnimatedSprite(int x, int y, SpriteBatch batch, String path, int columns, int rows){
        this.x = x;
        this.y = y;
        this.collided = false;
        this.animator = new Animator(batch, path, columns, rows);
    }

    // Selector de collided
    public boolean isCollided() {
        return collided;
    }

    public boolean isOutOfBounds() {
        return outOfBounds;
    }
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public void create() {
        this.animator.create();
        this.boundingBox = new Rectangle(x, y, animator.getWidth(), animator.getHeight());
    }
    public abstract void update();
    public void render() {
        this.animator.render(x, y);
    }

    // FUNCAO PARA AS COlISOES
    public void collidedWith(AnimatedSprite other) {
        if (this.boundingBox.overlaps(other.boundingBox)) {
            other.collided = true;
            this.collided = true;
        }
    }
}
