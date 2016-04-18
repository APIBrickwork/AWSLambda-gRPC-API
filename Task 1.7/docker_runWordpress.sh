# Pull docker images
docker pull mysql
docker pull wordpress

# Remove ALL existing docker containers
docker rm `docker ps --no-trunc -aq`

# Run mysql database
docker run --name wordpress-mysql -e MYSQL_ROOT_PASSWORD=cloud2016 -d mysql:latest
# Run wordpress
docker run --name wordpress --link wordpress-mysql:mysql -p 8080:80 -d wordpress
