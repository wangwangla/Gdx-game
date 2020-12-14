package kw.mulitplay.game.screen.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Null;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.screen.MainScreen;

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

    protected void enterScreen(BaseScreen screen){
        Constant.game.setScreen(screen);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public @Null<T extends Actor> T findActor(String name){
        return findActor(stage.getRoot(),name);
    }

    public @Null<T extends Actor> T findActor(Group group, String name){
        return group.findActor(name);
    }
}
