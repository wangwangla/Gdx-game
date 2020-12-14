package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Color;

public class Player {
    public short OWNER = -1;
    public Color color;
    public String name;
    public Player(String name){
        this.name = name;
    }
}
