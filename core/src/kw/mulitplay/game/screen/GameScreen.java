package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.net.InetAddress;
import java.util.List;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.asset.FontResource;
import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.screen.data.GameData;
import kw.mulitplay.game.screen.panel.GamePanel;

public class GameScreen extends BaseScreen {
    private GameData data;
    private Label timeLabel;
    private float time = 0;
    private int current = 0;
    private Label currentPlayer;
    private GamePanel panel;

    @Override
    protected void initData() {
        data = new GameData();
    }

    @Override
    protected void initView() {
       commonView();
        //note : this class important
        startGame();
    }

    public void commonView(){
        Image head = new Image(Resource.atlas.findRegion("white"));
        head.setColor(Color.valueOf("a6937c"));
        head.setSize(Constant.width,100);
        head.setY(Constant.height, Align.top);
        stage.addActor(head);
        Image content = new Image(Resource.atlas.findRegion("white"));
        content.setColor(Color.valueOf("d1c0a5"));
        content.setSize(Constant.width,Constant.height-100);
        stage.addActor(content);
        Image back = new Image(Resource.atlas.findRegion("back"));
        back.setName("back");
        back.setPosition(20,Constant.height - 31,Align.topLeft);
        stage.addActor(back);
        Label title = new Label("double game",new Label.LabelStyle(){{font = FontResource.commonfont; }});
        title.setAlignment(Align.center);
        title.setPosition(head.getWidth()/2,head.getY(Align.center), Align.center);
        stage.addActor(title);
        Image daojishi = new Image(Resource.atlas.findRegion("daojishi"));
        daojishi.setPosition(20,Constant.height-112, Align.topLeft);
        daojishi.setOrigin(Align.center);
        daojishi.setScale(0.7F);
        stage.addActor(daojishi);
        timeLabel = new Label("0",new Label.LabelStyle(){{font = FontResource.time; }});
        stage.addActor(timeLabel);
        timeLabel.setName("time");
        timeLabel.setPosition(daojishi.getX(Align.right)+5,daojishi.getY(Align.center),Align.left);
        currentPlayer = new Label(data.getCurrentPlay() ,new Label.LabelStyle(){{font = FontResource.commonfont; }});
        currentPlayer.setAlignment(Align.center);
        currentPlayer.setPosition(Constant.width/2,timeLabel.getY(Align.center),Align.center);
        stage.addActor(currentPlayer);
    }

    private void startGame(){
        current = 0;
        timeLabel.setText(current+"");
        Actor gamePanel = findActor("gamePanel");
        if (gamePanel != null) {
            gamePanel.remove();
        }
        panel = new GamePanel(data);
        panel.setPosition(Constant.width/2+3,Constant.height*0.42F,Align.center);
        stage.addActor(panel);
        addGamePanelListener();
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

    private void addGamePanelListener() {
        GamePanel gamePanel = findActor("gamePanel");
        gamePanel.setListener(new GamePanel.Listener() {
            @Override
            public void updatePlayer(Player currentPlay) {
                GameScreen.this.updatePlayer(currentPlay);
            }

            @Override
            public void passLevelPass(String text,boolean isClick) {
                showPassLevel(text,isClick);
            }

            @Override
            public void tipRemove() {
                Actor shadow = findActor("shadow");
                if (shadow==null) {
                    return;
                }
                shadow.remove();
                Actor ipName = stage.getRoot().findActor("ipName");
                if (ipName != null) {
                    ipName.remove();
                }
            }

            @Override
            public void showIp(List<InetAddress> list) {
                showIpLabel(list);
            }
        });
        gamePanel.initData();
    }

    private void showIpLabel(List<InetAddress> list) {
        Table table = new Table(){{
            for (InetAddress inetAddress : list) {
                Label label = new Label(inetAddress.getHostAddress(), new Label.LabelStyle() {{
                    font = FontResource.commonfont;
                }});
                add(label);
                label.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        GamePanel gamePanel = GameScreen.this.findActor("gamePanel");
                        gamePanel.clientSetListener();
                        Constant.multClient.connect(inetAddress.getHostAddress());
                    }
                });
                row();
            }
            pack();
        }};
        table.setPosition(Constant.width/2,Constant.height*0.4F,Align.center);
        table.setName("ipName");
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        updateTime(delta);
    }

    private void updateTime(float delta){
        if (panel.getStatus() != GamePanel.GameStatus.running)return;
        float deltaTemp = delta;
        time += deltaTemp;
        if (time>=1){
            time = 0;
            current++;
            timeLabel.setText(current+"");
        }
    }

    private void updatePlayer(Player currentPlay){
        currentPlayer.setText(currentPlay.name);
        currentPlayer.setColor(currentPlay.color);
    }


    public void showPassLevel(String text,boolean addClick) {
//        status = GamePanel.GameStatus.win;
        Group group = new Group();
        group.setName("shadow");
        group.setSize(Constant.width, Constant.height);
        group.setPosition(Constant.width / 2, Constant.height / 2, Align.center);
        stage.addActor(group);
        Image sha = new Image(Resource.atlas.findRegion("white"));
        group.addActor(sha);
        sha.setSize(group.getWidth(), group.getHeight());
        sha.setColor(Color.valueOf("44444477"));
        Label textLabel = new Label(text, new Label.LabelStyle() {{
            font = FontResource.commonfont;
        }});
        group.addActor(textLabel);
        textLabel.setAlignment(Align.center);
        textLabel.setScale(2);
        textLabel.setPosition(Constant.width / 2, Constant.height / 2, Align.center);
        if (!addClick)return;
        Label clickLabel = new Label("Click any key enter new Game", new Label.LabelStyle() {{
            font = FontResource.commonfont;
        }});
        group.addActor(clickLabel);
        clickLabel.setAlignment(Align.center);
        clickLabel.setPosition(Constant.width / 2, Constant.height * 0.3F, Align.center);
        //点击任意就重新开始

        group.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                group.remove();
                startGame();
                group.removeListener(this);
            }
        });
    }
}
