package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHExecuter
{
	private static final Logger logger = Logger.getLogger(SSHExecuter.class.getName());

	/**
	 * The JSCH used to connect via SSH to remote hosts.
	 */
	private JSch jsch = new JSch();

	private Session session = null;

	public enum ChannelType
	{
		SHELL, EXEC
	};

	public SSHExecuter()
	{

	}

	public void connectHost(String username, String host, int port, int timeout, String privateKeyFile)
	{

		try
		{
			jsch.addIdentity(privateKeyFile);
			JSch.setConfig("StrictHostKeyChecking", "no");
			logger.info("### Added private key from: " + privateKeyFile);
		} catch (JSchException e)
		{
			logger.warning("### Error when adding private key file. \n" + e.getMessage());
		}

		String connectionString = username + "@" + host + ":" + port;
		try
		{
			this.session = jsch.getSession(username, host, port);
			logger.info("### Created session: " + connectionString);
			this.session.connect(timeout);
			logger.info("### Connected to session.");

		} catch (JSchException e)
		{
			logger.warning("### Error when creating session for " + connectionString + "\n" + e.getMessage());
		}
	}

	public String sendToChannel(ChannelType type, String command, int timeout)
	{
		// The output (may be reused by the server to send back to client)
		String outputLog = "";

		String typeString = "";
		switch (type)
		{
		case SHELL:
			typeString = "shell";
			break;
		case EXEC:
			typeString = "exec";
			break;
		default:
			typeString = "shell";
			break;
		}

		if (typeString.equals("shell"))
		{
			try
			{
				ChannelShell channel = (ChannelShell) this.session.openChannel(typeString);
				// TODO: Implement if necessary
			} catch (JSchException e)
			{
				// TODO: Log
				e.printStackTrace();
			}
		} else if (typeString.equals("exec"))
		{
			try
			{
				ChannelExec exec = (ChannelExec) this.session.openChannel(typeString);

				
				// TODO: Get output 
				exec.setCommand(command);
				exec.connect();
				
				
				// BufferedReader in = new BufferedReader(new
				// InputStreamReader(exec.getInputStream()));

				// String message = null;
				// while ((message = in.readLine()) != null)
				// {
				// logger.info(message);
				// }


				exec.disconnect();

			} catch (JSchException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("output = " + outputLog);
		return outputLog;
	}

	public void tearDown()
	{
		this.session.disconnect();
		this.session = null;
		this.jsch = null;
	}
}
