# :no_entry_sign: This project is under active development and not ready for use yet. :no_entry_sign: 


# :no_entry_sign: :no_entry_sign: This project is under active development and not ready for use yet.

# Gradle Project AutoConfiguration for Java Development(`gradle-autoconfig`)

It is a result of my years of daily work in my career. It provides opinionated way for daily Java/Groovy development in enterprise. 
[Nebula Plugins](https://nebula-plugins.github.io/) is a similar effort, But `gradle-autoconfig` do it in a cleaner way.

Hopefully it maybe useful for you.

## NOTE for contributors 

After you cloned this project, please put `autoconfig.gradle` in your Gradle user home directory's init.d subdirectory, 
normally in `~/.gradle/init.d/`. A symbol link is working correctly.

```bash
ln -sv $PWD/gradle/autoconfig/autoconfig.gradle ~/.gradle/init.d/
```

## Design Goals

### Low learning curve and Maven compatible

The ultimate goal is automatically configuring everything. The only thing that user need to do is 
adding `dependences` to his project's `build.gradle`. All the building scripts are well organized.

It also provides similar experience for developers originally using Maven by utilizing/customizing 
several OpenSource Gradle plugins.

### Centralizing all build configurations for ease management

In an enterprise, normally there's a CI/CD system. The Ops team need to customize the building environment.
Also, specific branching/versioning rules needs to be applied to all projects. 
`gradle-autoconfig` is a way to standardize everything. 

`gradle-autoconfig` uses **ONE** and managed `settings.gradle`. The root project's only acting like a container
that manages the sub-projects, no source code can be put in root project. In this way, it greatly reduces the 
complexity of management of your Gradle projects.  

### Step out of your way

You can selectively disable every function `gradle-autoconfig` provides as you don't like or wish to customize it.
It also has a global switch to turn it off completely.

## Assumptions
1. Language: Java/Groovy, Only JDK8+ is supported.
2. Repository Manager: SonaType Nexus
3. Source Control Management: Git 
4. Gradle 4.2.1 and above

# User Guide

## Automated project discovery 

Recursively, any sub-directory in project root contains a file `build.gradle` will be treated as a valid Gradle project. For example:

```diff
 ├── .gradle 
 ├── build 
 ├── gradle
 │   └── wrapper  
+├── sub-project1
+│   └── build.gradle or build.gradle.kts
+│   └── sub-project2
+│       └── build.gradle or build.gradle.kts
 │
 ├── build.gradle or build.gradle.kts 
 ├── gradle.properties 
 ├── gradlew 
 ├── gradlew.bat 
 └── settings.gradle or settings.gradle.kts 
```

## Managed versioning/branching

Managing your artifact's versions is an important and difficult job. Automatically versioning might be a nice thought but really is hard to implement.
See this article for more details.[Semantic Versioning Sucks! Long Live Semantic Versioning](https://developer.okta.com/blog/2019/12/16/semantic-versioning).

There'are a lot of gradle plugins that trying to manage your project's version, such as :
1. [Reckon](https://github.com/ajoberstar/reckon). 
2. [Neblua Release Plugin](https://github.com/nebula-plugins/nebula-release-plugin)
3. [Axion Release Plugin](https://github.com/allegro/axion-release-plugin)
4. ...(Search `gradle git version` in Google or Gradle Plugin Portal for more results)

They're always enforcing some branching model or version scheme or way too complex. What's I need is a more flexible and intuitive way
to manage versions and releases. 

`SNAPSHOT` is strongly not recommended because you might wrongly override other's work or even in other CI/CD pipelines.
I think there'is only one scenario that `SNAPSHOT` version is convenient. You are working on multiple projects that have dependencies with each other.
It will save some work by installing your `SNAPSHOT` artifact to your local maven repository and other projects will get the updates immediately.

In order to keep the maximum compatibility with Maven. Version numbers should stick to [Semantic Versioning 1.0.0](https://semver.org/spec/v1.0.0.html). 
See [Maven Version Order Specification](https://maven.apache.org/pom.html#Version_Order_Specification) for more details.

So the version number should be in three forms:

1. `RELEASE` for officially publications.
2. `SNAPSHOT` for local development only.
3. `PRE-RELEASE` for integration purpose.

(To be continued ...)

## Tips And Tricks: Working with a Nexus server locally

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

