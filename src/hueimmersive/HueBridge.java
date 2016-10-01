package hueimmersive;

import hueimmersive.interfaces.IBridge;
import hueimmersive.interfaces.ILight;
import hueimmersive.interfaces.ILink;
import hueimmersive.lights.HColorLight;
import hueimmersive.lights.HDimmableLight;
import hueimmersive.lights.HLight;

import java.util.ArrayList;

import com.google.gson.JsonObject;


public final class HueBridge implements IBridge
{
	private static final int maxFindAttempts = 6;
	private static final int findPeriod = 1500;

	private static final int maxRegisterAttempts = 20;
	private static final int registerPeriod = 1500;

	private Settings.Bridge settings = new Settings.Bridge();

	private String internalipaddress = settings.getInternalipaddress();
	private String username = settings.getUsername();

	private static final String applicationname = "HueImmersive";
	private final String devicename = System.getProperty("user.name");

	private final ArrayList<HLight> lights = new ArrayList<HLight>();

	private final HueLink link = new HueLink();
	
	public HueBridge() throws Exception
	{
		if (internalipaddress != null && !internalipaddress.isEmpty() && username != null && !username.isEmpty())
		{
			connect();
		}
		else
		{
			find();
		}
	}

	public ILink getLink()
	{
		return link;
	}

	public void register() throws Exception
	{
		Debug.info(null, "create new user...");

		Main.ui.setConnectState(3);

		String devicetype = applicationname;
		if (devicename != null && !devicename.isEmpty())
		{
			devicetype += "#" + devicename;
		}

		JsonObject data = new JsonObject();
		data.addProperty("devicetype", devicetype);

		String username = null;
		int attempts = 0;
		while (attempts < maxRegisterAttempts) // abort after serval attempt
		{
			try // to register a new bridge user (user must press the link button)
			{
				JsonObject response = getLink().POST("http://" + internalipaddress + "/api/", data);

				if (getLink().getResponseType(response) == ILink.ResponseType.SUCCESS)
				{
					username = response.get("success").getAsJsonObject().get("username").getAsString();
					break;
				}
			}
			catch (Exception e)
			{
				Debug.exception(e);
				break;
			}

			attempts++;

			Thread.sleep(registerPeriod);
		}

		if (username != null)
		{
			Debug.info(null, "new user created");

			this.username = username;
			settings.setUsername(username);

			login();
		}
		else
		{
			Debug.info(null, "link button not pressed");

			Main.ui.setConnectState(4);
		}
	}

	public void login() throws Exception // try to login
	{
		JsonObject response = getLink().GET("http://" + internalipaddress + "/api/" + username);
		if (getLink().getResponseType(response) == ILink.ResponseType.DATA)
		{
			Debug.info(null, "login successful");

			getLink().setBaseAPIurl("http://" + internalipaddress + "/api/" + username);

			dumpBridgeConfig();
			
			findLights();
			
			Main.ui.setConnectState(2);
		}
		else if (getLink().getResponseType(response) == ILink.ResponseType.ERROR)
		{
			register();
		}
	}

	public void find() throws Exception
	{
		Debug.info(null, "setup new connection...");

		Main.ui.loadConnectionInterface();
		Main.ui.setConnectState(1);

		String internalipaddress = null;
		int attempts = 0;
		while (attempts < maxFindAttempts) // abort after several attempts
		{
			try // to get the bridge ip
			{
				JsonObject response = getLink().GET("https://www.meethue.com/api/nupnp");

				if (response != null)
				{
					internalipaddress = response.get("internalipaddress").getAsString();
					break;
				}
			}
			catch (Exception e)
			{
				Debug.exception(e);
			}

			attempts++;
			Thread.sleep(findPeriod);
		}

		if (internalipaddress != null)
		{
			Debug.info(null, "bridge found");

			this.internalipaddress = internalipaddress;
			settings.setInternalipaddress(internalipaddress);

			login();
		}
		else
		{
			Debug.info(null, "connection to bridge timeout");

			Main.ui.setConnectState(4);
		}
	}

	public void connect() throws Exception
	{
		Debug.info(null, "try fast connect...");

		JsonObject response = getLink().GET("http://" + internalipaddress + "/api/" + username);
		if (getLink().getResponseType(response) == ILink.ResponseType.DATA)
		{
			Debug.info(null, "fast connect successful");

			getLink().setBaseAPIurl("http://" + internalipaddress + "/api/" + username);

			dumpBridgeConfig();

			findLights();
		}
		else
		{
			Debug.info(null, "can't connect to bridge");

			find();
		}
	}
	
	public void findLights() throws Exception
	{
		Debug.info(null, "get lights...");

		JsonObject response = getLink().GET("/lights");
		
		for (int i = 1; i < 50; i++)
		{
			if (response.has(String.valueOf(i)))
			{
				JsonObject state = response.getAsJsonObject(String.valueOf(i)).getAsJsonObject("state");
				if (state.has("on") && state.has("hue") && state.has("sat") && state.has("bri"))
				{
					lights.add(new HColorLight(i, this));
				}
				else if (state.has("on") && state.has("bri"))
				{
					lights.add(new HDimmableLight(i, this));
				}
			}
		}

		Debug.info(null, lights.size() + " lights found");
	}

	public ArrayList<ILight> getLights()
	{
		return new ArrayList<ILight>(lights);
	}

	public boolean isConnected() throws Exception
	{
		return getLink().getResponseType(getLink().GET("")) == ILink.ResponseType.DATA;
	}

	public void dumpBridgeConfig() throws Exception
	{
		JsonObject response = getLink().GET("/config");

		Debug.info("bridge infos",
				"name: " + response.get("name").getAsString(),
				"ipaddress: " + response.get("ipaddress").getAsString(),
				"timezone: " + response.get("timezone").getAsString(),
				"swversion: " + response.get("swversion").getAsString(),
				"apiversion: " + response.get("apiversion").getAsString());
	}

	public void cleanup() throws Exception
	{
		if (username != null && !username.isEmpty())
		{
			getLink().DELETE("/config/whitelist/" + username);
		}
	}
}
