# 2D Shooter Android Game

### For development

##### Run the game only
```
./gradlew desktop:run -PappArgs="['local']"
```

##### Run local server/client
```
./gradlew desktop:run -PappArgs="['local']"
./gradlew desktop:run -PappArgs="['local']"
```

### For production

##### Run backend server
```
cd server
docker-compose up
```

##### Run a client
```
./gradlew desktop:run
```


### Example image

![Image of Game](http://i.imgur.com/ZDCPFB7.png)
