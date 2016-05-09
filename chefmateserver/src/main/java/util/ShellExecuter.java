package util;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A shell execution helper that manages the execution of given commands.
 * @author Tobias Freundorfer
 *
 */
public class ShellExecuter
{

	/**
	 * Private constructor preventing from instance creation.
	 */
	private ShellExecuter(){};
	
	/**
	 * Executes the given commands.
	 * @param processDirectory The directory in which the process should be executed.
	 * @param commands The commands that should be executed.
	 * @return Return-code of the executed command.
	 */
	public static int execute(String processDirectory, List<String> commands){
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(processDirectory));

		try
		{
			Process p = pb.start();
			int code = p.waitFor();
			
			return code;

		} catch (InterruptedException | IOException e)
		{
			e.printStackTrace();
			return 2;
		}
	}
	
}
