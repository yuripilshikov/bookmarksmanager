package bookmarksstorage.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	
	Optional<T> get(long id);
    
    List<T> getAll() throws ClassNotFoundException;
    
    void save(T t) throws ClassNotFoundException;
    
    void update(T t, String[] params);
    
    void delete(T t);
    
    void createDB(String string) throws ClassNotFoundException;
}
