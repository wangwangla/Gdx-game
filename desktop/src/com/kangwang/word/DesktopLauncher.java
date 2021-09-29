package com.kangwang.word;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;


import kw.mulitplay.game.GdxVideoTest;

public class DesktopLauncher {
    public static void main(String[] arg) {
//        texturePack();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "wrod squares";
        config.width =460; //485
        config.height =840;
        new LwjglApplication(new GdxVideoTest(),config);
    }

}