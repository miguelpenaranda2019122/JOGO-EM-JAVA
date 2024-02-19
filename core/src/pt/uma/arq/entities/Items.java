package pt.uma.arq.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Items{
    private SpriteBatch batch;
    private ArrayList<Item> items;
    private Timer timer;
    private CollisionAnimation collected;
    private CollisionAnimation explosion;
    private Music sound;

    public Items(ArrayList<Item> items){
        this.items = items;
    }

    public Items(SpriteBatch batch){
        this.batch = batch;
        items = new ArrayList<Item>();
        timer = new Timer();
        collected = new CollisionAnimation(0,0,batch, "collected.png", 6, "collected.mp3");
        explosion = new CollisionAnimation(0,0,batch, "explosion.png", 5, "explosion.mp3");
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Timer getTimer() {
        return timer;
    }

    public void create() {

        Random number = new Random();
        String[] arrayItems = {"apple.png", "melon.png", "pineapple.png", "razor_disc.png", "spiked_stone.png", "square_stone.png"};
        int[] colItems = {17, 17, 17, 8, 4, 4};

        for (int i = 0; i < 50 ; i++) {
            ItemType type;
            int index = number.nextInt(6);
            type = (index < 3) ? ItemType.FRUIT : ItemType.TRAP;
            int direction = number.nextInt(2) * 2 - 1;
            Item  item = new Item(number.nextInt(1241), 800, batch, arrayItems[index], colItems[index], 1,  direction, type );
            item.create();
            items.add(item);
        }
    }



    public void update(){
        for (Item item: items) {
            item.update();
        }

        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()){
            Item item = itemIterator.next();
            if (item.isCollided()) {
                if (item.getType() == ItemType.FRUIT) {
                    collected.update(item.x, item.y);
                    collected.scheduleDeletion();
                    sound = Gdx.audio.newMusic(Gdx.files.internal(collected.getSound()));
                    sound.setVolume(0.3f);
                    sound.play();
                }
                if (item.getType() == ItemType.TRAP) {
                    explosion.update(item.x, item.y);
                    explosion.scheduleDeletion();
                    sound = Gdx.audio.newMusic(Gdx.files.internal(explosion.getSound()));
                    sound.setVolume(0.5f);
                    sound.play();
                }
                itemIterator.remove();
            } else if (item.isOutOfBounds()) {
                itemIterator.remove();
            }
        }
    }

    public void scheduleItems() {

        timer.scheduleAtFixedRate(new TimerTask() {
            int index = 0;
            @Override
            public void run() {
                if (index < items.size()) {
                    Item item = items.get(index);
                    if (!item.isVisivel()) {
                        item.setVisivel(true);
                    }
                    index++;
                } else {
                    if (!items.isEmpty()) {
                        index = 0;
                    } else cancel();
                }
            }
        },0, 2000);
    }

    public void render(){
        for (Item item: items) {
            if (item.isVisivel()){
                item.render();
            }
        }
        if (collected.isActive()) {
            collected.render();
        }
        if (explosion.isActive()) {
            explosion.render();
        }

    }

    public void handleCollisions(Player player){

        for (Item item: items) {
            if (item.isVisivel() && !item.isCollided()) {
                player.collidedWith(item);

                if (item.isCollided() && item.getType() == ItemType.FRUIT) {
                    player.setScore(30);
                }

                if (item.isCollided() && item.getType() == ItemType.TRAP) {
                    player.setHealth(35);
                }
            }
        }

        for (Bullet bullet: player.getBullets()) {
            for (Item item: items) {
                if (item.getType() == ItemType.TRAP) {
                    bullet.collidedWith(item);
                    if (item.isCollided()){
                        player.setScore(10);
                    }
                }
            }
        }
    }
}

