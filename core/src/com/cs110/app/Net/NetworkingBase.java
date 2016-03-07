package com.cs110.app.Net;

import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
//import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.cs110.app.Screens.GameScreen;

import java.util.List;

/**
 * Created by marcof on 3/3/16.
 */
public abstract class NetworkingBase extends Listener {
    protected GameScreen gs;
    protected Connection connect;
    Player myPlayer;
    Player otherPlayer;
    protected int tick = 0;
    protected int lastTick = -1;
    float oldXCord, oldYCord;
    protected static int HIGHEST_TICK = 10000;
    protected String type = "server";

    public abstract void stop();

    public boolean updateTick(PacketMessage pm) {
        int packetTick = pm.tick;
        if ((packetTick > lastTick && (packetTick - lastTick) < 100) || (packetTick < lastTick && (lastTick - packetTick) > 5000)||lastTick == -1) {
            System.out.println("lastTick: "+ lastTick);
            System.out.println("packetTick: "+packetTick);
            lastTick = packetTick;
            return true;
        }
        else {
            System.out.println("lastTick: "+ lastTick);
            System.out.println("packetTick: "+packetTick);
            System.out.println("PACKET FAILURE NOT NEW PACKET");
            return false;
        }
    }

    public void processPacketMessage(PacketMessage packet) {
        System.out.println("processPacketMessage");
        //MOVEMENT
        if( this.gs == null){
            return;
        }
        if (updateTick(packet)) {
            oldXCord = packet.xCord;
            oldYCord = packet.yCord;
            otherPlayer.setPosition(oldXCord, oldYCord);
            otherPlayer.setRotation(packet.rotation);
        }
        // ATTACKS
        if (packet.attackType != null) {
            if (packet.attackType == 2) {
                new Attack(oldXCord, oldYCord, packet.rotation, gs.getWorld(), packet.attackType, type);
                new Attack(oldXCord, oldYCord, packet.rotation + Math.PI / 8, gs.getWorld(), packet.attackType, type);
                new Attack(oldXCord, oldYCord, packet.rotation - Math.PI / 8, gs.getWorld(), packet.attackType, type);
                new Attack(oldXCord, oldYCord, packet.rotation + Math.PI / 4, gs.getWorld(), packet.attackType, type);
            } else {
                Attack t = new Attack(oldXCord, oldYCord, packet.rotation, gs.getWorld(), packet.attackType, type);
            }
        }
    }


    public void startGame(GameScreen gs) {
        this.gs = gs;
        if(this instanceof NetworkingServer) {
            myPlayer = new Player(new Vector2(700, 700), "Player1");
            otherPlayer = new Player(new Vector2(500, 500), "Player2");
        }
        else if(this instanceof NetworkingClient){
            myPlayer = new Player(new Vector2(500, 500), "Player1");
            otherPlayer = new Player(new Vector2(700, 700), "Player2");
        }
        otherPlayer.setWorld(gs.getWorld());
        gs.getWorld().setSelfPlayer(myPlayer);
        gs.getWorld().setOtherPlayer(otherPlayer);
    }

    public void update()
    {
        if(connect != null && this.gs != null){
            System.out.println("Connecting GS");
            System.out.println(gs.getWorld().getOtherPlayer().getPosition());
            PacketMessage packetMessage = new PacketMessage();
            packetMessage.xCord =  gs.getWorld().getSelfPlayer().getPosition().x;
            packetMessage.yCord =  gs.getWorld().getSelfPlayer().getPosition().y;
            packetMessage.rotation = gs.getWorld().getSelfPlayer().getRotation();
            packetMessage.tick = tick;
            tick++;
            if(tick > HIGHEST_TICK){
                tick = 0;
            }
            if (gs.getWorld().attackOccured) {
                gs.getWorld().attackOccured = false;
                List<Attack> attacks = gs.getWorld().getAttacks();
                for(int i = 0; i<attacks.size(); i++){
                    Attack shot = attacks.get(i);
                    if(shot.drawn == false && (shot.getSenderID() == null || !(shot.getSenderID().equals(type)))) {
                        packetMessage.attackType = attacks.get(i).getType();
                        connect.sendTCP(packetMessage);
                        packetMessage.attackType = null;
                        shot.drawn = true;
                    }
                }
            }
            else{
                System.out.println("Sending");
                System.out.println("Connect obj: " + connect.getRemoteAddressUDP());
                connect.sendTCP(packetMessage);
            }
        }
    }

}
