package kw.mulitplay.game.screen.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kw.mulitplay.game.Constant;

public abstract class BaseScreen implements Screen {
    protected Stage stage;
    @Override
    public void show() {
        stage = new Stage(Constant.viewport,Constant.batch);
        Gdx.input.setInputProcessor(stage);
        initData();
        initView();
        initListener();
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();


    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
