# Math Manager
A Swing based math problem and test generation program.
[Screenshots can be found here](screenshots/screenshots.md).

## Features
 * Generate various math problems randomly or using parameters
 * View problems in a question browser
 * Store problems in a database
 * Create tests using stored problems
 * Export tests in PDF format. [Example](screenshots/export.pdf)

## Building from source with Maven
The source code can be built using [Apache Maven](https://maven.apache.org/). After Maven is installed, simply navigate to the project root and build using:
```
$ mvn clean compile assembly:single
```
The built jar will be located in `target/`

Run tests with
```
mvn test
```

## Dependencies
 * [h2database](https://github.com/h2database/h2database): a fast embedded Java SQL database
 * [jIconFont](http://jiconfont.github.io/): a simple iconfont implementation library
 * [material-design-icons](https://github.com/google/material-design-icons): the iconset made by Google for Android
