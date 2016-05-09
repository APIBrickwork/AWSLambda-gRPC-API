package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This class is used to initialize the server environment.
 * 
 * @author Tobias Freundorfer
 *
 */
public class EnvironmentInitializer
{
	private static final Logger logger = Logger.getLogger(EnvironmentInitializer.class.getName());

	/**
	 * The name of the configuration file.
	 */
	private static final String CONFIG_FILENAME = "envInitializer.conf";

	/**
	 * The directory that should be used.
	 */
	private String serverEnvDir = "";

	/* Section of property keys */
	private static final String PROPKEY_CHEF_REPO_URL = "chef_repo_url";
	private static final String PROPKEY_AWS_SECRET_ACCESS_KEY = "aws_secret_access_key";
	
	/*end*/
	
	/**
	 * The Chef.io repsoitory URL.
	 */
	private String chefRepoURL = "";

	/**
	 * The secret access key for AWS. Has to be set manually in the config file
	 * for security reasons!!
	 */
	private String awsSecretAccessKey = "";

	public void writeDefaultConfigFile()
	{
		// TODO: Check if this is working and not overwriting if folder already exists
		this.createEnvDir();
		Properties properties = new Properties();
		try
		{
			logger.info("### Writing default config file.");

			properties.setProperty(PROPKEY_CHEF_REPO_URL, "https://github.com/tfreundo/LabCourse-group4-SS2016-CHEFrepo.git");
			properties.setProperty(PROPKEY_AWS_SECRET_ACCESS_KEY, "TODO_SET_THIS_MANUALLY_DUE_TO_SECURITY_REASONS");

			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(this.serverEnvDir + CONFIG_FILENAME));
			properties.store(stream, "---Environment Initializer Config file---");

			stream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init(){
		logger.info("### Running ChefMate Environment Initializer.");
		this.createEnvDir();
		this.readConfig();
		this.fetchGitRepo();
	}

	private void readConfig()
	{
		Properties properties = new Properties();
		try
		{
			logger.info("### Reading config file.");

			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(this.serverEnvDir + CONFIG_FILENAME));
			properties.load(stream);
			
			this.chefRepoURL = properties.getProperty(PROPKEY_CHEF_REPO_URL);
			this.awsSecretAccessKey = properties.getProperty(PROPKEY_AWS_SECRET_ACCESS_KEY);
			
			stream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createEnvDir()
	{
		String homeDir = System.getProperty("user.home");
		this.serverEnvDir = homeDir + "/chefmateserver/";
		logger.info("### Creating in environment in directory " + this.serverEnvDir);

		// commands
		List<String> commands = new ArrayList<>();
		commands.add("mkdir");
		commands.add(this.serverEnvDir);

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(homeDir));

		try
		{
			Process p = pb.start();
			int code = p.waitFor();
			if (code == 0)
			{
				logger.info("### Success.");
			} else
			{
				logger.warning("### Error.");
			}

		} catch (InterruptedException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fetchGitRepo()
	{
		List<String> commands = new ArrayList<>();
		commands.add("git");
		commands.add("clone");
		commands.add(this.chefRepoURL);

		System.out.println(commands);

		logger.info("### Fetching git repository from " + this.chefRepoURL);
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(this.serverEnvDir));
		try
		{
			Process p = pb.start();
			int code = p.waitFor();
			if (code == 0)
			{
				logger.info("### Success.");
			} else
			{
				logger.warning("### Error.");
			}

		} catch (InterruptedException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
