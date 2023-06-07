# Api Task

## Description
The aim of the project was to create a simple server that would allow downloading information about repositories from github repositories for a given user. The application was written using the Spring boot framework. It issues an endpoint which returns a list of repositories for a given user. For each repository, it also returns a list of branches and the last commit made within a given branch.

## Installation 
Java version 11 and Gradle is required to run the application.


Project download:
1. Clone a Git repository with the command: git clone https://github.com/CezaryKretkowski/ApiTask.git
2. You can also download the project as a ZIP archive from the project's GitHub page.
3. Open  ApiTask folder in terminal.
4. Run command.
```cmd
   gradle build
```
5. Next go to build/libs.
```cmd
   cd build/libs
```
6. Run application.
```cmd
  java -jar ApiTask-0.0.1-SNAPSHOT.jar 
```

## Example of use.

To test the programs you need an external application that allows you to perform http queries like postman.
The query body looks like this:
```
http://localhost:8080/repositories/{username}
```
In the query header, pass the accept parameter "application/json"
for "application/json" header, server will return code 406.

![Cannot open fille ](ExampleOfUse.png)
