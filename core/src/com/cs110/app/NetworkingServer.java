package com.cs110.app;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

import java.util.Date;

public class NetworkingServer extends Listener{
    private static Server server;
    private static int udpPort = 27960, tcpPort = 27960;

    public NetworkingServer(){

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Creating the server ... ");
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.bind(tcpPort, udpPort);
        server.start();

        server.addListener(new NetworkingServer());
        System.out.println("Server running");
    }

    public void connected(Connection c){
        System.out.println("REceived a connection from " + c.getRemoteAddressTCP().getHostString());
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.message = "Hello friend! The time is " + new Date().toString();

        c.sendTCP(packetMessage);

    }

    public void received(Connection c, Object p){
        System.out.println("Received message at " + new Date().toString());
    }

    public void disconnected(Connection c){
        System.out.println("disconnected");
    }

}