package kw.mulitplay.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.asset.FontResource;
import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.screen.base.BaseScreen;

/**
 * all screen extends BaseScreen
 */
public class LoadingScreen extends BaseScreen {

    private boolean out;
    private float time;

    @Override
    protected void initView() {
        Image image = new Image(new Texture("splash.png"));
        stage.addActor(image);
        //init font resource
        new FontResource();
        //init resource
        Constant.resource = new Resource();
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
        enterNextScreen(delta);
    }

    public void enterNextScreen(float delta){
        if (out)return;
        time +=  delta;
        if (time>2){
            out = true;
            enterScreen(new MainScreen());
        }
    }
}
