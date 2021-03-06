package kw.mulitplay.game.asset;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import kw.mulitplay.game.constant.Constant;

public class Resource {
    public static TextureAtlas atlas;
    public HashMap<String, Integer> hashMap;
    public HashMap<Integer,String> animalData;
    public FontResource fontResource;
    public Resource() {
        fontResource = new FontResource();
        Constant.assetManager.load("image/main.atlas",TextureAtlas.class);
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

    public void getAtlas(){
        fontResource.getAtlas();
        atlas = Constant.assetManager.get("image/main.atlas");
    }
}
