package edu.rosehulman.pluggablegui.platform;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

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

	private Map<String, IPluginActivator> registeredPlugins = new HashMap<String, IPluginActivator>();
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
		if (!registeredPlugins.containsKey(activator.getUniqueName())) {
			pluginTableContent.add(createPluginPreview(activator));
		}

		Renderer.this.pluginTableContent.repaint();
		Renderer.this.pluginTableContent.invalidate();
		Renderer.this.pluginTableContent.revalidate();

		Renderer.this.repaint();
		Renderer.this.invalidate();
		Renderer.this.revalidate();

	}

	private void activatePlugin(IPluginActivator activator) {

		if (this.activePlugin != null) {
			this.activePlugin.deactivate();
			this.activePlugin = null;
		}

		this.activePlugin = activator;

		JPanel pluginUI = this.activePlugin.getManagedPlugin().render();
		if (pluginUI != null) {
			this.pluginUIPanel.add(pluginUI);
		}

		Renderer.this.pluginTableContent.repaint();
		Renderer.this.pluginTableContent.invalidate();
		Renderer.this.pluginTableContent.revalidate();

		Renderer.this.repaint();
		Renderer.this.invalidate();
		Renderer.this.revalidate();

	}

	private void deactivatePlugin(IPluginActivator activator) {

		if (activator != this.activePlugin) {
			throw new IllegalArgumentException(
					"Renderer Internal Consistency error: attempt to deactivate non-action plugin.");
		}

		this.activePlugin = null;
		this.pluginUIPanel.removeAll();

		Renderer.this.pluginTableContent.repaint();
		Renderer.this.pluginTableContent.invalidate();
		Renderer.this.pluginTableContent.revalidate();

		Renderer.this.repaint();
		Renderer.this.invalidate();
		Renderer.this.revalidate();
	}

	private JPanel createPluginPreview(final IBedazzledPlugin plugin) {
		if (registeredPlugins.containsKey(plugin.getUniqueName())) {
			return pluginPreviews.get(plugin.getUniqueName());
		}

		JPanel preview = new JPanel();
		preview.setBorder(new EmptyBorder(5, 10, 0, 0));
		preview.setAlignmentX(Component.LEFT_ALIGNMENT);

		preview.setLayout(new BoxLayout(preview, BoxLayout.Y_AXIS));
		preview.setOpaque(false);
		JLabel pluginLabel = new JLabel(plugin.getShortName());
		pluginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		preview.add(pluginLabel);

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

		final JButton uninstall = new JButton("Uninstall");
		uninstall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unregisterPlugin(plugin);
			}
		});

		JPanel buttonFrame = new JPanel();
		buttonFrame.setLayout(new BoxLayout(buttonFrame, BoxLayout.X_AXIS));
		buttonFrame.setOpaque(false);
		buttonFrame.add(activator);
		buttonFrame.add(uninstall);
		buttonFrame.setAlignmentX(Component.LEFT_ALIGNMENT);

		preview.add(buttonFrame);
		pluginPreviews.put(plugin.getUniqueName(), preview);
		registeredPlugins.put(plugin.getUniqueName(), activator);

		return preview;
	}

	public void registerPlugins(List<IBedazzledPlugin> activators) {
		for (IBedazzledPlugin iBedazzledPlugin : activators) {
			this.registerPlugin(iBedazzledPlugin);
		}
	}

	public synchronized void unregisterPlugin(IBedazzledPlugin activator) {
		if (registeredPlugins.containsKey(activator.getUniqueName())) {
			IPluginActivator pluginActivator = registeredPlugins.get(activator
					.getUniqueName());
			if (pluginActivator.isActive()) {
				pluginActivator.deactivate();
			}

			registeredPlugins.remove(activator.getUniqueName());

			JPanel preview = pluginPreviews.get(activator.getUniqueName());
			if (preview != null) {
				pluginTableContent.remove(preview);
				pluginPreviews.remove(activator.getUniqueName());
			}
		}

		Renderer.this.pluginTableContent.repaint();
		Renderer.this.pluginTableContent.invalidate();
		Renderer.this.pluginTableContent.revalidate();

		Renderer.this.repaint();
		Renderer.this.invalidate();
		Renderer.this.revalidate();
	}

	public void unregisterPlugins(List<IBedazzledPlugin> activators) {
		for (IBedazzledPlugin iBedazzledPlugin : activators) {
			this.unregisterPlugin(iBedazzledPlugin);
		}
	}

	public void terminatePlugin(String pluginId) {
		// TODO Possible future feature
	}

	private interface IPluginActivator {
		void activate();

		void deactivate();

		boolean isActive();

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

		@Override
		public boolean isActive() {
			return getActionCommand().equals(DEACTIVATE_COMMAND);
		}
	}

}
