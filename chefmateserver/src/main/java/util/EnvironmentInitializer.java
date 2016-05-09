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
	
	private Config config = null;
	
	/**
	 * Creates the environment initializer.
	 * @param writeDefaultConfig Whether the default config file should be written or not.
	 */
	public EnvironmentInitializer(boolean writeDefaultConfig){
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
		commands.add(this.config.getChefRepoURL());

		logger.info("### Fetching git repository from " + this.config.getChefRepoURL());
		logger.info("### Fetching using: " + commands);

		int code = ShellExecuter.execute(this.config.getServerEnvDir(), commands);

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
		if (this.config.getAwsSecretAccessKey().isEmpty())
		{
			logger.warning(
					"AWS Secret Key was empty. Be sure to insert it in the config " + Config.CONFIG_FILENAME + " first.");
		} else
		{
			List<String> commands = new ArrayList<>();
			commands.add(this.config.getChefProvisioningInitScriptPath());
			commands.add(this.config.getAwsSecretAccessKey());

			logger.info("### Exectuing AWS Chef Provisioning init script at: " + this.config.getChefProvisioningInitScriptPath());
			logger.info("### Executing using commands: " + commands);

			int code = ShellExecuter.execute(this.config.getServerEnvDir(), commands);

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
