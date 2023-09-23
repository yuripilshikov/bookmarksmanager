package bookmarksstorage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import bookmarksstorage.model.Bookmark;

public class BookmarkDao implements Dao<Bookmark> {
	private String uri = "sample.db";

	public BookmarkDao() {

	}

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
		    	Bookmark b = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("link"), rs.getInt("grade"), rs.getString("category"));
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
	
	public String[] getCategories() throws ClassNotFoundException {
		List<String> categories = new ArrayList<>();
		
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("select distinct category from bookmark");
			 while(rs.next())
		      {
		        categories.add(rs.getString("category"));
		      }	
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} 
		
		String[] cat = new String[categories.size()];
		for(int i = 0; i < categories.size(); i++) {
			cat[i] = categories.get(i);
		}		
		return cat;
	}

	@Override
	public void createDB(String uri) throws ClassNotFoundException {		
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri)) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("drop table if exists bookmark");
			statement.executeUpdate("drop table if exists categories");
			statement.executeUpdate("create table bookmark (id integer primary key autoincrement, name string, description string, link string, grade integer, category string)");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} 
		this.uri = uri;
	}
}
