package com.kangwang.word;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import kw.mulitplay.game.MulitPlayGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
//        texturePack();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "wrod squares";
        config.width =460; //485
        config.height =840;
        new LwjglApplication(new MulitPlayGame(),config);
    }

    static String[] atlasFileName = {"main"};

    private static void texturePack() {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.pot = false;
        settings.maxHeight = 2048;
        settings.maxWidth = 2048;
        settings.duplicatePadding = true;
        settings.paddingX = 8;
        settings.paddingY = 8;
        settings.edgePadding = true;
        settings.bleed = true;
        settings.combineSubdirectories = true;
        settings.format = Pixmap.Format.RGBA8888;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.useIndexes = false;
        settings.stripWhitespaceX = true;
        settings.stripWhitespaceY = true;
       processAndroid(settings);
    }

    private static void process(TexturePacker.Settings setting,String srcDir) {
        TexturePacker.process(setting, "../../Assets/spine/" + srcDir + "/", "../../Assets/atlas/", srcDir);
    }

    private static void processAndroid(TexturePacker.Settings setting) {
        for (int i = 0; i < atlasFileName.length; i++) {
            String input = atlasFileName[i];
            if (input == null) return;
            try {
                TexturePacker.process(
                        setting,
                        "../../Assets/" + input + "/",
                        "image/" ,
                        input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}