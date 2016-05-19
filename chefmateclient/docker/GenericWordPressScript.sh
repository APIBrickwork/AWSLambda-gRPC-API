
# Init chef repo
(echo "initChefRepo" 
	&& echo "ubuntu" # username
	&& echo $3 # host
	&& echo "chefmateserver_key.pem" # private key name
	&& echo "10000" # ssh timeout
	&& echo "shutdown" # shutdown client
) | java -jar chefmateclient-1.0-jar-with-dependencies.jar -h $1 -p $2

# Deploy WordPress with default settings
(echo "executeCookbook" 
	&& echo "ubuntu" # username
	&& echo $3 # host
	&& echo "chefmateserver_key.pem" # private key name
	&& echo "10000" # ssh timeout
	&& echo "cb-wordpress" # cookbook name
	&& echo "default.rb" # recipe name
	&& echo "0" # attributes qty: 0 = default values only
	&& echo "shutdown" # shutdown client
) | java -jar chefmateclient-1.0-jar-with-dependencies.jar -h $1 -p $2
