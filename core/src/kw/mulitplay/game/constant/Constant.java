package kw.mulitplay.game.constant;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;

import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.net.MultClient;
import kw.mulitplay.game.net.MultServer;

public class Constant {
    public static final short FANMIAN = 0;
    public static final short ZHENGMIAN = 1;
    public static final short SERVER = 0;
    public static final short CLIENT = 1;
    public static float width;
    public static float height;
    public static Viewport viewport;
    public static Game game;
    public static Batch batch;
    public static Resource resource;
    public static short isServer;
    public static short NOMAL = -1;

    public static MultClient multClient;
    public static MultServer multServer;

    public static float bgScale = 1;
    public static AssetManager assetManager;
}
