package edu.rosehulman.pluggablegui.platform;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader implements Runnable {
	
	private static List<String> pluginList = new ArrayList<String>();
	private static String extensionToUse = ".jar";
	
	
	public static List<String> getPluginList(){
		return pluginList;
	}

	public static void findExistingFiles(Path folder) {
		File dir = new File(folder.toString());
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				// Do something with child
				if(child.toString().contains(extensionToUse)){
					System.out.println(child);
					pluginList.add(child.toString());
					
				}
			}
		} else {
			// Handle the case where dir is not really a directory.
			// Checking dir.isDirectory() above would not be sufficient
			// to avoid race conditions with another process that deletes
			// directories.
		}
	}

	public static void watchDirectoryPath(Path path) {
		// Sanity check - Check if path is a folder
		try {
			Boolean isFolder = (Boolean) Files.getAttribute(path,
					"basic:isDirectory", NOFOLLOW_LINKS);
			if (!isFolder) {
				throw new IllegalArgumentException("Path: " + path
						+ " is not a folder");
			}
		} catch (IOException ioe) {
			// Folder does not exists
			ioe.printStackTrace();
		}

		System.out.println("Watching path: " + path);

		// We obtain the file system of the Path
		FileSystem fs = path.getFileSystem();

		// We create the new WatchService using the new try() block
		try (WatchService service = fs.newWatchService()) {

			// We register the path to the service
			// We watch for creation events
			path.register(service, ENTRY_CREATE);

			// Start the infinite polling loop
			WatchKey key = null;
			while (true) {
				key = service.take();

				// Dequeueing events
				Kind<?> kind = null;
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					// Get the type of the event
					kind = watchEvent.kind();
					if (OVERFLOW == kind) {
						continue; // loop
					} else if (ENTRY_CREATE == kind) {
						// A new Path was created
						Path newPath = ((WatchEvent<Path>) watchEvent)
								.context();
						// Output
						if (newPath.toString().contains(extensionToUse)) {
							System.out.println("New path created: " + newPath);
							//Call manager or something to notify about new jar to install
						}
					}
				}

				if (!key.reset()) {
					break; // loop
				}
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	@Override
	public void run() {
		// Folder we are going to watch
		Path folder = Paths.get("plugins");
		findExistingFiles(folder);
		watchDirectoryPath(folder);
		
	}
}
