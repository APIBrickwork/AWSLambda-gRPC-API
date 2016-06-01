# ChefMateServer
This is the ChefMateServer offering Chef-Functionality through gRPC calls.

## Installation

* Download the binary file.
* Make sure that a Java Runtime Environment is installed.
* Make sure that Chef.io is available.
* Make sure to FIRST call `-dc` and afterwards `-i`.

## Start Parameters
* __--help__ Show help prompt
* __-dc__ Creates the server environment folder at `$HOME/chefmateserver` and generates the default configuration file `chefmate.conf`. 

> The default config does NOT contain the AWS access and secret access key for security reasons. This has to be added manually, if local installation, or can be specified as input parameters for docker, if using docker containers. 

* __-i__ Initializes the server environment according to the settings specified in `chefmate.conf`. This includes pulling the following [Chef repository](https://github.com/tfreundo/LabCourse-group4-SS2016-CHEFrepo) by default (or the one specified in `chefmate.conf`) and afterwards executing the initialization script [chefMateServerChefProvisioningSetup.sh](https://github.com/tfreundo/LabCourse-group4-SS2016-CHEFrepo/blob/master/initScripts/chefMateServerChefProvisioningSetup.sh)
* __-p__ Start the ChefMateServer listening on the specified port.

## Docker

### Prerequesites

* Open the docker file and insert `aws_access_key` and `aws_secret_access_key` WITHIN the high comma's (if the key looks like xxxxx/xxxx be sure to escape the slash like this: xxxxx\/xxxx

### Images
* Build an image: `sudo docker build -t <name>:<version> .`
* List all images: `sudo docker images`

### Containers
* Show stats of running containers: `sudo docker stats --all`
* List all containers: `sudo docker ps -a`

### Get IP of container
Get the ID of the desired container and execute: `sudo docker inspect <containerId>` and look for the field `IPAddress`
