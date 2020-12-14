package kw.mulitplay.game.net;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import kw.mulitplay.game.Message;
import kw.mulitplay.game.position.VectorPosition;

public class MultClient {
    private Client client;
    public MultClient(String targetIp){
        client = new Client();
        client.start();
        Kryo kryo = client.getKryo();
        kryo.register(int[][].class);
        kryo.register(int[].class);
        kryo.register(Message.class);
        kryo.register(VectorPosition.class);

        try {
            client.connect(5000, targetIp, 7001,7002);
            initListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Array<Connection> array = new Array<>();
    private void initListener() {
        client.addListener(new Listener() {
            @Override
            public void received (Connection connection, Object object) {
                if (object instanceof Message) {
                    System.out.println("client message:"+((Message)object).toString());
                    listener.action((Message)object);
                }
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

    public void senMessage(Message message){
        for (Connection connection : array) {
            connection.sendTCP(message);
            System.out.println(message.getPosition().getX()+"===="+message.getPosition().getX());
        }
    }

    private NetListener listener;

    public void setListener(NetListener listener) {
        this.listener = listener;
    }
}
