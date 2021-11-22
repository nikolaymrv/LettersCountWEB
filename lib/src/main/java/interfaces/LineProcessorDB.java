package interfaces;

import java.util.List;

public interface LineProcessorDB {
	
	List<String> processLineFrom(String input) throws Exception;

}
