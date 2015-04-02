package edu.rosehulman.pluggablegui.platform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

import edu.rosehulman.pluggablegui.commons.ILogger;

public class Logger implements ILogger {

//	public static JTextPane loggerText = new JTextPane();
	public static JTextArea loggerText = new JTextArea(12, 70);

	public static JPanel loggerPanel = new JPanel();
	private static List<Color> colors = new ArrayList<Color>();
	private static Random rand = new Random();
	
	public Logger(){
		for (int r=0; r<100; r++) colors.add(new Color(r*255/100,       255,         0));
		for (int g=100; g>0; g--) colors.add(new Color(      255, g*255/100,         0));
		for (int b=0; b<100; b++) colors.add(new Color(      255,         0, b*255/100));
		for (int r=100; r>0; r--) colors.add(new Color(r*255/100,         0,       255));
		for (int g=0; g<100; g++) colors.add(new Color(        0, g*255/100,       255));
		for (int b=100; b>0; b--) colors.add(new Color(        0,       255, b*255/100));
		                          colors.add(new Color(        0,       255,         0));
	}
	
	private String bedazzle(String s){
		String bedazzled = "";
		
		for(int i = 0; i < s.length(); i++){
			Color c = colors.get(rand.nextInt(colors.size()));
			String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
			bedazzled += "<font size=\"3\" face=\"verdana\" color=\"" + hex + "\">" + s.charAt(i) + "</font>";
		}
		
		return bedazzled;
	}
	
	//Change bedazzle to dylandazzle for black text
	private String dylandazzle(String s){
		return "<font size=\"3\" face=\"verdana\" color=\"black\">" + s + "</font>";
	} 
	
	private String redazzle(String s){
		return "<font size=\"3\" face=\"verdana\" color=\"red\">" + s + "</font>";
	}
	
	@Override
	public void logInfo(String message) {
//		loggerText.setText(loggerText.getText().replaceAll("</html>", "").replaceAll("</body>", "") + "<p>" + bedazzle(message) + "</p></body></html>");

		loggerText.append(message + "\n");
	}

	@Override
	public void logError(String error) {
//		loggerText.setText(loggerText.getText().replaceAll("</html>", "").replaceAll("</body>", "") + "<p>" + redazzle(error) + "</p></body></html>");
		
		loggerText.append("ERROR: " + error + "\n");
	}

//	public JPanel render() {
//		JScrollPane loggerScrollPane = new JScrollPane(loggerText);
//		loggerPanel.setLayout(new BorderLayout());
//		loggerPanel.add(loggerScrollPane, BorderLayout.CENTER);
//		loggerText.setEditorKit(new HTMLEditorKit());
//		loggerScrollPane.setBackground(Color.black);
//		
//		loggerText.setSize(600, 200);
//		loggerPanel.setSize(600, 200);
//		loggerPanel.setMaximumSize(new Dimension(10000, 200));
//		
//		loggerText.setText("<html><head></head><body>Logger started</body></html>");
//		
//		return loggerPanel;
//	}
	
	public JPanel render(){
		JScrollPane loggerScrollPane = new JScrollPane(loggerText);
		loggerPanel.add(loggerScrollPane, BorderLayout.CENTER);
		loggerPanel.setSize(600, 200);
		
		return loggerPanel;
	}
}
