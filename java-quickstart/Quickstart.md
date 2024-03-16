
## SDK Minimal Example

Configure properties in [config.properties](src/main/resources/config.properties)

```
appName = <put your Deepchecks application name>
versionName = <put your version>
token = <put your token>
```

Build application:

```
mvn clean install
```

Start up application: 
* for Linux use [start.sh](start.sh) 
* for Windows use [start.bat](start.bat) 
* or execute:

```
java -jar target/quickstart.jar
```