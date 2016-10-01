package hueimmersive.lights;

import hueimmersive.HueBridge;
import hueimmersive.Settings;
import hueimmersive.interfaces.ILight;

import com.google.gson.JsonObject;


public abstract class HLight implements ILight
{
	protected final HueBridge bridge;

	protected final String name;
	protected final int id;
	protected final String uniqueid;

	protected final Settings.Light settings;

	public HLight(int id, HueBridge bridge) throws Exception
	{
		this.id = id;
		this.bridge = bridge;

		JsonObject response = bridge.getLink().GET("/lights/" + id);

		this.name = response.get("name").getAsString();
		this.uniqueid = response.get("uniqueid").getAsString();

		settings = new Settings.Light(this);
	}

	public final String getName()
	{
		return name;
	}

	public final int getID()
	{
		return id;
	}

	public final String getUniqueID()
	{
		return uniqueid;
	}

	public final boolean isOn() throws Exception
	{
		JsonObject response = bridge.getLink().GET("/lights/" + id);

		return response.get("state").getAsJsonObject().get("on").getAsBoolean();
	}

	public final void setOn(boolean on) throws Exception
	{
		JsonObject data = new JsonObject();
		data.addProperty("on", on);

		bridge.getLink().PUT("/lights/" + id + "/state/", data);
	}

	public final boolean getActive() throws Exception
	{
		return settings.getActive();
	}

	public final void setActive(boolean active) throws Exception
	{
		settings.setActive(active);
	}

	public final float getBrightnessMultiplier() throws Exception
	{
		return settings.getBrightnessMultiplier();
	}

	public final void setBrightnessMultiplier(float multiplier) throws Exception
	{
		settings.setBrightnessMultiplier(multiplier);
	}

	public final int getAlgorithm() throws Exception
	{
		return settings.getAlgorithm();
	}

	public final void setAlgorithm(int algorithm) throws Exception
	{
		settings.setAlgorithm(algorithm);
	}
}
