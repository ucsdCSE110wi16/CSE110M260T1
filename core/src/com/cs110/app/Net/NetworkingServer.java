package com.cs110.app.Net;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
import com.cs110.app.Screens.GameScreen;
import com.cs110.app.Screens.ServerWaitingScreen;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;


import java.util.List;

public class NetworkingServer extends NetworkingBase{
    private static Server server;
    private static ServerWaitingScreen currentScreen;
    private static int udpPort = 27961, tcpPort = 27961;
    private float oldXCord = 0, oldYCord = 0;

    public NetworkingServer() throws Exception {
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.bind(tcpPort, udpPort);
        server.start();
        server.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("Received a connection from " + c.getRemoteAddressTCP().getHostString());
                connect = c;
                if(currentScreen != null){
                    currentScreen.connected();
                }
                gs.connect();
            }

            public void received(Connection c, Object p) {
                System.out.println("RECIEVED");
                if (p instanceof PacketMessage) {
                    PacketMessage pm = (PacketMessage) p;
                    processPacketMessage(pm);
                }
            }

            public void disconnected(Connection c) {
                //Tell gameScreen we disconnected
                gs.disconnect();
            }
        }));
    }

    public NetworkingServer(ServerWaitingScreen s) throws Exception {
        this();
        this.currentScreen = s;
    }

    public void stop() {
        server.stop();
    }
}