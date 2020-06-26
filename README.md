# :no_entry_sign: This project is under active development and not ready for use yet. :no_entry_sign: 


# Gradle Project AutoConfiguration for Java Development

It is a result of my years of daily work in my career. It provides opinionated way for daily Java/Groovy backend microservice development in enterprise. 

Hopefully it maybe useful for you.


## NOTE: After you cloned this project, please execute `cd gradle-autoconfig; ln -sv ${PWD}/init.gradle ~/.gradle/init.gradle` 

## Design Goals

### Low learning curve

The ultimate goal is automatically configuring everything. The only thing that user need to do is 
adding `dependences` to his project's `build.gradle`. All the building scripts are well organized.

It also provides similar experience for developers originally using Maven by utilizing/customizing 
several OpenSource Gradle plugins.

### Centralizing all build configurations for ease management

In an enterprise, normally there's a CI/CD system. The Ops team need to customize the building environment.
Also, specific branching/versioning rules needs to be applied to all projects. 
`gradle-autoconfig` is a way to standardize everything. 

## Assumptions
1. Language: Java/Groovy, Only JDK8+ is supported.
2. Repository Manager: SonaType Nexus
3. Source Control Management: Git 

## A simple project discovery 
(TODO)

## Automatically versioning
(TODO)

All the artifacts produced sharing a single version which is determined automatically by Git info.

## Tips: Working with a Nexus server locally

1. Create a persistent data directory `mkdir -p ${HOME}/workspace/nexus-data`
2. Create a `docker-compose.yml`.
```$xslt
version: "3.8"
  services:
    nexus:
        restart: always
        image: sonatype/nexus
        volumes:
            - ${HOME}/workspace/nexus-data:/sonatype-work
```
NOTE: `restart: always` is useful. Docker instances will automatically start after computer reboot.
3. Run `docker-compose -d up`.
4. Create `proxy` type of Repositories for

5. Creating/Updating your `~/.gradle/gradle.properties`

```$xslt
nexus.mirror.url=127.0.0.1/nexus/contents/group/public
```

6. Replace your `~/.m2/settings.xml`
```$xslt
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <mirrors>
    <mirror>
      <id>local-public</id>
      <name>Dante Sun Public</name>
      <url>http://127.0.0.1:8081/nexus/content/groups/public</url>
      <mirrorOf>*</mirrorOf>
    </mirror>
  </mirrors>
  <profiles>
    <profile>
      <id>local-nexus</id>
      <repositories>
        <repository>
          <id>localhost</id>
          <url>http://127.0.0.1:8081/nexus/content/groups/public</url>
           <releases>
              <enabled>true</enabled>
           </releases>
           <snapshots>
              <enabled>true</enabled>
              <updatePolicy>always</updatePolicy>
           </snapshots>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>localhost</id>
          <url>http://127.0.0.1:8081/nexus/content/groups/public</url>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  <activeProfiles>
      <activeProfile>local-nexus</activeProfile>
   </activeProfiles>
</settings>
```

