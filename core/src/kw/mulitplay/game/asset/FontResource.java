package kw.mulitplay.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FontResource {
    public static BitmapFont commonfont;
    public static BitmapFont animalfont;
    public static BitmapFont time;
    public FontResource(){
        commonfont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
                new TextureRegion(new Texture(Gdx.files.internal("fonts/gamefont.png"))));
        animalfont = new BitmapFont(Gdx.files.internal("fonts/animal.fnt"),
                new TextureRegion(new Texture(Gdx.files.internal("fonts/animal.png"))));
        time = new BitmapFont(Gdx.files.internal("fonts/time.fnt"),
                new TextureRegion(new Texture(Gdx.files.internal("fonts/time.png"))));
    }
}
