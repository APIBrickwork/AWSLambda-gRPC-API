package util;

import java.util.ArrayList;
import java.util.List;
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
	 * The config for ChefMateServer.
	 */
	private Config config = null;

	/**
	 * Creates the environment initializer.
	 * 
	 * @param writeDefaultConfig
	 *            Whether the default config file should be written or not.
	 */
	public EnvironmentInitializer(boolean writeDefaultConfig)
	{
		config = Config.getInstance(writeDefaultConfig, true);
	}

	/**
	 * Initializes the server environment.
	 */
	public void init()
	{
		logger.info("### Running ChefMate Environment Initializer.");
		this.fetchGitRepo();
		this.executeChefProvisioningSetup();
	}

	/**
	 * Fetches the Chef git repository.
	 */
	private void fetchGitRepo()
	{
		List<String> commands = new ArrayList<>();
		commands.add("git");
		commands.add("clone");
		commands.add("-b");
		commands.add("development");
		commands.add(this.config.getChefRepoURL());

		logger.info("### Fetching git repository from " + this.config.getChefRepoURL());
		logger.info("### Fetching using: " + commands);

		ShellExecutor.execute(this.config.getServerEnvDir(), commands);
	}

	/**
	 * Exectues the initialization script for Chef Provisioning for AWS.
	 */
	private void executeChefProvisioningSetup()
	{
		if (this.config.getAwsAccessKey().isEmpty())
		{
			logger.warning("AWS Key was empty. Be sure to insert it in the config " + Config.CONFIG_FILENAME
					+ " first.");
		}
		if (this.config.getAwsSecretAccessKey().isEmpty())
		{
			logger.warning("AWS Secret Key was empty. Be sure to insert it in the config " + Config.CONFIG_FILENAME
					+ " first.");
		}
		else
		{
			List<String> commands = new ArrayList<>();
			commands.add(this.config.getChefProvisioningInitScriptPath());
			commands.add(this.config.getAwsAccessKey());
			commands.add(this.config.getAwsSecretAccessKey());

			logger.info("### Exectuing AWS Chef Provisioning init script at: "
					+ this.config.getChefProvisioningInitScriptPath());
			logger.info("### Executing using commands: " + commands);

			ShellExecutor.execute(this.config.getServerEnvDir(), commands);
		}
	}
}
