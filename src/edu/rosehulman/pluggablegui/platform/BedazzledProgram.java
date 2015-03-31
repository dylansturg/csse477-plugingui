package edu.rosehulman.pluggablegui.platform;

import javax.swing.JPanel;

import edu.rosehulman.pluggablegui.commons.ILogger;

public class BedazzledProgram extends JPanel {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public BedazzledProgram() {
		// Create a Renderer (for plugins)
		//	Display it
		// Get the logger
		ILogger logger = ReferenceBedazzler.sharedInstance.getSharedLogger();
		JPanel loggerUI = logger.render();
	}
}
