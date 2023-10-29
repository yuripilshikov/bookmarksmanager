package bookmarksstorage.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bookmarksstorage.dao.DatabaseHandler;
import bookmarksstorage.model.Bookmark;

public class ListFrame extends JPanel{

	private static final long serialVersionUID = 1L;

	MainWindow mw;
	
	DefaultListModel<Bookmark> listModel;
	JList<Bookmark> bookmarkList;
	JComboBox<String> categoriesCB;
	
//	String[] categories;
//	String selectedCategory;
	
	public ListFrame(final MainWindow mw) {
		this.mw = mw;
		
		this.setLayout(new BorderLayout());
		listModel = new DefaultListModel<>();
		categoriesCB = new JComboBox<String>();
		
		categoriesCB.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Changed: " + categoriesCB.getSelectedItem());
				try {
					refreshBookmarkList(DatabaseHandler.getBookmarksByCategory(mw.getDBFile().getAbsolutePath(), categoriesCB.getSelectedItem().toString()));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}			
		});
		
		this.add(categoriesCB, BorderLayout.NORTH);
		
		bookmarkList = new JList<>(listModel);
		
		bookmarkList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Bookmark b = bookmarkList.getSelectedValue();
				mw.refreshPropertiesFrame(b);
			}
		});		
		
		this.add(bookmarkList, BorderLayout.CENTER);
	}

	public void refreshCategories(String[] categories) {
		for(String c : categories) {
			categoriesCB.addItem(c);
		}
	}
	
	private void refreshBookmarkList(List<Bookmark> bookmarksByCategory) {
		listModel.clear();
		for(Bookmark b : bookmarksByCategory) {
			listModel.addElement(b);
		}
	}

	public String getCurrentCategory() {
		return categoriesCB.getSelectedItem().toString();
	}	
}
