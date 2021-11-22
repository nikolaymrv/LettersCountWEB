package LettersCountWEB;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OptionsChoice {
	public int choice(Scanner sc) {
		boolean flag = true;
		int variant = 0;
		while (flag) {
			System.out.println("Enter the varianf of type of input:");
			System.out.println("1 = Input in console.");
			System.out.println("2 = Read from file.");
			System.out.println("3 = Read from file, just Java 7 version.");
			System.out.println("4 = Read from file, just Java 8 version.");
			System.out.println("5 = Read from file, just Java 11 version.");
			System.out.println("6 = Read from DataBase");
			System.out.println("7 = Read a document from MongoDB");
			try {
				List<Integer> variantList = new ArrayList<>();
				String input = sc.next();
				variant = Integer.parseInt(input);

				flag = false;
				variantList.add(variant);
				break;

			} catch (NumberFormatException e) {
				System.out.println("Please, enter again (incorrect data entered)");
				System.out.println("Catch Exception:" + e.getMessage() + " " + e.toString());

			}

		}
		return variant;
}
}
