package kw.mulitplay.game;

import kw.mulitplay.game.position.VectorPosition;
public class Message {
    private int arr[][];
    private int id;
    private String name;
    private VectorPosition position;

    public Message(){}

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Message(int[][] arr) {
        this.arr = arr;
    }


}
