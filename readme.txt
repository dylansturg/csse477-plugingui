Bedazzled Plugin Application

The source code is available in the src folder.  The project utilizes 3 packages: commons (interfaces to be shared with plugin implementors), platform (core code for the application), and plugins (base plugins that are included in the standard application installation).

The edu.rosehulman.pluggablegui.platform.BedazzledProgram contains the main entrypoint for the application.  It can be run as a standard Java application (in Eclipse or however you prefer).

Plugins can be configured in the plugins/ directory.  Plugin JARs can be added at runtime by dropping them into the directory.  Registering new plugins takes a few seconds.

