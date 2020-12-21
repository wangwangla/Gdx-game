package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.asset.FontResource;
import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.screen.base.BaseScreen;

public class MainScreen extends BaseScreen {
    @Override
    protected void initView() {
        Image image  = new Image(Resource.atlas.findRegion("mainbg"));
        stage.addActor(image);
        image.setOrigin(Align.center);
        image.setScale(Constant.bgScale);
        image.setY(Constant.height/2,Align.center);
        Image button = new Image(Resource.atlas.findRegion("doubleduiyi"));
        stage.addActor(button);
        button.setPosition(Constant.width/2,Constant.height/2, Align.center);
        button.setName("button");
        Image button2 = new Image(Resource.atlas.findRegion("doubleduiyi"));
        stage.addActor(button2);
        button2.setPosition(Constant.width/2,Constant.height/2-150, Align.center);
        button2.setName("button2");
    }



    //this method only add listener to button
    @Override
    protected void initListener() {
        Actor button = stage.getRoot().findActor("button");
        //one device play  ,sad ,not write ai,so need two player use one device.
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Constant.isServer = Constant.NOMAL;
                Constant.game.setScreen(new GameScreen());
            }
        });
        //one net connnect;
        Actor button2 = stage.getRoot().findActor("button2");
        button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tip();
//                Constant.game.setScreen(new DScreen());
            }
        });
    }

    private void tip(){
        Group tipGroup = new Group();
        Image tipMessage = new Image(Resource.atlas.findRegion("white"));
        tipMessage.setSize(Constant.width,100);
        tipGroup.setSize(tipMessage.getWidth(),tipMessage.getHeight());
        tipGroup.addActor(tipMessage);
        Label serverLabel = new Label("server", new Label.LabelStyle(){{font = FontResource.commonfont;}});
        tipGroup.addActor(serverLabel);
        serverLabel.setColor(Color.BLACK);
        serverLabel.setPosition(Constant.width*0.2F,tipGroup.getY(Align.center),Align.center);
        //server
        serverLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Constant.isServer = Constant.SERVER;
                Constant.game.setScreen(new GameScreen());
            }
        });
        stage.addActor(tipGroup);
        Label clientLabel = new Label("client", new Label.LabelStyle(){{font = FontResource.commonfont;}});
        tipGroup.addActor(clientLabel);
        clientLabel.setColor(Color.BLACK);
        clientLabel.setPosition(Constant.width*0.7F,tipGroup.getY(Align.center),Align.center);
        //client
        clientLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Constant.isServer = Constant.CLIENT;
                Constant.game.setScreen(new GameScreen());
            }
        });
        tipGroup.setPosition(Constant.width/2,Constant.height/2,Align.center);
    }

    @Override
    protected void initData() {

    }
}
