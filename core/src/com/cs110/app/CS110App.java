package com.cs110.app;
import com.badlogic.gdx.Game;
import com.cs110.app.Net.NetworkingClient;
import com.cs110.app.Net.NetworkingServer;
import com.cs110.app.Screens.GameScreen;
import com.cs110.app.Screens.MenuScreen;

public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
                                    // game allows for screens

	com.cs110.app.Net.NetworkingServer NS;
	com.cs110.app.Net.NetworkingClient NC;
	boolean client;
	public CS110App(boolean client) {
		this.client = client;
	}
	
	@Override
	public void create () {
        MenuScreen screen = new MenuScreen(this);
		setScreen(screen);


	}
    public void start(boolean server) {
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


	@Override
	public void render () {
        super.render();
		if(client && NC != null) {
			NC.update();
		}
		else if(NS!= null) {
			//System.out.println("update");
			NS.update();
		}

	}

}
