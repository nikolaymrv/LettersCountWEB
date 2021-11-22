package LettersCountWEB;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import interfaces.LineProcessor;

public class LineFromFile implements LineProcessor{

	@Override
	public List<Integer> processLineFrom(String input) throws Exception {
		int i = 1;
		int vCounter = 0;
		int cCounter = 0;
		int countConsonants = 0;
		int countVowels = 0;
		final String NO = "No";
		final String YES = "Yes";

		ConsonantsCounter consonantsCounter = new ConsonantsCounter();
		VowelsCounter vowelsCounter = new VowelsCounter();
		MySQLWriteLineInDB writeLineInBD = new MySQLWriteLineInDB();
		MongoDBConnSingleton mongoDBConnSingle = new MongoDBConnSingleton();

		List<Integer> letterCount = new ArrayList<>();

		FileReader fileReader = new FileReader(input);
		Scanner scannerFileReader = new Scanner(fileReader);
		String lineFromFile = null;
		

		System.out.println("You wanna write this line in DB? (Yes or No)");
		
		Scanner scannerLineFromDatabase = new Scanner(System.in);
		String choiceDB = scannerLineFromDatabase.nextLine();

		if (choiceDB.equals(YES)) {
			while (scannerFileReader.hasNextLine()) {
				
				lineFromFile = scannerFileReader.nextLine();
				System.out.println("Line" + " " + i + " " + ":" + lineFromFile);
				writeLineInBD.writeLineInDB(lineFromFile);
				countVowels = vowelsCounter.getLettersCount(lineFromFile);
				countConsonants = consonantsCounter.getLettersCount(lineFromFile);

				vCounter += countVowels;
				cCounter += countConsonants;

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				final String FROM_FILE_TYPE = "From file";

				LettersDTO lettersDTO = new LettersDTO();
				lettersDTO.setVowelsCountInLine(countVowels);
				lettersDTO.setConsonantsCountInLine(countConsonants);
				lettersDTO.setReadedLine(lineFromFile);
				lettersDTO.setTimestamp(timestamp);
				lettersDTO.setTypeOfInput(FROM_FILE_TYPE);

				mongoDBConnSingle.insertUser(lettersDTO);

				System.out.println("Vowels in array" + " " + countVowels);
				System.out.println("Consonants in array" + " " + countConsonants);

				i++;
			}
		}

		else if (choiceDB.equals(NO)) {
			while (scannerFileReader.hasNextLine()) {
				lineFromFile = scannerFileReader.nextLine();
				System.out.println("Line" + " " + i + " " + ":" + lineFromFile);
				countVowels = vowelsCounter.getLettersCount(lineFromFile);
				countConsonants = consonantsCounter.getLettersCount(lineFromFile);

				vCounter += countVowels;
				cCounter += countConsonants;

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				final String FROM_FILE_TYPE = "From file";

				LettersDTO lettersDTO = new LettersDTO();
				lettersDTO.setVowelsCountInLine(countVowels);
				lettersDTO.setConsonantsCountInLine(countConsonants);
				lettersDTO.setReadedLine(lineFromFile);
				lettersDTO.setTimestamp(timestamp);
				lettersDTO.setTypeOfInput(FROM_FILE_TYPE);

				mongoDBConnSingle.insertUser(lettersDTO);

				System.out.println("Vowels in array" + " " + countVowels);
				System.out.println("Consonants in array" + " " + countConsonants);

				i++;
			}
		}

		letterCount.add(vCounter); // 0
		letterCount.add(cCounter); // 1

		return letterCount;
	}

}
