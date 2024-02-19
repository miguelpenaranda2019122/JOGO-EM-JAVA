package pt.uma.arq.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import pt.uma.arq.game.Animator;


public class CollisionAnimation{
    private int x;
    private int y;
    private boolean active;
    private Animator animator;
    private String sound;

    public CollisionAnimation() {
        x = 0;
        y = 0;
        active = false;
        animator = new Animator();
    }

    public CollisionAnimation( int x, int y, SpriteBatch batch, String path, int columns, String sound){
        this.x = x;
        this.y = y;
        animator = new Animator(batch, path, columns, 1);
        animator.create();
        this.active = false;
        this.sound = sound;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSound() {
        return sound;
    }

    public void scheduleDeletion() {
        active = true;
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setActive(false);
            }
        }, 1, 0 ,1);
    }

    public void update(int x , int y){
        this.x = x;
        this.y = y;
    }
    public void render(){ this.animator.render(x,y);}

}
