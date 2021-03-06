package application;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import customUI.ErrorAlert;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Main class - Sets up global variables, loads creation info, loads main menu scene
 * @author Max Gurr & Jenna Kumar
 *
 */
public class Main extends Application {
	private static final String _USERDIR = System.getProperty("user.dir");
	public static final String _FILEPATH =  _USERDIR + "/files";
	public static final String _RESOURCEPATH = _USERDIR + "/resources";
	public static final String _VIDPATH=_FILEPATH + "/videos";
	public static final String _AUDIOPATH = _FILEPATH + "/audio";
	public static final String _CREATIONPATH = _FILEPATH + "/creations";
	public static final String _CREATIONINFO = _FILEPATH + "/creationInfo.txt";
	
	private static List<Creation> _creationList;
	
	@Override
	public void start(Stage primaryStage) {
		setup(); 
		getCreationInfo();
		
		//Load main menu
		Parent layout;
		try {
			layout = FXMLLoader.load(getClass().getResource("/fxml/MainMenuPane.fxml"));
			Scene scene = new Scene(layout);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("VARpedia");
			primaryStage.show();
		} catch (IOException e) {
			//new ErrorAlert("Can't load application");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Setup done at launch of application - create storage folders and info file
	 */
	private void setup() {					
		//Create files folder if one doesn't exist
		try {
			String cmd = "mkdir -p " + _FILEPATH + "/creations " + _FILEPATH + "/videos " + _FILEPATH + "/audio";
			int exitVal = BashCommandClass.runBashProcess(cmd);
			if (exitVal != 0) {
				new ErrorAlert("Something went wrong");
				Platform.exit();
			}
			
			String infoFile = "touch " + _FILEPATH + "/creationInfo.txt";
			BashCommandClass.runBashProcess(infoFile);
		} catch (IOException | InterruptedException e) {
			new ErrorAlert("Something went wrong");
			Platform.exit();
		}
	}
	
	/**
	 * Get info about creations from info file
	 */
	private void getCreationInfo() {
		//Storage list
		_creationList = new ArrayList<Creation>();
		
		try {
			//Read creations from text file
			String creationsInfoCommand = "cat " + _CREATIONINFO;
			List<String> creationList = BashCommandClass.getListOutput(creationsInfoCommand);
				
			//For all lines in info file, get creation info, and make a new creation object
			for (String s : creationList) {
				String[] info = s.split(";");
				
				String filename = info[0].trim();
				String searchTerm = info[1].trim();
				String length = info[2].trim();
				String testAcc = info[3].trim();
				
				_creationList.add(new Creation(filename, searchTerm, length, testAcc));
			}
		} catch (IOException | InterruptedException e) {
			new ErrorAlert("Couldn't load creations");
		}
	}
	
	/**
	 * Getter for list of creations
	 */
	public static List<Creation> getCreationList() {
		return _creationList;
	}
	
	/**
	 * Delete creation from list
	 */
	public static void deleteCreation(String filename) {
		for (Creation c: _creationList) {
			if (c.getFilename().equals(filename)) {
				_creationList.remove(c);
				return;
			}
		}
	}
}
