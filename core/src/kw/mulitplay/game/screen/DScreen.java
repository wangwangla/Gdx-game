package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

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
        head.setColor(Color.valueOf("a6937c"));
        head.setSize(Constant.width,100);
        head.setY(Constant.height, Align.top);
        stage.addActor(head);
        Image content = new Image(new Texture("white.png"));
        content.setColor(Color.valueOf("d1c0a5"));
        content.setSize(Constant.width,Constant.height-100);
        stage.addActor(content);
        Image back = new Image(new Texture("back.png"));
        stage.addActor(back);
        back.setName("back");
        back.setPosition(20,Constant.height - 31,Align.topLeft);
        Label title = new Label("double game",new Label.LabelStyle(){{font = FontResource.commonfont; }});
        stage.addActor(title);
        title.setAlignment(Align.center);
        title.setPosition(head.getWidth()/2,head.getY(Align.center), Align.center);
        Image daojishi = new Image(new Texture("daojishi.png"));
        daojishi.setPosition(20,Constant.height-112, Align.topLeft);
        daojishi.setOrigin(Align.center);
        daojishi.setScale(0.7F);
        stage.addActor(daojishi);
        timeLabel = new Label("0",new Label.LabelStyle(){{font = FontResource.time; }});
        stage.addActor(timeLabel);
        timeLabel.setName("time");
        timeLabel.setPosition(daojishi.getX(Align.right)+5,daojishi.getY(Align.center),Align.left);
        Label currentPlayer = new Label(data.getCurrentPlay() ,new Label.LabelStyle(){{font = FontResource.commonfont; }});
        stage.addActor(currentPlayer);
        currentPlayer.setAlignment(Align.center);
        currentPlayer.setPosition(Constant.width/2,timeLabel.getY(Align.center),Align.center);
        GamePanel panel = new GamePanel(data);
        stage.addActor(panel);
        panel.setPosition(Constant.width/2,Constant.height*0.42F,Align.center);
        panel.setListener(new GamePanel.Listener() {
            @Override
            public void updatePlayer(Player currentPlay) {
                System.out.println(currentPlay.name);
                currentPlayer.setText(currentPlay.name);
                currentPlayer.setColor(currentPlay.color);
            }
        });
    }

    @Override
    protected void initListener() {
        findActor("back").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                enterScreen(new MainScreen());
            }
        });
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
