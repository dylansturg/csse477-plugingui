package edu.rosehulman.pluggablegui.platform;

import javax.swing.SwingUtilities;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlatform;
import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;
import edu.rosehulman.pluggablegui.commons.ILogger;

public class ReferenceBedazzler implements IBedazzledPlatform {

	public static ReferenceBedazzler sharedInstance = new ReferenceBedazzler();

	private static Logger sharedLogger = new Logger();

	private Renderer renderComponent;

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
			logError("Attempt to register a plugin without a Renderer");
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
	}

	private void logError(String message) {
		System.out.println("ReferenceBedazzler encountered error: " + message);
	}
}
