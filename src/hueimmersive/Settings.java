package hueimmersive;

import hueimmersive.interfaces.ILight;

import java.awt.*;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Settings
{
	private static final Preferences basenode = Preferences.userRoot().node("hueimmersive");
	protected Preferences setsnode = basenode;

	private Map<String, Object> setmap = new HashMap<>();

	Settings()
	{
		node("");
	}

	public final Settings node(String nodeName)
	{
		setsnode = setsnode.node(nodeName);

		setmap.clear();
		try
		{
			for (String key : setsnode.keys())
			{
				setmap.put(key, setsnode.get(key, ""));
			}
		} catch (BackingStoreException e)
		{
			e.printStackTrace();
		}

		return this;
	}

	public final void addDefaultEntry(String key, Object value)
	{
		if (!setmap.containsKey(key) && value != null)
		{
			setmap.put(key, value);
			setsnode.put(key, value.toString());
		}
	}

	protected final boolean getBoolean(String key)
	{
		if (setmap.containsKey(key))
			return setsnode.getBoolean(key, Boolean.parseBoolean(setmap.get(key).toString()));
		else
			return setsnode.getBoolean(key, false);
	}

	protected final int getInt(String key)
	{
		if (setmap.containsKey(key) && setmap.get(key) != null)
			return setsnode.getInt(key, Integer.parseInt(setmap.get(key).toString()));
		else
			return setsnode.getInt(key, 0);
	}

	protected final float getFloat(String key)
	{
		if (setmap.containsKey(key))
			return setsnode.getFloat(key, Float.parseFloat(setmap.get(key).toString()));
		else
			return setsnode.getFloat(key, 0.0f);
	}

	protected final String getString(String key)
	{
		if (setmap.containsKey(key))
			return setsnode.get(key, setmap.get(key).toString());
		else
			return setsnode.get(key, null);
	}

	protected final void setBoolean(String key, boolean value)
	{
		setsnode.putBoolean(key, value);
	}

	protected final void setInt(String key, int value)
	{
		setsnode.putInt(key, value);
	}

	protected final void setFloat(String key, float value)
	{
		setsnode.putFloat(key, value);
	}

	protected final void setString(String key, String value)
	{
		setsnode.put(key, value);
	}

	public static void reset(boolean exit) throws Exception
	{
		Debug.info(null, "reset all settings");

		basenode.removeNode();
		if (Control.bridge != null && Control.bridge.isConnected())
		{
			Control.bridge.cleanup();
		}

		if(exit)
		{
			Debug.closeLog();
			System.exit(0);
		}
	}

	//
	// Presets
	//

	public static class Main
	{
		private static Settings settings;

		static
		{
			settings = new Settings();

			settings.addDefaultEntry("ui_x", 250);
			settings.addDefaultEntry("ui_y", 200);
			settings.addDefaultEntry("cpi_x", 600);
			settings.addDefaultEntry("cpi_y", 200);
			settings.addDefaultEntry("oi_x", 250);
			settings.addDefaultEntry("oi_y", 450);
			settings.addDefaultEntry("chunks", 12);
			settings.addDefaultEntry("brightness", 100);
			settings.addDefaultEntry("saturation", 110);
			settings.addDefaultEntry("format", 0);
			settings.addDefaultEntry("colorgrid", false);
			settings.addDefaultEntry("restorelight", true);
			settings.addDefaultEntry("autoswitch", false);
			settings.addDefaultEntry("autoswitchthreshold", 10);
			settings.addDefaultEntry("gammacorrection", true);
			settings.addDefaultEntry("screen", 0);
			settings.addDefaultEntry("refreshdelay", 400);
		}

		public static boolean getBoolean(String key)
		{
			return settings.getBoolean(key);
		}

		public static int getInt(String key)
		{
			return settings.getInt(key);
		}

		public static float getFloat(String key)
		{
			return settings.getFloat(key);
		}

		public static String getString(String key)
		{
			return settings.getString(key);
		}

		public static void setBoolean(String key, boolean value)
		{
			settings.setBoolean(key, value);
		}

		public static void setInt(String key, int value)
		{
			settings.setInt(key, value);
		}

		public static void setFloat(String key, float value)
		{
			settings.setFloat(key, value);
		}

		public static void setString(String key, String value)
		{
			settings.setString(key, value);
		}

		public static ArrayList<String> getArguments()
		{
			String args = getString("arguments");

			ArrayList<String> arrArgs = new ArrayList<>();
			if (args != null)
			{
				arrArgs.addAll(Arrays.asList(args.split(",")));
			}

			return arrArgs;
		}

		public static void setArguments(ArrayList<String> args)
		{
			if (args.size() != 0)
			{
				String arguments = "";
				for (String arg : args)
				{
					arguments += "," + arg;
				}
				arguments = arguments.replaceFirst(",", "");
				setString("arguments", arguments);
			}
			else
			{
				settings.setsnode.remove("arguments");
			}
		}

		public static void debug() throws Exception
		{
			String[] keys;
			ArrayList<String> settingList = new ArrayList<>();
			keys = settings.setsnode.keys();
			Arrays.sort(keys);
			for (String s : keys)
			{
				settingList.add(s + " = " + getString(s));
			}
			Debug.info("settings general", settingList);
		}
	}

	public static class UserInterface extends Settings
	{
		private final String name;

		public UserInterface(String name) throws Exception
		{
			this.name = name;

			node("ui");

			addDefaultEntry(name + "_x", 200);
			addDefaultEntry(name + "_y", 200);
		}

		public Point getLocationX()
		{
			return new Point(getInt(name + "_x"), getInt(name + "_y"));
		}

		public void setLocation(Point location)
		{
			setInt(name + "_x", location.x);
			setInt(name + "_y", location.y);
		}
	}

	public static class Bridge extends Settings
	{
		Bridge() throws Exception
		{
			node("bridge");

			addDefaultEntry("internalipaddress", null);
			addDefaultEntry("username", null);
		}

		String getInternalipaddress()
		{
			return getString("internalipaddress");
		}

		void setInternalipaddress(String address)
		{
			setString("internalipaddress", address);
		}

		String getUsername() { return getString("username"); }

		void setUsername(String username) { setString("username", username); }

		public static void debug() throws Exception
		{
			String[] keys;
			ArrayList<String> settingList = new ArrayList<>();
			Settings settings = new Settings().node("bridge");

			keys = settings.setsnode.keys();
			Arrays.sort(keys);
			for (String s : keys)
			{
				settingList.add(s + " = " + settings.setsnode.get(s, null));
			}
			Debug.info("settings bridge", settingList);
		}
	}

	public static class Light extends Settings
	{
		public Light(ILight light) throws Exception
		{
			node("lights").node(light.getUniqueID());

			addDefaultEntry("act", true);
			addDefaultEntry("alg", 0);
			addDefaultEntry("bri", 1.0f);
		}

		public final boolean getActive() throws Exception
		{
			return getBoolean("act");
		}

		public final void setActive(boolean active) throws Exception
		{
			setBoolean("act", active);
		}

		public final float getBrightnessMultiplier() throws Exception
		{
			return getFloat("bri");
		}

		public final void setBrightnessMultiplier(float multiplier) throws Exception
		{
			setFloat("bri", multiplier);
		}

		public final int getAlgorithm() throws Exception
		{
			return getInt("alg");
		}

		public final void setAlgorithm(int algorithm) throws Exception
		{
			setInt("alg", algorithm);
		}

		public static void debug() throws Exception
		{
			ArrayList<String> settingList = new ArrayList<>();

			Settings settings = new Settings().node("lights");
			for (String node : settings.setsnode.childrenNames())
			{
				settingList.add(node + "");
				String[] keys = new Settings().node("lights").node(node).setsnode.keys();
				Arrays.sort(keys);
				for (String s : keys)
				{
					settingList.add("  " + s + " = " + settings.setsnode.node(node).get(s, null));
				}
			}
			Debug.info("settings lights", settingList);
		}
	}
}
