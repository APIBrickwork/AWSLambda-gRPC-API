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
echo '[default]' >> $credentialFilename
echo 'aws_access_key_id = Admin-key-pair-frankfurt' >> $credentialFilename
echo 'aws_secret_access_key = ./Admin-key-pair-frankfurt.pem' >> $credentialFilename

echo '--> Editing the knife.rb (Chef knife configuration file) to point to the previously created credentials file'
mkdir $ChefConfigDir
cd $ChefConfigDir
touch $knifeConfigFilename
echo "knife[:aws_credential_file] = \"$HOME/$credentialsDir/$credentialFilename\"" >> $knifeConfigFilename

echo 'INFO: You have to copy the Key >Admin-key-pair-frankfurt.pem< into the .aws directory'