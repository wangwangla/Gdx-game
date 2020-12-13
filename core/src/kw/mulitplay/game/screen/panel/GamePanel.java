package kw.mulitplay.game.screen.panel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayDeque;

import kw.mulitplay.game.Constant;
import kw.mulitplay.game.actor.PackActor;
import kw.mulitplay.game.screen.Player;
import kw.mulitplay.game.screen.data.GameData;
import sun.util.resources.cldr.ki.LocaleNames_ki;

public class GamePanel extends Group {
    private PackActor up = new PackActor();
    private PackActor down = new PackActor();
    private PackActor left = new PackActor();
    private PackActor right = new PackActor();

    private Player A;
    private Player B;

    private Player currentPlay;  //默認一個玩家開始
    private int arr[][];

    private GameData data;

    private ArrayDeque<PackActor> packActors = new ArrayDeque<>(0);
    public GamePanel(GameData data){
        this.data = data;
        initPlayer(); // 初始化玩家
        initTable();  //初始化格子
        initPacker();  //初始化牌
        initTip();  // 初始化提示框
        controller(); //添加控制
    }

    private void initTip() {
        addActor(up);
        addActor(down);
        addActor(left);
        addActor(right);
    }

    private void initPacker() {
        arr = data.shuffle();
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = 0; i1 < arr[0].length; i1++) {
                PackActor actor = new PackActor(data,arr[i][i1]);
                actor.setXY(i,i1);
                actor.setListener(listener);
                addActor(actor);
            }
        }
    }

    private void initTable() {
        Image table = new Image(new Texture("table.png"));
        addActor(table);
        table.setPosition(-4,-4);
        setSize(table.getWidth(),table.getHeight());
    }

    private void initPlayer() {
        A = new Player("play one");
        B = new Player("play two");
        currentPlay = A;
    }

    public void controller(){
        up.setListener(NullListener);
        down.setListener(NullListener);
        left.setListener(NullListener);
        right.setListener(NullListener);
    }


    public PackActor.TachListener NullListener = new PackActor.TachListener(){
        @Override
        public void action(PackActor target) {
            move(target);
            packActors.clear();
            resetTip();
        }
    };

    private void move(PackActor target) {
        if (target.isVisible()) {
            changePosition(target);
            changePlayer();
        }
    }

    private void changePosition(PackActor target) {
        PackActor actor = target;
        int tempY = actor.getTempY();
        int tempX = actor.getTempX();
        PackActor last = packActors.getLast();
        arr[last.getTempX()][last.getTempY()] = 0;
        arr[tempX][tempY] = Integer.parseInt(last.getName());
        last.setXY(tempX,tempY);
        last.setAnimalScale(1);
        target.setVisible(false);
    }

    public void changePlayer(){
        if (currentPlay == A){
            currentPlay = B;
        }else {
            currentPlay = A;
        }
        updateListener.updatePlayer(currentPlay);
    }

    public PackActor.TachListener listener = new PackActor.TachListener(){
        @Override
        public void action(PackActor target) {
            resetTip();
            if (target.getCurrentStatus() == Constant.FANMIAN){
                fanPacker(target);
            }else {
                //如果已經有選中的
                if (packActors.size()!=0){
                    alreadySelected(target);
                }else {
                    if (currentPlay.OWNER != target.getOwer())return;
                    packActors.add(target);
                    target.setAnimalScale(1.1F);
                }
                //提示
                 tip();
            }
        }

        private void tip() {
            if (packActors.size() == 0) {
                return;
            }
            PackActor last = packActors.getLast();
            int tempX = last.getTempX();
            int tempY = last.getTempY();
            if (tempX+1<4){
                if (arr[tempX + 1][tempY] == 0) {
                    right.setXY(tempX+1,tempY);
                    right.setVisible(true);
                }
            }
            if (tempX-1>=0){
                if (arr[tempX-1][tempY] == 0) {
                    left.setXY(tempX-1,tempY);
                    left.setVisible(true);
                }
            }
            if (tempY + 1<5){
                if (arr[tempX][tempY+1] == 0) {
                    up.setXY(tempX,tempY+1);
                    up.setVisible(true);
                }
            }
            if (tempY - 1>=0){
                if (arr[tempX][tempY-1] == 0) {
                    down.setXY(tempX,tempY-1);
                    down.setVisible(true);
                }
            }
        }

        private void alreadySelected(PackActor target) {
            PackActor last = packActors.getLast();
            int lastX = last.getTempX();
            int lastY = last.getTempY();
            int targetX = target.getTempX();
            int targetY = target.getTempY();
            if(last.getOwer()!=target.getOwer()&&isValue(lastX,lastY,targetX,targetY)){
                //是否可以吃掉對方
                int num = target.getNum();
                if ((last.getNum() == 10&&num==1)||num>=last.getNum()){
                    target.appear();
                    last.setXY(targetX,targetY);
                    arr[targetX][targetY] = arr[lastX][lastY];
                    arr[lastX][lastY] = 0;
                    last.setAnimalScale(1);
                    packActors.clear();
                    resetTip();
                    changePlayer();
                    return;
                }
                //否則   將本次的加進去
            }
            last.setAnimalScale(1);
            if (currentPlay.OWNER != target.getOwer())return;
            packActors.add(target);
            target.setAnimalScale(1.1F);
        }

        private void fanPacker(PackActor target) {
            target.open();
            // 默認playA是第一個節拍的
            if (A.OWNER == -1) {
                A.OWNER = target.getOwer();
                B.OWNER = (short) (1-target.getOwer());
            }
            changePlayer();
            if (packActors.size()!=0){
                PackActor last = packActors.getLast();
                last.setAnimalScale(1);
                packActors.clear();
            }
            return;
        }
    };

    private boolean isValue(int x,int y,int targetX,int targetY){
        if ((x+1==targetX)&&(y == targetY) ||
                (x - 1 == targetX)&&(y == targetY)||
                (x == targetX) &&(y== targetY+1||y == targetY-1)){
            return true;
        }
        return false;
    }

    private void resetTip(){
        up.setVisible(false);
        down.setVisible(false);
        left.setVisible(false);
        right.setVisible(false);
    }

    public void setListener(Listener listener) {
        this.updateListener = listener;

    }
    private Listener updateListener;

    public interface Listener{
        void updatePlayer(Player currentPlay);
    }
}
