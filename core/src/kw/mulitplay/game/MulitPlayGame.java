package kw.mulitplay.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import kw.mulitplay.game.screen.LoadingScreen;

public class MulitPlayGame extends Game {
    private Viewport viewport;
    @Override
    public void create() {
        //create viewport and as a constant
        Constant.viewport = viewport = new ExtendViewport(720,1280);
        // call to set value Constant.width and Constant.height
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //create batch
        Constant.batch = new CpuSpriteBatch();
        //loading
        setScreen(new LoadingScreen());
        Constant.game = this;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
        viewport.apply();
        Constant.height = viewport.getWorldHeight();
        Constant.width = viewport.getWorldWidth();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }
}
