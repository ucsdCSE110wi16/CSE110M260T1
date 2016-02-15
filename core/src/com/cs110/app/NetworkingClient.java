package com.cs110.app;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.*;
import com.cs110.app.Model.Player;
import com.cs110.app.Screens.GameScreen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.Scanner;
import java.util.Vector;

/**
 * Created by OriGilad on 1/28/16.
 */
public class NetworkingClient extends Listener {
    static Client client;
    static String ip="localhost";//"128.54.240.57";
    static int tcpPort = 27961, udpPort = 27961;
    float oldXCord, oldYCord;

    static boolean messageReceived=false;

    public NetworkingClient(final GameScreen gs) throws Exception{
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        final Player otherPlayer = new Player(new Vector2(5, 10), "Player2");
        gs.getWorld().setOtherPlayer(otherPlayer);

        client.addListener(new ThreadedListener(new Listener() {
            public void received(Connection c, Object p) {
                if (p instanceof PacketMessage) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PacketMessage packet = (PacketMessage) p;
                    if(oldXCord != packet.xCord || oldYCord != packet.yCord) {
                        System.out.println("Received News: X:" + oldXCord + "  Y:" + oldYCord);
                    }
                    oldXCord = packet.xCord;
                    oldYCord = packet.yCord;
                    otherPlayer.setPosition(oldXCord, oldYCord);


                    //packet.player = gs.getWorld().getPlayer();
                    PacketMessage packetMessage = new PacketMessage();
                    packetMessage.xCord = gs.getWorld().getSelfPlayer().getPosition().x;
                    packetMessage.yCord = gs.getWorld().getSelfPlayer().getPosition().y;

                    c.sendUDP(packetMessage);
                }
            }
        }));
        System.out.println("Client is now waiting");

    }


    public void update(){

    }
//    public static void main(String args[]) throws Exception{
//
//        NetworkingClient c = new NetworkingClient();
//
//
//
//
//    }



}
