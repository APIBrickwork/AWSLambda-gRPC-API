# Ubuntu 14.04 with Java 8 installed.
# Build image with:  docker build -t Chefmate/chemateserver .

FROM ubuntu
MAINTAINER Tobias Freundorfer, https://github.com/tfreundo

#################################
## INSERT AWS CREDENTIALS HERE ##
#################################
ENV host 'ENTER CHEFMATESERVER HOST'
ENV port 'ENTER CHEFMATESERVER PORT'

# Install java 8
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean

# Add user
RUN useradd -ms /bin/bash chefmate

# Set user and working directory (everything below is executed as this user)
USER chefmate
WORKDIR /home/chefmate

# Copy chefmatesclient to container
COPY ./chefmateclient-1.0.jar /home/chefmate/

# Copy scripts to container
COPY ./WordPressScript.sh /home/chefmate
COPY ./GenericWordPressScript.sh /home/chefmate

#Expose ports
EXPOSE 8080

############################
## ChefmateClient Section ##
############################

# Make the scripts executable
RUN chmod +x WordPressScript.sh
RUN chmod +x GenericWordPressScript.sh

# Start WordPress specific script
RUN ./WordPressScript.sh ${host} ${port}

# Start Generic script
RUN ./GenericWordPressScript.sh ${host} ${port}