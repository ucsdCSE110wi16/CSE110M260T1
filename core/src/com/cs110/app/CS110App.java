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
	public static RunEnum RUN_TYPE;

	public static String SERVER_URL = "104.154.19.223/";

	public CS110App(int type) {
		if(type == 0){
			RUN_TYPE = RunEnum.MULTIPLAYER_LOCAL;
			SERVER_URL = "127.0.0.1";
		}
		else if(type == 2)  {
			RUN_TYPE = RunEnum.MULTIPLAYER_BACKEND;
		}
		else if(type== 1){
			RUN_TYPE = RunEnum.SINGLE_PLAYER;
		}
	}

	@Override
	public void create() {
		if(CS110App.RUN_TYPE == RunEnum.SINGLE_PLAYER){
			ScreenManager.getInstance().initialize(this);
			ScreenManager.getInstance().showScreen(ScreenEnum.GAME, 5);
			return;
		}
		else {
			ScreenManager.getInstance().initialize(this);
			ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, local);
		}
	}


	public void setServer(ServerWaitingScreen s){
		try {
			network = new NetworkingServer(s);
		}
		catch(Exception e){}
	}


	// throws exception if connection doesn't work
	public void setClient(GameScreen s, String ip) throws Exception{
			network = new NetworkingClient(s, ip);
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
			System.out.println("network update");
			network.update();
		}
	}
}
