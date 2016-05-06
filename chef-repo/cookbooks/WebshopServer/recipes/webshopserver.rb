#
# Cookbook Name:: WebshopServer
# Recipe:: default
#
# Copyright 2016, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

# Use AWS to provision to
require 'chef/provisioning/aws_driver'
with_driver 'aws::eu-central-1'

machine 'webshopserver' do
   tag 'WebshopServer'
end
