package kw.mulitplay.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.AbstractList;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.screen.base.BaseScreen;

public class MainScreen extends BaseScreen {
    @Override
    protected void initView() {
        Image image  = new Image(new Texture("mainbg.png"));
        stage.addActor(image);
        Image button = new Image(new Texture("doubleduiyi.png"));
        stage.addActor(button);
        button.setPosition(Constant.width/2,Constant.height/2, Align.center);
        System.out.println(button.getWidth()+"=="+Constant.width);
        button.setName("button");
    }

    @Override
    protected void initListener() {
        Actor button = stage.getRoot().findActor("button");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Constant.game.setScreen(new DScreen());
            }
        });

    }

    @Override
    protected void initData() {

    }
}
