package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class BashCommandClass {
	
	public static int runBashProcess (String command) throws IOException, InterruptedException {
		List<String> commands;

		commands = new ArrayList<>();
		commands.add("bash");
		commands.add("-c");

		commands.add(2, command);

		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		Process process = processBuilder.start();
		process.waitFor();
		//get exit status
		int status = process.exitValue();
		return status;
	}

	public static String getOutputFromCommand(String command) throws IOException, InterruptedException{

		List<String> commands;

		commands = new ArrayList<>();
		commands.add("bash");
		commands.add("-c");
		
		commands.add(2, command);

		StringBuffer output = new StringBuffer();

		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		Process process = processBuilder.start();
		int status = process.waitFor();

		if (status != 0) {
			return " ";
		}

		//read output and append to string
		BufferedReader stdOut = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

		String line = "";			
		while ((line = stdOut.readLine())!= null) {
			output.append(line);
		}

		return output.toString();
	}
	
	public static List<String> getListOutput(String command) throws IOException, InterruptedException {
		List<String> commands;

		commands = new ArrayList<>();
		commands.add("bash");
		commands.add("-c");
		commands.add(2, command);
		
		List<String> output = new ArrayList<String>();

		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		Process process = processBuilder.start();
		int status = process.waitFor();


		if (status != 0) {
			return null;
		}
		
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		String line = null;
		while ((line = stdoutBuffered.readLine()) != null ) {
			output.add(line);
		}
		
		return output;
	}
}











































//package application;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class BashCommandClass {
//	
//	public static int runBashProcess (String command) throws IOException, InterruptedException {
//		List<String> commands;
//
//		commands = new ArrayList<>();
//		commands.add("bash");
//		commands.add("-c");
//
//		commands.add(2, command);
//
//		ProcessBuilder processBuilder = new ProcessBuilder(commands);
//		Process process = processBuilder.start();
//		process.waitFor();
//		//get exit status
//		int status = process.exitValue();
//		return status;
//	}
//
//	public static String getOutputFromCommand(String command) throws IOException, InterruptedException{
//
//		List<String> commands;
//
//		commands = new ArrayList<>();
//		commands.add("bash");
//		commands.add("-c");
//		
//		commands.add(2, command);
//
//		StringBuffer output = new StringBuffer();
//
//		ProcessBuilder processBuilder = new ProcessBuilder(commands);
//		Process process = processBuilder.start();
//		int status = process.waitFor();
//
//		if (status != 0) {
//			return " ";
//		}
//
//		//read output and append to string
//		BufferedReader stdOut = new BufferedReader(
//				new InputStreamReader(process.getInputStream()));
//
//		String line = "";			
//		while ((line = stdOut.readLine())!= null) {
//			output.append(line + " ");
//		}
//
//		return output.toString();
//
//	}
//
//}
