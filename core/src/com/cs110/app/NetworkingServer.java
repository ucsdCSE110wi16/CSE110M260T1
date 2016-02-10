package com.cs110.app;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

import java.util.Date;
import java.util.Scanner;

public class NetworkingServer extends Listener{
    private static Server server;
    private static int udpPort = 27960, tcpPort = 27960;

    public NetworkingServer() throws Exception{
        System.out.println("Creating the server ... ");
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.bind(tcpPort, udpPort);
        server.start();
        server.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("REceived a connection from " + c.getRemoteAddressTCP().getHostString());
                PacketMessage packetMessage = new PacketMessage();

                c.sendTCP(packetMessage);

            }

            public void received(Connection c, Object p) {
                if(p instanceof PacketMessage){
                    PacketMessage pm = (PacketMessage)p;
                }
                System.out.println("PacketClass " + p.getClass().toString());
                Scanner kboard = new Scanner(System.in);
                PacketMessage packetMessage = new PacketMessage();
                c.sendUDP(packetMessage);
            }

            public void disconnected(Connection c) {
                System.out.println("disconnected");
            }
        }));

    }

    public static void main(String[] args) throws Exception {

        NetworkingServer s = new NetworkingServer();
        System.out.println("Server running");
    }



}