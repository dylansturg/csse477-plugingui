package edu.rosehulman.pluggablegui.platform;

import edu.rosehulman.pluggablegui.commons.IBedazzledPlatform;
import edu.rosehulman.pluggablegui.commons.IBedazzledPlugin;
import edu.rosehulman.pluggablegui.commons.ILogger;

public class ReferenceBedazzler implements IBedazzledPlatform {
	
	public static ReferenceBedazzler sharedInstance;

	@Override
	public ILogger getLoggerForPlugin(IBedazzledPlugin plugin) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected ILogger getSharedLogger(){
		return null;
	}
	
	protected void registerPlugin(IBedazzledPlugin newPlugin){
		// Pass on to the current renderer
	}
	
	protected void unregisterPlugin(IBedazzledPlugin removedPlugin){
		// Pass on to the current renderer
	}

}
