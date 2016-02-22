package com.cs110.app.Net;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
import com.cs110.app.Screens.GameScreen;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;

/**
 * Created by OriGilad on 1/28/16.
 */
public class NetworkingClient extends Listener {
    private GameScreen gs;
    private Connection connect;
    static Client client;
    static String ip="localhost";//"128.54.240.57";
    static int tcpPort = 27961, udpPort = 27961;
    float oldXCord, oldYCord;

    static boolean messageReceived=false;

    public NetworkingClient(final GameScreen gs) throws Exception{
        this.gs = gs;
        final Player myPlayer = new Player(new Vector2(500,500),"Player1");
        final Player otherPlayer = new Player(new Vector2(700, 700), "Player2");

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

                if (p instanceof PacketMessage) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PacketMessage packet = (PacketMessage) p;
                    if (oldXCord != packet.xCord || oldYCord != packet.yCord) {
                        //System.out.println("Received News: X:" + oldXCord + "  Y:" + oldYCord);
                        System.out.println(otherPlayer.getPosition());
                    }
                    oldXCord = packet.xCord;
                    oldYCord = packet.yCord;
                    otherPlayer.setPosition(oldXCord, oldYCord);
                    otherPlayer.setRotation(packet.rotation);
                    if (packet.shotRad != null) {
                        System.out.println("ATTACK Recieved");
                        System.out.println("x" + packet.shotXCord);
                        System.out.println("y" + packet.shotYCord);
                        System.out.println("rad" + packet.shotRad);
                        gs.getWorld().addAttack(new Attack(packet.shotXCord, packet.shotYCord, packet.shotRad));
                    }


                    //packet.player = gs.getWorld().getPlayer();

                }
            }
        }));
        System.out.println("Client is now waiting");

    }


    public void update(){
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.xCord = gs.getWorld().getSelfPlayer().getPosition().x;
        packetMessage.yCord = gs.getWorld().getSelfPlayer().getPosition().y;
        packetMessage.rotation = gs.getWorld().getSelfPlayer().getRotation();
        if (gs.getWorld().attackOccured) {
            gs.getWorld().attackOccured = false;
            ArrayList<Attack> attacks = gs.getWorld().getAttacks();
            if (attacks.size() > 0) {
                packetMessage.shotXCord = gs.getWorld().getSelfPlayer().getPosition().x;
                packetMessage.shotYCord = gs.getWorld().getSelfPlayer().getPosition().y;
                packetMessage.shotRad = (float)attacks.get(0).rad;
                System.out.println("ATTACK SEND");
                System.out.println("shotXCord" + packetMessage.shotXCord);
                System.out.println("shotYCord" + packetMessage.shotYCord);
                System.out.println("pXCord" + gs.getWorld().getSelfPlayer().getPosition().x);
                System.out.println("pYCord" + gs.getWorld().getSelfPlayer().getPosition().y);
                System.out.println("rad" + packetMessage.shotRad);

            }
        }

        connect.sendUDP(packetMessage);
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
