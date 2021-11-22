package LettersCountWEB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import interfaces.DBLineProcessor;

public class MySQLWriteLineInDB implements DBLineProcessor{

	@Override
	public void writeLineInDB(String input) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/count_letters_database?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
		String userName = "root";
		String pass = "vfkfcdjkjx";

		Statement statement = null;

		try (Connection connection = DriverManager.getConnection(url, userName, pass)) {
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			int result = statement.executeUpdate("INSERT INTO lines_for_analysis VALUES ('" + input + "')");
			System.out.println("Added" + " " + result + " " + "rows");
		}
		
	}

	@Override
	public List<List> readLineFromDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/count_letters_database?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
		String userName = "root";
		String pass = "vfkfcdjkjx";

		List<List> allDataList = new ArrayList<>();

		Statement statement = null;
		ResultSet resultSet = null;

		DBLineLettersCount writeResultSet = new DBLineLettersCount();

		try (Connection connection = DriverManager.getConnection(url, userName, pass)) {
			System.out.println("Connection Succesful");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from count_letters_database.lines_for_analysis");
			allDataList = writeResultSet.dbLineLettersCount(resultSet);
		}
		return allDataList;
	}

}
