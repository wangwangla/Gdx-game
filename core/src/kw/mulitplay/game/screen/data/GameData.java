package kw.mulitplay.game.screen.data;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import kw.mulitplay.game.actor.PackActor;

public class GameData{
    private String currentPlay;
    private int arr[][] = new int[4][5];
    public HashMap<String, Integer> hashMap;
    public HashMap<Integer,String> animalData;
    public GameData(){
        currentPlay = "play one";
        hashMap = new HashMap();
        hashMap.put("象",1);
        hashMap.put("狮",2);
        hashMap.put("猪",3);
        hashMap.put("熊",4);
        hashMap.put("虎",5);
        hashMap.put("豹",6);
        hashMap.put("狗",7);
        hashMap.put("狼",8);
        hashMap.put("猫",9);
        hashMap.put("鼠",10);
        animalData = new HashMap<>();
        animalData.put(1,"象");
        animalData.put(2,"狮");
        animalData.put(3,"猪");
        animalData.put(4,"熊");
        animalData.put(5,"虎");
        animalData.put(6,"豹");
        animalData.put(7,"狗");
        animalData.put(8,"狼");
        animalData.put(9,"猫");
        animalData.put(10,"鼠");
    }

    public int[][] shuffle(){
        ArrayList<Integer> list = new ArrayList();
        for (int i = 1; i <= 20; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = list.get(index);
                index++;
            }
        }
        return arr;
    }

    public HashMap<Integer, String> getAnimalData() {
        return animalData;
    }

    public String getCurrentPlay() {
        return currentPlay;
    }
}
