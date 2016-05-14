package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import services.Chefmate;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.DestroyVMRequest;

public class ChefAttributesWriter
{

	private static final String DEFAULT_PRIORITY = "default";
	private static final String NORMAL_PRIORITY = "normal";

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
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_NAME + "'" + name + "'");
				writer.newLine();
			}
			if (!tag.isEmpty())
			{
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_TAG + "'" + tag + "'");
				writer.newLine();
			}
			if (!region.isEmpty())
			{
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_REGION + "'" + region + "'");
				writer.newLine();
			}
			if (!imageid.isEmpty())
			{
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_IMAGEID + "'" + imageid + "'");
				writer.newLine();
			}
			if (!username.isEmpty())
			{
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_USERNAME + "'" + username + "'");
				writer.newLine();
			}
			if (!instancetype.isEmpty())
			{
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_INSTANCETYPE + "'" + instancetype + "'");
				writer.newLine();
			}
			if (securityGroupIds.size() > 0)
			{
				writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_SECURITYGROUPIDS + "'");
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
			writer.write(NORMAL_PRIORITY + CHEFMATE_MACHINE_DESTROY_INSTANCEID + "'" + requestedVM.getInstanceId().getId() + "'");
			writer.newLine();
			
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
