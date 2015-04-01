package edu.rosehulman.pluggablegui.platform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;

public class Renderer extends JPanel {
	// Thing Eclipse told me to add
	private static final long serialVersionUID = -6525041044593318995L;
	
	private JScrollPane availablePluginTable;
	private JPanel pluginTableContent;
	private JPanel pluginUIPanel;
	
	private Map<String, IBedazzledPlugin> registeredPlugins = new HashMap<String, IBedazzledPlugin>();
	
	public Renderer(){
		// Build shit and shit
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(900, 600));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		pluginTableContent = new JPanel();
		pluginTableContent.setBackground(Color.MAGENTA);
		pluginTableContent.setLayout(new BoxLayout(pluginTableContent, BoxLayout.Y_AXIS));
		
		availablePluginTable = new JScrollPane(pluginTableContent);
		availablePluginTable.setPreferredSize(new Dimension(300, 600));
		availablePluginTable.setBackground(Color.GREEN);
		
		add(availablePluginTable);
		
		pluginUIPanel = new JPanel();
		pluginUIPanel.setPreferredSize(new Dimension(600, 600));
		pluginUIPanel.setBackground(Color.ORANGE);
		
		add(pluginUIPanel);
	}

	public void registerPlugin(IBedazzledPlugin activator) {
		// TODO ^
		// put the plugin into the available plugins side panel
		if(!registeredPlugins.containsKey(activator.getUniqueName())){
			registeredPlugins.put(activator.getUniqueName(), activator);
			
			pluginTableContent.add(createPluginPreview(activator));
		}
		
	}
	
	private JPanel createPluginPreview(IBedazzledPlugin plugin){
		JPanel preview = new JPanel();
		preview.setLayout(new BoxLayout(preview, BoxLayout.Y_AXIS));
		preview.setOpaque(false);
		preview.add(new JLabel(plugin.getShortName()));
		JButton activate = new JButton("Activate");
		//activate.setAlignmentX(Component.CENTER_ALIGNMENT);
		preview.add(activate);
		
		return preview;
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
