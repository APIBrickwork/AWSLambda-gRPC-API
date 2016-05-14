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
 * Singleton class representing the configuration file for ChefMate.
 * @author Tobias Freundorfer
 *
 */
public class Config
{
	private static final Logger logger = Logger.getLogger(EnvironmentInitializer.class.getName());

	/**
	 * The singleton instance.
	 */
	private static Config instance;
	
	/**
	 * The name of the configuration file.
	 */
	public static final String CONFIG_FILENAME = "chefmate.conf";
	
	/* Section of property keys */
	public static final String PROPKEY_CHEF_REPO_NAME = "chef_repo_name";
	public static final String PROPKEY_CHEF_REPO_URL = "chef_repo_url";
	public static final String PROPKEY_AWS_ACCESS_KEY = "aws_access_key";
	public static final String PROPKEY_AWS_SECRET_ACCESS_KEY = "aws_secret_access_key";
	public static final String PROPKEY_AWS_SSH_KEY_NAME = "aws_ssh_key_name";
	public static final String PROPKEY_CHEFMATEINFO_FROM_CHEF_KEYWORD_PUBLICDNS = "chefmate_from_chef_keyword_publicdns";
	public static final String PROPKEY_CHEFMATEINFO_FROM_CHEF_KEYWORD_INSTANCEID = "chefmate_from_chef_keyword_instanceid";
	
	/* end */

	/**
	 * The home directory of the user executing this.
	 */
	private String homeDir = "";

	/**
	 * The directory that should be used.
	 */
	private String serverEnvDir = "";

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
	 * The access key for AWS.
	 */
	private String awsAccessKey = "";
	
	/**
	 * The secret access key for AWS. Has to be set manually in the config file
	 * for security reasons!!
	 */
	private String awsSecretAccessKey = "";
	
	/**
	 * The name of the ssh key used.
	 */
	private String awsSSHKeyName = "";
	
	/**
	 * The keyword for public DNS information received from Chef.
	 */
	private String chefMateInfo_Keyword_PublicDNS = "";
	
	/**
	 * The keyword for instance id information received from Chef.
	 */
	private String chefMateInfo_Keyword_InstanceID = "";
	
	/**
	 * Private constructor.
	 * @param writeDefault Whether the default config file should be written or not.
	 * @param reload Whether the config file should be reloaded or not.
	 */
	private Config(boolean writeDefault, boolean reload){
		this.homeDir = System.getProperty("user.home") + "/";
		this.serverEnvDir = this.homeDir + "chefmateserver/";
		if(writeDefault){
			this.writeDefaultConfigFile();
		}
		if(reload){
			this.readConfig();
		}
	}
	
	/**
	 * Returns the singleton instance.
	 * @param writeDefault Whether the default config file should be written or not.
	 * @param reload Whether the config file should be reloaded or not.
	 * @return The singleton instance.
	 */
	public static synchronized Config getInstance(boolean writeDefault, boolean reload){
		if(Config.instance == null){
			Config.instance = new Config(writeDefault, reload);
		}
		return Config.instance;
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
			properties.setProperty(PROPKEY_AWS_ACCESS_KEY, "TODO_SET_THIS_MANUALLY_DUE_TO_SECURITY_REASONS");
			properties.setProperty(PROPKEY_AWS_SECRET_ACCESS_KEY, "TODO_SET_THIS_MANUALLY_DUE_TO_SECURITY_REASONS");
			properties.setProperty(PROPKEY_AWS_SSH_KEY_NAME, "chefmateserver_key");
			properties.setProperty(PROPKEY_CHEFMATEINFO_FROM_CHEF_KEYWORD_PUBLICDNS, "CHEFMATEINFO::PublicDNS=");
			properties.setProperty(PROPKEY_CHEFMATEINFO_FROM_CHEF_KEYWORD_INSTANCEID, "CHEFMATEINFO::InstanceID=");
			
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
	 * Reads the properties from the config file.
	 */
	private void readConfig()
	{
		Properties properties = new Properties();
		try
		{
			logger.info("### Reading config file.");

			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(this.getServerEnvDir() + CONFIG_FILENAME));
			properties.load(stream);

			this.chefRepoName = properties.getProperty(PROPKEY_CHEF_REPO_NAME);
			this.chefRepoURL = properties.getProperty(PROPKEY_CHEF_REPO_URL);
			this.awsAccessKey = properties.getProperty(PROPKEY_AWS_ACCESS_KEY);
			this.awsSecretAccessKey = properties.getProperty(PROPKEY_AWS_SECRET_ACCESS_KEY);
			this.awsSSHKeyName = properties.getProperty(PROPKEY_AWS_SSH_KEY_NAME);
			this.chefMateInfo_Keyword_InstanceID = properties.getProperty(PROPKEY_CHEFMATEINFO_FROM_CHEF_KEYWORD_INSTANCEID);
			this.chefMateInfo_Keyword_PublicDNS = properties.getProperty(PROPKEY_CHEFMATEINFO_FROM_CHEF_KEYWORD_PUBLICDNS);
			
			
			this.chefProvisioningInitScriptPath = this.serverEnvDir + this.chefRepoName
					+ "/initScripts/chefMateServerChefProvisioningSetup.sh";
			
			
			
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
		pb.directory(new File(getHomeDir()));

		ShellExecuter.execute(this.getHomeDir(), commands);
	}

	public String getHomeDir()
	{
		return homeDir;
	}

	public String getServerEnvDir()
	{
		return serverEnvDir;
	}

	public String getChefRepoURL()
	{
		return chefRepoURL;
	}

	public String getChefRepoName()
	{
		return chefRepoName;
	}

	public String getChefRepoPath(){
		return this.serverEnvDir + this.chefRepoName;
	}
	
	public String getChefCookbooksPath(){
		return this.getChefRepoPath() + "/cookbooks";
	}
	
	public String getChefAttributesPath(String cookbookname){
		return this.getChefCookbooksPath() + "/" + cookbookname +"/attributes";
	}
	
	public String getChefAttributesDefaultFilename(String cookbookname){
		return this.getChefAttributesPath(cookbookname) + "/default.rb";
	}
	
	public String getChefSSHKeyPath(){
		return this.getServerEnvDir() + ".ssh/";
	}
	
	public String getChefProvisioningInitScriptPath()
	{
		return chefProvisioningInitScriptPath;
	}

	public String getAwsSecretAccessKey()
	{
		return awsSecretAccessKey;
	}

	public String getAwsSSHKeyName()
	{
		return awsSSHKeyName;
	}

	public String getAwsAccessKey()
	{
		return awsAccessKey;
	}

	public String getChefMateInfo_Keyword_PublicDNS()
	{
		return chefMateInfo_Keyword_PublicDNS;
	}

	public String getChefMateInfo_Keyword_InstanceID()
	{
		return chefMateInfo_Keyword_InstanceID;
	}

}
