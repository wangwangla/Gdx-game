package kw.mulitplay.game.screen.panel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

import kw.mulitplay.game.ai.ComputateAI;
import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.net.message.Message;
import kw.mulitplay.game.actor.PackActor;
import kw.mulitplay.game.asset.Resource;
import kw.mulitplay.game.net.MultClient;
import kw.mulitplay.game.net.MultServer;
import kw.mulitplay.game.net.NetListener;
import kw.mulitplay.game.position.VectorPosition;
import kw.mulitplay.game.screen.Player;
import kw.mulitplay.game.screen.data.GameData;

public class GamePanel extends Group {
    //上下左右提示框
    private PackActor upTip = new PackActor("UP");
    private PackActor downTip = new PackActor("DOWN");
    private PackActor leftTip = new PackActor("LEFT");
    private PackActor rightTip = new PackActor("RIGHT");

    private Array<PackActor> redPackActors;
    private Array<PackActor> blackPackActors;

    private Player playOne;
    private Player playTwo;
    private Player currentPlay;  //默認一個玩家開始Nu

    private int arr[][];
    private GameData data;
    private ArrayDeque<PackActor> currentSelect ;
    private ComputateAI ai;

    public GamePanel(GameData data){
        this.data = data;
        this.currentSelect = new ArrayDeque<>(0);
        setName("gamePanel");
        initTable();
        ai = new ComputateAI();
    }

    private void initGame(){
        initPlayer(); // 初始化玩家
        initPacker();  //初始化牌
        initTip();  // 初始化提示框
        controller(); //添加控制
    }

    public void initData() {
        status = GameStatus.IDE;
        if (Constant.isServer == Constant.SERVER){
            arr = data.shuffle();
            MultServer server ;
            Constant.multServer = server = new MultServer();
            server.setListener(new NetListener() {
                @Override
                public void sendGameData() {
                    /**
                     * 开局
                     */
                    super.sendGameData();
                    Message arrMessage = new Message(arr);
                    arrMessage.setType("START");
                    Constant.multServer.sendMessage(arrMessage);
                    updateListener.tipRemove();
                }

                @Override
                public void play(Message message) {
                    super.play(message);
                    runNetMethod(message);
                }
            });
            updateListener.passLevelPass("wite player connect!",false);
            initGame();
        }else if (Constant.isServer == Constant.CLIENT){
            updateListener.passLevelPass("search server!!!",false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MultClient client = new MultClient();
                    Constant.multClient =client;
                    client.setAddress(new Address() {
                        @Override
                        public void address(List<InetAddress> list) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    updateListener.showIp(list);
                                }
                            });
                        }
                    });
                    client.init();
                }
            }).start();
        }else {
            arr = data.shuffle();
            initGame();
        }
    }

    public void clientSetListener(){
        Constant.multClient.setListener(new NetListener() {
            @Override
            public void start(Message message) {
                Gdx.app.postRunnable(()->{
                    showClient(message);
                    updateListener.tipRemove();
                });
            }

            @Override
            public void play(Message message) {
                super.play(message);
                runNetMethod(message);
            }
        });
    }

    public void showClient(Message message){
        arr = message.getArr();
        data.setArr(arr);
        initGame();
    }

    private void runNetMethod(Message message){
        String name = message.getName();
        PackActor actor = findActor(name);
        if (actor==null)return;
        if (message.getType().equals("")){
            //得到  target
            move(actor);
        }else {
            GamePanel.this.excute(actor);
        }
    }

    public boolean cancelTask(){
        if (Constant.isServer == Constant.SERVER){
            if (currentPlay == playTwo){
                return false;
            }
        }else if (Constant.isServer == Constant.CLIENT){
            if (currentPlay == playOne){
                return false;
            }
        }
        return true;
    }

    private void initTip() {
        addActor(upTip);
        addActor(downTip);
        addActor(leftTip);
        addActor(rightTip);
    }

    private Array<PackActor> all = new Array<>();
    private void initPacker() {
        redPackActors = new Array<>();
        blackPackActors = new Array<>();
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                PackActor actor = new PackActor(data,x,y);
                actor.setListener(listener);
                if (actor.getUseColor().equals(Color.RED)) {
                    redPackActors.add(actor);
                }else {
                    blackPackActors.add(actor);
                }
                addActor(actor);
            }
        }
        all.addAll(redPackActors);
        all.addAll(blackPackActors);
    }

    private void initTable() {
        Image table = new Image(Resource.atlas.findRegion("table"));
        addActor(table);
        table.setPosition(-6,-8);
        setSize(table.getWidth(),table.getHeight());
    }

    private void initPlayer() {
        playOne = new Player("play one");
        playTwo = new Player("play two");
        currentPlay = playOne;
    }

    public void controller(){
        upTip.setListener(NullListener);
        downTip.setListener(NullListener);
        leftTip.setListener(NullListener);
        rightTip.setListener(NullListener);
    }

    public PackActor.TachListener NullListener = new PackActor.TachListener(){
        @Override
        public void action(PackActor target) {
//            ----->>>>> 发送消息
            if (!cancelTask()) {
                return;
            }
            if (Constant.isServer != Constant.NOMAL) {
                Message message = new Message();
                message.setType("");
                message.setName(target.getName());
                message.setPosition(new VectorPosition(target.getTempX(),target.getTempY()));
                sendMessage(message);
            }
            move(target);
        }
    };

    private void sendMessage(Message message){
        if (Constant.isServer == Constant.SERVER){
            Constant.multServer.sendMessage(message);
        }else {
            Constant.multClient.senMessage(message);
        }
    }

    private void move(PackActor target) {
        if (target.isVisible()) {
            changePosition(target);
            resetTip(true);
        }
    }

    private void excute(PackActor target) {
        cancelTask();
        resetTip(false);
        if (target.getCurrentStatus() == Constant.FANMIAN){
            fanPacker(target);
        }else {
            //如果已經有選中的
            if (currentSelect.size()!=0){
                alreadySelected(target);
            }else {
                if (currentPlay.OWNER != target.getOwer())return;
                currentSelect.add(target);
                target.setAnimalScale(1.1F);
            }
            //提示
            tip();
        }
    }

    private void changePosition(PackActor target) {
        System.out.println("setPosition");
        PackActor actor = target;
        int tempY = actor.getTempY();
        int tempX = actor.getTempX();
        PackActor last = currentSelect.getLast();
        arr[last.getTempX()][last.getTempY()] = 0;
        arr[tempX][tempY] = Integer.parseInt(last.getName());
        last.setXY(tempX,tempY);
        last.setAnimalScale(1);
        target.setVisible(false);
        changePlayer();
    }

    private void changePosition(PackActor last,int tempX,int tempY) {
        currentSelect.clear();
        arr[last.getTempX()][last.getTempY()] = 0;
        arr[tempX][tempY] = Integer.parseInt(last.getName());
        last.setXY(tempX,tempY);
        last.setAnimalScale(1);
        changePlayer();
    }

    public void changePlayer(){
        if (status == GameStatus.win) {
            return;
        }
        if (currentPlay == playOne){
            currentPlay = playTwo;
            updateListener.touch(false);
            if (Constant.isServer == Constant.NOMAL) {
                addAction(Actions.delay(1, Actions.run(() -> {
                    AI();
                    updateListener.touch(true);
                })));
            }
        }else {
            currentPlay = playOne;
        }
        updateListener.updatePlayer(currentPlay);
    }

    public PackActor.TachListener listener = new PackActor.TachListener(){
        @Override
        public void action(PackActor target) {
            //发送消息    ---->>>>
            if (!cancelTask()) {
                return;
            }
            if (Constant.isServer != Constant.NOMAL){
                Message message = new Message();
                message.setType("NULLTYPE");
                message.setName(target.getName());
                message.setPosition(new VectorPosition(target.getTempX(),target.getTempY()));
                sendMessage(message);
            }
            excute(target);
        }
    };

    private void tip() {
        if (currentSelect.size() == 0) {
            return;
        }
        PackActor last = currentSelect.getLast();
        int tempX = last.getTempX();
        int tempY = last.getTempY();
        if (tempX+1<4){
            if (arr[tempX + 1][tempY] == 0) {
                rightTip.setXY(tempX+1,tempY);
                rightTip.setVisible(true);
            }
        }
        if (tempX-1>=0){
            if (arr[tempX-1][tempY] == 0) {
                leftTip.setXY(tempX-1,tempY);
                leftTip.setVisible(true);
            }
        }
        if (tempY + 1<5){
            if (arr[tempX][tempY+1] == 0) {
                upTip.setXY(tempX,tempY+1);
                upTip.setVisible(true);
            }
        }
        if (tempY - 1>=0){
            if (arr[tempX][tempY-1] == 0) {
                downTip.setXY(tempX,tempY-1);
                downTip.setVisible(true);
            }
        }
    }

    private void alreadySelected(PackActor target) {
        PackActor last = currentSelect.getLast();
        int lastX = last.getTempX();
        int lastY = last.getTempY();
        int targetX = target.getTempX();
        int targetY = target.getTempY();
        if(last.getOwer()!=target.getOwer()&&isValue(lastX,lastY,targetX,targetY)){
            //是否可以吃掉對方
            int num = target.getNum();
            if (target.getNum() == 10&& last.getNum()==1){

            }else if ((last.getNum() == 10&&num==1)||num>=last.getNum()){
                target.appear();
                removeTarget(target);
                last.setXY(targetX,targetY);
                arr[targetX][targetY] = arr[lastX][lastY];
                arr[lastX][lastY] = 0;
                last.setAnimalScale(1);
                resetTip(true);
                checkPassLevel();
                changePlayer();
                //判断是不是可以过关
                return;
            }
            //否則   將本次的加進去
        }
        last.setAnimalScale(1);
        if (currentPlay.OWNER != target.getOwer())return;
        currentSelect.add(target);
        target.setAnimalScale(1.1F);
    }


    public void removeTarget(PackActor actor){
        if (actor.getUseColor().equals(Color.RED)){
            redPackActors.removeValue(actor,true);
        }else {
            blackPackActors.removeValue(actor,true);
        }
    }

    private void checkPassLevel() {
        if (redPackActors.size<=0){
            updateListener.passLevelPass("black win",true);
            Constant.multServer.closed();
            Constant.multClient.closed();
        }else if (blackPackActors.size<=0){
            updateListener.passLevelPass("red win",true);
            Constant.multServer.closed();
            Constant.multClient.closed();
        }
    }

    public void AI() {
        PackActor[] excete = ai.excete(all,currentPlay.color,arr);
        if (excete==null){
            HashMap<PackActor, Array<Vector2>> canMove = ai.getCanMove();
            Array<PackActor> actors = ai.getActorsAll();
            PackActor packActor = actors.get(0);
            Array<Vector2> vector2s = canMove.get(packActor);
            System.out.println("====>>>>>>"+packActor.toString());
            Vector2 vector2 = vector2s.get(0);
            changePosition(packActor,(int) vector2.x,(int) vector2.y);
        }else if(excete.length<=1){
            System.out.println(excete[0].toString());
            GamePanel.this.excute(excete[0]);
        }else if (excete.length==2){
            GamePanel.this.excute(excete[0]);
            GamePanel.this.excute(excete[1]);
            System.out.println(excete[0].toString()+">>>>>"+excete[1].toString());
        }
    }

    public enum GameStatus{
        running,win,IDE;
    }

    private GameStatus status = GameStatus.running;

    public GameStatus getStatus() {
        return status;
    }

    private void fanPacker(PackActor target) {
        target.open();
        // 默認playA是第一個節拍的
        if (playOne.OWNER == -1) {
            playOne.OWNER = target.getOwer();
            playOne.color = target.getUseColor();
            playTwo.OWNER = (short) (1-target.getOwer());
            playTwo.color = target.getOtherColor();
        }

        if (currentSelect.size()!=0){
            PackActor last = currentSelect.getLast();
            last.setAnimalScale(1);
            currentSelect.clear();
        }
        changePlayer();
        return;
    }

    private boolean isValue(int x,int y,int targetX,int targetY){
        if ((x+1==targetX)&&(y == targetY) ||
                (x - 1 == targetX)&&(y == targetY)||
                (x == targetX) &&(y== targetY+1||y == targetY-1)){
            return true;
        }
        return false;
    }

    private void resetTip(boolean isClear){
        if (isClear)  currentSelect.clear();
        upTip.setVisible(false);
        downTip.setVisible(false);
        leftTip.setVisible(false);
        rightTip.setVisible(false);
    }

    public void setListener(Listener listener) {
        this.updateListener = listener;

    }
    private Listener updateListener;

    public interface Listener{
        void updatePlayer(Player currentPlay);
        void passLevelPass(String text,boolean isClick);
        void tipRemove();
        void showIp(List<InetAddress> list);
        void touch(boolean b);
    }


    public interface Address{
        void address(List<InetAddress> inetAddresses);
    }
}
