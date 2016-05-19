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