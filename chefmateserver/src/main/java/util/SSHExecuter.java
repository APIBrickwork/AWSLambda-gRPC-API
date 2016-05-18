package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Helper class for SSH execution using the library JSch.
 * 
 * @author Tobias Freundorfer
 *
 */
public class SSHExecuter
{
	private static final Logger logger = Logger.getLogger(SSHExecuter.class.getName());

	/**
	 * The JSCH used to connect via SSH to remote hosts.
	 */
	private JSch jsch = new JSch();

	/**
	 * The session.
	 */
	private Session session = null;

	/**
	 * Enumeration of the available channels to use.
	 */
	public enum ChannelType
	{
		EXEC
	};

	/**
	 * Connects to the given host.
	 * 
	 * @param username
	 *            The username that should be used.
	 * @param host
	 *            The host address that should be used.
	 * @param port
	 *            The port that should be used.
	 * @param timeout
	 *            The timeout that should be used.
	 * @param privateKeyFile
	 *            The private key that should be used.
	 */
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

	/**
	 * Sends the given command through the given channel.
	 * 
	 * @param type
	 *            The type of channel that should be used.
	 * @param command
	 *            The command that should be transmitted.
	 * @param timeout
	 *            The timeout that should be used.
	 * @return
	 */
	public List<String> sendToChannel(ChannelType type, String command, int timeout)
	{
		// The output (may be reused by the server to send back to client)
		List<String> outputLog = new ArrayList<>();

		String typeString = "";
		switch (type)
		{
		case EXEC:
			typeString = "exec";
			break;
		default:
			typeString = "exec";
			break;
		}

		if (typeString.equals("exec"))
		{
			try
			{
				ChannelExec exec = (ChannelExec) this.session.openChannel(typeString);

				exec.setCommand(command);
				exec.connect();
				logger.info("### Executing " + command + " on channel " + typeString);
				exec.setInputStream(null);
				exec.setErrStream(System.err);

				InputStream in;

				try
				{
					in = exec.getInputStream();
					byte[] tmp = new byte[1024];
					while (true)
					{
						while (in.available() > 0)
						{
							int i = in.read(tmp, 0, 1024);
							if (i < 0)
								break;
							String s = new String(tmp, 0, i);
							System.out.print(s);
							outputLog.add(s);
						}
						if (exec.isClosed())
						{
							if (in.available() > 0)
								continue;
							String exitstatus = "\n exit-status: " + exec.getExitStatus() + "\n";
							System.out.println(exitstatus);
							outputLog.add(exitstatus);
							break;
						}

						try
						{
							Thread.sleep(1000);
						} catch (Exception ee)
						{
						}
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}

				exec.disconnect();

			} catch (JSchException e)
			{
				e.printStackTrace();
			}

		} else if (typeString.equals("scp"))
		{

		}
		return outputLog;
	}

	/**
	 * Tears the ressources down.
	 */
	public void tearDown()
	{
		this.session.disconnect();
		this.session = null;
		this.jsch = null;
	}
}
