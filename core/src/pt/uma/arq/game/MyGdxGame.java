package pt.uma.arq.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.arq.entities.*;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private Player player;
    private Items items;
    private Music music;


    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1280, 800);
        batch = new SpriteBatch();

        player = new Player(100,100, batch);
        player.create();

        items = new Items(batch);
        items.create();

        items.scheduleItems();

        music = Gdx.audio.newMusic(Gdx.files.internal("Dakiti (8-Bit Bad Bunny & Jhay Cortez Emulation) (4).ogg"));


        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

        backgroundManagement = new BackgroundManagement(batch);

    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        items.handleCollisions(player);
        batch.begin();
        backgroundManagement.render();

        items.update();
        items.render();

        player.update();
        player.render();


        for (Bullet bullet : player.getBullets()) {
            bullet.render();
        }

        if (items.getItems().isEmpty() && player.getHealth() != 0){
            BitmapFront.drawText(600, 400, "  WIN!!\nScore: " + player.getScore(), batch);
        } else if (player.getHealth() == 0) {
                items.getTimer().cancel();
                items.getItems().clear();
                BitmapFront.drawText(600, 400, "YOU LOSE!!", batch);
        } else {
            BitmapFront.drawText(10, 750, "Health: " + player.getHealth(), batch);
            BitmapFront.drawText(10, 720, "Score: " + player.getScore(), batch);
        }

        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        music.stop();
        music.dispose();
    }

}