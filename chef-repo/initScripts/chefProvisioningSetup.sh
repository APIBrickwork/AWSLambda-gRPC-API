#!/bin/bash
# This script creates the aws credentials folder and files and sets everything up for Chef Provisioning (with AWS)
# author: Tobias Freundorfer
# date: 06.05.2016

echo "### Starting Setup for Chef Provisioning with AWS ###"

credentialsDir=".aws"
credentialFilename="credentials"
configFilename="config"
ChefConfigDir="git/LabCourse-group4-SS2016/chef-repo/.chef/"
knifeConfigFilename="knife.rb"

echo '### Creating .aws folder in the home directory ###'
# Create folder and files
cd ~
mkdir $credentialsDir
cd $credentialsDir
touch $credentialFilename


echo "### Creating AWS credential file ###"

echo "--> Please insert your AWS Secret Access Key: "
read awsSecretKey
# Write default credentials to file
echo '[default]' > $credentialFilename
echo 'aws_access_key_id = AKIAIJRM4MK36G27VPCQ' >> $credentialFilename
echo "aws_secret_access_key = $awsSecretKey" >> $credentialFilename

echo "### Creating AWS Config file ###"
touch $configFilename
echo 'export CHEF_DRIVER=aws # on Unix' > $configFilename

echo "--> Please create the directory /etc/chef if not already existing."
read
echo "--> Copy your private key for AWS into the folder /etc/chef and call it 'client.pem'."
read

echo "### Creating the knife.rb (Chef knife configuration file) ###"

cd ~
cd $ChefConfigDir
touch $knifeConfigFilename
echo "knife[:aws_credential_file] = \"$HOME/$credentialsDir/$credentialFilename\"" > $knifeConfigFilename
echo "knife[:ssh_key_name] = \"knife\"" >> $knifeConfigFilename
echo "knife[:region] = \"eu-central-1\"" >> $knifeConfigFilename