package pt.uma.arq.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class BitmapFront {
    private static BitmapFont font;
    private static void initialize() {
        if (font == null) {
            // Load the font file from the assets folder
            FileHandle fontFile = Gdx.files.internal("gamefont.fnt");

            // Create a new BitmapFont object using the font file
            font = new BitmapFont(fontFile);

            // Set the color of the font to white
            font.setColor(Color.YELLOW);
        }
    }
    public static void drawText(int x, int y, String text, SpriteBatch batch) {
        initialize();
        font.draw(batch, text, x, y);
    }
}
