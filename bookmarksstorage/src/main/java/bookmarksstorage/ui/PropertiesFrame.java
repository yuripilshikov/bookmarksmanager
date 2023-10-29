package bookmarksstorage.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import bookmarksstorage.dao.DatabaseHandler;
import bookmarksstorage.model.Bookmark;

public class PropertiesFrame extends JPanel {
	private static final long serialVersionUID = 1L;

	MainWindow mw;

	JButton btnSave, btnCancel, btnDelete, btnNew;
	JTextArea tName, tDesc, tUrl, tGrade;

	public PropertiesFrame(final MainWindow mw) {
		this.mw = mw;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel lName = new JLabel("Channel name");
		JLabel lDesc = new JLabel("Description");
		JLabel lUrl = new JLabel("Link");
		JLabel lGrade = new JLabel("Grade");

		tName = new JTextArea();
		tName.setColumns(20);
		tDesc = new JTextArea();
		tDesc.setColumns(20);
		tDesc.setLineWrap(true);
		tDesc.setRows(8);

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

			}
		});

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() { // refresh
			@Override
			public void actionPerformed(ActionEvent e) {
				// refreshList();
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// not working...
//						listModel.removeElement(bookmarkList.getSelectedValue());
//						dao.delete(bookmarkList.getSelectedValue());				
//						bookmarkList.ensureIndexIsVisible(listModel.getSize());
			}
		});

		btnNew = new JButton("Create new");
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bookmark b = new Bookmark(1, tName.getText(), tDesc.getText(), tUrl.getText(),
						Integer.parseInt(tGrade.getText()));
				try {
					DatabaseHandler.saveBookmark(mw.getDBFile().getAbsolutePath(), mw.getCurrentCategory(), b);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		buttonP.add(btnSave);
		buttonP.add(btnCancel);
		buttonP.add(btnDelete);
		buttonP.add(btnNew);

		this.add(nameP);
		this.add(descP);
		this.add(urlP);
		this.add(gradeP);
		this.add(buttonP);
	}

	public void refresh(Bookmark b) {
		if (b != null) {
			tName.setText(b.getName());
			tDesc.setText(b.getDescription());
			tUrl.setText(b.getLink());
			tGrade.setText("" + b.getGrade());
		} else {
			tName.setText("");
			tDesc.setText("");
			tUrl.setText("");
			tGrade.setText("");
		}
	}
}
