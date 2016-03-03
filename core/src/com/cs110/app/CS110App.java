package com.cs110.app;
import com.badlogic.gdx.Game;
import com.cs110.app.Net.NetworkingClient;
import com.cs110.app.Net.NetworkingServer;
import com.cs110.app.Screens.GameScreen;
import com.cs110.app.Screens.ScreenEnum;
import com.cs110.app.Screens.ScreenManager;
import com.cs110.app.Screens.ServerWaitingScreen;


public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
	// game allows for screens

	com.cs110.app.Net.NetworkingServer NS;
	com.cs110.app.Net.NetworkingClient NC;
	boolean client;

	public CS110App(boolean client) {
		this.client = client;
	}

	@Override
	public void create() {
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
	}

	public void start(boolean server) {
		System.out.println("STARTED");
		GameScreen screen = new GameScreen();
		setScreen(screen);
		try {
			if (server)
				NS = new NetworkingServer(screen);
			else
				NC = new NetworkingClient(screen); // Uncomment to be client
		} catch (Exception e) {

		}
	}

	public void startServer(ServerWaitingScreen s){
		try {
			NS = new NetworkingServer(s);
		}
		catch(Exception e){}
	}

	public void startClient(GameScreen s, String ip){
		try {
			NC = new NetworkingClient(s, ip);
		}
		catch(Exception e){}
	}

	public void setGameScreen(GameScreen s) {
		if(NS != null) {
			NS.startGame(s);
		}
	}


	@Override
	public void render() {
		super.render();
		if (client && NC != null) {
			NC.update();
		} else if (NS != null) {
			//System.out.println("update");
			NS.update();
		}
	}
}
