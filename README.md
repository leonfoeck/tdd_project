# Mensa Model with TDD

This repository contains the template for the task.
We provide a [Maven](https://maven.apache.org) project that you can import easily into your IDE.
The project provides several defaults:
- The necessary dependencies:
  * for the implementation, we provide you Google's [Guava](https://github.com/google/guava) library
  * for testing, we provide you [JUnit 5](https://junit.org), [Mockito](https://mockito.org), and
    [Google Truth](https://truth.dev).
- The Maven plugins to compile the code and run the tests, plus measuring coverage with 
  [JaCoCo](https://www.jacoco.org).  Additionally, we provide the [PITest](https://pitest.org)
  plugin for mutation testing. (If you want to use the Google Java Code Style and its automated
  formatter, feel free to uncomment the respective plugin in the `pom.xml`, too.)
- The source skeleton for the implementation is in the folder `src/`.
- The place to put your tests is the `test/` folder hierarchy.
- If you want or need to utilise resources for your test, we provide the `test-resources/` folder
  as the preconfigured test-resources location.

Please note: JaCoCo by default computes coverage for all classes in the `src` tree.
For grading, however, we do restrict our coverage computation to only those classes you need to
implement.

Please check the assignment description on [Artemis](https://artemis.fim.uni-passau.de) for details
and an extensive task description.