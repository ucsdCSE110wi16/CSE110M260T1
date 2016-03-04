package com.cs110.app.Net;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
import com.cs110.app.Screens.GameScreen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OriGilad on 1/28/16.
 */
public class NetworkingClient extends NetworkingBase {
    static Client client;
    static String ip;
    static int tcpPort = 27961, udpPort = 27961;
    float oldXCord, oldYCord;
    static boolean messageReceived=false;

    public NetworkingClient(final GameScreen gs) throws Exception {
        this(gs, "localhost");

    }
    public NetworkingClient(final GameScreen gs, String ip) throws Exception {
        this.ip = ip;
        this.gs = gs;
        type = "client";
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c) {
                connect = c;
            }

            public void received(Connection c, Object p) {
                connect = c;
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

    public void stop() {
        client.stop();
    }
}
