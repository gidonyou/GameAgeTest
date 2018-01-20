package com.gmail.gidonyouyt.gameAge;

public class ConfigYml {

	private GameAge plugin; // Main Name

	public ConfigYml(GameAge plugin) { // Main Name
		this.plugin = plugin;
	}

	public void loadConfiguration() {
		// See "Creating you're defaults"
		
		addDefaults();
		
		plugin.getConfig().options().copyDefaults(true); 
		plugin.saveConfig();
		
		updateSettings();
	}
	
	private void updateSettings() {
		for (GameSettings settings : GameSettings.values()) {
			settings.value = plugin.getConfig().getDouble(settings.name());
		}
		
	}

	private void addDefaults() {
		for (GameSettings settings : GameSettings.values()) {
			plugin.getConfig().addDefault(settings.name(), settings.value());
		}
	}
	
	private void updateSetting(GameSettings setting , double value) {
		setting.value = value;
		plugin.getConfig().set(setting.name(), value);
		
	}
}
