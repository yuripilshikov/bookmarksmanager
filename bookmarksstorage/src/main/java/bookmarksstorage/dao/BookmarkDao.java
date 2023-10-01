package bookmarksstorage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import bookmarksstorage.model.Bookmark;

public class BookmarkDao implements Dao<Bookmark> {
	private String uri;
	
	private Map<Integer, String> categories = new HashMap<>();

	
	public List<Bookmark> getBookmarksByCategory(String category) {		
		List<Bookmark> bookmarks = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
				System.out.println(connection.isClosed());
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);
				ResultSet rs = statement.executeQuery("SELECT b.id, b.Name, b.Description, b.link, b.grade, c.Category \r\n"
						+ "FROM BookmarkCategories as bc\r\n"
						+ "INNER JOIN Bookmarks as b \r\n"
						+ "inner join Categories as c\r\n"
						+ "on bc.CategoryID = c.id and bc.BookmarkID = b.id\r\n"
						+ "WHERE c.Category LIKE '" + category + "'");
				 while(rs.next())
			      {
					 Bookmark b = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("link"), rs.getInt("grade"));
					 System.out.println(">>> new bookmark: " +rs.getString("name") );
				        bookmarks.add(b);
			      }	

			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			} 

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Here are " + bookmarks.size() + " bookmarks.");
				
		return bookmarks;
	}
	
	public List<String> getCategories(Bookmark b) {
		
		
		return null;
	}
	
	public void refreshCategories() throws ClassNotFoundException {		
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			System.out.println(connection.isClosed());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
//			ResultSet rs1 = connection.getMetaData().getTables(null, null, null, null);
//		    while (rs1.next()) {
//		        System.out.println(rs1.getString("TABLE_NAME"));
//		    }
			ResultSet rs = statement.executeQuery("SELECT id, Category FROM Categories;");
			 while(rs.next())
		      {
		    	categories.put(rs.getInt("id"), rs.getString("Category"));
		    	System.out.println(rs.getInt("id") + "\t" + rs.getString("Category"));
		      }	

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} 
	}
	
	
	// TODO
	/*-- запрос списка категорий
	 * select c.Category 
FROM BookmarkCategories as bc 
inner join Categories as c 
ON bc.CategoryID = c.id 
WHERE bc.BookmarkID = 2 -- bookmarkID is a bookmark's id
	 * 
	 * */
	
	
	

	@Override
	public Optional<Bookmark> get(long id) {
		// TODO Auto-generated method stub
		//return Optional.ofNullable(bookmarks.get((int) id));
		return null;
	}

	@Override
	public List<Bookmark> getAll() throws ClassNotFoundException {
		List<Bookmark> bookmarks = new ArrayList<>();
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("select * from bookmark");
			 while(rs.next())
		      {
		    	Bookmark b = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("link"), rs.getInt("grade"));
		        bookmarks.add(b);
		      }	

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} 
				
		return bookmarks;
	}

	@Override
	public void save(Bookmark t) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("insert into bookmark (name, description, link, grade, category) values ('test1', 'test2', 'test3', 1, 'test4')");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} 
	}

	@Override
	public void update(Bookmark t, String[] params) {
		//
	}

	@Override
	public void delete(Bookmark t) {
		//bookmarks.remove(t);
	}
	
	public String[] getCategories(){
		
		String[] cat = new String[categories.size()];
		for(Entry<Integer,String> entry : categories.entrySet()) {
			cat[entry.getKey()-1] = entry.getValue();
		}

		return cat;
	}

	@Override
	public void createDB(String uri) throws ClassNotFoundException {		
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("drop table if exists Bookmarks");
			statement.executeUpdate("drop table if exists Categories");
			statement.executeUpdate("drop table if exists BookmarkCategories ");
			
			statement.executeUpdate("CREATE TABLE Bookmarks (\r\n"
					+ "	id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
					+ "	Name TEXT,\r\n"
					+ "	Description TEXT,\r\n"
					+ "	link TEXT,\r\n"
					+ "	grade INTEGER\r\n"
					+ ");");
			
			statement.executeUpdate("CREATE TABLE Categories (\r\n"
					+ "	id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
					+ "	Category TEXT\r\n"
					+ ");");
			
			statement.executeUpdate("CREATE TABLE BookmarkCategories (\r\n"
					+ "	id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
					+ "	CategoryID INTEGER,\r\n"
					+ "	BookmarkID INTEGER,\r\n"
					+ "	CONSTRAINT BookmarkCategories_FK FOREIGN KEY (BookmarkID) REFERENCES Bookmarks(id),\r\n"
					+ "	CONSTRAINT BookmarkCategories_FK_1 FOREIGN KEY (CategoryID) REFERENCES Categories(id)\r\n"
					+ ");");
			
			statement.executeUpdate("INSERT INTO Bookmarks\r\n"
					+ "(Name, Description, link, grade)\r\n"
					+ "VALUES('name 1', 'Test 1', 'www.www', 2);");
			
			statement.executeUpdate("INSERT INTO Bookmarks\r\n"
					+ "(Name, Description, link, grade)\r\n"
					+ "VALUES('name 2', 'Test 2', 'fff.fff', 1);");
			
			statement.executeUpdate("INSERT INTO Categories\r\n"
					+ "(Category)\r\n"
					+ "VALUES('Blender');");
			statement.executeUpdate("INSERT INTO Categories\r\n"
					+ "(Category)\r\n"
					+ "VALUES('Krita');");
			statement.executeUpdate("INSERT INTO BookmarkCategories\r\n"
					+ "(id, CategoryID, BookmarkID)\r\n"
					+ "VALUES(1, 1, 1);");
			statement.executeUpdate("INSERT INTO BookmarkCategories\r\n"
					+ "(id, CategoryID, BookmarkID)\r\n"
					+ "VALUES(2, 2, 2);");
			statement.executeUpdate("INSERT INTO BookmarkCategories\r\n"
					+ "(id, CategoryID, BookmarkID)\r\n"
					+ "VALUES(3, 1, 2);");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} 
		this.uri = uri;
		refreshCategories();
	}
	
	public void setURI(String uri) {
		this.uri = uri;
	}
}
