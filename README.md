#### build, test and run local environment

1 > compile / create artifct `build/libs/spring-rest*.jar` / run tests

`gradle build`


2 > start docker containers according to docker-compose.yml file

`docker-compose up`

3 > have a coffee bro, you deserve it `\m/`


#### use postman as API client/test-/monitor-/documentation-suite

1 > download postman from https://www.getpostman.com/

2 > import postman collection

`./spring-rest.postman_collection.json`

3 > import postman environment

`localhost.postman_environment.json`


#### what and why
```
. \
├── Dockerfile                            -> docker configuration file
├── README.md                             -> this file
├── build.gradle                          -> gradle dependency management and repository config 
├── docker-compose.yml                    -> config for docker compose, container run config
├── localhost.postman_environment.json    -> postman rest client environment config
├── settings.gradle                       -> gradle configuration 
├── spring-rest.postman_collection.json   -> postman rest client request config
└── src
    ├── main
    │   ├── java                          -> source code
    │   └── resources                     -> application runtime config files
    └── test
        ├── java                          -> test source code
        └── resources                     -> test properties and config files

```