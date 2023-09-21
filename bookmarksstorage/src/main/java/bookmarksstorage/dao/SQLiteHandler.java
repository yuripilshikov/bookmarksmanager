package bookmarksstorage.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmarksstorage.model.Bookmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteHandler {
	public static List<Bookmark> connectToDB(String uri) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		List<Bookmark> list = new ArrayList<>();
		Connection connection = null;
		try {
			connection= DriverManager.getConnection("jdbc:sqlite:" + uri);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			statement.executeUpdate("drop table if exists bookmark");
		      statement.executeUpdate("create table bookmark (id integer primary key autoincrement, name string, description string, link string, grade integer, category string)");
		      statement.executeUpdate("insert into bookmark(name, description, link, grade, category) values('b1', 'lorem ipsum', 'ww.www.wwww', 1, 'Blender')");
		      statement.executeUpdate("insert into bookmark(name, description, link, grade, category) values('c1', 'loremfdsfsadfsadfsdaf', 'ww.www.wsdfw', 2, 'Blender')");
		      statement.executeUpdate("insert into bookmark(name, description, link, grade, category) values('d1', 'lorem sdfsafsdfsdf', 'ww.www.sdfsfsdf', 3, 'Krita')");
		      ResultSet rs = statement.executeQuery("select * from bookmark");
		      while(rs.next())
		      {
		    	Bookmark b = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("link"), rs.getInt("grade"), rs.getString("category"));
		        list.add(b);
		      }			
		} catch(SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		}
		return list;
	}
	
	
	
	public void performRequest(String request) {
		
	}
}
