package edu.rosehulman.pluggablegui.plugins;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlatform;
import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;

public class DefaultPlugin implements IBedazzledPlugin {

	@Override
	public String getShortName() {
		return "Default";
	}

	@Override
	public void start(IBedazzledPlatform environment) {
		//Does nothing
	}

	@Override
	public void stop() {
		//Does nothing
	}

	@Override
	public JPanel render() {
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea();
		
		String message = "This is the default plugin.";
		text.append(message);
		
		panel.add(text);
		
		return panel;
	}

	@Override
	public String getUniqueName() {
		return "";
	}

	@Override
	public int getVersion() {
		return 0;
	}

}
