package kw.mulitplay.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.asset.FontResource;
import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.screen.data.GameData;

public class PackActor extends Group {
    private Image bg;    // 背景
    private Image fanPack;   // 反面
    private Image zhengPack;     //正面
    private Label animalLabel;      //動物名字
    private int num = 0;   //num
    private String name;
    private Color useColor ;
    private Color redColor = Color.RED;
    private Color blackColor = Color.BLACK;
    private int tempX;
    private int tempY;
    private short currentStatus;
    private short ower;
    private boolean live = true;
    public PackActor(String name){
        setSize(160,185);
        Image image = new Image(Resource.atlas.findRegion("white"));
        setName(name);
        addActor(image);
        setVisible(false);
        currentStatus = Constant.ZHENGMIAN;
    }

    public PackActor(GameData data, int x, int y){
        int i = data.getArr()[x][y];
        name = i+"";
        setName(name);
        currentStatus = Constant.FANMIAN;
        useColor = blackColor;
        ower = 0;   //默認為0  ，為黑色
        if (i>10){
            i -= 10;
            useColor = redColor;
            ower = 1;
        }
        this.num = i;
        if (num == 0){
            for (int[] ints : data.getArr()) {

            }
        }
        bg = new Image(Resource.atlas.findRegion("white"));
        addActor(bg);
        fanPack = new Image(Resource.atlas.findRegion("pckback"));
        setSize(fanPack.getWidth(),fanPack.getHeight());
        bg.setSize(getWidth(),getHeight());
        fanPack.setPosition(getWidth()/2,getHeight()/2, Align.center);
        zhengPack = new Image(Resource.atlas.findRegion(num+""));
        addActor(zhengPack);
        zhengPack.setVisible(false);
        zhengPack.setPosition(getWidth(),0,Align.bottomRight);
        name = data.getAnimalData().get(num);
        animalLabel = new Label(name,new Label.LabelStyle(){{font = FontResource.animalfont;}});
        addActor(animalLabel);
        animalLabel.setColor(useColor);
        animalLabel.setAlignment(Align.center);
        animalLabel.setPosition(20,getHeight()-20, Align.center);
        addActor(fanPack);
        setXY(x,y);
    }

    public int getTempX() {
        return tempX;
    }

    public int getTempY() {
        return tempY;
    }

    public void setXY(int x, int y) {
        this.tempX = x;
        this.tempY = y;
        setPosition(x*(getWidth()+4.5F),y*(getHeight()+7));
    }

    public short getCurrentStatus() {
        return currentStatus;
    }

    public void open(){
        currentStatus = Constant.ZHENGMIAN;
        fanPack.setVisible(false);
        zhengPack.setVisible(true);
    }

    public void setListener(TachListener listener) {
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                listener.action(PackActor.this);
            }
        });
    }

    public int getNum() {
        return num;
    }

    public void appear(){
        live = false;
        remove();
    }

    public Color getOtherColor() {
        return useColor == redColor ? blackColor : redColor;
    }

    public interface TachListener{
        public void action(PackActor actor);
    }

    public short getOwer() {
        return ower;
    }

    public void setAnimalScale(float scale){
        setOrigin(Align.center);
        setScale(scale,scale);
    }

    public Color getUseColor() {
        return useColor;
    }

    public boolean getStatus(){
        if (currentStatus == Constant.FANMIAN){
            return false;
        }
        return true;
    }

    public boolean isLive() {
        return live;
    }

    @Override
    public String toString() {
        return "name:"+getName()+",x:"+tempX+",y:"+tempY;
    }
}
