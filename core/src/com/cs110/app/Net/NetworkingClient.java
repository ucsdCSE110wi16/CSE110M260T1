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
public class NetworkingClient extends Listener implements NetworkingBase {
    private GameScreen gs;
    private Connection connect;
    static Client client;
    Player myPlayer;
    Player otherPlayer;
    private int tick = 0;
    private int networkTick = 0;
    static String ip;
    static int tcpPort = 27961, udpPort = 27961;
    float oldXCord, oldYCord;
    static boolean messageReceived=false;

    public NetworkingClient(final GameScreen gs) throws Exception {
        this(gs, "localhost");

    }
    public NetworkingClient(final GameScreen gs, String ip) throws Exception {
        this.ip = ip;
        this.gs = gs;
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("Client Connected");
                connect = c;
            }
            public void received(Connection c, Object p) {
                connect = c;
                if (p instanceof PacketMessage) {
                    PacketMessage packet = (PacketMessage) p;
                    if(networkTick == 0){
                        networkTick = packet.tick;
                    }
                    else if(packet.tick > networkTick){
                        networkTick = packet.tick;
                    }
                    else {
                        return;
                    }
                    if (oldXCord != packet.xCord || oldYCord != packet.yCord) {}
                    oldXCord = packet.xCord;
                    oldYCord = packet.yCord;
                    otherPlayer.setPosition(oldXCord, oldYCord);
                    otherPlayer.setRotation(packet.rotation);
                    if (packet.attackType != null) {
                        if (packet.attackType == 2) {
                            new Attack(oldXCord, oldYCord, packet.rotation, gs.getWorld(), packet.attackType, "server");
                            new Attack(oldXCord, oldYCord, packet.rotation + Math.PI / 8, gs.getWorld(), packet.attackType, "server");
                            new Attack(oldXCord, oldYCord, packet.rotation - Math.PI / 8, gs.getWorld(), packet.attackType, "server");
                            new Attack(oldXCord, oldYCord, packet.rotation + Math.PI / 4, gs.getWorld(), packet.attackType, "server");
                        } else {
                            Attack t = new Attack(oldXCord, oldYCord, packet.rotation, gs.getWorld(), packet.attackType, "server");
                        }
                    }

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
            packetMessage.tick = tick;
            tick++;
            if (gs.getWorld().attackOccured) {
                gs.getWorld().attackOccured = false;
                List<Attack> attacks = gs.getWorld().getAttacks();
                for(int i = 0; i<attacks.size(); i++){
                    Attack shot = attacks.get(i);
                    if(shot.drawn == false && (shot.getSenderID() == null || !shot.getSenderID().equals("server"))) {
                        packetMessage.attackType = attacks.get(i).getType();
                        connect.sendUDP(packetMessage);
                        packetMessage.attackType = null;
                        shot.drawn = true;
                    }
                }

            }
            else {
                connect.sendUDP(packetMessage);
            }
        }
    }

    public void startGame(GameScreen gs) {
        System.out.println("STARTED GAME");
        myPlayer = new Player(new Vector2(500,500),"Player1");
        otherPlayer = new Player(new Vector2(700, 700), "Player2");
        otherPlayer.setWorld(gs.getWorld());
        gs.getWorld().setSelfPlayer(myPlayer);
        gs.getWorld().setOtherPlayer(otherPlayer);
    }

    public void stop() {
        client.stop();
    }


}
