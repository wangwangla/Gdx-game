package kw.mulitplay.game.screen.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import kw.mulitplay.game.Constant;

public class GameData{
    private String currentPlay;
    private int arr[][] = new int[4][5];

    public GameData(){
        currentPlay = "play one";
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

    public int[][] getArr() {
        return arr;
    }

    public HashMap<Integer, String> getAnimalData() {
        return Constant.resource.animalData;
    }

    public String getCurrentPlay() {
        return currentPlay;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }
}
