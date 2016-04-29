#!/bin/bash
# This script creates the aws credentials folder and files
# author: Tobias Freundorfer
# date: 22.04.2016
credentialsDir=".aws"
credentialFilename="credentials"
ChefConfigDir="git/LabCourse-group4-SS2016/chef-repo/.chef/"
knifeConfigFilename="knife.rb"

echo '--> Creating .aws folder in the home directory and adding the credentials file'
# Create folder and files
cd ~
mkdir $credentialsDir
cd $credentialsDir
touch $credentialFilename

# Write default credentials to file
echo '[default]' > $credentialFilename
echo 'aws_access_key_id = AKIAIJRM4MK36G27VPCQ' >> $credentialFilename
echo 'aws_secret_access_key = Your Secret Access Key' >> $credentialFilename

echo '--> Editing the knife.rb (Chef knife configuration file) to point to the previously created credentials file'
cd ~
mkdir $ChefConfigDir
cd $ChefConfigDir
touch $knifeConfigFilename
echo "knife[:aws_credential_file] = \"$HOME/$credentialsDir/$credentialFilename\"" > $knifeConfigFilename
echo "knife[:ssh_key_name] = \"Admin-chefIO-knife\"" >> $knifeConfigFilename
echo "knife[:region] = \"eu-central-1\"" >> $knifeConfigFilename
echo "knife[:local_mode] = true" >> $knifeConfigFilename

echo '###### User-TODO ######'
echo 'INFO: You have to copy the Key >Admin-chefIO-knife.pem< into the $HOME/.ssh directory'
echo 'INFO: You have to edit your Secret Access Key in the credentials file at $HOME/.aws.'