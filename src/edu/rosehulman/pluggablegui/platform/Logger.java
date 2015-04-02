package edu.rosehulman.pluggablegui.platform;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.rosehulman.pluggablegui.commons.ILogger;

public class Logger implements ILogger {

	public static JTextArea loggerText = new JTextArea(12, 70);
	public static JPanel loggerPanel = new JPanel();
	
	@Override
	public void logInfo(String message) {
		loggerText.append(message + "\n");
	}

	@Override
	public void logError(String error) {
		loggerText.append("ERROR: " + error + "\n");
	}

	@Override
	public JPanel render() {
		JTextField tf = new JTextField(12);
		//loggerPanel.add(tf, BorderLayout.NORTH);
		JScrollPane loggerScrollPane = new JScrollPane(loggerText);
		loggerPanel.add(loggerScrollPane, BorderLayout.CENTER);
		loggerPanel.setSize(600, 200);
		
		return loggerPanel;
	}
}
