package com.cs110.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Networking {
  public Networking() {
    // The following code loops through the available network interfaces
    // Keep in mind, there can be multiple interfaces per device, for example
    // one per NIC, one per active wireless and the loopback
    // In this case we only care about IPv4 address ( x.x.x.x format )
    List<String> addresses = new ArrayList<String>();
    try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        for(NetworkInterface ni : Collections.list(interfaces)){
            for(InetAddress address : Collections.list(ni.getInetAddresses()))
            {
                if(address instanceof Inet4Address){
                    addresses.add(address.getHostAddress());
                    Gdx.app.log("MyTag", address.getHostAddress());
                }
            }
        }
    } catch (SocketException e) {
        e.printStackTrace();
    }

    // Print the contents of our array to a string.  Yeah, should have used StringBuilder
    String ipAddress = new String("");
    for(String str:addresses)
    {
        ipAddress = ipAddress + str + "\n";
    }
  }
}
