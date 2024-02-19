package pt.uma.arq.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends AnimatedSprite {
    private boolean visivel = false;
    private ItemType type;
    private int xDirection;

    public Item(){
        super();
    }

    public Item(int x, int y, SpriteBatch batch, String path, int columns, int rows, int xDirection, ItemType type){
        super(x, y, batch, path, columns, rows);
        this.xDirection = xDirection;
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    @Override
    public void update(){

        boundingBox.setPosition(x,y);

        x += 3 * xDirection;

        if (isVisivel()) {
            y -= 3;
        }

        if (x > 1240) {
            xDirection *= -1;
        }

        if (x < 0) {
            xDirection *= -1;
        }

        if (y < -50) {
            setOutOfBounds(true);
        }
    }
}
