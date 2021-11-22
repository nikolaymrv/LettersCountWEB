package LettersCountWEB;

import java.util.ArrayList;
import java.util.List;
import interfaces.LineProcessor;

public class LineFromConsole implements LineProcessor{

	@Override
	public List<Integer> processLineFrom(String input) throws Exception {
List<Integer> lettersCount = new ArrayList<>();
		
		int vCounter = 0;
		int cCounter = 0;
		int countConsonants = 0;
		int countVowels = 0;
		
		ConsonantsCounter consonants = new ConsonantsCounter();
		VowelsCounter vowels = new VowelsCounter();

		countConsonants = consonants.getLettersCount(input);
		countVowels = vowels.getLettersCount(input);

		vCounter += countVowels;
		cCounter += countConsonants;

		lettersCount.add(vCounter);
		lettersCount.add(cCounter);

		System.out.println("Vowels in array" + " " + countVowels);
		System.out.println("Consonants in array" + " " + countConsonants);

		return lettersCount;
	}

}
