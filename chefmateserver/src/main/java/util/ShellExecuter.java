package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
	 * @return The error and standard output of the process executed.
	 */
	public static List<String> execute(String processDirectory, List<String> commands)
	{

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(processDirectory));
		pb.redirectErrorStream(true);
		List<String> outputLog = new ArrayList<>();
		try
		{
			Process p = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null)
			{
				outputLog.add(line);
				System.out.println(line);
			}

			int code = p.waitFor();
			if (code == 0)
			{
				logger.info("### Process terminated successfully with command: " + commands);

			} else
			{
				logger.warning("### Process terminated unsuccessfully.");
			}

			return outputLog;

		} catch (InterruptedException | IOException e)
		{
			outputLog.add(e.getMessage());
			return outputLog;
		}
	}
}
