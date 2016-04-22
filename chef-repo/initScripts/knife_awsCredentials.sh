#!/bin/bash
# This script creates the aws credentials folder and files
# author: Tobias Freundorfer
# date: 22.04.2016

# Create folder and files
cd ~
mkdir .aws
cd .aws
touch credentials

# Write default credentials to file
echo '[default]' >> credentials
echo 'aws_access_key_id = Admin-key-pair-frankfurt' >> credentials
echo 'aws_secret_access_key = ./Admin-key-pair-frankfurt.pem' >> credentials

echo 'INFO: You have to copy the Key >Admin-key-pair-frankfurt.pem< into the .aws directory'
