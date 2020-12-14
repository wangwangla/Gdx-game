package kw.mulitplay.game.net;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import kw.mulitplay.game.Message;

public class MultServer {
    private Server server;
    public Array<Connection> array = new Array<>();
    public void createServer(){
        server = new Server();
        server.start();
        Kryo kryo = server.getKryo();
        kryo.register(Message.class);
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
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                array.add(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                array.removeValue(connection,true);
            }
        });
    }

    public void sendMessage(){
        for (Connection connection : array) {
            connection.sendTCP("cc");
        }
    }
}
