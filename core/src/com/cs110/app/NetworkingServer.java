package com.cs110.app;

import com.cs110.app.Screens.GameScreen;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

import java.util.Date;
import java.util.Scanner;

public class NetworkingServer extends Listener{
    private static Server server;
    private static int udpPort = 27961, tcpPort = 27961;
    private float oldXCord = 0, oldYCord = 0;
    public NetworkingServer(final GameScreen gs) throws Exception{
        System.out.println("Creating the server ... ");
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.bind(tcpPort, udpPort);
        server.start();
        server.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("Received a connection from " + c.getRemoteAddressTCP().getHostString());
                PacketMessage packetMessage = new PacketMessage();
                packetMessage.xCord = (int) gs.getWorld().getSelfPlayer().getPosition().x;
                packetMessage.yCord = (int) gs.getWorld().getSelfPlayer().getPosition().y;

                c.sendUDP(packetMessage);

            }


                public void received(Connection c, Object p) {

                    if (p instanceof PacketMessage) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        PacketMessage pm = (PacketMessage) p;
                        if (oldXCord != pm.xCord || oldYCord != pm.yCord) {
                            System.out.println("Received News: X:" + oldXCord + "  Y:" + oldYCord);
                        }
                        oldXCord = pm.xCord;
                        oldYCord = pm.yCord;

//                    System.out.println("Received message as Server: " + pm.message);
                    }

                    PacketMessage packetMessage = new PacketMessage();
                    packetMessage.xCord =  gs.getWorld().getSelfPlayer().getPosition().x;
                    packetMessage.yCord =  gs.getWorld().getSelfPlayer().getPosition().y;
//
//                Scanner kboard = new Scanner(System.in);
//                packetMessage.message = kboard.nextLine();
                    c.sendUDP(packetMessage);
                }

            public void disconnected(Connection c) {
                System.out.println("disconnected");
            }
        }));

    }

//    public static void main(String[] args) throws Exception {
//
//        NetworkingServer s = new NetworkingServer();
//        System.out.println("Server running");
//    }
//


}