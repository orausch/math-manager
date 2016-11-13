# Math Manager
A Swing based math problem generation program. 

## Features
 * Generate various math problems randomly
 * Store problems in a database
 * Create tests using generated problems

## Building from source with Maven
The source code can be built using [Apache Maven] (https://maven.apache.org/). After Maven is installed, simply navigate to the project root and build using:
```
$ mvn clean compile assembly:single
```
The built jar will be located in `target/`

## Dependencies
 * [h2database](https://github.com/h2database/h2database): a fast embedded Java SQL database
 * [jIconFont](http://jiconfont.github.io/): a simple iconfont implementation library
 * [material-design-icons](https://github.com/google/material-design-icons): the iconset made by Google for Android
