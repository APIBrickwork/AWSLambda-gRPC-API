# Create VM
(echo "createVM" 
	&& echo "wordpress" # instance name
	&& echo "scriptedTag" # instance tag
	&& echo "eu-central-1" # region
	&& echo "ami-87564feb" # image-id
	&& echo "ubuntu" # username
	&& echo "t2.micro" # flavor
	&& echo "sg-79ae5d11" # security group
	&& echo "shutdown" # shutdown client
) | java -jar chefmateclient-1.0-jar-with-dependencies.jar -h $1 -p $2

# Init chef repo
(echo "initChefRepo" 
	&& echo "ubuntu" # username
	&& echo "TODO HOST??" # host
	&& echo "chefmateserver_key.pem" # private key name
	&& echo "10000" # ssh timeout
	&& echo "shutdown" # shutdown client
) | java -jar chefmateclient-1.0-jar-with-dependencies.jar -h $1 -p $2

# Deploy WordPress with default settings
(echo "executeCookbook" 
	&& echo "ubuntu" # username
	&& echo "TODO HOST??" # host
	&& echo "chefmateserver_key.pem" # private key name
	&& echo "10000" # ssh timeout
	&& echo "cb-wordpress" # cookbook name
	&& echo "default.rb" # recipe name
	&& echo "0" # attributes qty: 0 = default values only
	&& echo "shutdown" # shutdown client
) | java -jar chefmateclient-1.0-jar-with-dependencies.jar -h $1 -p $2
