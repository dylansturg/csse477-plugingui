package edu.rosehulman.pluggablegui.platform;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.rosehulman.pluggablegui.commons.ILogger;

public class BedazzledProgram extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7323463350634651199L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bedazzle();
			}
		});
	}

	private static void bedazzle() {
		JFrame frame = new JFrame("Bedazzled Plugins");

		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(new BedazzledProgram());
		frame.setVisible(true);
	}

	public BedazzledProgram() {
		// Create a Renderer (for plugins)
		// Display it
		// Get the logger
		
		setBackground(Color.CYAN);
		
		Renderer renderer = new Renderer();
		ReferenceBedazzler.sharedInstance.registerRenderer(renderer);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(renderer);
		
		ILogger logger = ReferenceBedazzler.sharedInstance.getSharedLogger();
		if (logger != null) {
			JPanel loggerUI = logger.render();
			if (loggerUI != null){
				add(loggerUI);
			}
		} else {
			// test code
			JPanel testPanel = new JPanel();
			testPanel.add(new JLabel("Logger goes here"));
			testPanel.setBackground(Color.RED);
			
			add(testPanel);
		}
	}
}
