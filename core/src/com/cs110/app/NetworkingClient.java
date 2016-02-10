package com.cs110.app;

import com.cs110.app.Model.*;
import com.cs110.app.Screens.GameScreen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.Scanner;

/**
 * Created by OriGilad on 1/28/16.
 */
public class NetworkingClient extends Listener {
    static Client client;
    static String ip="128.54.240.57";
    static int tcpPort = 27961, udpPort = 27961;
    int oldXCord, oldYCord;

    static boolean messageReceived=false;

    public NetworkingClient(final GameScreen gs) throws Exception{
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(new ThreadedListener(new Listener() {
            public void received(Connection c, Object p) {
                if (p instanceof PacketMessage) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PacketMessage packet = (PacketMessage) p;
                    if(oldXCord != packet.xCord || oldYCord != packet.yCord){
                        System.out.println("Received News: X:" + oldXCord + "  Y:" + oldYCord);
                    }
                    oldXCord = packet.xCord;
                    oldYCord = packet.yCord;


                    //packet.player = gs.getWorld().getPlayer();
                    PacketMessage packetMessage = new PacketMessage();
                    packetMessage.xCord = (int)gs.getWorld().getPlayer().getPosition().x;
                    packetMessage.yCord = (int)gs.getWorld().getPlayer().getPosition().y;

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
