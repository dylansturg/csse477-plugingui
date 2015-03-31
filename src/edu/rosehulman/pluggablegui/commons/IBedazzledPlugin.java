package edu.rosehulman.pluggablegui.commons;

import javax.swing.JPanel;

public interface IBedazzledPlugin {
	public String getShortName();
	
	// Plugin Lifecycle
	public void start(ILogger log);
	public void stop();
	public JPanel render();
}
