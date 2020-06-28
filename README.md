# :no_entry_sign: :no_entry_sign: This project is under active development and not ready for use yet. 

# `gradle-autoconfig` - Gradle Project AutoConfiguration Framework

It is a result of my years of daily work in my career. Originally are the rules my team followed, some of them are implemented
by a gradle plugin, some of them are just a common sense. In this project, I try to turn the `common sense` part into code too.
It provides opinionated way for daily Java/Groovy development in enterprise. 

[Nebula Plugins](https://nebula-plugins.github.io/) is a similar effort, But `gradle-autoconfig` do it in a cleaner way.

Hopefully it maybe useful for you.

## Note

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

## Managed Repository Policy

The centralized repository management is really important for an enterprise because of the concerns for Security/API Compatibility/LICENSE ...etc.

However, Gradle allows you to specify separate Repository Manager(SonaType Nexus/Ivy/Artifactory) for Plugin/BuildScript dependencies and Project dependencies.

1. `pluginManagement.repositories{}` for `plugins{}` DSL
2. `project.buildscript.repositories{}` for `apply plugin: ${pluginId}` DSL
3. `project.repositories` for your project's dependency
4. `buildSrc` is a separate project that can be configured independently.

`gradle-autoconfig` provides a way to set a single repository manager for all. 

## Managed Project Build Layout

Gradle supports a complex project hierarchy when you are using multiple `settings.gradle` files.
see [Build Lifecycle](https://docs.gradle.org/current/userguide/build_lifecycle.html) and [Multi-project Builds](https://docs.gradle.org/current/userguide/multi_project_builds.html)) for details

It's really hard to see the relationships and building logic the first glance. You have to maintain a big `settings.gradle` file.
For example, [SpringBoot's settings.gradle](https://github.com/spring-projects/spring-boot/blob/master/settings.gradle) or [Gradle's settings.gradle](https://github.com/gradle/gradle/blob/master/settings.gradle.kts).

In `gradle-autoconfig`,  any sub-directory(recursively) in project root contains a file `build.gradle` will be treated as a valid Gradle project. For example:

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



