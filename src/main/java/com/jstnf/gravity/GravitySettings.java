package com.jstnf.gravity;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;

public class GravitySettings
{
	private Gravity plugin;
	private File configFile;

	public YamlConfiguration config;

	public enum Defaults
	{
		ENABLED_ON_START("gravity.enabled-on-start", true),
		WORLDS("gravity.worlds", new ArrayList<String>()),
		CONFIG_VERSION("gravity.config-version", 1);

		private String path;
		private Object defaultValue;

		Defaults(String path, Object defaultValue)
		{
			this.path = path;
			this.defaultValue = defaultValue;
		}

		public String getPath()
		{
			return path;
		}

		public Object getDefaultValue()
		{
			return defaultValue;
		}
	}

	public GravitySettings(Gravity plugin)
	{
		this.plugin = plugin;
	}

	public boolean generateConfig()
	{
		config = new YamlConfiguration();
		configFile = new File(plugin.getDataFolder(), "config.yml");

		/* Check if config.yml has been generated already */
		if (!configFile.exists())
		{
			/* config.yml hasn't been generated yet */
			try
			{
				/* Try generating config.yml from resources and assigning it */
				plugin.saveResource("config.yml", false);
				config.load(configFile);
			}
			catch (Exception e)
			{
				/* Something went wrong! */
				e.printStackTrace();
				plugin.getLogger().severe("Could not generate config files.");
				plugin.getLogger().severe("Gravity will now be disabled.");
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				return false;
			}
		}
		else
		{
			/* Check config version */
			try
			{
				/* Match config version */
				config.load(configFile);
				int currentConfigVersion = config.getInt(Defaults.CONFIG_VERSION.getPath(), 0);
				if (currentConfigVersion != (int) Defaults.CONFIG_VERSION.getDefaultValue())
				{
					/* Uh oh! The config version does not match! */
					plugin.getLogger().warning("Your config version (" + currentConfigVersion
							+ ") does not match the current config version (" + Defaults.CONFIG_VERSION
							.getDefaultValue() + ").");
					plugin.getLogger().warning("Your current configuration will be saved to config.yml.backup.");

					/* Begin config backup */
					plugin.getLogger().info("Commencing backup...");

					File backup = new File(plugin.getDataFolder(), "config.yml.backup");
					backup.createNewFile();

					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
					BufferedWriter out = new BufferedWriter(new FileWriter(backup, true));

					String currLine = "";
					while ((currLine = in.readLine()) != null)
					{
						out.write(currLine);
						out.newLine();
					}

					in.close();
					out.close();

					plugin.getLogger().info("Configuration backup successful!");

					/* Generate new config.yml */
					plugin.getLogger().info("Generating a new config.yml...");
					plugin.saveResource("config.yml", true);

					config.load(configFile);
				}
			}
			catch (Exception e)
			{
				/* Something went wrong loading the config! */
				e.printStackTrace();
				plugin.getLogger().severe("Could not load config files.");
				plugin.getLogger().severe("Gravity will now be disabled.");
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				return false;
			}
		}
		return true;
	}
}