package edu.rosehulman.pluggablegui.commons;

import javax.swing.JPanel;

public interface ILogger {
	public void logInfo(String message);
	public void logError(String error);
	
	public JPanel render();
}
