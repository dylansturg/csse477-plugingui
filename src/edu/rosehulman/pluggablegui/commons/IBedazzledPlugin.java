package edu.rosehulman.pluggablegui.commons;

import javax.swing.JPanel;

public interface IBedazzledPlugin {
	// Human readable for UI
	public String getShortName();
	// Distinguish from other plugins
	public String getUniqueName();
	public int getVersion();
	
	// Plugin Lifecycle
	public void start(IBedazzledPlatform environment);
	public void stop();
	public JPanel render();
}
