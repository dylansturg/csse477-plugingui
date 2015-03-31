package edu.rosehulman.pluggablegui.platform;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlatform;
import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;
import edu.rosehulman.pluggablegui.commons.ILogger;

public class ReferenceBedazzler implements IBedazzledPlatform {

	public static ReferenceBedazzler sharedInstance = new ReferenceBedazzler();

	private Renderer renderComponent;

	private ReferenceBedazzler() {
	}

	@Override
	public ILogger getLoggerForPlugin(IBedazzledPlugin plugin) {
		// TODO Auto-generated method stub
		return null;
	}

	protected ILogger getSharedLogger() {
		return null;
	}

	protected void registerPlugin(IBedazzledPlugin newPlugin) {
		// Pass on to the current renderer
		if (this.renderComponent != null) {
			this.renderComponent.registerPlugin(newPlugin);
		} else {
			logError("Attempt to register a plugin without a Renderer");
		}
	}

	protected void unregisterPlugin(IBedazzledPlugin removedPlugin) {
		// Pass on to the current renderer
		if (this.renderComponent != null) {
			this.renderComponent.unregisterPlugin(removedPlugin);
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
