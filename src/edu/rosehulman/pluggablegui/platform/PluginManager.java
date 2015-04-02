package edu.rosehulman.pluggablegui.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PluginManager {
	private static final String CONFIG_FILE_NAME = "config.txt";

	private String readPluginActivatorClass(ClassLoader pluginJar) {
		InputStream configStream = pluginJar
				.getResourceAsStream(CONFIG_FILE_NAME);
		if (configStream == null) {
			throw new IllegalArgumentException(
					"Failed to find a configuration for the given plugin ClassLoader.  Plugin cannot be installed.");
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(configStream));
		try {
			String className = reader.readLine();
			return className;
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to read the class name for the config file.", e);
		}
	}
}
