package bookmarksstorage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import bookmarksstorage.model.Bookmark;

public class BookmarkDao implements Dao<Bookmark>{
		
	private List<Bookmark> bookmarks; 

	public BookmarkDao() {
		bookmarks =  new ArrayList<>();
		
		bookmarks.add(new Bookmark(0, "Test 1", "Desc", "www", 2, "Blender"));
		bookmarks.add(new Bookmark(1, "Test 1", "Desc", "www", 2, "Blender"));
		bookmarks.add(new Bookmark(2, "Test 1", "Desc", "www", 2, "Blender"));
	}

	@Override
	public Optional<Bookmark> get(long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(bookmarks.get((int)id));
	}

	@Override
	public List<Bookmark> getAll() {
		return bookmarks;
	}

	@Override
	public void save(Bookmark t) {
		bookmarks.add(t);
	}

	@Override
	public void update(Bookmark t, String[] params) {
		//
	}

	@Override
	public void delete(Bookmark t) {
		bookmarks.remove(t);
	}

	@Override
	public void connectDefaultDB(String string) {
		try {
			bookmarks = SQLiteHandler.connectToDB(string);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
