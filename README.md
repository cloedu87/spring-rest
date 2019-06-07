
1 > compile / create artifct `build/libs/spring-rest*.jar` / run tests

`gradle build`

2 > build docker image

`docker build -t springrest .`

3 > pull the mongoDB image

`docker pull mongo:4.0.10-xenial`

4 > run images
`docker run -p 27017:27017 --name mongodb -d mongo:4.0.10-xenial`
`docker run -p 8080:8080 --name springrest --link mongodb:mongodb springrest`

5 > have a coffee bro, you deserve it `\m/`