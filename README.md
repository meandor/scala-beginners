# Scala Beginners
Scala Introduction for Beginners

## Official Beginners Guide
The official Scala beginners guide is a great guide for your first step into the Scala world. 

http://docs.scala-lang.org/getting-started.html

## IDE
Jetbrains has a community edition for IntelliJ which will be sufficient for Scala programming

https://www.jetbrains.com/idea/download

## Scala Intro
We start off with installing IntelliJ and Scala on your machine.
The official Scala beginners guide uses sbt to build and manage Scala projects.
We will not go that route and use [gradle](https://gradle.org/) instead. Gradle is a build
tool that will help you built and manage your Scala project.

### Creating a new project with Gradle
The following commands should be done using a linux shell. On windows machines you can use 
the git bash for example.

First we will create a new folder for our new project:
````bash
mkdir parallel-numbers
````

This created the folder called `parallel-numbers`. Now change into that directory:

````bash
cd parallel-numbers
````

Here we will create a new scala project with gradle:
````bash
./gradle init --type scala-library
````  

If you list the content of your folder with:
````bash
ls -all
````

You will see the directory structure gradle created for your application.
In the directory you will see the files
* `build.gradle`
* `settings.gradle`

The `build.gradle` tells gradle what it should build.
It should look like this:
````
...
// Tell gradle that this is a scala project
apply plugin: 'scala'

...

dependencies {
    // Use Scala 2.11
    compile 'org.scala-lang:scala-library:2.11.8'
    ...
}
````

The `settings.gradle` file contains gradle specific settings. At first it should just
contain this:
````
// This project is called parallel-numbers
rootProject.name = 'parallel-numbers'
```` 

The source code will be in the folder `src`.
```
src
├── main                        // contains the main code and files that are used for running your app
|   └── scala                   // all scala files should be in here
|       └── Library.scala       // your scala code
└── test                        // only test code should be in here, does not use this part for running your app
    └── scala                   // all scala files should be in here
        └── LibrarySuite.scala  // test for your scala code found in the main folder
```
