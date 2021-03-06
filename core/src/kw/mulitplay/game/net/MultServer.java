package kw.mulitplay.game.net;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import kw.mulitplay.game.net.message.Message;
import kw.mulitplay.game.position.VectorPosition;

public class MultServer{
    private Server server;
    public Array<Connection> array = new Array<>();
    public MultServer(){
        server = new Server();
        server.start();
        Kryo kryo = server.getKryo();
        kryo.register(int[][].class);
        kryo.register(int[].class);
        kryo.register(Message.class);
        kryo.register(VectorPosition.class);
        try {
            server.bind(7001,7002);
            initListener();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initListener() {
        server.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);
                System.out.println("=======>>> received");
                if (object instanceof Message) {
                    System.out.println("====>>>>>server play!");
                    Message message = (Message) object;
                    listener.play(message);
                }
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                System.out.println("======>>>>connect");
                array.add(connection);
                listener.sendGameData();
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("=======>>> disconnection");
                super.disconnected(connection);
                array.removeValue(connection,true);
            }
        });
    }

    public void sendMessage(Message action){
        for (Connection connection : array) {
            connection.sendTCP(action);
            System.out.println(action.toString()+"===>发送消息！！！！！>"+action.getName());
        }
    }

    private NetListener listener;

    public void setListener(NetListener listener) {
        this.listener = listener;
    }

    public void closed(){
        server.close();
    }
}
