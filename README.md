A basic GUI for testing voltorb

Plug-ins to install:
Swing Designer
Swing Designer Documentation
SWT Designer
SWT Designer Core
SWT Designer Documentation
SWT Designer SWT_AWT Support
WindowBuilder Core
WindowBuilder Core Documentation
WindowBuilder Core UI
WindowBuilder GroupLayout Support

When selecting files for collectData, select in alphabetical order or it will crash.

You may need to add the necessary libraries to its build path manually.
Just click on each missing library and hit 'Edit'.
Search the file in your ADT directory and select it.

TODO: 
	1) Events in action listener need to be moved into independent threads, so that
		display willl update even when nothing is selected by player (ie. when other
		player faints and has to switch monsters mid-turn)