package edu.rosehulman.pluggablegui.platform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;

public class Renderer extends JPanel {
	// Thing Eclipse told me to add
	private static final long serialVersionUID = -6525041044593318995L;

	private static final String ACTIVATE_COMMAND = "activate";
	private static final String DEACTIVATE_COMMAND = "deactivate";

	private JScrollPane availablePluginTable;
	private JPanel pluginTableContent;
	private JPanel pluginUIPanel;

	private IPluginActivator activePlugin;

	private Map<String, IBedazzledPlugin> registeredPlugins = new HashMap<String, IBedazzledPlugin>();
	private Map<String, JPanel> pluginPreviews = new HashMap<String, JPanel>();

	public Renderer() {
		// Build shit and shit
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(900, 600));

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		pluginTableContent = new JPanel();
		pluginTableContent.setBackground(Color.MAGENTA);
		pluginTableContent.setLayout(new BoxLayout(pluginTableContent,
				BoxLayout.Y_AXIS));

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
		if (!registeredPlugins.containsKey(activator.getUniqueName())) {
			registeredPlugins.put(activator.getUniqueName(), activator);

			pluginTableContent.add(createPluginPreview(activator));
		}

	}

	private synchronized void activatePlugin(IPluginActivator activator) {
		System.out.println("Activating a plugin... "
				+ activator.getManagedPlugin().getShortName());

		if (this.activePlugin != null) {
			this.activePlugin.deactivate();
			this.activePlugin = null;
		}

		this.activePlugin = activator;

		JPanel pluginUI = this.activePlugin.getManagedPlugin().render();
		if (pluginUI != null) {
			this.pluginUIPanel.add(pluginUI);
		}

		this.redraw();
		this.validate();

	}

	private void deactivatePlugin(IPluginActivator activator) {
		System.out.println("Deactivating a plugin... "
				+ activator.getManagedPlugin().getShortName());

		if (activator != this.activePlugin) {
			throw new IllegalArgumentException(
					"Renderer Internal Consistency error: attempt to deactivate non-action plugin.");
		}

		this.activePlugin = null;

		this.pluginUIPanel.removeAll();

		this.repaint();
	}

	private void redraw() {
		if (!SwingUtilities.isEventDispatchThread()) {

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Renderer.this.update(getGraphics());
				}
			});

		} else {
			this.update(getGraphics());
		}
	}

	private JPanel createPluginPreview(final IBedazzledPlugin plugin) {
		JPanel preview = new JPanel();
		preview.setLayout(new BoxLayout(preview, BoxLayout.Y_AXIS));
		preview.setOpaque(false);
		preview.add(new JLabel(plugin.getShortName()));

		final PluginButton activator = new PluginButton(plugin, "Activate");
		activator.setActionCommand(ACTIVATE_COMMAND);

		activator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (activator.getActionCommand().equals(ACTIVATE_COMMAND)) {
					activator.activate();
				} else {
					activator.deactivate();
				}
			}
		});

		preview.add(activator);
		pluginPreviews.put(plugin.getUniqueName(), preview);

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

	private interface IPluginActivator {
		void activate();

		void deactivate();

		IBedazzledPlugin getManagedPlugin();
	}

	private class PluginButton extends JButton implements IPluginActivator {

		private IBedazzledPlugin managedPlugin;

		public PluginButton(IBedazzledPlugin toManage, String text) {
			super(text);
			managedPlugin = toManage;
		}

		public IBedazzledPlugin getManagedPlugin() {
			return managedPlugin;
		}

		@Override
		public void activate() {
			this.setActionCommand(DEACTIVATE_COMMAND);
			this.setText("Deactivate");

			managedPlugin.start(ReferenceBedazzler.sharedInstance);

			activatePlugin(this);
		}

		@Override
		public void deactivate() {
			this.setActionCommand(ACTIVATE_COMMAND);
			this.setText("Activate");

			managedPlugin.stop();

			deactivatePlugin(this);
		}
	}

}
