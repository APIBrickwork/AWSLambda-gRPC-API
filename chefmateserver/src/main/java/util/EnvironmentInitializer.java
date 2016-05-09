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
	private static final String CONFIG_FILENAME = "chefmate.conf";

	/**
	 * The home directory of the user executing this.
	 */
	private String homeDir = "";

	/**
	 * The directory that should be used.
	 */
	private String serverEnvDir = "";

	/* Section of property keys */
	private static final String PROPKEY_CHEF_REPO_NAME = "chef_repo_name";
	private static final String PROPKEY_CHEF_REPO_URL = "chef_repo_url";
	private static final String PROPKEY_AWS_SECRET_ACCESS_KEY = "aws_secret_access_key";

	/* end */

	/**
	 * The Chef.io repsoitory URL.
	 */
	private String chefRepoURL = "";

	/**
	 * The name of the Chef.io repository.
	 */
	private String chefRepoName = "";

	/**
	 * The path to the ChefMateServer AWS Provisioninig init script.
	 */
	private String chefProvisioningInitScriptPath = "";

	/**
	 * The secret access key for AWS. Has to be set manually in the config file
	 * for security reasons!!
	 */
	private String awsSecretAccessKey = "";

	/**
	 * Creates a new instance.
	 */
	public EnvironmentInitializer()
	{
		this.homeDir = System.getProperty("user.home");
		this.serverEnvDir = this.homeDir + "/chefmateserver/";
	}

	/**
	 * Writes the default configuration properties to the config file.
	 */
	public void writeDefaultConfigFile()
	{
		this.createEnvDir();
		Properties properties = new Properties();
		try
		{
			logger.info("### Writing default config file.");

			properties.setProperty(PROPKEY_CHEF_REPO_NAME, "LabCourse-group4-SS2016-CHEFrepo");
			properties.setProperty(PROPKEY_CHEF_REPO_URL,
					"https://github.com/tfreundo/LabCourse-group4-SS2016-CHEFrepo.git");
			properties.setProperty(PROPKEY_AWS_SECRET_ACCESS_KEY, "TODO_SET_THIS_MANUALLY_DUE_TO_SECURITY_REASONS");

			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(this.serverEnvDir + CONFIG_FILENAME));
			properties.store(stream, "---Environment Initializer Config file---");

			stream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the server environment.
	 */
	public void init()
	{
		logger.info("### Running ChefMate Environment Initializer.");
		this.createEnvDir();
		this.readConfig();
		this.fetchGitRepo();
		this.executeChefProvisioningSetup();
	}

	/**
	 * Reads the properties from the config file.
	 */
	private void readConfig()
	{
		Properties properties = new Properties();
		try
		{
			logger.info("### Reading config file.");

			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(this.serverEnvDir + CONFIG_FILENAME));
			properties.load(stream);

			this.chefRepoName = properties.getProperty(PROPKEY_CHEF_REPO_NAME);
			this.chefRepoURL = properties.getProperty(PROPKEY_CHEF_REPO_URL);
			this.awsSecretAccessKey = properties.getProperty(PROPKEY_AWS_SECRET_ACCESS_KEY);

			stream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creates the server environment directory.
	 */
	private void createEnvDir()
	{
		logger.info("### Creating environment in directory " + this.serverEnvDir + ".");

		// commands
		List<String> commands = new ArrayList<>();
		commands.add("mkdir");
		commands.add(this.serverEnvDir);

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(homeDir));

		int code = ShellExecuter.execute(this.homeDir, commands);

		if (code == 0)
		{
			logger.info("### Success.");
		} else
		{
			logger.warning("### Error creating environment in directory " + this.serverEnvDir + ".");
		}

	}

	/**
	 * Fetches the Chef.io git repository.
	 */
	private void fetchGitRepo()
	{
		List<String> commands = new ArrayList<>();
		commands.add("git");
		commands.add("clone");
		commands.add("-b");
		// TODO: Change to master ASAP
		commands.add("development");
		commands.add(this.chefRepoURL);

		logger.info("### Fetching git repository from " + this.chefRepoURL);
		logger.info("### Fetching using: " + commands);

		int code = ShellExecuter.execute(this.serverEnvDir, commands);

		if (code == 0)
		{
			logger.info("### Success.");
		} else
		{
			logger.warning("### Error.");
		}
	}

	/**
	 * Exectues the initialization script for Chef Provisioning for AWS.
	 */
	private void executeChefProvisioningSetup()
	{
		if (this.awsSecretAccessKey.isEmpty())
		{
			logger.warning(
					"AWS Secret Key was empty. Be sure to insert it in the config " + CONFIG_FILENAME + " first.");
		} else
		{
			this.chefProvisioningInitScriptPath = this.serverEnvDir + this.chefRepoName
					+ "/initScripts/chefMateServerChefProvisioningSetup.sh";
			List<String> commands = new ArrayList<>();
			commands.add(this.chefProvisioningInitScriptPath);
			commands.add(this.awsSecretAccessKey);

			logger.info("### Exectuing AWS Chef Provisioning init script at: " + this.chefProvisioningInitScriptPath);
			logger.info("### Executing using commands: " + commands);

			int code = ShellExecuter.execute(this.serverEnvDir, commands);

			if (code == 0)
			{
				logger.info("### Success.");
			} else
			{
				logger.warning("### Error.");
			}
		}
	}
}
