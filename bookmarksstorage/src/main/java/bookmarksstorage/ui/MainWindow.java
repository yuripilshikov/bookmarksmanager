package bookmarksstorage.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import bookmarksstorage.dao.DatabaseHandler;
import bookmarksstorage.model.Bookmark;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
			
	private ListFrame listPanel;
	private PropertiesFrame bookmarkProperties;	
	private File dbFile;
		
	public MainWindow() {
		super("Bookmark database");
		dbFile = new File("test.db");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		
		createMainMenu();
		
		listPanel = new ListFrame(this);
		bookmarkProperties = new PropertiesFrame(this);
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, bookmarkProperties);
		this.add(jsp);
		
		this.setVisible(true);
	}

	private void createMainMenu() {
		JMenuBar ob = new JMenuBar();
		JMenu ob1 = new JMenu("File");
		JMenu ob2 = new JMenu("Help");
		ob.add(ob1);
		ob.add(ob2);
		JMenuItem m11 = new JMenuItem("Connect to database...");
		m11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION) {
					dbFile = fileChooser.getSelectedFile();
					try {
						listPanel.refreshCategories(DatabaseHandler.getCategories(dbFile.getAbsolutePath()));
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}					
				}
			}
		});
		JMenuItem m12 = new JMenuItem("New default database");
		m12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseHandler.createStandardDB(dbFile.getAbsolutePath());
					listPanel.refreshCategories(DatabaseHandler.getCategories(dbFile.getAbsolutePath()));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		JMenuItem m22 = new JMenuItem("Quit");
		ob1.add(m11);
		ob1.add(m12);
		ob1.add(m22);
		this.setJMenuBar(ob);
	}

	public void refreshPropertiesFrame(Bookmark b) {
		bookmarkProperties.refresh(b);
		// TODO Auto-generated method stub
		
	}

	public String getCurrentCategory() {		
		return listPanel.getCurrentCategory();
	}

	public File getDBFile() {
		return dbFile;
	}


}
