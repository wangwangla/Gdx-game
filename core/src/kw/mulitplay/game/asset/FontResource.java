package kw.mulitplay.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import kw.mulitplay.game.Constant;

public class FontResource {
    public static BitmapFont commonfont;
    public static BitmapFont animalfont;
    public static BitmapFont time;
    AssetManager assetManager ;
    public FontResource(){
        assetManager = Constant.assetManager;
        BitmapFontLoader.BitmapFontParameter textureParameter = null;
        textureParameter = new BitmapFontLoader.BitmapFontParameter();
        textureParameter.genMipMaps = true;
        textureParameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.atlasName = "image/main.atlas";
        assetManager.load("fonts/gamefont.fnt", BitmapFont.class, textureParameter);
        assetManager.load("fonts/animal.fnt", BitmapFont.class, textureParameter);
        assetManager.load("fonts/time.fnt", BitmapFont.class, textureParameter);
    }

    public void getAtlas(){
        commonfont = assetManager.get("fonts/gamefont.fnt", BitmapFont.class);
        animalfont = assetManager.get("fonts/animal.fnt",BitmapFont.class);
        time = assetManager.get("fonts/time.fnt",BitmapFont.class);
    }
}
