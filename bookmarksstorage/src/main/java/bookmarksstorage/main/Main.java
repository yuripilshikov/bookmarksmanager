package bookmarksstorage.main;

import javax.swing.SwingUtilities;

import bookmarksstorage.ui.MainWindow;

public class Main {
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow mw = new MainWindow();
			}			
		});		
		
	}
}
