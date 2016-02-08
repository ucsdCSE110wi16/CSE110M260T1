package com.cs110.app;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.Scanner;

/**
 * Created by OriGilad on 1/28/16.
 */
public class NetworkingClient extends Listener {
    static Client client;
    static String ip="137.110.91.239";
    static int tcpPort = 27960, udpPort = 27960;

    static boolean messageReceived=false;

    public NetworkingClient() throws Exception{
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(new ThreadedListener(new Listener() {
            public void received(Connection c, Object p) {
                if (p instanceof PacketMessage) {
                    PacketMessage packet = (PacketMessage) p;
                    System.out.println("Message received: " + packet.message);
                    PacketMessage packetMessage = new PacketMessage();
                    packetMessage.message = "hello";
                    c.sendUDP(packetMessage);
                }
            }
        }));
        System.out.println("Client is now waiting");
        while(true){

        }
    }

    public static void main(String args[]) throws Exception{

        NetworkingClient c = new NetworkingClient();




    }



}
