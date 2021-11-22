package LettersCountWEB;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnSingleton {
	private static MongoClient mClient;
	
	   Properties prop = new Properties();
	    private MongoClient getMongoClient() {

	        if (mClient == null) {
	        	
	        	try (FileInputStream input = new FileInputStream("D:\\Programs\\Programming\\Workspace\\LettersCountWEB\\lib\\src\\main\\resources\\my.properties")) {

	                prop.load(input);
	    	
	            mClient = MongoClients.create(prop.getProperty("url"));
	            } catch (IOException ex) {
	    		ex.printStackTrace();
	    	}
	            
	        }
	        return mClient;
	    }
	    
	    private MongoDatabase getDB() {
	        return getMongoClient().getDatabase(prop.getProperty("database"));
	    }
	   
	    private MongoCollection<Document> getUserCollection() {
	        return getDB().getCollection(prop.getProperty("collection"));
	    }
	    
	    public void insertUser(LettersDTO lettersDTO) {
	    	org.apache.log4j.BasicConfigurator.configure();

	    	var todoDocument = new Document(Map.of("_id", new ObjectId(), "Line", lettersDTO.getReadedLine(),
					"Type of input", lettersDTO.getTypeOfInput(), "Vowels", lettersDTO.getVowelsCountInLine(),
					"Consonants", lettersDTO.getConsonantsCountInLine(), "Date of create", lettersDTO.getTimestamp()));
	    	
	    	getUserCollection().insertOne(todoDocument);

	    }
	    
	    public void queryUsers() {
	    	org.apache.log4j.BasicConfigurator.configure();

	    	getUserCollection().find().forEach((Consumer<? super Document>) System.out::println);    	
	    	
	    }
	    
	    public static void main(String[] args) {
	    	MongoDBConnSingleton mongoConnSingle = new MongoDBConnSingleton();
	    	//mongoConnSingle.insertUser(null);
	    	mongoConnSingle.queryUsers();
		}
}
