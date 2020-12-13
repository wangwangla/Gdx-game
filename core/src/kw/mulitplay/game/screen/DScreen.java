package kw.mulitplay.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import java.awt.AWTKeyStroke;

import javax.swing.ComboBoxEditor;
import javax.xml.bind.annotation.XmlSeeAlso;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.asset.FontResource;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.screen.data.GameData;
import kw.mulitplay.game.screen.panel.GamePanel;

public class DScreen extends BaseScreen {
    private GameData data;
    private Label timeLabel;
    @Override
    protected void initView() {
        Image head = new Image(new Texture("white.png"));
        stage.addActor(head);
        head.setColor(Color.valueOf("a6937c"));
        head.setSize(Constant.width,100);
        head.setY(Constant.height, Align.top);
        Image content = new Image(new Texture("white.png"));
        stage.addActor(content);
        content.setColor(Color.valueOf("d1c0a5"));
        content.setSize(Constant.width,Constant.height-100);
        Image back = new Image(new Texture("back.png"));
        stage.addActor(back);
        back.setPosition(20,Constant.height - 31,Align.topLeft);
        Label title = new Label("double game",new Label.LabelStyle(){{font = FontResource.font; }});
        stage.addActor(title);
        title.setAlignment(Align.center);
        title.setFontScale(4);
        title.setPosition(Constant.width/2,Constant.height-31, Align.top);
        Image daojishi = new Image(new Texture("daojishi.png"));
        daojishi.setPosition(20,Constant.height-122, Align.topLeft);
        stage.addActor(daojishi);
        timeLabel = new Label("10:33",new Label.LabelStyle(){{font = FontResource.font; }});
        stage.addActor(timeLabel);
        timeLabel.setName("time");
        timeLabel.setFontScale(5);
        timeLabel.setPosition(daojishi.getX(Align.right)+5,daojishi.getY(Align.center),Align.left);
        Label currentPlayer = new Label(data.getCurrentPlay() ,new Label.LabelStyle(){{font = FontResource.font; }});
        stage.addActor(currentPlayer);
        currentPlayer.setAlignment(Align.center);
        currentPlayer.setPosition(Constant.width/2,timeLabel.getY(Align.center),Align.center);
        currentPlayer.setFontScale(6);
        GamePanel panel = new GamePanel(data);
        stage.addActor(panel);
        panel.setPosition(Constant.width/2,Constant.height*0.42F,Align.center);
        panel.setListener(new GamePanel.Listener() {
            @Override
            public void updatePlayer(Player currentPlay) {
                System.out.println(currentPlay.name);
                currentPlayer.setText(currentPlay.name);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        data = new GameData();
    }

    private float time = 0;
    private int current = 0;
    @Override
    public void render(float delta) {
        super.render(delta);
        time += delta;
        if (time>=1){
            time = 0;
            current++;
            timeLabel.setText(current);
        }
    }
}
