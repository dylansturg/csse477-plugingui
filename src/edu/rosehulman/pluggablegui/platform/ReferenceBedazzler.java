package edu.rosehulman.pluggablegui.platform;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.SwingUtilities;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlatform;
import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;
import edu.rosehulman.pluggablegui.commons.ILogger;

public class ReferenceBedazzler implements IBedazzledPlatform {

	public static ReferenceBedazzler sharedInstance = new ReferenceBedazzler();

	private static Logger sharedLogger = new Logger();

	private Renderer renderComponent;

	private Queue<IBedazzledPlugin> registeredPluginQueue = new LinkedList<IBedazzledPlugin>();

	private ReferenceBedazzler() {
	}

	@Override
	public ILogger getLoggerForPlugin(IBedazzledPlugin plugin) {
		return sharedLogger;
	}

	protected ILogger getSharedLogger() {
		return sharedLogger;
	}

	protected void registerPlugin(final IBedazzledPlugin newPlugin) {
		// Pass on to the current renderer
		if (this.renderComponent != null) {

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ReferenceBedazzler.this.renderComponent
							.registerPlugin(newPlugin);
				}
			});

		} else {
			registeredPluginQueue.add(newPlugin);
		}
	}

	protected void unregisterPlugin(final IBedazzledPlugin removedPlugin) {
		// Pass on to the current renderer
		if (this.renderComponent != null) {

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ReferenceBedazzler.this.renderComponent
							.unregisterPlugin(removedPlugin);
				}
			});
		} else {
			logError("Attempt to unregister a plugin without a Renderer");
		}
	}

	protected void registerRenderer(Renderer component) {
		this.renderComponent = component;

		if (registeredPluginQueue.size() > 0) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					for (IBedazzledPlugin iBedazzledPlugin : registeredPluginQueue) {
						ReferenceBedazzler.this.renderComponent
								.registerPlugin(iBedazzledPlugin);
					}
				}
			});
		}
	}

	private void logError(String message) {
		System.out.println("ReferenceBedazzler encountered error: " + message);
	}
}
