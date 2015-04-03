package edu.rosehulman.pluggablegui.platform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlatform;
import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;
import edu.rosehulman.pluggablegui.commons.ILogger;

public class BedazzledProgram extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7323463350634651199L;

	public static BedazzledProgram sharedInstance;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new Thread(new PluginLoader())).start();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bedazzle();
			}
		});
	}

	private static void bedazzle() {
		final JFrame frame = new JFrame("Bedazzled Plugins");

		frame.setSize(900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(new BedazzledProgram());
		frame.setVisible(true);
//		frame.pack();
	}

	public BedazzledProgram() {
		// Create a Renderer (for plugins)
		// Display it
		// Get the logger
		sharedInstance = this;

		setBackground(Color.CYAN);

		Renderer renderer = new Renderer();
		ReferenceBedazzler.sharedInstance.registerRenderer(renderer);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(renderer);

		ILogger logger = ReferenceBedazzler.sharedInstance.getSharedLogger();
		if (logger != null) {
			JPanel loggerUI = logger.render();
			if (loggerUI != null) {
				add(loggerUI);
			}
		} else {
			// test code
			JPanel testPanel = new JPanel();
			testPanel.setPreferredSize(new Dimension(900, 150));
			testPanel.add(new JLabel("Logger goes here"));
			testPanel.setBackground(Color.RED);

			add(testPanel);
		}

		JPanel footer = new JPanel();
		footer.setBackground(Color.WHITE);
		footer.add(new JLabel("Bedazzled Plugins Inc."));
		footer.add(new JLabel("© 2015"));
		footer.setPreferredSize(footer.getPreferredSize());

		add(footer);
		appendTestData(renderer);
	}

	private void appendTestData(Renderer target) {
		String[] words = { "Harry", "Potter", "Fancy", "Pants", "Cat", "Pony",
				"Lemons", "Apple", "iPhone", "Caramel Frap", "Sea Lion",
				"Pancakes", "Puppy", "Twilight", "key", "mound", "waiter",
				"prince", "shout", "blister", "novel", "force", "milkshake",
				"boys", "yard", "brings", "her", "Cole", "loser", "is", "does",
				"won't", "faces", "barrista", "steamed milk", "half caf",
				"white", "girls" };

		Random rando = new Random();
		for (int i = 0; i < 2; i++) {
			StringBuilder pluginName = new StringBuilder();

			int wordCount = rando.nextInt(8) + 1;
			for (int w = 0; w < wordCount; w++) {
				int word = rando.nextInt(words.length);
				pluginName.append(words[word] + " ");
			}

			final String shortName = pluginName.toString();
			final String unique = "" + i;
			IBedazzledPlugin generatedPlugin = new IBedazzledPlugin() {

				@Override
				public void stop() {
					// TODO Auto-generated method stub

				}

				@Override
				public void start(IBedazzledPlatform environment) {
					// TODO Auto-generated method stub
					logMessage(shortName + "is starting up!");

				}

				public void logMessage(String msg) {
					ILogger logger = ReferenceBedazzler.sharedInstance
							.getSharedLogger();
					logger.logInfo(msg);
				}

				@Override
				public JPanel render() {
					JPanel testPane = new JPanel();
					testPane.setBackground(Color.BLACK);

					JLabel fancyLabel = new JLabel(shortName);
					fancyLabel.setForeground(Color.CYAN);
					testPane.add(fancyLabel);
					return testPane;
				}

				@Override
				public int getVersion() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public String getUniqueName() {
					return unique;
				}

				@Override
				public String getShortName() {
					// TODO Auto-generated method stub
					return shortName;
				}
			};

			target.registerPlugin(generatedPlugin);
		}
	}
}
