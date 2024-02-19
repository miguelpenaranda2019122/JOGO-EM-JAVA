package pt.uma.arq.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import pt.uma.arq.game.Animator;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends AnimatedSprite{

    private int health;
    private int score;
    private float velocityY; // Velocidade de Salto
    private boolean isJumping; // Verifica que esta no ar
    private float gravity;
    private ArrayList<Bullet> bullets;
    private SpriteBatch batch;
    private boolean isMoving;

    public Player() {
        super();
    }
    public Player(int x, int y, SpriteBatch batch){
        super( x, y, batch, "frog_idle.png", 11, 1);
        velocityY = 0;
        isJumping = false;
        isMoving = false;
        gravity = -0.5f;
        bullets = new ArrayList<>();
        this.batch = batch;
        health = 100;
        score = 0;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    // Aunmentar a potuação
    public void setScore(int score) {
        this.score += score;
    }

    // Diminui a vida
    public void setHealth(int health) {
        this.health -= health;

        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void jump() {
        if (!isJumping) {
            velocityY = 12;
            isJumping = true;
        }
    }

    public void shoot() {
            Bullet bullet = new Bullet(x + 20, y + 40, batch);
            bullet.create();
            bullets.add(bullet);
    }

    @Override
    public void update(){

        this.boundingBox.setPosition(x,y);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && x > 0){
            x -= 7;
            animator.setFlip(true);
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && x < 1220){
            x += 7;
            animator.setFlip(false);
            isMoving = true;
        } else isMoving = false;

        if (isMoving) {
            animator.setAnimation("frog_run.png", 12);
        } else{
            animator.setAnimation("frog_idle.png", 11);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            jump();
        }

        if (isJumping) {
            y += velocityY;
            velocityY += gravity;

            if (y <= 100) {
                y = 100;
                isJumping = false;
                velocityY = 0;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shoot();
        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            if (bullet.isCollided() || bullet.isOutOfBounds()){
                bulletIterator.remove();
            }
        }
    }
}
