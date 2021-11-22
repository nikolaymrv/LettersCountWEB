package servlets;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import LettersCountWEB.ConsonantsCounter;
import LettersCountWEB.LineFromConsole;
import LettersCountWEB.LineFromFile;
import LettersCountWEB.MongoDBConnSingleton;
import LettersCountWEB.MySQLReadLineForAnalysis;
import LettersCountWEB.MySQLWriteLineInDB;
import LettersCountWEB.VowelsCounter;
import LettersCountWEB.LettersDTO;

@WebServlet("/LCServlet")
public class LCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<LettersDTO> lineParameters = new ArrayList<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		final String YES = "Yes";
		final String NO = "No";
		final String KEYBOARD_TYPE = "Keyboard";
		final String FROM_FILE = "From file";
		int vCounter = 0;
		int cCounter = 0;
		int countConsonants = 0;
		int countVowels = 0;
		int index = 0;
		int vowelsIndex = 0;
		int consonantsIndex = 1;

		PrintWriter writer = response.getWriter();

		LineFromConsole lineFromConsole = new LineFromConsole();
		// LineFromFile lineFromFile = new LineFromFile();

		MySQLWriteLineInDB MSQLWriteLine = new MySQLWriteLineInDB();
		MongoDBConnSingleton mongoConnSingle = new MongoDBConnSingleton();

		ConsonantsCounter consonantsCounter = new ConsonantsCounter();
		VowelsCounter vowelsCounter = new VowelsCounter();

		String lineORpath = request.getParameter("lineORpath");
		String typeOFinput = request.getParameter("typeOFinput");
		String DBinput = request.getParameter("DBinput");

		List<Integer> letterCount = new ArrayList<>();// для кінечних значень обсчета
		List<List> allDataList = new ArrayList<>();
		List<String> lineCount = new ArrayList<>(); // для ліній
		List<Integer> counterList = new ArrayList<>(); // для значень кожної лінії

		switch (typeOFinput) {
		case ("Keyboard"):
			if (YES.equals(DBinput)) {
				try {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					letterCount = lineFromConsole.processLineFrom(lineORpath);
					writer.println("<p>Line: " + lineORpath + "</p>");
					writer.println("<p>Type of input: " + typeOFinput + "</p>");
					writer.println("<p>Database input: " + DBinput + "</p>");
					writer.println("<p>Vowels count: " + letterCount.get(0) + "</p>");
					writer.println("<p>Consonants count: " + letterCount.get(1) + "</p>");
					MSQLWriteLine.writeLineInDB(lineORpath);

					LettersDTO lettersDTO = new LettersDTO();
					lettersDTO.setVowelsCountInLine(letterCount.get(0));
					lettersDTO.setConsonantsCountInLine(letterCount.get(1));
					lettersDTO.setReadedLine(lineORpath);
					lettersDTO.setTimestamp(timestamp);
					lettersDTO.setTypeOfInput(KEYBOARD_TYPE);

					lineParameters.add(lettersDTO);

					mongoConnSingle.insertUser(lettersDTO);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					writer.close();
				}
			} else if (NO.equals(DBinput)) {
				try {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					letterCount = lineFromConsole.processLineFrom(lineORpath);
					writer.println("<p>Line: " + lineORpath + "</p>");
					writer.println("<p>Type of input: " + typeOFinput + "</p>");
					writer.println("<p>Database input: " + DBinput + "</p>");
					writer.println("<p>Vowels count: " + letterCount.get(0) + "</p>");
					writer.println("<p>Consonants count: " + letterCount.get(1) + "</p>");

					LettersDTO lettersDTO = new LettersDTO();
					lettersDTO.setVowelsCountInLine(letterCount.get(0));
					lettersDTO.setConsonantsCountInLine(letterCount.get(1));
					lettersDTO.setReadedLine(lineORpath);
					lettersDTO.setTimestamp(timestamp);
					lettersDTO.setTypeOfInput(KEYBOARD_TYPE);

					lineParameters.add(lettersDTO);

					mongoConnSingle.insertUser(lettersDTO);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					writer.close();
				}
			}

			break;

		case ("File"):

			// D:\text.txt

			FileReader fileReader = new FileReader(lineORpath);
			Scanner scannerFileReader = new Scanner(fileReader);
			String lineFromFile = null;

			if (YES.equals(DBinput)) {
				try {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					while (scannerFileReader.hasNextLine()) {

						lineFromFile = scannerFileReader.nextLine();
						MSQLWriteLine.writeLineInDB(lineFromFile);
						countVowels = vowelsCounter.getLettersCount(lineFromFile);
						countConsonants = consonantsCounter.getLettersCount(lineFromFile);

						letterCount.add(countVowels);
						letterCount.add(countConsonants);

						writer.println("<p>Line: " + lineFromFile + "</p>");

						writer.println("<p>Vowels count: " + countVowels + "</p>");
						writer.println("<p>Consonants count: " + countConsonants + "</p>");

						vCounter += countVowels;
						cCounter += countConsonants;

						LettersDTO lettersDTO = new LettersDTO();
						lettersDTO.setVowelsCountInLine(letterCount.get(vowelsIndex));
						lettersDTO.setConsonantsCountInLine(letterCount.get(consonantsIndex));
						lettersDTO.setReadedLine(lineFromFile);
						lettersDTO.setTimestamp(timestamp);
						lettersDTO.setTypeOfInput(FROM_FILE);

						lineParameters.add(lettersDTO);

						mongoConnSingle.insertUser(lettersDTO);
						
						vowelsIndex = vowelsIndex + 2;
						consonantsIndex = consonantsIndex + 2;

					}
					writer.println("<p>All Vowels count: " + vCounter + "</p>");
					writer.println("<p>All Consonants count: " + cCounter + "</p>");

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					writer.close();
				}
			} else if (NO.equals(DBinput)) {
				try {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					while (scannerFileReader.hasNextLine()) {

						lineFromFile = scannerFileReader.nextLine();
						countVowels = vowelsCounter.getLettersCount(lineFromFile);
						countConsonants = consonantsCounter.getLettersCount(lineFromFile);

						letterCount.add(countVowels);
						letterCount.add(countConsonants);

						writer.println("<p>Line: " + lineFromFile + "</p>");
						writer.println("<p>Vowels count: " + countVowels + "</p>");
						writer.println("<p>Consonants count: " + countConsonants + "</p>");

						vCounter += countVowels;
						cCounter += countConsonants;

						LettersDTO lettersDTO = new LettersDTO();
						lettersDTO.setVowelsCountInLine(letterCount.get(vowelsIndex));
						lettersDTO.setConsonantsCountInLine(letterCount.get(consonantsIndex));
						lettersDTO.setReadedLine(lineFromFile);
						lettersDTO.setTimestamp(timestamp);
						lettersDTO.setTypeOfInput(FROM_FILE);

						lineParameters.add(lettersDTO);

						mongoConnSingle.insertUser(lettersDTO);
						
						vowelsIndex = vowelsIndex + 2;
						consonantsIndex = consonantsIndex + 2;

					}
					writer.println("<p>All Vowels count: " + vCounter + "</p>");
					writer.println("<p>All Consonants count: " + cCounter + "</p>");

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					writer.close();
				}
			}

			break;

		case ("DatabaseLine"):
			try {
				allDataList = MSQLWriteLine.readLineFromDB();
				lineCount = allDataList.get(0);
				counterList = allDataList.get(1);
				letterCount = allDataList.get(2);

				while (index < lineCount.size()) {
					writer.println("<p>--------------------------------------------------------------------<p>");
					writer.println("<p>Line number" + " " + index + ":" + " " + lineCount.get(index) + "<p>");
					writer.println("<p>Line vowels" + ":" + " " + counterList.get(vowelsIndex)+ "<p>");
					writer.println("<p>Line consonants" + ":" + " " + counterList.get(consonantsIndex) + "<p>");

					vowelsIndex = vowelsIndex + 2;
					consonantsIndex = consonantsIndex + 2;
					index++;
				}
				writer.println("<p><------------------------------------------------------------------><p>");
				writer.println("<p>All vowels" + ":" + " " + letterCount.get(0) + "<p>");
				writer.println("<p>All consonants" + ":" + " " + letterCount.get(1) + "<p>");
				writer.println("<p><------------------------------------------------------------------><p>");

				
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
			
		case("MongoDoc"):
			
			break;
		}

	}

}
