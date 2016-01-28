package com.cs110.app;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by OriGilad on 1/28/16.
 */
public class NetworkingClient extends Listener {
    static Client client;
    static String ip="localhost";
    static int tcpPort = 27960, udpPort = 27960;

    static boolean messageReceived=false;

    public static void main(String args[]) throws Exception{
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(new NetworkingClient());

        System.out.println("Client is now waiting");

        while(!messageReceived){
            Thread.sleep(1000);
        }
    }

    public void received(Connection c, Object p){
        if(p instanceof PacketMessage){
            PacketMessage packet = (PacketMessage)p;
            System.out.println("Message received: " + packet.message);
            messageReceived = true;
        }
    }

}
