package com.cs110.app;
import com.badlogic.gdx.Game;
import com.cs110.app.Net.NetworkingBase;
import com.cs110.app.Net.NetworkingClient;
import com.cs110.app.Net.NetworkingServer;
import com.cs110.app.Screens.GameScreen;
import com.cs110.app.Screens.ScreenEnum;
import com.cs110.app.Screens.ScreenManager;
import com.cs110.app.Screens.ServerWaitingScreen;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
	// game allows for screens

	NetworkingBase network;
	public static boolean local;
	public static boolean gameOnly;

	public CS110App(int type) {
		if(type == 0){
			local = true;
		}
		else {
			local = false;
		}
		if(type== 1){
			CS110App.gameOnly = true;
		}
	}

	@Override
	public void create() {
		if(CS110App.gameOnly){
			ScreenManager.getInstance().initialize(this);
			ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 5);
			return;
		}
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, local);
	}


	public void setServer(ServerWaitingScreen s){
		try {
			network = new NetworkingServer(s);
		}
		catch(Exception e){}
	}



	public boolean setClient(GameScreen s, String ip){
		//For development
		try {
			System.out.println("set client");
			network = new NetworkingClient(s, ip);
		}
		catch(Exception e){
			System.out.println("Exception");
			System.out.println(e);
			return false;
		}
		return true;
	}

	public void stopNetworking() {
		if(network != null) {
			network.stop();
			network = null;
		}
	}
	public void startGame(GameScreen s) {
		System.out.println(network);
		if(network != null) {

			network.startGame(s);
		}
	}


	@Override
	public void render() {
		super.render();
		if(network != null) {
			network.update();
		}
	}
}
