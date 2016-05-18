# Ubuntu 14.04 with Java 8 installed.
# Build image with:  docker build -t Chefmate/chemateserver .

FROM ubuntu
MAINTAINER Tobias Freundorfer, https://github.com/tfreundo

#################################
## INSERT AWS CREDENTIALS HERE ##
#################################
ENV awsAccessKey 'ENTER KEY'
ENV awsSecretAccessKey 'ENTER KEY'

# Install java 8 and git
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y git && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean

# Install chef-client
RUN wget https://packages.chef.io/stable/ubuntu/10.04/chef_12.10.24-1_amd64.deb
RUN dpkg -i chef_12.10.24-1_amd64.deb

# Install chef-dk
RUN wget https://packages.chef.io/stable/ubuntu/12.04/chefdk_0.13.21-1_amd64.deb
RUN dpkg -i chefdk_0.13.21-1_amd64.deb

# Add user
RUN useradd -ms /bin/bash chefmate

# Make dir for chef and grant permissions to user
RUN mkdir /etc/chef
RUN chown chefmate:chefmate -R /etc/chef

# Set user and working directory (everything below is executed as this user)
USER chefmate
WORKDIR /home/chefmate

# Copy chefmateserver to container
RUN mkdir /home/chefmate/chefmateserver
COPY ./chefmateserver-1.0.jar /home/chefmate/chefmateserver

# Copy ssh keys to container
RUN mkdir /home/chefmate/.ssh
COPY ./chefmateserver_key.pem /home/chefmate/.ssh
COPY ./chefmateserver_key.pub /home/chefmate/.ssh

#Expose ports
EXPOSE 8080

############################
## ChefmateServer Section ##
############################

# Change dir to chefmateserver
WORKDIR /home/chefmate/chefmateserver

# Create default chefmateserver config file
RUN java -jar chefmateserver-1.0.jar -dc

# Modify config and insert AWS credentials
RUN sed -i "s/^aws_access_key=.*$/aws_access_key=${awsAccessKey}/" chefmate.conf 
RUN sed -i "s/^aws_secret_access_key=.*$/aws_secret_access_key=${awsSecretAccessKey}/" chefmate.conf

# Initialize
RUN java -jar chefmateserver-1.0.jar -i


# Start server
RUN java -jar chefmateserver-1.0.jar -p 8080

# TODO: Add user permissions to create /etc/chef (or create beforehand??)