package kw.mulitplay.game.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import kw.mulitplay.game.Message;

public class MultClient {
    private Client client;
    public MultClient(String targetIp){
        client = new Client();
        client.start();
        Kryo kryo = client.getKryo();
        kryo.register(kryo.register(Message.class));
        try {
            client.connect(5000, targetIp, 2001,3001);
            initListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        client.addListener(new Listener() {
            @Override
            public void received (Connection connection, Object object) {
                System.out.println("client message:"+((Message)object).toString());
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
            }
        });
    }

    private void senMessage(){
        client.sendTCP(new Message());
    }
}
