package com.gmail.gidonyouyt.gameAge;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;

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
		for (GameSettings settings : GameSettings.values())
			settings.value = plugin.getConfig().getDouble(settings.name());
	}

	private void addDefaults() {
		for (GameSettings settings : GameSettings.values())
			plugin.getConfig().addDefault(settings.name(), settings.value());

	}

	public void updateSetting(GameSettings setting, double value) {
		SendMessage.sendMessageOP(setting.name() + "설정이 업데이트 되었습니다.  " + setting.value() + " --> " + value);
		setting.value = value;
		plugin.getConfig().set(setting.name(), value);
		plugin.saveConfig();
	}
}
