package kw.mulitplay.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.asset.FontResource;
import kw.mulitplay.game.screen.base.BaseScreen;

public class LoadingScreen extends BaseScreen {

    @Override
    protected void initView() {
        Image image = new Image(new Texture("splash.png"));
        stage.addActor(image);
        new FontResource();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (out)return;
        enterNextScreen(delta);
    }

    private boolean out;
    private float time;

    public void enterNextScreen(float delta){
        time +=  delta;
        if (time>2){
            out = true;
            enterScreen(new MainScreen());
        }
    }
}
