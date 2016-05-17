package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import services.Chefmate.CreateVMRequest;
import services.Chefmate.DeployDBRequest;
import services.Chefmate.DeployWPAppRequest;
import services.Chefmate.DestroyVMRequest;
import services.Chefmate.ExecuteCookbookRequest;

public class ChefAttributesWriter
{

	private static final String DEFAULT_PRIORITY = "default";
	private static final String NORMAL_PRIORITY = "normal";
	private static final String OVERRIDE_PRIORITY = "override";

	private static final String CHEFMATE_AWS_CREDENTIALS_ACCESS_KEY = "['chefmate']['aws']['credentials']['accesskey'] = ";
	private static final String CHEFMATE_AWS_CREDENTIALS_SECRET_KEY = "['chefmate']['aws']['credentials']['secretkey'] = ";

	private static final String CHEFMATE_MACHINE_NAME = "['chefmate']['machine']['name'] = ";
	private static final String CHEFMATE_MACHINE_TAG = "['chefmate']['machine']['tag'] = ";
	private static final String CHEFMATE_MACHINE_REGION = "['chefmate']['machine']['region'] = ";
	private static final String CHEFMATE_MACHINE_IMAGEID = "['chefmate']['machine']['imageid'] = ";
	private static final String CHEFMATE_MACHINE_INSTANCETYPE = "['chefmate']['machine']['instancetype'] = ";
	private static final String CHEFMATE_MACHINE_SECURITYGROUPIDS = "['chefmate']['machine']['defaultsecuritygroupdids'] = ";
	private static final String CHEFMATE_MACHINE_USERNAME = "['chefmate']['machine']['username'] = ";

	private static final String CHEFMATE_MACHINE_DESTROY_INSTANCEID = "['chefmate']['machine']['delete']['instanceid'] = ";

	private static final String CHEFMATE_MACHINE_MYSQL_SERVICENAME = "['chefmate']['machine']['mysql']['servicename'] = ";
	private static final String CHEFMATE_MACHINE_MYSQL_PORT = "['chefmate']['machine']['mysql']['port'] = ";
	private static final String CHEFMATE_MACHINE_MYSQL_ROOTPW = "['chefmate']['machine']['mysql']['rootpw'] = ";

	private static final String CHEFMATE_MACHINE_WORDPRESS_SERVERNAME = "['wordpress']['server_name'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_PORT = "['wordpress']['server_port'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_DB_NAME = "['wordpress']['db']['name'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_DB_HOST = "['wordpress']['db']['host'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_DB_PORT = "['wordpress']['db']['port'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_DB_USERNAME = "['wordpress']['db']['user'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_DB_USERPASSWORD = "['wordpress']['db']['pass'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_DB_ROOTPASSWORD = "['wordpress']['db']['root_password'] = ";
	private static final String CHEFMATE_MACHINE_WORDPRESS_CONFIG_OPTIONS = "['wordpress']['wp_config_options'] = ";

	/**
	 * Prevents from instance creation.
	 */
	private ChefAttributesWriter()
	{
	};

	/**
	 * Writes the Chef.io attributes (default and custom) file to the given
	 * repository.
	 * 
	 * @param filename
	 *            The filename (including the absolute path) that should be
	 *            used.
	 * @param requestedVM
	 *            The values that should be added.
	 */
	public static void writeAttributesFile(String filename, CreateVMRequest requestedVM)
	{
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(filename));
			writeDefault(writer);
			writer.newLine();

			String name = requestedVM.getName();
			String tag = requestedVM.getTag();
			String region = requestedVM.getRegion();
			String imageid = requestedVM.getImageId();
			String instancetype = requestedVM.getInstanceType();
			String username = requestedVM.getUsername();

			List<String> securityGroupIds = new ArrayList<>();
			for (int i = 0; i < requestedVM.getSecurityGroupIdsCount(); i++)
			{
				securityGroupIds.add(requestedVM.getSecurityGroupIds(i));
			}

			// Write custom attributes to file below
			writer.write("# Custom values set by user (higher priority than default)");
			writer.newLine();

			if (!name.isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_NAME + "'" + name + "'");
				writer.newLine();
			}
			if (!tag.isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_TAG + "'" + tag + "'");
				writer.newLine();
			}
			if (!region.isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_REGION + "'" + region + "'");
				writer.newLine();
			}
			if (!imageid.isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_IMAGEID + "'" + imageid + "'");
				writer.newLine();
			}
			if (!username.isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_USERNAME + "'" + username + "'");
				writer.newLine();
			}
			if (!instancetype.isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_INSTANCETYPE + "'" + instancetype + "'");
				writer.newLine();
			}
			if (securityGroupIds.size() > 0)
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_SECURITYGROUPIDS + "'");
				for (int i = 0; i < securityGroupIds.size(); i++)
				{
					writer.write(securityGroupIds.get(i));
					// Only seperate with comma if i is leq size-2
					if (i <= securityGroupIds.size() - 2)
					{
						writer.write(",");
					}
				}
				writer.write("'");
				writer.newLine();
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the Chef.io attributes (default and custom) file to the given
	 * repository.
	 * 
	 * @param filename
	 *            The filename (including the absolute path) that should be
	 *            used.
	 * @param requestedVM
	 *            The values that should be added.
	 */
	public static void writeAttributesFile(String filename, DestroyVMRequest requestedVM)
	{
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(filename));
			writeDefault(writer);
			writer.newLine();
			writer.write("# Custom values set by user (higher priority than default)");
			writer.newLine();
			if (!requestedVM.getInstanceId().getId().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_DESTROY_INSTANCEID + "'"
						+ requestedVM.getInstanceId().getId() + "'");
				writer.newLine();
			}

		} catch (

		IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the Chef.io attributes (default and custom) file to the given
	 * repository.
	 * 
	 * @param filename
	 *            The filename (including the absolute path) that should be
	 *            used.
	 * @param request
	 *            The values that should be added.
	 */
	public static void writeAttributesFile(String filename, DeployWPAppRequest request)
	{
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(filename));
			writer.write("# Custom values set by user (higher priority than default)");
			writer.newLine();
			if (!request.getServerName().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_SERVERNAME + "'" + request.getServerName()
						+ "'");
				writer.newLine();
			}
			if (!request.getPort().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_PORT + "'" + request.getPort() + "'");
				writer.newLine();
			}
			if (!request.getDbName().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_DB_NAME + "'" + request.getDbName() + "'");
				writer.newLine();
			}
			if (!request.getDbHost().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_DB_HOST + "'" + request.getDbHost() + "'");
				writer.newLine();
			}
			if (!request.getDbPort().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_DB_PORT + "'" + request.getDbPort() + "'");
				writer.newLine();
			}
			if (!request.getDbUserName().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_DB_USERNAME + "'" + request.getDbUserName()
						+ "'");
				writer.newLine();
			}
			if (!request.getDbUserPassword().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_DB_USERPASSWORD + "'"
						+ request.getDbUserPassword() + "'");
				writer.newLine();
			}
			if (!request.getDbRootPassword().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_DB_ROOTPASSWORD + "'"
						+ request.getDbRootPassword() + "'");
				writer.newLine();
			}
			if (!request.getWpConfigOptions().isEmpty())
			{
				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_WORDPRESS_CONFIG_OPTIONS + "'"
						+ request.getWpConfigOptions() + "'");
				writer.newLine();
			}

		} catch (

		IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the Chef.io attributes (default and custom) file to the given
	 * repository.
	 * 
	 * @param filename
	 *            The filename (including the absolute path) that should be
	 *            used.
	 * @param request
	 *            The values that should be added.
	 */
	public static void writeAttributesFile(String filename, DeployDBRequest request)
	{
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(filename));
			writeDefault(writer);
			writer.newLine();
			writer.write("# Custom values set by user (higher priority than default)");
			writer.newLine();
			if (!request.getServiceName().isEmpty())
			{

				writer.write(
						OVERRIDE_PRIORITY + CHEFMATE_MACHINE_MYSQL_SERVICENAME + "'" + request.getServiceName() + "'");
				writer.newLine();
			}
			if (!(request.getPort() == 0))
			{

				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_MYSQL_PORT + "'" + request.getPort() + "'");
				writer.newLine();
			}
			if (!request.getRootPassword().isEmpty())
			{

				writer.write(OVERRIDE_PRIORITY + CHEFMATE_MACHINE_MYSQL_ROOTPW + "'" + request.getRootPassword() + "'");
				writer.newLine();
			}

		} catch (

		IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the Chef.io attributes (default and custom) file to the given
	 * repository.
	 * 
	 * @param filename
	 *            The filename (including the absolute path) that should be
	 *            used.
	 * @param genericExecuteCookbook
	 *            The values that should be added.
	 */
	public static void writeAttributesFile(String filename, ExecuteCookbookRequest genericExecuteCookbook)
	{
		// TODO: Evaluate
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(filename));
			writeDefault(writer);
			writer.newLine();
			writer.write("# Custom values set by user (higher priority than default)");
			writer.newLine();

			if (genericExecuteCookbook.getAttributesKeysCount() > 0
					&& genericExecuteCookbook.getAttributesValuesCount() > 0 && (genericExecuteCookbook
							.getAttributesKeysCount() == genericExecuteCookbook.getAttributesValuesCount()))
			{
				int size = genericExecuteCookbook.getAttributesValuesCount();
				for (int i = 0; i < size; i++)
				{
					writer.write(OVERRIDE_PRIORITY + genericExecuteCookbook.getAttributesKeys(i) + " = '"
							+ genericExecuteCookbook.getAttributesValues(i) + "'");
					writer.newLine();
				}

			}
		} catch (

		IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the default Chef.io attribute values to the file.
	 * 
	 * @param writer
	 *            The BufferedWriter that should be used.
	 * @throws IOException
	 */
	private static void writeDefault(BufferedWriter writer) throws IOException
	{
		writer.write("#");
		writer.newLine();
		writer.write("# This file was autogenerated by ChefMateServer");
		writer.newLine();
		writer.write("#");
		writer.newLine();
		writer.newLine();
		writer.write("# Default values used if nothing else is specified");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_AWS_CREDENTIALS_ACCESS_KEY + "'"
				+ Config.getInstance(false, true).getAwsAccessKey() + "'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_AWS_CREDENTIALS_SECRET_KEY + "'"
				+ Config.getInstance(false, false).getAwsSecretAccessKey() + "'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_NAME + "'chefmate'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_TAG + "'chefmate-tag'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_REGION + "'eu-central-1'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_IMAGEID + "'ami-87564feb'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_INSTANCETYPE + "'t2.micro'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_SECURITYGROUPIDS + "'sg-79ae5d11'");
		writer.newLine();
		writer.write(DEFAULT_PRIORITY + CHEFMATE_MACHINE_USERNAME + "'ubuntu'");
		writer.newLine();
	}
}
