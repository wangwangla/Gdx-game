package kw.mulitplay.game.net.message;

import kw.mulitplay.game.position.VectorPosition;
public class Message {
    private int arr[][];
    private String name;
    private VectorPosition position;
    private String type;
    public Message(){}

    public Message(int[][] arr) {
        this.arr = arr;
    }

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VectorPosition getPosition() {
        return position;
    }

    public void setPosition(VectorPosition position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
