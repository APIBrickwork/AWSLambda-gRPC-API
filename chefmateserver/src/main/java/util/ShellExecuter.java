package util;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import services.EC2OpsImpl;

/**
 * A shell execution helper that manages the execution of given commands.
 * 
 * @author Tobias Freundorfer
 *
 */
public class ShellExecuter
{
	private static final Logger logger = Logger.getLogger(EC2OpsImpl.class.getName());

	/**
	 * Private constructor preventing from instance creation.
	 */
	private ShellExecuter()
	{
	};

	/**
	 * Executes the given commands.
	 * 
	 * @param processDirectory
	 *            The directory in which the process should be executed.
	 * @param commands
	 *            The commands that should be executed.
	 * @return Return-code of the executed command.
	 */
	public static int execute(String processDirectory, List<String> commands)
	{

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(processDirectory));
		try
		{
			Process p = pb.start();
			int code = p.waitFor();
			// TODO: Handle it
			Scanner s = new Scanner(p.getInputStream()).useDelimiter("\\Z");
			logger.info(s.next());
			s.close();

			return code;

		} catch (InterruptedException | IOException e)
		{
			e.printStackTrace();
			return 2;
		}
	}

}
