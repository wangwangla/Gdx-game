package kw.mulitplay.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.screen.base.BaseScreen;

/**
 * all screen extends BaseScreen
 */
public class LoadingScreen extends BaseScreen {
    private AssetManager assetManager;
    private boolean out;
    private float time;

    @Override
    protected void initView() {
        assetManager = Constant.assetManager;
        Image image = new Image(new Texture("splash.webp"));
        stage.addActor(image);
        image.setOrigin(Align.center);
        image.setScale(Constant.bgScale);
        image.setY(Constant.height/2,Align.center);
        //init font resource
        //init resource
        Constant.resource = new Resource();
        Constant.resource.getAtlas();
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
        if (assetManager.update()){
            out = true;
            enterScreen(new MainScreen());
        }
    }
}
