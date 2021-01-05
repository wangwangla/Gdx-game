package kw.mulitplay.game.net;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import kw.mulitplay.game.net.message.Message;
import kw.mulitplay.game.position.VectorPosition;
import kw.mulitplay.game.screen.panel.GamePanel;

public class MultClient {
    private Client client;
    public MultClient(){

    }

    public void init(){
        client = new Client();
        client.start();
        Kryo kryo = client.getKryo();
        kryo.register(int[][].class);
        kryo.register(int[].class);
        kryo.register(Message.class);
        kryo.register(VectorPosition.class);
        try {
            List<InetAddress> inetAddresses = client.discoverHosts(7002, 10000);
            address.address(inetAddresses);
//            client.connect(5000, inetAddress, 7001,7002);
//            initListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(String str){
        try {
            client.connect(5000, str, 7001,7002);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initListener();
    }

    private Array<Connection> array = new Array<>();
    private void initListener() {
        client.addListener(new Listener() {
            @Override
            public void received (Connection connection, Object object) {
                System.out.println("===========>>>> received");
                if (object instanceof Message) {
                    Message message = (Message) object;
                    if (message.getType().equals("START")){
                        listener.start(message);
                    }else {
                        listener.play(message);
                    }
                }
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                System.out.println("===========>>>>>> connect");
                array.add(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                array.removeValue(connection,true);
                System.out.println("=========>>>>>>>disconnect");
            }
        });
    }

    public void senMessage(Message message){
        for (Connection connection : array) {
            connection.sendTCP(message);
            System.out.println(message.getName());
        }
    }

    private NetListener listener;

    public void setListener(NetListener listener) {
        this.listener = listener;
    }

    public void closed(){
        client.close();
    }

    private GamePanel.Address address ;

    public void setAddress(GamePanel.Address address) {
        this.address = address;
    }

}
