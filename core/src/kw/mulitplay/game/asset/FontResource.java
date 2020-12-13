package kw.mulitplay.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FontResource {
    public static BitmapFont font ;
    public FontResource(){
        font = new BitmapFont(Gdx.files.internal("fonts/army_stencil.fnt"),
                new TextureRegion(new Texture(Gdx.files.internal("fonts/army_stencil.png"))));
    }

}
