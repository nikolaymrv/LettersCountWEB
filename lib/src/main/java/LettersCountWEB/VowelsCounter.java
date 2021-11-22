package LettersCountWEB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import interfaces.LettersCounter;

public class VowelsCounter implements LettersCounter{
		
	List<Character> vowels = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

	@Override
	public int getLettersCount(String input) {
		int vowelsCount = 0;

		char[] lineToChars = input.toLowerCase().toCharArray();

		for (int i = 0; i < input.length(); i++) {
			if (vowels.contains(lineToChars[i])) {
				vowelsCount++;
			}

		}
		return vowelsCount;
	}
}
