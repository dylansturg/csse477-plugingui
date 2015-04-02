package edu.rosehulman.pluggablegui.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;

public class PluginManager {
	private static final String CONFIG_FILE_NAME = "config.txt";
	public static final PluginManager sharedInstance = new PluginManager();
	
	private PluginManager(){
		
	}

	public void registerPlugin(Path newPath) {
		try {
			JarFile jarFile = new JarFile(newPath.toString());
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + newPath + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			IBedazzledPlugin p = (IBedazzledPlugin) Class.forName(
					readPluginActivatorClass(cl), true, cl).newInstance();

			ReferenceBedazzler.sharedInstance.registerPlugin(p);
		} catch (Exception e) {
			System.out.println("plugin not loaded at " + newPath);
		} 
	}

	private String readPluginActivatorClass(ClassLoader pluginJar) {
		InputStream configStream = pluginJar
				.getResourceAsStream(CONFIG_FILE_NAME);
		if (configStream == null) {
			throw new IllegalArgumentException(
					"Failed to find a configuration for the given plugin ClassLoader.  Plugin cannot be installed.");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				configStream));
		try {
			String className = reader.readLine();
			return className;
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Failed to read the class name for the config file.", e);
		}
	}
}
