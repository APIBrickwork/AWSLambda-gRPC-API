package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DeleteKeyPairRequest;
import com.amazonaws.services.ec2.model.DeleteKeyPairResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

import io.grpc.stub.StreamObserver;
import services.Chefmate.AWSInstanceId;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.CreateVMResponse;
import services.Chefmate.DestroyVMRequest;
import services.Chefmate.DestroyVMResponse;
import services.Chefmate.InitCHEFRepoRequest;
import services.Chefmate.InitCHEFRepoResponse;
import util.ChefAttributesWriter;
import util.Config;
import util.SSHExecutor;
import util.SSHExecutor.ChannelType;
import util.ShellExecutor;

/**
 * Service implementation for EC2Ops.
 * 
 * @author Tobias Freundorfer
 *
 */
public class EC2OpsImpl implements EC2OpsGrpc.EC2Ops
{
	private static final Logger logger = Logger.getLogger(EC2OpsImpl.class.getName());

	/**
	 * Creates a VM on AWS.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void createVM(CreateVMRequest request, StreamObserver<CreateVMResponse> responseObserver)
	{
		logger.info("### Received request for createVM with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();
		List<String> instanceInformations = new ArrayList<>();

		Config config = Config.getInstance(false, false);
		List<String> instanceIds = new ArrayList<>();
		List<String> publicDNSs = new ArrayList<>();

		DefaultAWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();
		AmazonEC2Client ec2 = new AmazonEC2Client(credentials);

		try
		{
			ec2.setRegion(Region.getRegion(Regions.fromName(request.getRegion())));

		} catch (IllegalArgumentException e)
		{
			String msg = "### Region " + request.getRegion() + " is not valid.";
			logger.warning(msg);
			outputLog.add(msg);
		}

		// Check if ssh key already exists in AWS
		List<KeyPairInfo> keypairs = ec2.describeKeyPairs().getKeyPairs();
		boolean keypairExistsOnAWS = false;

		for (KeyPairInfo info : keypairs)
		{
			if (info.getKeyName().equals(config.getAwsSSHKeyName()))
			{
				keypairExistsOnAWS = true;
			}
		}
		// Create key-pair if it doesn't exist
		logger.info("### KeyPair for ChefMateServer exists on AWS = " + keypairExistsOnAWS);
		boolean keyPairExistsLocally = new File(config.getDefaultSSHKeyPath() + config.getAwsSSHKeyName()).exists();
		logger.info("### KeyPair for ChefMateServer exists Locally = " + keyPairExistsLocally);

		if (!keyPairExistsLocally | !keypairExistsOnAWS)
		{
			// Remove it from AWS if it only exists there
			if (keypairExistsOnAWS)
			{
				logger.info("### Deleting KeyPair from AWS.");
				DeleteKeyPairRequest deleteKeyPairRequest = new DeleteKeyPairRequest();
				deleteKeyPairRequest.withKeyName(config.getAwsSSHKeyName());

				DeleteKeyPairResult result = ec2.deleteKeyPair(deleteKeyPairRequest);
				logger.info("### Deleted key pair: " + result.toString());
			}

			CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();
			createKeyPairRequest.withKeyName(config.getAwsSSHKeyName());

			CreateKeyPairResult result = ec2.createKeyPair(createKeyPairRequest);

			KeyPair keypair = new KeyPair();
			keypair = result.getKeyPair();
			String privatekey = keypair.getKeyMaterial();
			String keyName = keypair.getKeyName();
			logger.info("### Writing keypair " + config.getDefaultSSHKeyPath() + keyName);

			// Write key to .ssh
			FileWriter writer = null;
			try
			{
				writer = new FileWriter(new File(config.getDefaultSSHKeyPath() + keyName));
				writer.write(privatekey);

			} catch (IOException e)
			{
				String msg = "### Error while writing SSH private key with name " + config.getDefaultSSHKeyPath()
						+ keyName + "\n " + e.getMessage();
				logger.warning(msg);
				outputLog.add(msg);
			} finally
			{
				try
				{
					writer.close();
				} catch (IOException e)
				{
					String msg = "### Error while closing FileWriter";
					logger.warning(msg);
					outputLog.add(msg);
				}
			}

			// chmod 600 on key
			List<String> chmodCommands = new ArrayList<>();
			chmodCommands.add("chmod");
			chmodCommands.add("600");
			chmodCommands.add(keyName);
			outputLog.addAll(ShellExecutor.execute(config.getDefaultSSHKeyPath(), chmodCommands));

			// ssh-add on key
			List<String> sshAddCommands = new ArrayList<>();
			sshAddCommands.add("ssh-add");
			sshAddCommands.add(keyName);
			outputLog.addAll(ShellExecutor.execute(config.getDefaultSSHKeyPath(), sshAddCommands));
		}

		RunInstancesRequest runRequest = new RunInstancesRequest();
		runRequest.setImageId(request.getImageId());
		runRequest.setInstanceType(InstanceType.fromValue(request.getInstanceType()));
		runRequest.setSecurityGroupIds(request.getSecurityGroupIdsList());

		runRequest.setKeyName(config.getAwsSSHKeyName());
		runRequest.setMinCount(1);
		runRequest.setMaxCount(1);

		RunInstancesResult runResult = ec2.runInstances(runRequest);
		List<String> createdInstancesIds = new ArrayList<String>();

		logger.info("### Created " + runResult.getReservation().getInstances().size() + " instance/s.");
		InstanceState desiredState = new InstanceState();
		desiredState.withName(InstanceStateName.Running);

		for (int i = 0; i < runResult.getReservation().getInstances().size(); i++)
		{
			// Wait until instance is running
			String currentInstanceId = runResult.getReservation().getInstances().get(i).getInstanceId();
			logger.info("### Waiting for Instance with ID " + currentInstanceId + " to get in InstanceState "
					+ desiredState.getName() + ".");
			DescribeInstancesRequest describeRequest = new DescribeInstancesRequest();

			// Request for the instance state of the current instance
			List<String> instanceToDescribe = new ArrayList<String>();
			instanceToDescribe.add(currentInstanceId);
			describeRequest.setInstanceIds(instanceToDescribe);
			InstanceState currentState = null;

			// Request every 10 seconds if the instance state is running
			do
			{
				DescribeInstancesResult result = ec2.describeInstances(describeRequest);
				currentState = result.getReservations().get(0).getInstances().get(0).getState();

				try
				{
					Thread.sleep(10000);
				} catch (InterruptedException e)
				{
					String msg = "### Error while waiting for Instance with ID " + currentInstanceId
							+ " to get in InstanceState Running. \n " + e.getMessage();
					logger.warning(msg);
					outputLog.add(msg);
				}
			} while (!currentState.getName().equals(desiredState.getName()));

			// Request once again to be sure the public DNS is already set.
			DescribeInstancesResult result = ec2.describeInstances(describeRequest);

			instanceIds.add(currentInstanceId);
			String currentPublicDns = result.getReservations().get(0).getInstances().get(0).getPublicDnsName();
			publicDNSs.add(currentPublicDns);

			String msg = "############################\n ### Instance-Information " + i + " | instance-id = "
					+ currentInstanceId + " | publicDNS = " + currentPublicDns + "\n ############################";
			instanceInformations.add(msg);
			logger.info(msg);
			createdInstancesIds.add(currentInstanceId);
		}

		if (!request.getName().isEmpty())
		{
			CreateTagsRequest tagRequest = new CreateTagsRequest();
			tagRequest.setResources(createdInstancesIds);
			Tag tag = new Tag();
			tag.setKey("Name");
			tag.setValue(request.getName());

			List<Tag> tags = new ArrayList<Tag>();
			tags.add(tag);
			tagRequest.setTags(tags);
			ec2.createTags(tagRequest);
		}

		if (!request.getTag().isEmpty())
		{
			// Is only possible as soon as instance is running
			CreateTagsRequest tagRequest = new CreateTagsRequest();
			tagRequest.setResources(createdInstancesIds);
			Tag tag = new Tag();
			tag.setKey("Tag");
			tag.setValue(request.getTag());

			List<Tag> tags = new ArrayList<Tag>();
			tags.add(tag);
			tagRequest.setTags(tags);
			ec2.createTags(tagRequest);
		}

		// Bootstrap the instance using knife
		List<String> bootstrapCommands = new ArrayList<>();
		bootstrapCommands.add("knife");
		bootstrapCommands.add("bootstrap");
		bootstrapCommands.add("-V"); // Verbose output
		bootstrapCommands.add(publicDNSs.get(0));
		bootstrapCommands.add("-z");
		bootstrapCommands.add("--ssh-user");
		bootstrapCommands.add(request.getUsername());
		bootstrapCommands.add("-N");
		if (!request.getName().isEmpty())
		{
			bootstrapCommands.add(request.getName());
		} else
		{
			bootstrapCommands.add("chefnode");
		}
		bootstrapCommands.add("--sudo");
		bootstrapCommands.add("--ssh-identity-file");
		bootstrapCommands.add(config.getDefaultSSHKeyPath() + config.getAwsSSHKeyName());

		String execDir = config.getChefRepoPath();

		// catch possible race condition
		int tries = 0;
		boolean success = false;

		while (!success && tries < 10)
		{
			tries++;
			System.out.println("start " + success + " | " + tries);
			logger.info("### Starting bootstrapping using from directory " + execDir + "/.chef" + " using commands: "
					+ bootstrapCommands);
			List<String> log = ShellExecutor.execute(execDir + "/.chef", bootstrapCommands);
			outputLog.addAll(log);
			for (int j = 0; j < log.size(); j++)
			{
				// sleep if there is a network error
				if (log.get(j).startsWith("ERROR: Network Error"))
				{
					try
					{
						Thread.sleep(20000);
						success = false;
						break;
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}

				} else
				{
					success = true;
				}
			}
			System.out.println("end " + success + " | " + tries);
		}

		for (String msg : instanceInformations)
		{
			logger.info(msg);
			outputLog.add(msg);
		}

		CreateVMResponse resp = CreateVMResponse.newBuilder()
				.setInstanceId(AWSInstanceId.newBuilder().setId(instanceIds.get(0)).build())
				.setPublicDNS(publicDNSs.get(0)).addAllOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

	/**
	 * Destorys a VM on AWS.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void destroyVM(DestroyVMRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		logger.info("### Received request for destroyVM with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

		DefaultAWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();
		AmazonEC2Client ec2 = new AmazonEC2Client(credentials);

		try
		{
			ec2.setRegion(Region.getRegion(Regions.fromName(request.getRegion())));

		} catch (IllegalArgumentException e)
		{
			 String msg = "### Region " + request.getRegion() + " is not valid.";
			 logger.warning(msg);
			 outputLog.add(msg);
		}

		DescribeInstancesResult describeResult = ec2.describeInstances();

		String seekingMsg = "### Seeking in " + describeResult.getReservations().size()
				+ " reservations for instance with Id = " + request.getInstanceId();
		logger.info(seekingMsg);
		outputLog.add(seekingMsg);

		for (Reservation reservation : describeResult.getReservations())
		{
			List<Instance> instances = reservation.getInstances();
			for (Instance instance : instances)
			{
				if (instance.getInstanceId().equals(request.getInstanceId().getId()))
				{
					String foundMsg = "### Found instance with Id = " + instance.getInstanceId()
							+ ". Terminating it...";
					logger.info(foundMsg);
					outputLog.add(foundMsg);

					TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest();
					List<String> ids = new ArrayList<>();
					ids.add(request.getInstanceId().getId());
					terminateRequest.setInstanceIds(ids);
					TerminateInstancesResult terminateResult = ec2.terminateInstances(terminateRequest);

					String terminatedMsg = "### Terminated instances " + terminateResult.getTerminatingInstances();
					logger.info(terminatedMsg);
					outputLog.add(terminatedMsg);
				}
			}
		}

		DestroyVMResponse resp = DestroyVMResponse.newBuilder().addAllOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

	/**
	 * Initializes the Chef Repository on the VM.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void initChefRepo(InitCHEFRepoRequest request, StreamObserver<InitCHEFRepoResponse> responseObserver)
	{
		logger.info("### Received request for initChefRepo with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

		Config config = Config.getInstance(false, false);

		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();

		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();
		int timeout = request.getCredentials().getTimeout();
		SSHExecutor ssh = new SSHExecutor();

		ssh.connectHost(username, host, 22, timeout, keyfile);

		String aptGetUpdateCommand = "sudo apt-get -y update";
		logger.info("### Starting apt-repository update using commands: " + aptGetUpdateCommand);
		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, aptGetUpdateCommand, timeout));
		ssh.tearDown();

		String gitInstallCommand = "sudo apt-get -y install git";
		logger.info("### Installing git using commands: " + gitInstallCommand);
		ssh = new SSHExecutor();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, gitInstallCommand, timeout));
		ssh.tearDown();

		String pullChefRepoCommand = "mkdir git && cd git && git clone -b development " + config.getChefRepoURL();

		logger.info("### Pulling git repository using commands: " + pullChefRepoCommand);
		ssh = new SSHExecutor();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, pullChefRepoCommand, timeout));
		ssh.tearDown();

		InitCHEFRepoResponse resp = InitCHEFRepoResponse.newBuilder().addAllOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}
}
