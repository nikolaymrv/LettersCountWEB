package LettersCountWEB;

import java.sql.Timestamp;

public class LettersDTO {
	
	private int vowelsCountInLine;
	private int consonantsCountInLine;
	private String readedLine;
	private Timestamp timestamp;
	private String typeOfInput;

	public String getReadedLine() {
		return readedLine;
	}

	public void setReadedLine(String readedLine) {
		this.readedLine = readedLine;
	}

	public int getVowelsCountInLine() {
		return vowelsCountInLine;
	}

	public void setVowelsCountInLine(int vowelsCountInLine) {
		this.vowelsCountInLine = vowelsCountInLine;
	}

	public int getConsonantsCountInLine() {
		return consonantsCountInLine;
	}

	public void setConsonantsCountInLine(int consonantsCountInLine) {
		this.consonantsCountInLine = consonantsCountInLine;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTypeOfInput() {
		return typeOfInput;
	}

	public void setTypeOfInput(String typeOfInput) {
		this.typeOfInput = typeOfInput;
	}

	public String toString() {

		StringBuffer toStringDTO = new StringBuffer();
		toStringDTO.append(readedLine);
		toStringDTO.append("\n");
		toStringDTO.append(vowelsCountInLine);
		toStringDTO.append("\n");
		toStringDTO.append(consonantsCountInLine);
		toStringDTO.append("\n");
		toStringDTO.append(timestamp);
		toStringDTO.append("\n");
		toStringDTO.append(typeOfInput);

		return toStringDTO.toString();

	}

}
