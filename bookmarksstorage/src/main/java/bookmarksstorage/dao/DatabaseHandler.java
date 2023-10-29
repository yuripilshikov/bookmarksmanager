package bookmarksstorage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bookmarksstorage.model.Bookmark;

public class DatabaseHandler {
	public static List<Bookmark> getBookmarksByCategory(String uri, String category) throws ClassNotFoundException{
		List<Bookmark> bookmarks = new ArrayList<>();
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri);
				Statement statement = connection.createStatement()) {
			
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT b.id, b.Name, b.Description, b.link, b.grade, c.Category \r\n"
					+ "FROM BookmarkCategories as bc\r\n"
					+ "INNER JOIN Bookmarks as b \r\n"
					+ "inner join Categories as c\r\n"
					+ "on bc.CategoryID = c.id and bc.BookmarkID = b.id\r\n"
					+ "WHERE c.Category LIKE '" + category + "'");
			
			while(rs.next()) {
				 Bookmark b = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("link"), rs.getInt("grade"));
			     bookmarks.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookmarks;
	}
	
	public static String[] getCategories(String uri) throws ClassNotFoundException {
		List<String> categories = new ArrayList<>();
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri);
				Statement statement = connection.createStatement()) {
			
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT category FROM categories");
			while(rs.next()) {
				categories.add(rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] categoriesArray = new String[categories.size()];
		for(int i = 0; i < categories.size(); i++) {
			categoriesArray[i] = categories.get(i);			
		}
		
		System.out.println(Arrays.toString(categoriesArray));		
		return categoriesArray;
	}
	
	public static void saveBookmark(String uri, String category, Bookmark b) throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			PreparedStatement pstmt = connection.prepareStatement("insert into bookmarks (name, description, link, grade) values (?, ?, ?, ?)");
			pstmt.setQueryTimeout(30);
			pstmt.setString(1, b.getName());
			pstmt.setString(2, b.getDescription());
			pstmt.setString(3, b.getLink());
			pstmt.setInt(4, b.getGrade());
			pstmt.executeUpdate();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("select id from bookmarks where name = '" + b.getName() + "'");
			int id = rs.getInt("id");
			
			rs = statement.executeQuery("select id from Categories where Category = '" + category + "'");
			int catId = rs.getInt("id");
			
			pstmt = connection.prepareStatement("insert into BookmarkCategories (CategoryID, BookmarkID) values (?, ?) ");
			pstmt.setInt(1, id);
			pstmt.setInt(2, catId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void createStandardDB(String uri) throws ClassNotFoundException {
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
			statement.executeUpdate("INSERT INTO Categories\r\n"
					+ "(Category)\r\n"
					+ "VALUES('Drawing');");
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
	}
}
