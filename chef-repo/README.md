# Overview

This is the dedicated repository storing every cookbook used in the Lab Course 'Cloud Architectures and Management' in SS2016 at the University of Stuttgart.

# Repository Directories

This repository contains several directories, and each directory contains a README file that describes what it is for in greater detail, and how to use it for managing your systems with Chef.

* `cookbooks/` - Cookbooks you download or create.
* `data_bags/` - Store data bags and items in .json in the repository.
* `roles/` - Store roles in .rb or .json in the repository.
* `environments/` - Store environments in .rb or .json in the repository.

# Configuration

The config file, `.chef/knife.rb` is a repository specific configuration file for knife. If you're using the Chef Platform, you can download one for your organization from the management console. If you're using the Open Source Chef Server, you can generate a new one with `knife configure`. For more information about configuring Knife, see the Knife documentation.

https://docs.chef.io/knife.html

# Amazon WebServices with Chef

This section describes specific setups to use Chef with the Amazon WebServices (AWS)

## AWS EC2 Plugin

The official Chef plugin to create, bootstrap and manage Amazon EC2 instances (see https://github.com/chef/knife-ec2 for further information).

If using the ChefDK simply execute the following command:

`chef gem install knife-ec2`

### Initializing

Use the script [Knife AWS Credentials Initialization Script](/chef-repo/initScripts/knife_awsCredentials.sh) for the inital setup. 
It will automatically:
 * Create the folder .chef within the chef-repo directory
 * Create the Knife configuration file (knife.rb) and set the necessary values
 * Create the folder .aws in your home directory
 * Create the AWS credentials file and set the necessary values

After this executing READ THE MANUAL TODOs IN YOUR TERMINAL.

### Important Commands

#### Creating a new EC2 instance

`knife ec2 server create -I <image-name>`

Exemplary images:

 * ami-e2df388d: Amazon Linux AMI
 * ami-87564feb: Ubuntu Server 14.04 LTS

#### Listing all EC2 instances

`knife ec2 server list`

#### Terminating (and destroying) an existing EC2 instance

`knife ec2 server delete --purge <instance-id>`
