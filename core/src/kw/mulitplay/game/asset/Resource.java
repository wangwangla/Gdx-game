package kw.mulitplay.game.asset;

import java.util.HashMap;

public class Resource {

    public HashMap<String, Integer> hashMap;
    public HashMap<Integer,String> animalData;
    public Resource() {
        hashMap = new HashMap();
        hashMap.put("象", 1);
        hashMap.put("狮", 2);
        hashMap.put("猪", 3);
        hashMap.put("熊", 4);
        hashMap.put("虎", 5);
        hashMap.put("豹", 6);
        hashMap.put("狗", 7);
        hashMap.put("狼", 8);
        hashMap.put("猫", 9);
        hashMap.put("鼠", 10);
        animalData = new HashMap<>();
        animalData.put(1, "象");
        animalData.put(2, "狮");
        animalData.put(3, "猪");
        animalData.put(4, "熊");
        animalData.put(5, "虎");
        animalData.put(6, "豹");
        animalData.put(7, "狗");
        animalData.put(8, "狼");
        animalData.put(9, "猫");
        animalData.put(10, "鼠");
    }

}
