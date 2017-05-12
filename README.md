# BibGen

![BibGen](bibgen.png)</br>

## Description
   BibGen is a acronym of Gerenciador de Bilioteca (Library Management, in english) 
    it is made using Java, Swing and Hibernate.
   
   This desktop application must give the librarian operations over the library such as:
   
   * Add, update and remove users and books to the system
   * Search through the users and books entries.
   * Allow users to rent, renew, return and schedule a rent of books.
   * Print daily reports 

   The goal of this project is to obtain grades in Software Development Process' class.
    
## Packages
* Entities

    
    * Book
    * User
    * Librarian
    * UsersBook
    * ScheduleBook
    
* GUI 
    
   
    * Main Window - JFrame and the main method
    * Panel Manager - The handler of the Login and Application JPanels
    * Librarian Panel - JPanel which handles all subpanels after the login
    * Many subpanels
    
* Util

    The utilities for entities and components such as: users, books, tables, columns and hibernate connection.
