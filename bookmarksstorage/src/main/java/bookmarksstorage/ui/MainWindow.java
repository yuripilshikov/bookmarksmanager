package bookmarksstorage.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bookmarksstorage.dao.BookmarkDao;
import bookmarksstorage.model.Bookmark;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	BookmarkDao dao;
	JButton btnSave, btnCancel, btnDelete, btnNew;
	JTextArea tName, tDesc, tUrl, tGrade;
	DefaultListModel<Bookmark> listModel;
	JList<Bookmark> bookmarkList;
	File database;
	String[] categories;
	String selectedCategory;

	public MainWindow() {
		super("Bookmark database");

		dao = new BookmarkDao();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);

		createMainMenu();
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
					database = fileChooser.getSelectedFile();
					dao.setURI(database.getName());
					System.out.println(database.getAbsolutePath());
					try {
						dao.refreshCategories();
						populateWindow();
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
					dao.createDB("test.db");
					dao.refreshCategories();

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				categories = dao.getCategories();
				populateWindow();
				refreshList();
			}
		});
		JMenuItem m22 = new JMenuItem("Quit");
		ob1.add(m11);
		ob1.add(m12);
		ob1.add(m22);
		this.setJMenuBar(ob);
	}

	private void populateWindow() {
		JPanel listPanel = new JPanel();

		listPanel.setLayout(new BorderLayout());
		listModel = new DefaultListModel<>();

		JComboBox<String> cat = new JComboBox<String>(categories);
		selectedCategory = cat.getSelectedItem().toString();

		listPanel.add(cat, BorderLayout.NORTH);

		for (Bookmark b : dao.getBookmarksByCategory(selectedCategory)) {
			listModel.addElement(b);
		}
		bookmarkList = new JList<>(listModel);
		bookmarkList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Bookmark b = bookmarkList.getSelectedValue();
				try {
					tName.setText(b.getName());
					tDesc.setText(b.getDescription());
					tUrl.setText(b.getLink());
					tGrade.setText("" + b.getGrade());
				} catch (Exception exx) {
					exx.printStackTrace();
				}
			}
		});

		listPanel.add(bookmarkList, BorderLayout.CENTER);
		

		JPanel bookmarkProperties = new JPanel();
		bookmarkProperties.setLayout(new BoxLayout(bookmarkProperties, BoxLayout.Y_AXIS));

		JLabel lName = new JLabel("Channel name");
		JLabel lDesc = new JLabel("Description");
		JLabel lUrl = new JLabel("Link");
		JLabel lGrade = new JLabel("Grade");

		tName = new JTextArea();
		tName.setColumns(20);
		tDesc = new JTextArea();
		tDesc.setColumns(20);
		tDesc.setLineWrap(true);
		tDesc.setRows(5);

		tUrl = new JTextArea();
		tUrl.setColumns(20);
		tGrade = new JTextArea();
		tGrade.setColumns(20);

		JPanel nameP = new JPanel();
		nameP.add(lName);
		nameP.add(tName);

		JPanel descP = new JPanel();
		descP.add(lDesc);
		descP.add(tDesc);

		JPanel urlP = new JPanel();
		urlP.add(lUrl);
		urlP.add(tUrl);

		JPanel gradeP = new JPanel();
		gradeP.add(lGrade);
		gradeP.add(tGrade);

		// buttons
		JPanel buttonP = new JPanel();
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(dao.get(0));
			}
		});

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() { // refresh
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshList();
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// not working...
//				listModel.removeElement(bookmarkList.getSelectedValue());
//				dao.delete(bookmarkList.getSelectedValue());				
//				bookmarkList.ensureIndexIsVisible(listModel.getSize());
			}
		});

		btnNew = new JButton("Create new");
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				Bookmark b = new Bookmark(99, tName.getText(), tDesc.getText(), tUrl.getText(),
//						Integer.parseInt(tGrade.getText())); // TODO remove magic number
//				try {
//					dao.save(b);
//				} catch (ClassNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				listModel.addElement(b);
//				bookmarkList.ensureIndexIsVisible(listModel.getSize());

			}
		});

		buttonP.add(btnSave);
		buttonP.add(btnCancel);
		buttonP.add(btnDelete);
		buttonP.add(btnNew);

		bookmarkProperties.add(nameP);
		bookmarkProperties.add(descP);
		bookmarkProperties.add(urlP);
		bookmarkProperties.add(gradeP);
		bookmarkProperties.add(buttonP);

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, bookmarkProperties);

		this.add(jsp);

	}

	private void refreshList() {
		listModel.clear();
//		for (Bookmark b : dao.getAll()) {
//			listModel.addElement(b);
//		}
		bookmarkList.ensureIndexIsVisible(listModel.getSize());
	}

}
