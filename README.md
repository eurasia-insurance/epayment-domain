To run test you could use docker image mysql server by running 

```shell
docker run -it --rm \
	-e MYSQL_DATABASE=junit \
	-e MYSQL_USER=junit \
	-e MYSQL_PASSWORD=junit \
	-e MYSQL_RANDOM_ROOT_PASSWORD=yes \
	-p 33306:3306 \
	--name junit_mysql \
	mariadb
```

And stop it by

```shell
docker container kill junit_mysql
```
