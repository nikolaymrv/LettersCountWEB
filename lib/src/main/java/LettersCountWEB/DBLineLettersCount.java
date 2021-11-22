package LettersCountWEB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBLineLettersCount {
	
	public List<List> dbLineLettersCount(ResultSet resultSet) throws SQLException {
		int vCounter = 0;
		int cCounter = 0;
		int countConsonants = 0;
		int countVowels = 0;
		String line;

		List<Integer> lettersCount = new ArrayList<>(); //для кінечних значень
		List<String> linesCount = new ArrayList<>(); //для ліній
		List<Integer> counterList = new ArrayList<>(); //для значень кожної лінії
		List<List> lineLetterCount = new ArrayList<>(); //для всіх лістів
		
		int iterator = 0;
		int listIterator = 0;
		
		while (resultSet.next()) {
			
			line = resultSet.getString("lines");
			List<String> linesFromSQL = new ArrayList<>();
			linesFromSQL.add(line);
			//System.out.println("Line from list" + " " + iterator + ": " + linesFromSQL.get(listIterator));

			ConsonantsCounter consonantsCounter = new ConsonantsCounter();
			VowelsCounter vowelsCounter = new VowelsCounter();

			countVowels = vowelsCounter.getLettersCount(linesFromSQL.get(listIterator));
			countConsonants = consonantsCounter.getLettersCount(linesFromSQL.get(listIterator));

			linesCount.add(line);
			
			vCounter += countVowels;
			cCounter += countConsonants;

			//System.out.println("Vowels in array" + " " + countVowels);
			//System.out.println("Consonants in array" + " " + countConsonants);
			
			counterList.add(countVowels); //0 - 2 - 4 - ...
			counterList.add(countConsonants); //1 - 3 - 5 - ...
			
			iterator++;
		}
		
		lettersCount.add(vCounter); // 0
		lettersCount.add(cCounter); // 1
		
		lineLetterCount.add(linesCount); //0
		lineLetterCount.add(counterList); //1
		lineLetterCount.add(lettersCount); //2

		return lineLetterCount;
	}
}
