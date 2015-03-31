package edu.rosehulman.pluggablegui.platform;

import java.util.List;

import javax.swing.JPanel;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;

public class Renderer extends JPanel {
	// Thing Eclipse told me to add
	private static final long serialVersionUID = -6525041044593318995L;
	
	public Renderer(){
		// Build shit and shit
	}

	public void registerPlugin(IBedazzledPlugin activator) {
		// TODO ^
		// put the plugin into the available plugins side panel
	}

	public void registerPlugins(List<IBedazzledPlugin> activators) {
		for (IBedazzledPlugin iBedazzledPlugin : activators) {
			this.registerPlugin(iBedazzledPlugin);
		}
	}

	public void unregisterPlugin(IBedazzledPlugin activator) {
		// TODO ^
	}

	public void unregisterPlugins(List<IBedazzledPlugin> activators) {
		for (IBedazzledPlugin iBedazzledPlugin : activators) {
			this.unregisterPlugin(iBedazzledPlugin);
		}
	}

	public void terminatePlugin(String pluginId) {
		// remove from side panel and clear it's JPanel
	}

	
}
