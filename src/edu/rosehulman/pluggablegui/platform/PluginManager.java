package edu.rosehulman.pluggablegui.platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;

public class PluginManager {
	private static final String CONFIG_FILE_NAME = "config.txt";
	public static final PluginManager sharedInstance = new PluginManager();

	private PluginManager() {

	}

	public void registerPlugin(Path newPath) {
		try {
			String filesystem = newPath.toAbsolutePath().getParent().toString();
			String filename = newPath.getFileName().toString();
			String path = "";
			//idk if right, but we need it right now
			if(filesystem.endsWith("plugins")){
				path = filesystem + File.separator + filename;
			}else{
				path = filesystem + File.separator + "plugins" + File.separator + filename;
			}
			
			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile(path);
			@SuppressWarnings("unused")
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + path + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);
			
			IBedazzledPlugin p = (IBedazzledPlugin) Class.forName(
					readPluginActivatorClass(jarFile, e), true, cl)
					.newInstance();

			ReferenceBedazzler.sharedInstance.registerPlugin(p);
		} catch (Exception e) {
			System.out.println("plugin not loaded at " + newPath);
		}
	}

	private String readPluginActivatorClass(JarFile pluginJar,
			Enumeration<JarEntry> entries) {

		String className = null;
		JarEntry entry = null;
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();

			String name = entry.getName();
			if (name.equals(CONFIG_FILE_NAME)) {
				try {
					InputStream stream = pluginJar.getInputStream(entry);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(stream));
					className = reader.readLine();
					reader.close();
					stream.close();
				} catch (IOException e) {
					throw new IllegalArgumentException(
							"Failed to read the class name for the config file.",
							e);
				}
			}
		}

		if (className == null) {
			throw new IllegalArgumentException("Failed to find a "
					+ CONFIG_FILE_NAME
					+ " specifying the Plugin activator class.");
		}

		return className;
	}
}
