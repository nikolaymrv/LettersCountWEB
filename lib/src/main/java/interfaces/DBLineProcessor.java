package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface DBLineProcessor {
	
	void writeLineInDB(String input) throws ClassNotFoundException, SQLException;

	List<List> readLineFromDB() throws ClassNotFoundException, SQLException;

}
