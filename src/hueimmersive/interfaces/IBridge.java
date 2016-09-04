package hueimmersive.interfaces;

import java.util.ArrayList;


public interface IBridge
{
	ILink getLink();

	void register() throws Exception;
	void login() throws Exception;

	void find() throws Exception;
	void connect() throws Exception;

	boolean isConnected() throws Exception;

	void findLights() throws Exception;
	ArrayList<ILight> getLights();

    void cleanup() throws Exception;
}
