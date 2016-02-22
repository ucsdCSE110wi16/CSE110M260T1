package com.cs110.app.Net;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Player;
import com.cs110.app.Screens.GameScreen;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

public class NetworkingServer extends Listener{
    private static Server server;
    private static int udpPort = 27961, tcpPort = 27961;
    private float oldXCord = 0, oldYCord = 0;
    public NetworkingServer(final GameScreen gs) throws Exception{
        final Player myPlayer = new Player(new Vector2(700,700),"Player1");
        final Player otherPlayer = new Player(new Vector2(500, 500), "Player2");
        gs.getWorld().setSelfPlayer(myPlayer);
        gs.getWorld().setOtherPlayer(otherPlayer);
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
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        PacketMessage pm = (PacketMessage) p;
                        if (oldXCord != pm.xCord || oldYCord != pm.yCord) {
                            System.out.println("Received News: X:" + oldXCord + "  Y:" + oldYCord);
                        }
                        oldXCord = pm.xCord;
                        oldYCord = pm.yCord;
                        otherPlayer.setPosition(oldXCord, oldYCord);
                        otherPlayer.setRotation(pm.rotation);


//                    System.out.println("Received message as Server: " + pm.message);
                    }

                    PacketMessage packetMessage = new PacketMessage();
                    packetMessage.xCord =  gs.getWorld().getSelfPlayer().getPosition().x;
                    packetMessage.yCord =  gs.getWorld().getSelfPlayer().getPosition().y;
                    packetMessage.rotation = gs.getWorld().getSelfPlayer().getRotation();

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