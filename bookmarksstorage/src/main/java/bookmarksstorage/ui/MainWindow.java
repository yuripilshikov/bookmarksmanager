package bookmarksstorage.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import bookmarksstorage.dao.Dao;
import bookmarksstorage.model.Bookmark;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	BookmarkDao dao;

	JButton btnSave, btnCancel, btnDelete, btnNew;

	// temporary
	private final String[] categories = { "Blender", "Java", "Krita" };

	public MainWindow() {
		super("Bookmark database");

		// logic part
		dao = new BookmarkDao();

		// frame related
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);

		populateWindow();
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
		JMenuItem m12 = new JMenuItem("New database");
		JMenuItem m13 = new JMenuItem("New dummy database");
		JMenuItem m22 = new JMenuItem("Quit");
		ob1.add(m11);
		ob1.add(m12);
		ob1.add(m13);
		ob1.add(m22);
		this.setJMenuBar(ob);
	}

	private void populateWindow() {

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (Bookmark b : dao.getAll()) {
			listModel.addElement(b.getName());
		}
		final JList<String> bookmarkList = new JList<>(listModel);
		bookmarkList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(bookmarkList.getSelectedIndex() + " " + bookmarkList.getSelectedValue());								
			}
		});

		JComboBox<String> cat = new JComboBox<String>(categories);
		listPanel.add(cat, BorderLayout.NORTH);
		listPanel.add(bookmarkList, BorderLayout.CENTER);

		JPanel bookmarkProperties = new JPanel();
		bookmarkProperties.setLayout(new BoxLayout(bookmarkProperties, BoxLayout.Y_AXIS));

		JLabel lName = new JLabel("Channel name");
		JLabel lDesc = new JLabel("Description");
		JLabel lUrl = new JLabel("Link");
		JLabel lGrade = new JLabel("Grade");

		JTextArea tName = new JTextArea();
		tName.setColumns(20);
		JTextArea tDesc = new JTextArea();
		tDesc.setColumns(20);
		tDesc.setLineWrap(true);
		tDesc.setRows(5);

		JTextArea tUrl = new JTextArea();
		tUrl.setColumns(20);
		JTextArea tGrade = new JTextArea();
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
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(dao.get(0));
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(dao.get(0));
			}
		});

		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(dao.get(0));
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

		add(jsp);

	}

}
