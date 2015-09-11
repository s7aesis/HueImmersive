package hueimmersive.interfaces;


public interface IBridge
{
	void register() throws Exception;
	void login() throws Exception;

	void find() throws Exception;
	void connect() throws Exception;

	void findLights() throws Exception;
}