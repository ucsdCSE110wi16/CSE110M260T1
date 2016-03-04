package com.cs110.app.Net;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
import com.cs110.app.Screens.GameScreen;
import com.cs110.app.Screens.ServerWaitingScreen;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;


import java.util.List;

public class NetworkingServer extends Listener implements NetworkingBase{
    private static Server server;
    private static GameScreen gs;
    Player myPlayer;
    Player otherPlayer;
    private static ServerWaitingScreen currentScreen;

    private static int udpPort = 27961, tcpPort = 27961;
    private float oldXCord = 0, oldYCord = 0;
    private Connection connect;

    public NetworkingServer(ServerWaitingScreen s) throws Exception {
        this.currentScreen = s;
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.bind(tcpPort, udpPort);
        server.start();

        server.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("Received a connection from " + c.getRemoteAddressTCP().getHostString());
                connect = c;
                currentScreen.connected();
            }


            public void received(Connection c, Object p) {
                if (p instanceof PacketMessage) {
                    PacketMessage pm = (PacketMessage) p;
                    oldXCord = pm.xCord;
                    oldYCord = pm.yCord;
                    otherPlayer.setPosition(oldXCord, oldYCord);
                    otherPlayer.setRotation(pm.rotation);
                    if (pm.attackType != null) {
                        if (pm.attackType == 2) {
                            Attack t1 = new Attack(oldXCord, oldYCord, pm.rotation, gs.getWorld(), pm.attackType, "client");
                            Attack t2 = new Attack(oldXCord, oldYCord, pm.rotation + Math.PI / 8, gs.getWorld(), pm.attackType, "client");
                            Attack t3 = new Attack(oldXCord, oldYCord, pm.rotation - Math.PI / 8, gs.getWorld(), pm.attackType, "client");
                            Attack t4 = new Attack(oldXCord, oldYCord, pm.rotation + Math.PI / 4, gs.getWorld(), pm.attackType, "client");
                        } else {
                            Attack t = new Attack(oldXCord, oldYCord, pm.rotation, gs.getWorld(), pm.attackType, "client");
                        }
                    }
                }
            }

            public void disconnected(Connection c) {
                System.out.println("disconnected");
                gs.disconnect();
            }
        }));
    }

    public void startGame(GameScreen gs) {
        this.gs = gs;
        myPlayer = new Player(new Vector2(700,700),"Player1");
        otherPlayer = new Player(new Vector2(500, 500), "Player2");
        otherPlayer.setWorld(gs.getWorld());
        gs.getWorld().setSelfPlayer(myPlayer);
        gs.getWorld().setOtherPlayer(otherPlayer);
        System.out.println("Starting the game");
    }

    public void update(){
        if(connect != null){
            PacketMessage packetMessage = new PacketMessage();
            packetMessage.xCord =  gs.getWorld().getSelfPlayer().getPosition().x;
            packetMessage.yCord =  gs.getWorld().getSelfPlayer().getPosition().y;
            packetMessage.rotation = gs.getWorld().getSelfPlayer().getRotation();
            if (gs.getWorld().attackOccured) {
                gs.getWorld().attackOccured = false;
                List<Attack> attacks = gs.getWorld().getAttacks();
                for(int i = 0; i<attacks.size(); i++){
                    Attack shot = attacks.get(i);
                    if(shot.drawn == false && (shot.getSenderID() == null ||!shot.getSenderID().equals("client"))) {
                        packetMessage.attackType = attacks.get(i).getType();
                        connect.sendUDP(packetMessage);
                        packetMessage.attackType = null;
                        shot.drawn = true;
                    }
                }


                }
            else{
                connect.sendUDP(packetMessage);
            }
        }
    }
    public void stop() {
        server.stop();
    }

//    public static void main(String[] args) throws Exception {
//
//        NetworkingServer s = new NetworkingServer();
//        System.out.println("Server running");
//    }

//


}