package kw.mulitplay.game.ai;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Set;

import kw.mulitplay.game.actor.PackActor;
import kw.mulitplay.game.constant.Constant;

public class ComputateAI {
    // 有没有翻起的牌
    private Array<PackActor> list;
    private int status;
    private Array<PackActor> actorsTemp;
    public ComputateAI(){

    }

    public PackActor[] excete(Array<PackActor> actors){
        this.actorsTemp = actors;
        //找出符合当前用户的牌
        this.list = select(actors);
        PackActor actor = null;
        HashMap<PackActor, Array<PackActor>> meKill = canKill(list);
        HashMap<PackActor, Array<PackActor>> killMe = canKillMe(list);
        if (isAlreadyFan().size>0){
            if (killMe.size()>0){
                //kill me ，need add select
                int random = getRandom(killMe.size());
                PackActor actor1 = canKillArray.get(random);
                Array<PackActor> packActorArray = killMe.get(actor1);
                PackActor actor2 = packActorArray.get(getRandom(packActorArray.size));
                System.out.println("AI---- test kill!!!");
                return new PackActor[]{actor1,actor2};
            }else if (meKill.size()>0){
                //i kil
                int random = getRandom(killMe.size());
                PackActor actor1 = canKillArray.get(random);
                Array<PackActor> packActorArray = killMe.get(actor1);
                PackActor actor2 = packActorArray.get(getRandom(packActorArray.size));
                System.out.println("AI---- test be killss!!!");
                return new PackActor[]{actor1,actor2};
            }else {
                System.out.println("AI--- test fan pai!!!");
                actor = fanpai();
                return new PackActor[]{actor};
            }
        }else {
            //随机翻拍
            System.out.println("AI--- test fan pai!!!");
            actor = fanpai();
            return new PackActor[]{actor};
        }
    }

    public PackActor fanpai(){
        Array<PackActor> actors = new Array<>();
        System.out.println("===========");
        for (PackActor actor : list) {
            if (actor.getCurrentStatus()!= Constant.ZHENGMIAN) {
                actors.add(actor);
                System.out.println(actor.getCurrentStatus()+"===="+actor.getNum());
            }
        }
        System.out.println("===========");

        try{
            int random = getRandom(actors.size);
            PackActor actor = actors.get(random);
            System.out.println(actor.getNum()+"--------------------------");
            return actor;
        }catch (Exception e){
            System.out.println("=============");
        }
        return null;
    }

    public int getRandom(int range){
        double random = Math.random();
        int v = (int)(random * range);
        return v;
    }


    public Array<PackActor> select(Array<PackActor> actors){
        Array<PackActor> actorsTemp = new Array<>();
        for (PackActor actor : actors) {
            if (actor.getOwer() == 1){
                actorsTemp.add(actor);
            }
        }
        return actorsTemp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Array<PackActor> isAlreadyFan(){
        Array<PackActor> fan = new Array<>();
        for (PackActor actor : list) {
            if (actor.getCurrentStatus() == Constant.ZHENGMIAN){
                fan.add(actor);
            }
        }
        return fan;
    }

    private Array<PackActor> canKillArray;
    private Array<PackActor> canKillMeArray;
    // 有没有可以吃的牌
    public HashMap<PackActor,Array<PackActor>> canKill(Array<PackActor> actors){
        //我自己的牌
        canKillArray = new Array<>();
        HashMap<PackActor,Array<PackActor>> hashMap = new HashMap<>();
        for (int i = 0; i < actors.size; i++) {
            PackActor actor = actors.get(i);
            Array<PackActor> arrayActor = getArrayActor(actor);
            if (arrayActor.size>0){
                Array<PackActor> packActorArray = new Array<>();
                //可以进行kill
                for (PackActor packActor : actorsTemp) {
                    if (kill(actor,packActor)) {
                        packActorArray.add(packActor);
                    }
                }
                if (packActorArray.size>0) {
                    hashMap.put(actor, packActorArray);
                    canKillArray.add(actor);
                }
            }
        }
        return hashMap;
    }
    // 有没有可以杀我的
    public HashMap<PackActor,Array<PackActor>> canKillMe(Array<PackActor> actors){
        //我自己的牌
        canKillMeArray = new Array<>();
        HashMap<PackActor,Array<PackActor>> hashMap = new HashMap<>();
        for (int i = 0; i < actors.size; i++) {
            //我的牌，可以想吃的
            PackActor actor = actors.get(i);
            Array<PackActor> arrayActor = getArrayActor(actor);
            if (arrayActor.size>0){
                Array<PackActor> packActorArray = new Array<>();
                //可以进行kill
                for (PackActor packActor : actorsTemp) {
                    if (kill(packActor,actor)) {
                        packActorArray.add(packActor);
                    }
                }
                if (packActorArray.size>0) {
                    hashMap.put(actor, packActorArray);
                    canKillArray.add(actor);
                }
            }
        }
        return hashMap;
    }


    public boolean kill(PackActor last,PackActor target){
        int num = target.getNum();
        if (target.getNum() == 10&& last.getNum()==1){
        }else if ((last.getNum() == 10&&num==1)||num>=last.getNum()){
            return true;
        }
        return false;
    }

    //移动之后会不会有危险

    //随机翻

    //根据位置获取元素
    public Array<PackActor> getArrayActor(PackActor actor){
        Array<PackActor> actors = new Array<>();
        for (PackActor actorTemp : list) {
            if (actorTemp.getStatus()){
                if (comparePosition(
                        actor.getTempX(),
                        actor.getTempY(),
                        actor.getTempX()-1,
                        actor.getTempY())) {
                    actors.add(actorTemp);
                }else if (comparePosition(
                        actor.getTempX(),
                        actor.getTempY(),
                        actor.getTempX()+1,
                        actor.getTempY())) {
                    actors.add(actorTemp);
                }else if (comparePosition(
                        actor.getTempX(),
                        actor.getTempY(),
                        actor.getTempX(),
                        actor.getTempY()-1)) {
                    actors.add(actorTemp);
                }else if (comparePosition(
                        actor.getTempX(),
                        actor.getTempY(),
                        actor.getTempX(),
                        actor.getTempY()+1)) {
                    actors.add(actorTemp);
                }
            }
        }
        return actors;
    }

    public PackActor getActor(int x,int y){
        for (PackActor actor : list) {
            if (actor.getStatus()&&comparePosition(x,y,actor.getTempX(),actor.getTempY())){
                return actor;
            }
        }
        return null;
    }

    public boolean comparePosition(int x,int y,int targetX,int targetY){
        if (x == targetX){
            if (y == targetY){
                return true;
            }
        }
        return false;
    }

//    canMove(){
//
//    }
}
