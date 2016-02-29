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
public class NetworkingClient extends Listener {
    private GameScreen gs;
    private Connection connect;
    static Client client;

    static String ip="localhost";//"137.110.91.137";
    static int tcpPort = 27961, udpPort = 27961;
    float oldXCord, oldYCord;

    static boolean messageReceived=false;

    public NetworkingClient(final GameScreen gs) throws Exception{
        this.gs = gs;
        final Player myPlayer = new Player(new Vector2(500,500),"Player1");
        final Player otherPlayer = new Player(new Vector2(700, 700), "Player2");
        otherPlayer.setWorld(gs.getWorld());
        gs.getWorld().setSelfPlayer(myPlayer);
        gs.getWorld().setOtherPlayer(otherPlayer);
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);


        client.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c){
                System.out.println("Client Connected");
                connect = c;
            }
            public void received(Connection c, Object p) {
                connect = c;
                if (p instanceof PacketMessage) {
                    //System.out.println("Packet of PacketMessage");


                    PacketMessage packet = (PacketMessage) p;
                    if(oldXCord != packet.xCord || oldYCord != packet.yCord) {
//                        System.out.println("Received News: X:" + oldXCord + "  Y:" + oldYCord);
//                        System.out.println(otherPlayer.getPosition());

                    }
                    oldXCord = packet.xCord;
                    oldYCord = packet.yCord;
                    otherPlayer.setPosition(oldXCord, oldYCord);
                    otherPlayer.setRotation(packet.rotation);
                    /*if (packet.shotRad != null) {
                        System.out.println("ATTACK Recieved");
                        System.out.println("x" + packet.shotXCord);
                        System.out.println("y" + packet.shotYCord);
                        System.out.println("rad" + packet.shotRad);
                        gs.getWorld().addAttack(new Attack(packet.shotXCord, packet.shotYCord, packet.shotRad));
                    }*/

                    //packet.player = gs.getWorld().getPlayer();

                }
            }
        }));
        System.out.println("Client is now waiting");

    }


    public void update() {
        if (client.isConnected() && connect != null) {
            PacketMessage packetMessage = new PacketMessage();
            packetMessage.xCord = gs.getWorld().getSelfPlayer().getPosition().x;
            packetMessage.yCord = gs.getWorld().getSelfPlayer().getPosition().y;
            packetMessage.rotation = gs.getWorld().getSelfPlayer().getRotation();
            if (gs.getWorld().attackOccured) {
                gs.getWorld().attackOccured = false;
                List<Attack> attacks = gs.getWorld().getAttacks();
                for(int i = 0; i<attacks.size(); i++){
                    packetMessage.attackType = attacks.get(i).getType();
                    connect.sendUDP(packetMessage);
                }

            }
            //System.out.println("sending to server");
            connect.sendUDP(packetMessage);
        }
    }
    //}
//    public static void main(String args[]) throws Exception{
//
//        NetworkingClient c = new NetworkingClient();
//
//
//
//
//    }



}
