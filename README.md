# BibGen

![BibGen](bibgen.png)</br>

## Description
   BibGen is the acronym of Gerenciador de Biblioteca (Library Management, in english).
   This desktop application must give the librarian operations over the library such as:
   
   * Add, update and remove users and books in the system
   * Search through the users and books entries.
   * Allow users to rent, renew, return and schedule books.
   * Print daily reports. 

   The goal of this project is to obtain grades in Software Development Process' class.
    
## Requirements
All the required libs are present in the lib folder, add them in the classpath.
 If you'd rather have them added as dependency add the following in your pom.xml,
  however the oxyde theme still needs to be added to your classpath.
```xml
<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-entitymanager</artifactId>
    <version>5.0.1.Final</version>
</dependency>
```

```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.41</version>
</dependency>
```

```xml
<!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.4.1</version>
</dependency>
```

## Compile and run
This application is completely dependent of a data source (MySQL), therefore be sure
the MySQL service is up and running.

In the persistence.xml you will have to fill the values for MYSQL-PORT, MYSQL-USER,
MYSQL-PASSWORD, in the lines 22, 24 and 26 respectively. (default MySQL port is 3308).

After all the minimum required configuration, you can execute the code in the IDE of 
your choice. The application entry-point is the MainWindow class.

The application will generate all required tables automatically, however no entries
will be inserted by default. As such, after the first run you must manually go to MySQL
command line and insert a entry in the librarian panel.

```mysql
use bibgendb;
INSERT INTO librarian values ("admin", "admin");
```