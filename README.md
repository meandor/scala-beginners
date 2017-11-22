# Scala Beginners
Scala Introduction for Beginners. The code that is created for in this tutorial can be found in this repository.

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

### Setup IntelliJ
First open the build.gradle file in your newly created project with IntelliJ.
IntelliJ will ask you if you want to open it as a file or as a project. You of course want to open
it as a project.

This will take you straight to the gradle import dialog of IntelliJ. You can check the use auto-import
checkbox just press "OK".

After that IntelliJ will take some time to scan your project and set everything up accordingly.

Now you can open the `Library.scala` file in IntelliJ by double clicking it in the project view (left).
You will then see the files content in your main view. There is a yellow notice on the top which tells
you to install the Scala plugin for IntelliJ if you don't have it yet. Install that and restart IntelliJ
(just follow the dialogs).

With a restarted IntelliJ you can see new yellow hints on the top of your main view. Click those and
setup your Scala SDK and your Java JDK. If you have none, use the configure button to add them to IntelliJ.
Again, just follow the dialigs that are popping up.

IntelliJ should automatically rebuild your project with gradle, after you have done that. If not, there
should be a dialog popping up telling you to import the changes or use auto-import.

Now we are ready to get productive and write some code!

### Calculate some numbers
We want to write a function, that adds a constant number to a list of numbers.

Lets start by writing a test first. Open the `LibrarySuite` file in the `test` directory.

Gradle created this file. Gradle thinks we want to use JUnit for testing. Gradle is stupid.
Replace the whole content of the file with this:
````scala
import org.scalatest.{FeatureSpec, FunSuite, Matchers}

class LibrarySuite extends FeatureSpec with Matchers {
  feature("someLibraryMethod that does something") {
    scenario("always true") {
      def library = new Library()

      library.someLibraryMethod() shouldBe true
    }
  }
}

```` 

This basically does the same as the code before but without any JUnit. It tests the `someLibraryMethod()`
of the `Library` class and checks if it returns `true`. 

We will add a new test, that will call a function `addConstantNumber()` from the `Library` class:
````scala
...
  feature("add a constant number to a list of numbers") {
    scenario("add 42 to a list of 10 numbers") {
      def library = new Library()

      library.addConstantNumber(42, List(1, 2, 42)) shouldBe List(43, 44, 84)
    }
  }
...
````

IntelliJ will mark you some stuff in a bright red color. Click on `addConstantNumber` and press
`alt + enter`. This will open a menu. Just click `enter` again to create the `addConstantNumber`
function in the `Library` class.

Now just name the parameters and try to make the test green. You can execute the test by clicking
on the test function in the `LibrarySuite` class and pressing `ctrl + shift + f10`.

**_!!!SPOILER ALERT!!!_**

Your implementation should look something like this:

````scala
def addConstantNumber(constantNumber: Int, numberList: List[Int]): List[Int] = {
  numberList.map(number => number + constantNumber)
}
````
Map maps every element of the list to a new value. The new value is the old value plus our constant
number.

Now lets add a new scenario to our feature test which will add a lot of numbers:

````scala
...
    scenario("add 42 to a list of 3000000 numbers") {
      def library = new Library()

      library.addConstantNumber(42, (1 to 3000000).toList) shouldBe (43 to 3000042).toList
    }
...
````
This will add 42 to 3,000,000 numbers. The test should take more than a second to compute,
depending on your pc. The run view of IntelliJ that opens when you execute the test should
have a number next to the test which will tell you how long it took to execute the test.

### Lets go parallel!
The same task could be done in parallel. For that the numbers can be divided in smaller
chunks of numbers and to those smaller chunks the constant number can be added concurrently.

For that we start again by adding a new feature to the `LibrarySuite` test:
````scala
  feature("parallelly add a constant number to a list of numbers") {
    scenario("add 42 to a list of 10 numbers") {
      def library = new Library()

      library.addConstantNumberParallel(42, List(1, 2, 42)) shouldBe List(43, 44, 84)
    }

    scenario("add 42 to a list of 100000 numbers") {
      def library = new Library()

      library.addConstantNumberParallel(42, (1 to 3000000).toList) shouldBe (43 to 3000042).toList
    }
  }
````
Notice that we have to create a new function called `addConstantNumberParallel`!

Add a new function to the `Library` class:
````scala
  def addConstantNumberParallel(constantNumber: Int, numberList: List[Int]): ParSeq[Int] = {
    numberList.par.map(number => number + constantNumber)
  }
````
By calling `.par` on `numberList` we create a parallel list, that can do parallel
computations on the list like we want. This is all you have to change to introduce parallel
computing to this kind of exercise.

You should execute the whole `LibrarySuite` and check out the time it takes to execute each test.    

My setting showed speed ups of up to 2 for the parallel computation.

## Links
* https://alvinalexander.com/scala/scala-parallel-programming-collections-par-performance
