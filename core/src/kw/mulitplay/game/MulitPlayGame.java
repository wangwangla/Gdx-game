package kw.mulitplay.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.screen.LoadingScreen;

public class MulitPlayGame extends Game {
    private Viewport viewport;
    @Override
    public void create() {
        //create viewport and as a constant
        kw.mulitplay.game.constant.Constant.viewport = viewport = new ExtendViewport(720,1280);
        // call to set value Constant.width and Constant.height
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //create batch
        kw.mulitplay.game.constant.Constant.batch = new CpuSpriteBatch();
        //loading
        kw.mulitplay.game.constant.Constant.game = this;
        kw.mulitplay.game.constant.Constant.assetManager = new AssetManager();
        setScreen(new LoadingScreen());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
        viewport.apply();
        kw.mulitplay.game.constant.Constant.height = viewport.getWorldHeight();
        kw.mulitplay.game.constant.Constant.width = viewport.getWorldWidth();
        kw.mulitplay.game.constant.Constant.bgScale = Math.max(kw.mulitplay.game.constant.Constant.width / 720, kw.mulitplay.game.constant.Constant.height / 1280);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (kw.mulitplay.game.constant.Constant.multServer!=null) {
            kw.mulitplay.game.constant.Constant.multServer.closed();
        }
        if (kw.mulitplay.game.constant.Constant.multClient!=null){
            Constant.multClient.closed();
        }
    }
}
